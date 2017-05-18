package com.myself.wypqwer.zhengqi_zhilian;

/**
 * Created by 王聿鹏 on 2017/5/17.
 * <p>
 * 描述 ：
 */

public class Path {
    private static String faGai_Url="http://beidoujieshou.sytxmap.com:6500/FGWebServer.asmx";
    private static String _MapUrl = "http://ysmapservices.sytxmap.com/arcgis/rest/services/New/FaGai_Wai/MapServer";

    public static String get_faGai_Url() {

        return faGai_Url;

    }

    public static String get_MapUrl() {
        return _MapUrl;
    }
}