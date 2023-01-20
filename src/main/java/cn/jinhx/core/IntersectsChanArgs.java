package cn.jinhx.core;

import io.lettuce.core.protocol.CommandArgs;

import java.util.Hashtable;
import java.util.Map;

/**
 * 采用与指定区域相交的地理围栏
 * @author y-z-f
 * @since 2023/1/20
 */
public class IntersectsChanArgs extends IntersectsArgs {
    /**
     * 附加元数据
     */
    private Map<String, String> meta = new Hashtable<>();
    /**
     * 超时时间
     */
    private Long ex;
    /**
     * 监听的集合名称
     */
    private String key;

    /**
     * 添加元数据
     */
    public IntersectsChanArgs addMeta(String key, String val) {
        this.meta.put(key, val);
        return this;
    }

    /**
     * 设置超时时间，单位秒
     */
    public IntersectsChanArgs ex(long ex) {
        this.ex = ex;
        return this;
    }

    /**
     * 设置监听的数据集合名称
     */
    public IntersectsChanArgs key(String key) {
        this.key = key;
        return this;
    }

    @Override
    public <K, V> void build(CommandArgs<K, V> commandArgs) {
        super.fence();
        for (Map.Entry<String, String> item : meta.entrySet()) {
            commandArgs.add("META").add(item.getKey()).add(item.getValue());
        }
        if (ex != null) {
            commandArgs.add("EX").add(ex);
        }
        commandArgs.add("INTERSECTS").add(key);
        super.build(commandArgs);
    }
}
