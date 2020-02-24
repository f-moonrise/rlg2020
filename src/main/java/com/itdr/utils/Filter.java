package com.itdr.utils;

import java.io.UnsupportedEncodingException;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/19 16:27
 */
public class Filter {

    /**
     * 乱码过滤器
     * @param code
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String MessyCode(String code) throws UnsupportedEncodingException {
        if(code!=null) {
            byte[] bs = code.getBytes("ISO-8859-1");

            for (int i = 0; i < bs.length; i++) {
                byte b = bs[i];
                if (b == 63) {
                    break;
                } else if (b > 0) {
                    continue;
                } else if (b < 0) {
                    code = new String(bs, "UTF-8");
                    break;
                }
            }
        }
        return code;
    }
}
