package cn.jinhx.commands;

import cn.jinhx.core.IntersectsChanArgs;
import cn.jinhx.core.NearbyChanArgs;
import cn.jinhx.core.WithinChanArgs;
import io.lettuce.core.dynamic.Commands;
import io.lettuce.core.dynamic.annotation.Command;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * WebHook相关指令
 * @author y-z-f
 * @since 2023/1/20
 */
public interface WebhooksCommands extends Commands {

    /**
     * 删除一个钩子
     */
    @Command("DELHOOK :name")
    String del(@Param("name")String name);

    /**
     * 通过表达式删除钩子
     */
    @Command("PDELHOOK :pattern")
    String delPattern(@Param("pattern")String pattern);

    /**
     * 查询钩子
     */
    @Command("HOOKS :pattern")
    String find(@Param("pattern")String pattern);

    /**
     * 设置基于指定边界相交的地理围栏
     */
    @Command("SETHOOK :name :endpoint :args")
    String set(@Param("name")String name, @Param("endpoint")String endpoint, @Param("args") IntersectsChanArgs args);

    /**
     * 设置基于邻近指定点的地理围栏
     */
    @Command("SETHOOK :name :args")
    String set(@Param("name")String name, @Param("args") NearbyChanArgs args);

    /**
     * 设置基于处于在指定框的地理围栏
     */
    @Command("SETHOOK :name :args")
    String set(@Param("name")String name, @Param("args") WithinChanArgs args);
}
