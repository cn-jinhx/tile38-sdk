package cn.jinhx.core;

import cn.jinhx.core.model.*;
import io.lettuce.core.CompositeArgument;
import io.lettuce.core.protocol.CommandArgs;
import io.netty.util.internal.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于查询邻近指定区域的对象
 * @author y-z-f
 * @since 2023/1/20
 */
public class NearbyArgs implements CompositeArgument {
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
     * 是否去重
     */
    private boolean distance = false;
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
     * 是否开启地理围栏，用于SETCHAN
     */
    private boolean fence = false;
    /**
     * 地理围栏关注的事件
     */
    private final ArrayList<DetectType> detect = new ArrayList<>();
    /**
     *
     */
    private String commands;
    /**
     * 数据输出类型
     */
    private OutputFormat outputFormat;
    /**
     * 针对Outputformat类型是HASHES附加参数
     */
    private long precision;
    /**
     * 圆形参数
     */
    private CircleParam circle;
    /**
     * 漫游参数
     */
    private RoamParam roam;

    /**
     * 游标位置
     */
    public NearbyArgs cursor(int val) {
        this.cursor = val;
        return this;
    }

    /**
     * 设定输出的数据条数，默认输出100条
     */
    public NearbyArgs limit(int val) {
        this.limit = val;
        return this;
    }

    /**
     * 用于数据标识字段的过滤条件，非对应的值
     */
    public NearbyArgs addMatch(String val) {
        this.match.add(val);
        return this;
    }

    /**
     * 指定去重
     */
    public NearbyArgs distance() {
        this.distance = true;
        return this;
    }

    /**
     * 筛选过滤条件
     */
    public NearbyArgs addWhere(String val) {
        this.where.add(val);
        return this;
    }

    /**
     * 类似in的范围过滤条件
     */
    public NearbyArgs addWherein(String field, List<String> inval) {
        this.wherein.put(field, inval);
        return this;
    }

    /**
     * 支持Lua脚本的过滤条件
     */
    public NearbyArgs addWhereEval(String val) {
        this.whereEval.add(val);
        return this;
    }

    /**
     * 结果不包含Field
     */
    public NearbyArgs noFields() {
        this.noFields = true;
        return this;
    }

    /**
     * 是否开启地理围栏，用于SETCHAN
     */
    public NearbyArgs fence() {
        this.fence = true;
        return this;
    }

    /**
     * 地理围栏关注的事件
     */
    public NearbyArgs detect(DetectType val) {
        this.detect.add(val);
        return this;
    }

    public NearbyArgs commands(String val) {
        this.commands = val;
        return this;
    }

    /**
     * 设置数据输出格式
     */
    public NearbyArgs outputFormat(OutputFormat val, long precision) {
        this.outputFormat = val;
        this.precision = precision;
        return this;
    }

    /**
     * 设置数据输出格式
     */
    public NearbyArgs outputFormat(OutputFormat val) {
        return this.outputFormat(val, 0);
    }

    /**
     * 圆形参数
     */
    public NearbyArgs value(CircleParam param) {
        this.circle = param;
        return this;
    }

    /**
     * 设置漫游表达式
     */
    public NearbyArgs value(RoamParam param) {
        this.roam = param;
        return this;
    }

    /**
     * {@link NearbyArgs} 构建器
     */
    public static class Builder {
        private Builder() {}

        /**
         * 根据圆形进行查询
         * @param lon 中心点坐标经度
         * @param lat 中心点坐标纬度
         * @param meters 半径，单位米
         */
        public static NearbyArgs circle(double lon, double lat, long meters) {
            return new NearbyArgs().value(new CircleParam(lat, lon, meters));
        }

        /**
         * 根据漫游进行查询
         * @param key 集合名称
         * @param pattern 表达式
         * @param meters 半径，单位米
         */
        public static NearbyArgs roam(String key, String pattern, long meters) {
            return new NearbyArgs().value(new RoamParam(key, pattern, meters));
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
        if (distance) {
            commandArgs.add("DISTANCE");
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
        if (fence) {
            commandArgs.add("FENCE");
        }
        for (DetectType item : detect) {
            if (item == detect.get(0)) {
                commandArgs.add("DETECT").add(item.getType());
                continue;
            }
            commandArgs.add("," + item.getType());
        }
        if (!StringUtil.isNullOrEmpty(commands)) {
            commandArgs.add("COMMANDS").add(commands);
        }
        commandArgs.add(outputFormat.getType());
        if (outputFormat == OutputFormat.HASHES) {
            commandArgs.add(precision);
        }
        if (circle != null) {
            commandArgs.add("POINT").add(circle.getLat()).add(circle.getLon())
                    .add(circle.getMeters());
        }
        if (roam != null) {
            commandArgs.add("ROAM").add(roam.getKey()).add(roam.getPattern()).add(roam.getMeters());
        }
    }
}
