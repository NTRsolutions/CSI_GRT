package com.growatt.shinephone.bean.ossv2;

import java.util.List;

/**
 * Created：2018/1/17 on 16:35
 * Author:gaideng on dg
 * Description:Oss用户管理实体
 */

public class OssPlantManagerBean {
    private int ind;
    /**
     * 多个服务器列表用户数据
     */
    private List<Pager> pagers;

    /**
     * 单个服务器数据
     */
    public static class Pager{
        /**
         * 总数量
         */
        private String total;
        /**
         * 总页面
         */
        private String pages;
        /**
         * 当前页面
         */
        private String offset;
        /**
         * 每页数量
         */
        private String size;
        /**
         * 服务器id
         * 1: server.growatt.com
         2: server-cn.growatt.com
         3: server.smten.com
         4: zt.growatt.com
         5: m.solarqt.com:8080
         9:本机
         */
        private int serverId;
        /**
         * 数据列表
         */
        private List<Data> datas;

        /**
         * 用户单个数据
         */
        public static class Data{
            /**用户名*/
            private String accountName;
            /**安装时间*/
            private String creatDate;
            /**邮箱*/
            private String email;
            /**别名*/
            private String alias;
            /**真实姓名*/
            private String activeName;
            /**用户id*/
            private String uId;
            /**安装商代码*/
            private String iCode;
            /**手机号*/
            private String phoneNum;
            /**serverId*/
            private int serverDataId;
            /**设备数量*/
            private String deviceCount;
            /**电站id*/
            private String pId;
            /**时区*/
            private String timezone;
            /**设计功率*/
            private String nominal_power;
            /**电站名*/
            private String plantName;
            /**城市*/
            private String city;
            /**国家*/
            private String country;

            public String getAccountName() {
                return accountName;
            }

            public void setAccountName(String accountName) {
                this.accountName = accountName;
            }

            public String getCreatDate() {
                return creatDate;
            }

            public void setCreatDate(String creatDate) {
                this.creatDate = creatDate;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getAlias() {
                return alias;
            }

            public void setAlias(String alias) {
                this.alias = alias;
            }

            public String getActiveName() {
                return activeName;
            }

            public void setActiveName(String activeName) {
                this.activeName = activeName;
            }

            public String getuId() {
                return uId;
            }

            public void setuId(String uId) {
                this.uId = uId;
            }

            public String getiCode() {
                return iCode;
            }

            public void setiCode(String iCode) {
                this.iCode = iCode;
            }

            public String getPhoneNum() {
                return phoneNum;
            }

            public void setPhoneNum(String phoneNum) {
                this.phoneNum = phoneNum;
            }

            public int getServerDataId() {
                return serverDataId;
            }

            public void setServerDataId(int serverDataId) {
                this.serverDataId = serverDataId;
            }

            public String getDeviceCount() {
                return deviceCount;
            }

            public void setDeviceCount(String deviceCount) {
                this.deviceCount = deviceCount;
            }

            public String getpId() {
                return pId;
            }

            public void setpId(String pId) {
                this.pId = pId;
            }

            public String getTimezone() {
                return timezone;
            }

            public void setTimezone(String timezone) {
                this.timezone = timezone;
            }

            public String getNominal_power() {
                return nominal_power;
            }

            public void setNominal_power(String nominal_power) {
                this.nominal_power = nominal_power;
            }

            public String getPlantName() {
                return plantName;
            }

            public void setPlantName(String plantName) {
                this.plantName = plantName;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getPages() {
            return pages;
        }

        public void setPages(String pages) {
            this.pages = pages;
        }

        public String getOffset() {
            return offset;
        }

        public void setOffset(String offset) {
            this.offset = offset;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public int getServerId() {
            return serverId;
        }

        public void setServerId(int serverId) {
            this.serverId = serverId;
        }

        public List<Data> getDatas() {
            return datas;
        }

        public void setDatas(List<Data> datas) {
            this.datas = datas;
        }
    }

    public int getInd() {
        return ind;
    }

    public void setInd(int ind) {
        this.ind = ind;
    }

    public List<Pager> getPagers() {
        return pagers;
    }

    public void setPagers(List<Pager> pagers) {
        this.pagers = pagers;
    }
}
