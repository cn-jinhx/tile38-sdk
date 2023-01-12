package cn.jinhx.core;

import io.lettuce.core.CompositeArgument;
import io.lettuce.core.internal.LettuceAssert;
import io.lettuce.core.protocol.CommandArgs;

import java.util.Hashtable;
import java.util.Map;

/**
 * PSet附加字段参数
 * @author y-z-f
 * @date 2023/1/8
 */
public class FieldArgs implements CompositeArgument {
    private Map<String, String> fields = new Hashtable<>();

    /**
     * 添加附加字段
     * @param key 字段名
     * @param value 字段值
     */
    public FieldArgs add(String key, String value) {
        LettuceAssert.notEmpty(key, "Key must not be empty");

        fields.put(key, value);
        return this;
    }

    /**
     * 删除附件字段
     * @param key 字段名
     */
    public FieldArgs del(String key) {
        LettuceAssert.notEmpty(key, "Key must not be empty");

        fields.remove(key);
        return this;
    }

    /**
     * {@link FieldArgs} 构建器
     */
    public static class Builder {
        private Builder() {}

        /**
         * 创建 {@link FieldArgs} 对象
         *
         * @param key 字段名
         * @param value 字段值
         * @return {@link FieldArgs}
         * @see FieldArgs#add(String,String)
         */
        public static FieldArgs add(String key, String value) {
            return new FieldArgs().add(key, value);
        }

        /**
         * 创建 {@link FieldArgs} 对象
         *
         * @param key 字段名
         * @param value 字段值
         * @return {@link FieldArgs}
         * @see FieldArgs#add(String,String)
         */
        public static FieldArgs add(String key, String value, String key1, String value1) {
            return new FieldArgs().add(key, value).add(key1, value1);
        }
    }

    @Override
    public <K, V> void build(CommandArgs<K, V> commandArgs) {
        for(Map.Entry<String, String> item : fields.entrySet()) {
            commandArgs.add(item.getKey()).add(item.getValue());
        }
    }
}
