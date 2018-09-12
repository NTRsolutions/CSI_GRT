package com.growatt.shinephone.bean.max;

/**
 * Created：2018/2/6 on 14:07
 * Author:gaideng on dg
 * Description:Max一键诊断  电网线路阻抗实体
 */

public class MaxCheckOneKeyRSTBean {
    /**
     * 图片颜色id
     */
    private int imgColorId;
    /**
     * 文本颜色id
     */
    private int textColorId;
    /**
     * 是否选中
     */
    private boolean isSelect = true;
    /**
     * 标题
     */
    private String title;
    /**
     * 值
     */
    private String value;
    /**
     * 单位
     */
    private String unit;

    public int getImgColorId() {
        return imgColorId;
    }

    public void setImgColorId(int imgColorId) {
        this.imgColorId = imgColorId;
    }

    public int getTextColorId() {
        return textColorId;
    }

    public void setTextColorId(int textColorId) {
        this.textColorId = textColorId;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
