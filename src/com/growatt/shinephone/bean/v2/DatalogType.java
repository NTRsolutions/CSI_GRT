package com.growatt.shinephone.bean.v2;

/**
 * Created：2017/12/9 on 10:01
 * Author:gaideng on dg
 * Description:采集器类型
 */

public enum DatalogType {
    WEBBOX {
        public String getIndicate() {
            return "0";
        }

        public String getText() {
            return "Shine WebBox";
        }
    },
    PANO {
        public String getIndicate() {
            return "1";
        }

        public String getText() {
            return "Shine Pano";
        }
    },
    WIFI {
        public String getIndicate() {
            return "2";
        }

        public String getText() {
            return "ShineWifiBox";
        }
    },
    SHINE_NET {
        public String getIndicate() {
            return "3";
        }

        public String getText() {
            return "ShineNet";
        }
    },
    WIFI_LINE {
        public String getIndicate() {
            return "5";
        }

        public String getText() {
            return "ShineLan";
        }
    },
    SHINE_WIFI {
        public String getIndicate() {
            return "6";
        }

        public String getText() {
            return "ShineWIFI";
        }
    },
    SHINE_3G {
        public String getIndicate() {
            return "7";
        }

        public String getText() {
            return "Shine3G";
        }
    },
    SHINE_GPRS {
        public String getIndicate() {
            return "8";
        }

        public String getText() {
            return "ShineGPRS";
        }
    },
    SHINE_LANBOX {
        public String getIndicate() {
            return "9";
        }

        public String getText() {
            return "ShineLanBox";
        }
    },
    SHINE_RFStick {
        public String getIndicate() {
            return "10";
        }

        public String getText() {
            return "ShineRFStick";
        }
    },
    SHINE_WIFI_S {
        public String getIndicate() {
            return "11";
        }

        public String getText() {
            return "ShineWIFI-S";
        }
    },
}
