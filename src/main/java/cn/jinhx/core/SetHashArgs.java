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
public class SetHashArgs implements CompositeArgument {
    /**
     * 数据类型
     */
    private ElementType elementType = ElementType.HASH;
    /**
     * GeoHash
     */
    private String hash;
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
     * 设置GeoHash
     */
    public SetHashArgs hash(String hash) {
        this.hash = hash;
        return this;
    }

    /**
     * 设置超时时间，单位秒
     */
    public SetHashArgs ex(long ex) {
        this.ex = ex;
        return this;
    }

    /**
     * 仅在不存在的情况下创建
     */
    public SetHashArgs nx() {
        this.nx = true;
        return this;
    }

    /**
     * 仅在点存在的情况下创建
     */
    public SetHashArgs xx() {
        this.xx = true;
        return this;
    }

    /**
     * 添加附加字段
     * @param key 字段名
     * @param value 字段值
     */
    public SetHashArgs add(String key, String value) {
        LettuceAssert.notEmpty(key, "Key must not be empty");

        fields.put(key, value);
        return this;
    }

    /**
     * 删除附件字段
     * @param key 字段名
     */
    public SetHashArgs del(String key) {
        LettuceAssert.notEmpty(key, "Key must not be empty");

        fields.remove(key);
        return this;
    }

    /**
     * {@link SetHashArgs} 构建器
     */
    public static class Builder {
        private Builder() {}

        /**
         * 创建 {@link SetHashArgs} 对象
         * @param hash GeoHash
         */
        public static SetHashArgs hash(String hash) {
            return new SetHashArgs().hash(hash);
        }

        /**
         * 创建 {@link SetHashArgs} 对象
         * @param hash GeoHash
         * @param ex 超时时间，单位秒
         */
        public static SetHashArgs ex(String hash, long ex) {
            return new SetHashArgs().hash(hash).ex(ex);
        }

        /**
         * 创建仅在数据不存在下创建数据的 {@link SetHashArgs} 对象
         * @param hash GeoHash
         */
        public static SetHashArgs nx(String hash) {
            return new SetHashArgs().hash(hash).nx();
        }

        /**
         * 创建仅在数据存在下创建数据的 {@link SetHashArgs} 对象
         * @param hash GeoHash
         */
        public static SetHashArgs xx(String hash) {
            return new SetHashArgs().hash(hash).xx();
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

        commandArgs.add(elementType.getType()).add(hash);
    }
}
