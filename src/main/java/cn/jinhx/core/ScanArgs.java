package cn.jinhx.core;

import cn.jinhx.core.model.*;
import io.lettuce.core.CompositeArgument;
import io.lettuce.core.protocol.CommandArgs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于检索集合中符合要求的对象
 * @author y-z-f
 * @since 2023/1/20
 */
public class ScanArgs implements CompositeArgument {
    /**
     * 游标位置
     */
    private Integer cursor;
    /**
     * 设定输出的数据条数，默认输出100条
     */
    private Integer limit;
    /**
     * 用于数据标识字段的过滤条件，非对应的值
     */
    private final List<String> match = new ArrayList<>();
    /**
     * 排序规则
     */
    private boolean asc = false;
    /**
     * 排序规则
     */
    private boolean desc = false;
    /**
     * 筛选过滤条件
     */
    private final List<String> where = new ArrayList<>();
    /**
     * 类似in的范围过滤条件
     */
    private final Map<String, List<String>> wherein = new HashMap<>();
    /**
     * 支持Lua脚本的过滤条件
     */
    private final List<String> whereEval = new ArrayList<>();
    /**
     * 结果不包含Field
     */
    private boolean noFields = false;
    /**
     * 数据输出类型
     */
    private OutputFormat outputFormat;
    /**
     * 针对Outputformat类型是HASHES附加参数
     */
    private long precision;

    /**
     * 游标位置
     */
    public ScanArgs cursor(int val) {
        this.cursor = val;
        return this;
    }

    /**
     * 设定输出的数据条数，默认输出100条
     */
    public ScanArgs limit(int val) {
        this.limit = val;
        return this;
    }

    /**
     * 用于数据标识字段的过滤条件，非对应的值
     */
    public ScanArgs addMatch(String val) {
        this.match.add(val);
        return this;
    }

    public ScanArgs asc() {
        this.asc = true;
        return this;
    }

    public ScanArgs desc() {
        this.desc = true;
        return this;
    }

    /**
     * 筛选过滤条件
     */
    public ScanArgs addWhere(String val) {
        this.where.add(val);
        return this;
    }

    /**
     * 类似in的范围过滤条件
     */
    public ScanArgs addWherein(String field, List<String> inval) {
        this.wherein.put(field, inval);
        return this;
    }

    /**
     * 支持Lua脚本的过滤条件
     */
    public ScanArgs addWhereEval(String val) {
        this.whereEval.add(val);
        return this;
    }

    /**
     * 结果不包含Field
     */
    public ScanArgs noFields() {
        this.noFields = true;
        return this;
    }

    /**
     * 设置数据输出格式
     */
    public ScanArgs outputFormat(OutputFormat val, long precision) {
        this.outputFormat = val;
        this.precision = precision;
        return this;
    }

    /**
     * 设置数据输出格式
     */
    public ScanArgs outputFormat(OutputFormat val) {
        return this.outputFormat(val, 0);
    }

    /**
     * {@link ScanArgs} 构建器
     */
    public static class Builder {
        private Builder() {}

        public static ScanArgs where(String ...where) {
            ScanArgs args = new ScanArgs();
            for (String item : where) {
                args.addWhere(item);
            }
            return args;
        }

        public static ScanArgs whereIn(String key, List<String> value) {
            return new ScanArgs().addWherein(key, value);
        }
    }

    @Override
    public <K, V> void build(CommandArgs<K, V> commandArgs) {
        if (cursor != null) {
            commandArgs.add("CURSOR").add(cursor);
        }
        if (limit != null) {
            commandArgs.add("LIMIT").add(limit);
        }
        for (String item : match) {
            commandArgs.add("MATCH").add(item);
        }
        if (asc) {
            commandArgs.add("ASC");
        }
        if (desc) {
            commandArgs.add("DESC");
        }
        for (String item : where) {
            commandArgs.add("WHERE").add(item);
        }
        for (Map.Entry<String, List<String>> item : wherein.entrySet()) {
            commandArgs.add("WHEREIN").add(item.getKey());
            for (String value : item.getValue()) {
                commandArgs.add(value);
            }
        }
        for (String item : whereEval) {
            commandArgs.add("WHEREEVAL").add(item);
        }
        if (noFields) {
            commandArgs.add("NOFIELDS");
        }
        commandArgs.add(outputFormat.getType());
        if (outputFormat == OutputFormat.HASHES) {
            commandArgs.add(precision);
        }
    }
}
