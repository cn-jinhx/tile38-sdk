package cn.jinhx.core;

import io.lettuce.core.protocol.CommandArgs;

import java.util.Hashtable;
import java.util.Map;

/**
 * 采用存在于指定框中的地理围栏
 * @author y-z-f
 * @since 2023/1/20
 */
public class WithinChanArgs extends WithinArgs {
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
    public WithinChanArgs addMeta(String key, String val) {
        this.meta.put(key, val);
        return this;
    }

    /**
     * 设置超时时间，单位秒
     */
    public WithinChanArgs ex(long ex) {
        this.ex = ex;
        return this;
    }

    /**
     * 设置监听的数据集合名称
     */
    public WithinChanArgs key(String key) {
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
        commandArgs.add("WITHIN").add(key);
        super.build(commandArgs);
    }
}
