package com.growatt.shinephone.util.v2;


import com.growatt.shinephone.bean.ossv2.OssPlantManagerBean;
import com.growatt.shinephone.bean.ossv2.OssUserManagerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created：2018/1/22 on 16:39
 * Author:gaideng on dg
 * Description:Oss集成商用户管理工具类
 */

public class OssUserManagerUtils {
    /**
     * 设置用户所属url
     * @param bean
     * @return
     */
    public static OssUserManagerBean setUserUrl(OssUserManagerBean bean){
        if (bean == null) return bean;
        List<OssUserManagerBean.Pager> pagers = bean.getPagers();
        if (pagers != null){
            for (OssUserManagerBean.Pager pager:pagers) {
                int serverId = pager.getServerId();
                List<OssUserManagerBean.Pager.Data> datas = pager.getDatas();
                if (datas != null){
                    for (OssUserManagerBean.Pager.Data data:datas) {
                        data.setServerDataId(serverId);
                    }
                }
            }
        }
        return bean;
    }

    /**
     * 合并多服务器数据
     * 用户管理
     * @param bean
     * @return
     */
    public static List<OssUserManagerBean.Pager.Data> getAllUser(OssUserManagerBean bean){
        if (bean == null) return null;
        List<OssUserManagerBean.Pager> pagers = bean.getPagers();
        List<OssUserManagerBean.Pager.Data> newList = new ArrayList<>();
        if (pagers != null){
            for (OssUserManagerBean.Pager pager:pagers) {
                int serverId = pager.getServerId();
                List<OssUserManagerBean.Pager.Data> datas = pager.getDatas();
                if (datas != null){
                    for (OssUserManagerBean.Pager.Data data:datas) {
                        data.setServerDataId(serverId);
                    }
                    newList.addAll(datas);
                }
            }
        }
        return newList;
    }
    /**
     * 合并多服务器数据
     * 电站管理
     * @param bean
     * @return
     */
    public static List<OssPlantManagerBean.Pager.Data> getAllUser(OssPlantManagerBean bean){
        if (bean == null) return null;
        List<OssPlantManagerBean.Pager> pagers = bean.getPagers();
        List<OssPlantManagerBean.Pager.Data> newList = new ArrayList<>();
        if (pagers != null){
            for (OssPlantManagerBean.Pager pager:pagers) {
                int serverId = pager.getServerId();
                List<OssPlantManagerBean.Pager.Data> datas = pager.getDatas();
                if (datas != null){
                    for (OssPlantManagerBean.Pager.Data data:datas) {
                        data.setServerDataId(serverId);
                    }
                    newList.addAll(datas);
                }
            }
        }
        return newList;
    }
}
