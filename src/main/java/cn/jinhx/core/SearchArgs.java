package cn.jinhx.core;

import io.lettuce.core.CompositeArgument;
import io.lettuce.core.internal.LettuceAssert;
import io.lettuce.core.protocol.CommandArgs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询类通用配置参数
 * @author y-z-f
 * @since 2023/1/12
 */
public class SearchArgs implements CompositeArgument {
    /**
     * 是否开启地理围栏，用于SETCHAN
     */
    private boolean fence = false;
    /**
     * 对象处在指定的区域范围，仅在fence生效时可用。
     */
    private boolean detectInside = true;
    /**
     * 对象处在指定的区域范围外，仅在fence生效时可用。
     */
    private boolean detectOutside = true;
    /**
     * 对象未处在指定的范围内时，对象进入区域内时，仅在fence生效时可用。
     */
    private boolean detectEnter = true;
    /**
     * 对象处在指定的范围内，离开指定的区域时，仅在fence生效时可用。
     */
    private boolean detectExit = true;
    /**
     * 对象未处在指定的区域范围内，对象进入并离开区域时，仅在fence生效时可用。
     */
    private boolean detectCross = true;
    /**
     * 筛选过滤条件
     */
    private List<String> where = new ArrayList<>();
    /**
     * 类似in的范围过滤条件
     */
    private Map<String, List<String>> wherein = new HashMap<>();
    /**
     * 支持Lua脚本的过滤条件
     */
    private List<String> whereEval = new ArrayList<>();
    /**
     * 用于过滤数据标识的条件
     */
    private List<String> match = new ArrayList<>();
    /**
     * 结果不包含Field
     */
    private boolean noFields = false;
    /**
     * 通过搜索的边界框区域裁剪相交的对象
     */
    private boolean clip = false;
    /**
     * 设定输出的数据条数，默认输出100条
     */
    private Integer limit;
    /**
     * 仅支持WITH与INTERSECTS条件，增加指定距离的缓冲
     */
    private Integer buffer;

    /**
     * 添加条件
     * @param exp 条件表达式
     */
    public SearchArgs addWhere(String exp) {
        this.where.add(exp);
        return this;
    }

    /**
     * 添加IN条件
     * @param name 条件字段
     * @param value 数据列
     */
    public SearchArgs addWherein(String name, List<String> value) {
        LettuceAssert.notEmpty(name, "the name can not be empty");
        LettuceAssert.notNull(value, "the value can not be null");
        this.wherein.put(name, value);
        return this;
    }

    /**
     * 移除IN条件
     * @param name 条件字段
     */
    public SearchArgs delWherein(String name) {
        LettuceAssert.notEmpty(name, "the name can not be empty");
        this.wherein.remove(name);
        return this;
    }

    /**
     * 添加支持Lua脚本的过滤条件
     */
    public SearchArgs addEval(String exp) {
        LettuceAssert.notEmpty(exp, "the exp can not be empty");
        this.whereEval.add(exp);
        return this;
    }

    /**
     * 添加针对数据标识的过滤条件
     * @param exp 表达式
     */
    public SearchArgs addMatch(String exp) {
        LettuceAssert.notEmpty(exp, "the exp can not be empty");
        this.match.add(exp);
        return this;
    }

    /**
     * 设定输出不包含Field
     */
    public SearchArgs noFields() {
        this.noFields = true;
        return this;
    }

    /**
     * 通过搜索的边界框区域裁剪相交的对象
     */
    public SearchArgs clip() {
        this.clip = true;
        return this;
    }

    /**
     * 设定输出的数据条数，默认输出100条
     */
    public SearchArgs limit(int limit) {
        this.limit = limit;
        return this;
    }

    public SearchArgs buffer(int buffer) {
        this.buffer = buffer;
        return this;
    }

    @Override
    public <K, V> void build(CommandArgs<K, V> commandArgs) {

    }
}
