package cn.jinhx.core;

import cn.jinhx.core.model.ElementType;
import io.lettuce.core.CompositeArgument;
import io.lettuce.core.internal.LettuceAssert;
import io.lettuce.core.protocol.CommandArgs;

import java.util.Hashtable;
import java.util.Map;

/**
 * Set附加字段参数
 * @author y-z-f
 * @date 2023/1/12
 */
public class SetPointArgs implements CompositeArgument {
    /**
     * 数据类型
     */
    private ElementType elementType = ElementType.POINT;
    /**
     * 经度
     */
    private double lng;
    /**
     * 纬度
     */
    private double lat;
    /**
     * 海拔或速度
     */
    private Double z;
    /**
     * 超时时间，单位秒
     */
    private long ex = 0;
    /**
     * 是否仅在不存在的情况下创建
     */
    private boolean nx = false;
    /**
     * 是否仅在点存在的情况下创建
     */
    private boolean xx = false;
    /**
     * 附件数据字段
     */
    private Map<String, String> fields = new Hashtable<>();

    /**
     * 设置经度
     */
    public SetPointArgs lng(double lng) {
        this.lng = lng;
        return this;
    }

    /**
     * 设置纬度
     */
    public SetPointArgs lat(double lat) {
        this.lat = lat;
        return this;
    }

    /**
     * 设置海拔或速度
     */
    public SetPointArgs z(double z) {
        this.z = z;
        return this;
    }

    /**
     * 设置超时时间，单位秒
     */
    public SetPointArgs ex(long ex) {
        this.ex = ex;
        return this;
    }

    /**
     * 仅在不存在的情况下创建
     */
    public SetPointArgs nx() {
        this.nx = true;
        return this;
    }

    /**
     * 仅在点存在的情况下创建
     */
    public SetPointArgs xx() {
        this.xx = true;
        return this;
    }

    /**
     * 添加附加字段
     * @param key 字段名
     * @param value 字段值
     */
    public SetPointArgs add(String key, String value) {
        LettuceAssert.notEmpty(key, "Key must not be empty");

        fields.put(key, value);
        return this;
    }

    /**
     * 删除附件字段
     * @param key 字段名
     */
    public SetPointArgs del(String key) {
        LettuceAssert.notEmpty(key, "Key must not be empty");

        fields.remove(key);
        return this;
    }

    /**
     * {@link SetPointArgs} 构建器
     */
    public static class Builder {
        private Builder() {}

        /**
         * 创建 {@link FieldArgs} 对象
         * @param lng 经度
         * @param lat 纬度
         */
        public static SetPointArgs lngLat(double lng, double lat) {
            return new SetPointArgs().lng(lng).lat(lat);
        }

        /**
         * 创建 {@link FieldArgs} 对象
         * @param lng 经度
         * @param lat 纬度
         * @param ex 超时时间，单位秒
         */
        public static SetPointArgs ex(double lng, double lat, long ex) {
            return new SetPointArgs().lng(lng).lat(lat).ex(ex);
        }

        /**
         * 创建仅在数据不存在下创建数据的 {@link FieldArgs} 对象
         * @param lng 经度
         * @param lat 纬度
         */
        public static SetPointArgs nx(double lng, double lat) {
            return new SetPointArgs().lng(lng).lat(lat).nx();
        }

        /**
         * 创建仅在数据存在下创建数据的 {@link FieldArgs} 对象
         * @param lng 经度
         * @param lat 纬度
         */
        public static SetPointArgs xx(double lng, double lat) {
            return new SetPointArgs().lng(lng).lat(lat).xx();
        }
    }

    @Override
    public <K, V> void build(CommandArgs<K, V> commandArgs) {
        for(Map.Entry<String, String> item : fields.entrySet()) {
            commandArgs.add("FIELD").add(item.getKey()).add(item.getValue());
        }

        if (ex != 0) {
            commandArgs.add("EX").add(ex);
        }
        if (nx == true) {
            commandArgs.add("NX");
        } else if (xx == true) {
            commandArgs.add("XX");
        }

        commandArgs.add(elementType.getType()).add(lat).add(lng);

        if (z != null) {
            commandArgs.add(z);
        }

    }
}
