package cn.jinhx.core.model;

/**
 * 地理围栏事件类型
 */
public enum DetectType {
    /**
     * 对象处在指定的区域范围，仅在fence生效时可用。
     */
    INSIDE("INSIDE"),
    /**
     * 对象处在指定的区域范围外，仅在fence生效时可用。
     */
    OUTSIDE("OUTSIDE"),
    /**
     * 对象未处在指定的范围内时，对象进入区域内时，仅在fence生效时可用。
     */
    ENTER("ENTER"),
    /**
     * 对象处在指定的范围内，离开指定的区域时，仅在fence生效时可用。
     */
    EXIT("EXIT"),
    /**
     * 对象未处在指定的区域范围内，对象进入并离开区域时，仅在fence生效时可用。
     */
    CROSS("CROSS");

    DetectType(String type){
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
