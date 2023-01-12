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
public class SetObjectArgs implements CompositeArgument {
    /**
     * 数据类型
     */
    private ElementType elementType = ElementType.OBJECT;
    /**
     * GeoJson
     */
    private String obj;
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
     * 设置GeoJson
     */
    public SetObjectArgs obj(String obj) {
        this.obj = obj;
        return this;
    }

    /**
     * 设置超时时间，单位秒
     */
    public SetObjectArgs ex(long ex) {
        this.ex = ex;
        return this;
    }

    /**
     * 仅在不存在的情况下创建
     */
    public SetObjectArgs nx() {
        this.nx = true;
        return this;
    }

    /**
     * 仅在点存在的情况下创建
     */
    public SetObjectArgs xx() {
        this.xx = true;
        return this;
    }

    /**
     * 添加附加字段
     * @param key 字段名
     * @param value 字段值
     */
    public SetObjectArgs add(String key, String value) {
        LettuceAssert.notEmpty(key, "Key must not be empty");

        fields.put(key, value);
        return this;
    }

    /**
     * 删除附件字段
     * @param key 字段名
     */
    public SetObjectArgs del(String key) {
        LettuceAssert.notEmpty(key, "Key must not be empty");

        fields.remove(key);
        return this;
    }

    /**
     * {@link SetObjectArgs} 构建器
     */
    public static class Builder {
        private Builder() {}

        /**
         * 创建 {@link SetObjectArgs} 对象
         * @param obj GeoJson
         */
        public static SetObjectArgs hash(String obj) {
            return new SetObjectArgs().obj(obj);
        }

        /**
         * 创建 {@link SetObjectArgs} 对象
         * @param obj GeoJson
         * @param ex 超时时间，单位秒
         */
        public static SetObjectArgs ex(String obj, long ex) {
            return new SetObjectArgs().obj(obj).ex(ex);
        }

        /**
         * 创建仅在数据不存在下创建数据的 {@link SetObjectArgs} 对象
         * @param obj GeoHash
         */
        public static SetObjectArgs nx(String obj) {
            return new SetObjectArgs().obj(obj).nx();
        }

        /**
         * 创建仅在数据存在下创建数据的 {@link SetObjectArgs} 对象
         * @param obj GeoHash
         */
        public static SetObjectArgs xx(String obj) {
            return new SetObjectArgs().obj(obj).xx();
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

        commandArgs.add(elementType.getType()).add(obj);
    }
}
