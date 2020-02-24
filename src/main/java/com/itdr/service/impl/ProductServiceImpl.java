package com.itdr.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;
import com.itdr.mapper.ItdrCategoryMapper;
import com.itdr.mapper.ItdrProductMapper;
import com.itdr.pojo.ItdrCategory;
import com.itdr.pojo.ItdrProduct;
import com.itdr.pojo.vo.ProductVO;
import com.itdr.service.ProductService;
import com.itdr.utils.Filter;
import com.itdr.utils.ObjectToVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/20 18:23
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ItdrProductMapper productMapper;
    @Autowired
    ItdrCategoryMapper categoryMapper;

    @Override
    public ServerResponse<ItdrCategory> baseCategory(Integer pid) {
        //参数合法性判断
        if(pid == null || pid < 0){
            return ServerResponse.defeatedRS(
                    ConstCode.ItdrCategory.UNLAWFULENSS_PARAM.getCode(),
                    ConstCode.ItdrCategory.UNLAWFULENSS_PARAM.getDesc()
                    );
        }

        //根据父id查找直接子类
        List<ItdrCategory> li = categoryMapper.selectByParentID(pid);

        //返回成功数据
        return ServerResponse.successRS(li);
    }

    @Override
    public ServerResponse<ProductVO> detail(Integer productId) {
        System.out.println(productId);

        //参数合法性判断
        if(productId == null || productId < 0){
            return ServerResponse.defeatedRS(
                    ConstCode.ItdrCategory.UNLAWFULENSS_PARAM.getCode(),
                    ConstCode.ItdrCategory.UNLAWFULENSS_PARAM.getDesc()
            );
        }


        //根据父id查找直接子类
        ItdrProduct product = productMapper.selectByPrimaryKey(productId);
        if(product == null || product.getPnum() <= 0){
            return ServerResponse.defeatedRS(
                    ConstCode.ItdrCategory.INEXISTENCE_PRODUCT.getCode(),
                    ConstCode.ItdrCategory.INEXISTENCE_PRODUCT.getDesc()
                    );
        }

        //封装VC
        ProductVO productVO = ObjectToVOUtil.ProductToUserVO(product);

        //返回成功数据
        return ServerResponse.successRS(productVO);
    }

    @Override
    public ServerResponse<ProductVO> list(String word) throws UnsupportedEncodingException {

        word = Filter.MessyCode(word);
        System.out.println(word);

        //参数非空判断
        if(StringUtils.isEmpty(word)){
            return ServerResponse.defeatedRS(
                    ConstCode.ItdrCategory.EMPTY_STR.getCode(),
                    ConstCode.ItdrCategory.EMPTY_STR.getDesc()
            );
        }

        //模糊查询数据
        String keyWord = "%" + word + "%";
        List<ItdrProduct> li = productMapper.selectByName(keyWord);

        //分装VO
        List<ProductVO> liNew = new ArrayList<ProductVO>();
        for(ItdrProduct product : li){
            ProductVO pvo = ObjectToVOUtil.ProductToUserVO(product);
            liNew.add(pvo);
        }

        //返回成功数据
        return ServerResponse.successRS(liNew);
    }

    @Override
    public ServerResponse<ProductVO> list(String word, Integer pageNum, Integer pageSize, String orderBy) throws UnsupportedEncodingException {

        word = Filter.MessyCode(word);
        System.out.println(word);

        //参数非空判断
        if(StringUtils.isEmpty(word)){
            return ServerResponse.defeatedRS(
                    ConstCode.ItdrCategory.EMPTY_STR.getCode(),
                    ConstCode.ItdrCategory.EMPTY_STR.getDesc()
            );
        }

        //模糊查询数据
        String keyWord = "%" + word + "%";

        //分页排序参数处理
        String[] split = new String[2];
        if(!StringUtils.isEmpty(orderBy)){
            split = orderBy.split("_");
            PageHelper.startPage(pageNum,pageSize,split[0]+" "+split[1]);
        }else{
            PageHelper.startPage(pageNum,pageSize);
        }


        //开启分页
        List<ItdrProduct> li = productMapper.selectByName(keyWord);
        PageInfo pageInfo = new PageInfo(li);

        //分装VO
        List<ProductVO> liNew = new ArrayList<ProductVO>();
        for(ItdrProduct product : li){
            ProductVO pvo = ObjectToVOUtil.ProductToUserVO(product);
            liNew.add(pvo);
        }

        pageInfo.setList(liNew);


        //返回成功数据
        return ServerResponse.successRS(pageInfo);
    }
}
