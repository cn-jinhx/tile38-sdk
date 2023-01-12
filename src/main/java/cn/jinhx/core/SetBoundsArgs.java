package cn.jinhx.core;

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
public class SetBoundsArgs implements CompositeArgument {
    /**
     * 数据类型
     */
    private ElementType elementType = ElementType.BOUNDS;
    /**
     * 西南角经度
     */
    private double minLng;
    /**
     * 西南角纬度
     */
    private double minLat;
    /**
     * 东北角经度
     */
    private double maxLng;
    /**
     * 东北角纬度
     */
    private double maxLat;
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
     * 设置西南角经度
     */
    public SetBoundsArgs minLng(double minLng) {
        this.minLng = minLng;
        return this;
    }

    /**
     * 设置西南角纬度
     */
    public SetBoundsArgs minLat(double minLat) {
        this.minLat = minLat;
        return this;
    }

    /**
     * 设置东北角经度
     */
    public SetBoundsArgs maxLng(double maxLng) {
        this.maxLng = maxLng;
        return this;
    }

    /**
     * 设置东北角纬度
     */
    public SetBoundsArgs maxLat(double maxLat) {
        this.maxLat = maxLat;
        return this;
    }

    /**
     * 设置超时时间，单位秒
     */
    public SetBoundsArgs ex(long ex) {
        this.ex = ex;
        return this;
    }

    /**
     * 仅在不存在的情况下创建
     */
    public SetBoundsArgs nx() {
        this.nx = true;
        return this;
    }

    /**
     * 仅在点存在的情况下创建
     */
    public SetBoundsArgs xx() {
        this.xx = true;
        return this;
    }

    /**
     * 添加附加字段
     * @param key 字段名
     * @param value 字段值
     */
    public SetBoundsArgs add(String key, String value) {
        LettuceAssert.notEmpty(key, "Key must not be empty");

        fields.put(key, value);
        return this;
    }

    /**
     * 删除附件字段
     * @param key 字段名
     */
    public SetBoundsArgs del(String key) {
        LettuceAssert.notEmpty(key, "Key must not be empty");

        fields.remove(key);
        return this;
    }

    /**
     * {@link SetBoundsArgs} 构建器
     */
    public static class Builder {
        private Builder() {}

        /**
         * 创建 {@link SetBoundsArgs} 对象
         * @param minLng 西南角经度
         * @param minLat 西南角纬度
         * @param maxLng 东北角经度
         * @param maxLat 东北角纬度
         */
        public static SetBoundsArgs bounds(double minLng, double minLat, double maxLng, double maxLat) {
            return new SetBoundsArgs().minLng(minLng).minLat(minLat).maxLng(maxLng).maxLat(maxLat);
        }

        /**
         * 创建 {@link SetBoundsArgs} 对象
         * @param minLng 西南角经度
         * @param minLat 西南角纬度
         * @param maxLng 东北角经度
         * @param maxLat 东北角纬度
         * @param ex 超时时间，单位秒
         */
        public static SetBoundsArgs ex(double minLng, double minLat, double maxLng, double maxLat, long ex) {
            return new SetBoundsArgs().minLng(minLng).minLat(minLat).maxLng(maxLng).maxLat(maxLat).ex(ex);
        }

        /**
         * 仅在数据不存在的情况下创建数据的 {@link SetBoundsArgs} 对象
         * @param minLng 西南角经度
         * @param minLat 西南角纬度
         * @param maxLng 东北角经度
         * @param maxLat 东北角纬度
         */
        public static SetBoundsArgs nx(double minLng, double minLat, double maxLng, double maxLat) {
            return new SetBoundsArgs().minLng(minLng).minLat(minLat).maxLng(maxLng).maxLat(maxLat).nx();
        }

        /**
         * 仅在数据存在的情况下创建数据的 {@link SetBoundsArgs} 对象
         * @param minLng 西南角经度
         * @param minLat 西南角纬度
         * @param maxLng 东北角经度
         * @param maxLat 东北角纬度
         */
        public static SetBoundsArgs xx(double minLng, double minLat, double maxLng, double maxLat) {
            return new SetBoundsArgs().minLng(minLng).minLat(minLat).maxLng(maxLng).maxLat(maxLat).xx();
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

        commandArgs.add(elementType.getType()).add(minLat).add(minLng).add(maxLat).add(maxLng);
    }
}
