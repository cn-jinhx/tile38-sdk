package cn.jinhx.core;

/**
 * 数据输出类型
 * @author y-z-f
 * @since 2023/1/16
 */
public enum OutputFormat {
    COUNT("COUNT"),
    IDS("IDS"),
    OBJECTS("OBJECTS"),
    POINTS("POINTS"),
    BOUNDS("BOUNDS"),
    HASHES("HASHES");

    OutputFormat(String format) {
        this.format = format;
    }

    private String format;

    public String getType() {
        return format;
    }

    public void setType(String format) {
        this.format = format;
    }
}
