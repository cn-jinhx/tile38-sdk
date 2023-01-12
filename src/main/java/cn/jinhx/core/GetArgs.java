package cn.jinhx.core;

import io.lettuce.core.CompositeArgument;
import io.lettuce.core.protocol.CommandArgs;

/**
 * Get附加字段参数
 * @author y-z-f
 * @since 2023/1/8
 */
public class GetArgs implements CompositeArgument {
    private boolean obj = false;
    private boolean point = false;
    private boolean bounds = false;
    private boolean hash = false;
    private long precision = 5;
    private boolean withFields = false;

    /**
     * 获取GetObject结果
     */
    public GetArgs obj() {
        this.obj = true;
        return this;
    }

    /**
     * 获取点结果
     */
    public GetArgs point() {
        this.point = true;
        return this;
    }

    /**
     * 获取矩形结果
     */
    public GetArgs bounds() {
        this.bounds = true;
        return this;
    }

    /**
     * 获取GeoHash结果
     */
    public GetArgs hash() {
        this.hash = true;
        return this;
    }

    /**
     * 设定GeoHash类型返回值的精度
     * @param precision 精度位数
     */
    public GetArgs precision(long precision) {
        this.precision = precision;
        return this;
    }

    /**
     * 包括获取附加字段
     */
    public GetArgs withFields() {
        this.withFields = true;
        return this;
    }

    public static class Builder {
        private Builder() {}

        public static GetArgs obj() {
            return new GetArgs().obj();
        }

        public static GetArgs point() {
            return new GetArgs().point();
        }

        public static GetArgs bounds() {
            return new GetArgs().bounds();
        }

        public static GetArgs hash() {
            return new GetArgs().hash();
        }

        public static GetArgs precision(long precision) {
            return new GetArgs().precision(precision);
        }

        public static GetArgs withFields() {
            return new GetArgs().withFields();
        }
    }

    @Override
    public <K, V> void build(CommandArgs<K, V> commandArgs) {
        if (obj) {
            commandArgs.add("OBJECT");
        } else if (point) {
            commandArgs.add("POINT");
        } else if (bounds) {
            commandArgs.add("BOUNDS");
        } else if (hash) {
            commandArgs.add("HASH").add(precision);
        }

        if (withFields) {
            commandArgs.add("WITHFIELDS");
        }
    }
}
