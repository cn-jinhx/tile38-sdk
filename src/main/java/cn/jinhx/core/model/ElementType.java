package cn.jinhx.core.model;

/**
 * 坐标类型
 * @author y-z-f
 * @since 2023/1/12
 */
public enum ElementType {
    /**
     * 经纬度点
     */
    POINT("POINT"),
    /**
     * 矩形
     */
    BOUNDS("BOUNDS"),
    /**
     * GeoHash格式
     */
    HASH("HASH"),
    /**
     * GeoJson格式
     */
    OBJECT("OBJECT");

    ElementType(String type){
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
