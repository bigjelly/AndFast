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

    public final static int TYPE_PULL_REFRESH = 1;
    public final static int TYPE_LOAD_MORE = TYPE_PULL_REFRESH + 1;

    /**网络请求异常code*/
    public final static int TYPE_NET_UNAVAILABLE_CODE = 1004;
    public final static int TYPE_NET_ERROR_CODE = TYPE_NET_UNAVAILABLE_CODE+1;

    // EventBus Code
    public static final class EventCode {
        public static final int TEST = 0x8858111;
    }
}
