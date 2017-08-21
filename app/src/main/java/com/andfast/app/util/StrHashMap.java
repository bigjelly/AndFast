package com.andfast.app.util;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mby on 17-8-18.
 */

public class StrHashMap<K, V> extends HashMap {
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator iter = this.entrySet().iterator();
        sb.append("\n[ ");
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            if (!TextUtils.isEmpty(val)){
                sb.append(key + " : " + val + " ,");
            }
        }
        sb.append(" ]\n");
        return sb.toString();
    }
}
