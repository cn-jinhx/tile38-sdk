package cn.jinhx.core;

import io.lettuce.core.CompositeArgument;
import io.lettuce.core.protocol.CommandArgs;

import java.util.ArrayList;
import java.util.List;

/**
 * Lua脚本参数
 * @author y-z-f
 * @since 2023/1/20
 */
public class EvalArgs implements CompositeArgument {
    /**
     * 数据集合
     */
    private List<String> keys = new ArrayList<>();

    /**
     * 参数值
     */
    private List<String> args = new ArrayList<>();

    public EvalArgs addKey(String key) {
        this.keys.add(key);
        return this;
    }

    public EvalArgs addArg(String arg) {
        this.args.add(arg);
        return this;
    }

    /**
     * {@link EvalArgs} 构建器
     */
    public static class Builder {
        private Builder() {}

        public static EvalArgs addKeys(String ...key) {
            EvalArgs args = new EvalArgs();
            for (String item : key) {
                args.addKey(item);
            }
            return args;
        }
        
        public static EvalArgs addArgs(String ...arg) {
            EvalArgs args = new EvalArgs();
            for (String item : arg) {
                args.addArg(item);
            }
            return args;
        }
    }

    @Override
    public <K, V> void build(CommandArgs<K, V> commandArgs) {
        commandArgs.add(this.keys.size());
        for (String item : this.keys) {
            commandArgs.add(item);
        }
        for (String item : this.args) {
            commandArgs.add(item);
        }
    }
}
