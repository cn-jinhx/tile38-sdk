package cn.jinhx.commands;

import cn.jinhx.core.*;
import io.lettuce.core.dynamic.Commands;
import io.lettuce.core.dynamic.annotation.Command;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * 查询相关指令
 * @author y-z-f
 * @since 2023/1/20
 */
public interface SearchCommands extends Commands {

    /**
     * 查询与指定边界相交的对象
     */
    @Command("INTERSECTS :key :args")
    String intersects(@Param("key")String key, @Param("args")IntersectsArgs args);

    /**
     * 查询邻近指定区域的对象
     */
    @Command("NEARBY :key :args")
    String nearby(@Param("key")String key, @Param("args")NearbyArgs args);

    /**
     * 用于检索集合中符合要求的对象
     */
    @Command("SCAN :key :args")
    String scan(@Param("key")String key, @Param("args")ScanArgs args);

    /**
     * 查询检索集合中的对象，主要用于值为字符串的对象，如POINT、OBJECT需要使用SCAN检索
     */
    @Command("SEARCH :key :args")
    String search(@Param("key")String key, @Param("args") SearchArgs args);

    /**
     * 用于查询处在指定框中的对象
     */
    @Command("WITHIN :key :args")
    String within(@Param("key")String key, @Param("args")WithinArgs args);
}
