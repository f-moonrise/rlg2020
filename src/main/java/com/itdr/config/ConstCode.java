package com.itdr.config;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/18 17:59
 */
public class ConstCode {

    public static final int DEFAULT_SUCCESS = 200;
    public static final int DEFAULT_FAIL = 100;
    public static final String UNLAWFULENSS_PARAM = "非法参数";

    public enum ItdrUserEnum{
//        状态信息
        ERROR_PASSWORD(1,"密码错误"),
        EMPTY_USERNAME(2,"用户名不能为空"),
        EMPTY_PASSWORD(3,"密码不能为空"),
        EMPTY_QUESTION(4,"问题不能为空"),
        EMPTY_ANSWER(5,"答案不能为空"),
        INEXISTENCE_USER(6,"用户名不存在"),
        EXIST_USER(7,"用户名已存在"),
        EXIST_EMATL(8,"邮箱已注册"),
        EMPTY_PARAM(9,"注册信息不能为空"),
        EMPTY_PARAM2(10,"更新个人信息失败"),
        SUCCESS_USER(11,"用户注册成功"),
        SUCCESS_MSG(12,"校验成功"),
        NO_LOGIN(13,"用户未登录"),
        NO_QUESTION(14,"该用户未设置找回密码问题"),
        ERROR_ANSWER(15,"问题答案错误"),
        LOSE_EFFICACY(16,"token已经失效"),
        UNLAWFULNESS_TOKEN(17,"非法的token"),
        DEFACTED_PASSWORDNEW(18,"修改密码操作失误"),
        SUCCESS_PASSWORDNEW(19,"修改密码操作成功"),
        ERROR_PASSWORDNEW(20,"旧密码输入错误"),
        SUCCESS_USERMSG(21,"更新个人信息成功"),
        FORCE_EXIT(22,"用户未登录，无法获取当前用户信息"),
        LOGOUT(23,"退出成功"),
        FAIL_LOGIN(24,"登录失败"),
        FAIL_REGISTER(25,"注册失败"),
        FAIL_MSG(26,"校验失败"),
        EMPTY_STR(27,"参数不能为空"),
        EMPTY_USERNAMEOREMATL(28,"账户或邮箱不能为空"),
        EMPTY_TYPE(29,"类型不能为空"),
        EXIST_USERNAMEOREMATL(30,"用户名或邮箱已存在"),
        EMPTY_EMATL(31,"邮箱不能为空"),
        EMPTY_PHONE(32,"手机号不能为空"),
        ;



        private int code;
        private String desc;

        ItdrUserEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public enum ItdrCategory{
            //状态信息
            UNLAWFULENSS_PARAM(1,"非法参数"),
            INEXISTENCE_PRODUCT(2,"商品不存在"),
            EMPTY_STR(3,"参数不能为空"),
            FAIL_ADD(4,"添加商品失败"),
            BEYOND_STOCK(2,"商品不存在")
            ;

            private int code;
            private String desc;

            ItdrCategory(int code, String desc) {
                    this.code = code;
                    this.desc = desc;
            }

            public int getCode() {
                        return code;
                }

            public void setCode(int code) {
                        this.code = code;
                }

            public String getDesc() {
                        return desc;
                }

            public void setDesc(String desc) {
                        this.desc = desc;
                }
        }

    public enum CartEnum{
        //状态信息
        UNLAWFULENSS_PARAM(1,"非法参数"),
        INEXISTENCE_PRODUCT(2,"商品不存在"),
        EMPTY_CART(3,"购物车没有更多商品")
        ;

        private int code;
        private String desc;

        CartEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
