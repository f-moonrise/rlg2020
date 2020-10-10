package com.itdr.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;
import com.itdr.config.pay.Configs;
import com.itdr.mapper.ItdrOrderItemMapper;
import com.itdr.mapper.ItdrOrderMapper;
import com.itdr.mapper.ItdrPayinfoMapper;
import com.itdr.pojo.ItdrOrder;
import com.itdr.pojo.ItdrOrderItem;
import com.itdr.pojo.ItdrPayinfo;
import com.itdr.pojo.ItdrUser;
import com.itdr.service.AliPayService;
import com.itdr.utils.DateUtil;
import com.itdr.utils.JsonUtils;
import com.itdr.utils.ObjectToVOUtil;
import com.itdr.utils.ZxingUtils;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/26 18:43
 */
@Service
public class AliPayServiceImpl implements AliPayService {

    @Autowired
    ItdrOrderMapper orderMapper;
    @Autowired
    ItdrOrderItemMapper orderItemMapper;
    @Autowired
    ItdrPayinfoMapper payInfoMapper;


    @Override
    public ServerResponse pay(ItdrUser user, Integer orderNo) {
        //参数非空判断
        if(orderNo == null || orderNo < 0){
            return ServerResponse.defeatedRS(
                    ConstCode.OrderEnum.UNLAWFULENSS_PARAM.getCode(),
                    ConstCode.OrderEnum.UNLAWFULENSS_PARAM.getDesc());
        }

        //判断订单是否存在
        ItdrOrder o = orderMapper.selectByOrderNoAndUserId(orderNo,user.getId());
        if(o == null){
            return ServerResponse.defeatedRS(
                    ConstCode.OrderEnum.INEXISTENCE_ORDER.getCode(),
                    ConstCode.OrderEnum.INEXISTENCE_ORDER.getDesc());
        }

        //判断订单和用户是否匹配
        if(o.getUserid() != user.getId()){
            return ServerResponse.defeatedRS(
                    ConstCode.OrderEnum.INEXISTENCE_ORDER.getCode(),
                    ConstCode.OrderEnum.INEXISTENCE_ORDER.getDesc());
        }

        //获取订单对应的商品详情
        List<ItdrOrderItem> orderItemList = orderItemMapper.selectByOrderNoAndUserId(orderNo,user.getId());
//        System.out.println("===========================");
//        System.out.println(orderItemList);
//        System.out.println("===========================");
        //调用支付宝接口获取支付二维码
        AlipayTradePrecreateResponse response = null;
        try{
            response = test_trade_precreate(o,orderItemList);
        }catch (AlipayApiException e){
            e.printStackTrace();
            return ServerResponse.defeatedRS(ConstCode.OrderEnum.FAIL_ADD.getCode(),
                    ConstCode.OrderEnum.FAIL_ADD.getDesc());
        }

        //成功执行下一步
        if (response != null && response.isSuccess()) {
            // 将二维码信息串生成图片，并保存，（需要修改为运行机器上的路径）
            String filePath = String.format(Configs.getSavecode_test()+"qr-%s.png",
                    response.getOutTradeNo());
            ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);



            //预下单成功返回信息
            Map map = new HashMap();
            map.put("orderNo", o.getOrderNo());

            map.put("qrCode", filePath);
            return ServerResponse.successRS(map);
        }else{
            return ServerResponse.defeatedRS("下单失败！");
        }
    }


    @Override
    public ServerResponse alipayCallback(Map<String, String> map) {
        ServerResponse sr = null;

        //step1:获取ordrNo
        Integer orderNo = Math.toIntExact(Long.parseLong(map.get("out_trade_no")));
        //step2:获取流水号
        String tarde_no = map.get("trade_no");
        //step3:获取支付状态
        String trade_status = map.get("trade_status");
        //step4:获取支付时间
        String payment_time = map.get("gmt_payment");

        ItdrOrder order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            //不是要付款的订单
            sr = ServerResponse.defeatedRS("FAILED");
            return sr;
        }

        if (order.getStatus() != 10) {
            //防止支付宝重复回调
            sr = ServerResponse.defeatedRS("SUCCESS");
            return sr;
        }

        if (trade_status.equals("TRADE_SUCCESS")) {
            //校验状态码，支付成功
            //更改数据库中订单的状态+更改支付时间+更新时间+删除用过的本地二维码
            order.setStatus(20);
            order.setPaymentTime(DateUtil.strToDate(payment_time));
            orderMapper.updateByPrimaryKey(order);

            //支付成功，删除本地存在的二维码图片
            String str = String.format(Configs.getSavecode_realy()+"qr-%s.png",
                    order.getOrderNo());
            File file = new File(str);
            boolean b = file.delete();

        }

        //保存支付宝支付信息
        ItdrPayinfo payInfo = new ItdrPayinfo();
        payInfo.setOrderNo(Math.toIntExact(orderNo));
        payInfo.setPayPlatform(1);
        payInfo.setPlatformStatus(trade_status);
        payInfo.setPlatformNumber(tarde_no);
        payInfo.setUserid(order.getUserid());

        int result = payInfoMapper.insert(payInfo);
        if (result > 0) {
            //支付信息保存成功返回结果SUCCESS，让支付宝不再回调
            sr = ServerResponse.successRS("SUCCESS");
            return sr;
        }
        //支付信息保存失败返回结果
        sr = ServerResponse.defeatedRS("SUCCESS");
        return sr;
    }

    @Override
    public ServerResponse queryOrderPayStatus(ItdrUser user, Integer orderNo) {
        //参数非空判断
        if(orderNo == null || orderNo < 0){
            return ServerResponse.defeatedRS(
                    ConstCode.OrderEnum.UNLAWFULENSS_PARAM.getCode(),
                    ConstCode.OrderEnum.UNLAWFULENSS_PARAM.getDesc());
        }

        //判断订单是否存在
        ItdrOrder o = orderMapper.selectByOrderNoAndUserId(orderNo,user.getId());
        if(o == null){
            return ServerResponse.defeatedRS(
                    ConstCode.OrderEnum.INEXISTENCE_ORDER.getCode(),
                    ConstCode.OrderEnum.INEXISTENCE_ORDER.getDesc());
        }

        //判断订单状态
        if(o.getStatus() != 20){
            return ServerResponse.successRS(false);
        }
        return ServerResponse.successRS(true);
    }

    //支付宝预下单核心代码块
    private AlipayTradePrecreateResponse test_trade_precreate(ItdrOrder order, List<ItdrOrderItem> orderItems) throws AlipayApiException {
        //读取配置文件信息
        Configs.init("zfbinfo.properties");

        //实例化支付宝客户端
        AlipayClient alipayClient = new DefaultAlipayClient(Configs.getOpenApiDomain(),
                Configs.getAppid(), Configs.getPrivateKey(), "json", "utf-8",
                Configs.getAlipayPublicKey(), Configs.getSignType());

        //创建API对应的request类
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();

        //获取一个BizContent对象,并转换成json格式
        String str = JsonUtils.obj2String(ObjectToVOUtil.getBizContent(order, orderItems));
        request.setBizContent(str);
        //设置支付宝回调路径（在做回调接口的时候要打开这里并设置正确的回调路径）
        request.setNotifyUrl(Configs.getNotifyUrl_test());
        //获取响应,这里要处理一下异常
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        //返回响应的结果
        return response;
    }
}

