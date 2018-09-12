package com.growatt.shinephone.util.v2;

import com.growatt.shinephone.bean.v2.Fragment1ListBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created：2017/12/2 on 15:30
 * Author:gaideng on dg
 * Description:fragment1第二版util
 */

public class FragV2Util {
    /**
     * 封装一个修改设备信息的Map
     */
    public static Map<String,Object> getDeviceInfoMap(Fragment1ListBean bean){
        Map<String,Object> map = new HashMap<>();
        String deviceType = bean.getDeviceType();
        map.put("alias",bean.getDeviceAilas());
//        map.put("image", Environment.getExternalStorageDirectory()+"/"+ ShineApplication.IMAGE_FILE_LOCATION);
        map.put("image", "");
        map.put("type",deviceType);
        if("inverter".equals(deviceType)){
            if ("1".equals(bean.getInvType())){
                map.put("maxId",bean.getDeviceSn());
            }else if ("2".equals(bean.getInvType())){
                map.put("jlinvId",bean.getDeviceSn());
            }else {
                map.put("inverterId",bean.getDeviceSn());
            }
        }
        else if ("mix".equals(deviceType)){
            map.put("mixId",bean.getDeviceSn());
        }
        else{
            map.put("storageId",bean.getDeviceSn());
        }
        return map;
    }
}
