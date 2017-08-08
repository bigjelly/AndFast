package com.andfast.app.constant;

/**
 * Created by mby on 17-7-31.
 */

public class GeneralID {

    /**接口根地址*/
    public static final String BASE_SERVER_URL = "http://is.snssdk.com/";

    /**
     * 页面间参数传递KEY值
     */
    public class Extra {
        public static final String TAB = "tab";
    }

    public final static int TPYE_PULL_REFRESH = 1001;
    public final static int TPYE_LOAD_MORE = TPYE_PULL_REFRESH + 1;
}
