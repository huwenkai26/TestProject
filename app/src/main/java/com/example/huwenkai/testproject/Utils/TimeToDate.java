package com.example.huwenkai.testproject.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huwenkai on 2017-03-14.
 */

public class TimeToDate {



    public static String stampToDate(String cc_time) {
        String re_StrTime = null;
        //同理也可以转为其它样式的时间格式.例如："yyyy/MM/dd HH:mm"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));

        return re_StrTime;
    }
}
