package cn.jinhx.core;

import cn.jinhx.core.model.*;
import cn.jinhx.geojson.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.CompositeArgument;
import io.lettuce.core.internal.LettuceAssert;
import io.lettuce.core.protocol.CommandArgs;
import io.netty.util.internal.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于查询处在指定框中的对象
 * @author y-z-f
 * @since 2023/1/20
 */
public class WithinArgs implements CompositeArgument {
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
     * 采用自身数据
     */
    private GetParam get;
    /**
     * 矩形参数
     */
    private BoundsParam bounds;
    /**
     * GeoJson参数
     */
    private String geoJson;
    /**
     * 圆形参数
     */
    private CircleParam circle;
    /**
     * 瓦片参数
     */
    private TileParam tile;
    private QuadKeyParam quadkey;
    /**
     * GeoHash
     */
    private GeoHashParam geohash;
    /**
     * 扇形参数
     */
    private SectorParam sector;

    /**
     * 游标位置
     */
    public WithinArgs cursor(int val) {
        this.cursor = val;
        return this;
    }

    /**
     * 设定输出的数据条数，默认输出100条
     */
    public WithinArgs limit(int val) {
        this.limit = val;
        return this;
    }

    /**
     * 用于数据标识字段的过滤条件，非对应的值
     */
    public WithinArgs addMatch(String val) {
        this.match.add(val);
        return this;
    }

    /**
     * 筛选过滤条件
     */
    public WithinArgs addWhere(String val) {
        this.where.add(val);
        return this;
    }

    /**
     * 类似in的范围过滤条件
     */
    public WithinArgs addWherein(String field, List<String> inval) {
        this.wherein.put(field, inval);
        return this;
    }

    /**
     * 支持Lua脚本的过滤条件
     */
    public WithinArgs addWhereEval(String val) {
        this.whereEval.add(val);
        return this;
    }

    /**
     * 结果不包含Field
     */
    public WithinArgs noFields() {
        this.noFields = true;
        return this;
    }

    /**
     * 是否开启地理围栏，用于SETCHAN
     */
    public WithinArgs fence() {
        this.fence = true;
        return this;
    }

    /**
     * 地理围栏关注的事件
     */
    public WithinArgs detect(DetectType val) {
        this.detect.add(val);
        return this;
    }

    public WithinArgs commands(String val) {
        this.commands = val;
        return this;
    }

    /**
     * 设置数据输出格式
     */
    public WithinArgs outputFormat(OutputFormat val, long precision) {
        this.outputFormat = val;
        this.precision = precision;
        return this;
    }

    /**
     * 设置数据输出格式
     */
    public WithinArgs outputFormat(OutputFormat val) {
        return this.outputFormat(val, 0);
    }

    /**
     * 设置采用存储的数据作为查询
     */
    public WithinArgs value(GetParam param) {
        LettuceAssert.notNull(param, "GetParam");
        this.get = param;
        return this;
    }

    /**
     * 矩形参数
     */
    public WithinArgs value(BoundsParam param) {
        LettuceAssert.notNull(param, "BoundsParam");
        this.bounds = param;
        return this;
    }

    /**
     * GeoJson参数
     */
    public WithinArgs value(GeoJsonObject geoJson) throws JsonProcessingException {
        String json = new ObjectMapper().writeValueAsString(geoJson);
        this.geoJson = json;
        return this;
    }

    /**
     * 圆形参数
     */
    public WithinArgs value(CircleParam param) {
        this.circle = param;
        return this;
    }

    /**
     * 瓦片参数
     */
    public WithinArgs value(TileParam param) {
        this.tile = param;
        return this;
    }

    public WithinArgs value(QuadKeyParam param) {
        this.quadkey = param;
        return this;
    }

    /**
     * GeoHash参数值
     */
    public WithinArgs value(GeoHashParam param) {
        this.geohash = param;
        return this;
    }

    /**
     * 扇形参数
     */
    public WithinArgs value(SectorParam param) {
        this.sector = param;
        return this;
    }

    /**
     * {@link IntersectsArgs} 构建器
     */
    public static class Builder {
        private Builder() {}

        /**
         * 根据已存储的数据进行检索
         */
        public static WithinArgs key(String key, String id) {
            return new WithinArgs().value(new GetParam(key, id));
        }

        /**
         * 根据矩形框进行检索
         */
        public static WithinArgs bounds(double minLon, double minLat, double maxLon, double maxLat) {
            return new WithinArgs().value(new BoundsParam(minLat, minLon, maxLat, maxLon));
        }

        /**
         * 根据单个点查询
         */
        public static WithinArgs geoJson(double lon, double lat) throws JsonProcessingException {
            return new WithinArgs().value(new Point(lon, lat));
        }

        /**
         * 根据多个点查询
         */
        public static WithinArgs geoJson(MultiPoint param) throws JsonProcessingException {
            return new WithinArgs().value(param);
        }

        /**
         * 根据线条查询
         */
        public static WithinArgs geoJson(LineString param) throws JsonProcessingException {
            return new WithinArgs().value(param);
        }

        /**
         * 根据多个线条组合查询
         */
        public static WithinArgs geoJson(MultiLineString param) throws JsonProcessingException {
            return new WithinArgs().value(param);
        }

        /**
         * 根据多边形查询
         */
        public static WithinArgs geoJson(Polygon param) throws JsonProcessingException {
            return new WithinArgs().value(param);
        }

        /**
         * 根据多边形组合查询
         */
        public static WithinArgs geoJson(MultiPolygon param) throws JsonProcessingException {
            return new WithinArgs().value(param);
        }

        /**
         * 根据异构组合查询
         */
        public static WithinArgs geoJson(GeometryCollection param) throws JsonProcessingException {
            return new WithinArgs().value(param);
        }

        /**
         * 根据圆形进行查询
         * @param lon 中心点坐标经度
         * @param lat 中心点坐标纬度
         * @param meters 半径，单位米
         */
        public static WithinArgs circle(double lon, double lat, long meters) {
            return new WithinArgs().value(new CircleParam(lat, lon, meters));
        }

        /**
         * 根据瓦片进行查询
         */
        public static WithinArgs tile(long x, long y, long z) {
            return new WithinArgs().value(new TileParam(x, y, z));
        }

        /**
         * 根据QuadKey查询
         */
        public static WithinArgs quadKey(String param) {
            return new WithinArgs().value(new QuadKeyParam(param));
        }

        /**
         * 根据GeoHash查询
         */
        public static WithinArgs geoHash(String param) {
            return new WithinArgs().value(new GeoHashParam(param));
        }

        /**
         * 根据扇形查询
         * @param lon 中心坐标经度
         * @param lat 中心坐标纬度
         * @param meters 半径，单位米
         * @param bearing1 角度1
         * @param bearing2 角度2
         */
        public static WithinArgs sector(double lon, double lat, long meters, double bearing1, double bearing2) {
            return new WithinArgs().value(new SectorParam(lat, lon, meters, bearing1, bearing2));
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
        if (get != null) {
            commandArgs.add("GET").add(get.getKey()).add(get.getId());
        }
        if (bounds != null) {
            commandArgs.add("BOUNDS").add(bounds.getMinLat()).add(bounds.getMinLon())
                    .add(bounds.getMaxLat()).add(bounds.getMaxLon());
        }
        if (!StringUtil.isNullOrEmpty(geoJson)) {
            commandArgs.add("OBJECT").add(geoJson);
        }
        if (circle != null) {
            commandArgs.add("CIRCLE").add(circle.getLat()).add(circle.getLon())
                    .add(circle.getMeters());
        }
        if (tile != null) {
            commandArgs.add("TILE").add(tile.getX()).add(tile.getY()).add(tile.getZ());
        }
        if (quadkey != null) {
            commandArgs.add("QUADKEY").add(quadkey.getQuadKey());
        }
        if(geohash != null) {
            commandArgs.add("HASH").add(geohash.getGeoHash());
        }
        if (sector != null) {
            commandArgs.add("SECTOR").add(sector.getLat()).add(sector.getLon())
                    .add(sector.getMeters()).add(sector.getBearing1())
                    .add(sector.getBearing2());
        }
    }
}
