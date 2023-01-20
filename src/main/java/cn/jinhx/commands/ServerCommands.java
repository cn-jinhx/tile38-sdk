package cn.jinhx.commands;

import io.lettuce.core.dynamic.Commands;
import io.lettuce.core.dynamic.annotation.Command;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * 服务器相关指令
 * @author y-z-f
 * @since 2023/1/20
 */
public interface ServerCommands extends Commands {

    /**
     * 获取服务器配置的值
     */
    @Command("CONFIG GET :name")
    String getConfig(@Param("name")String name);

    /**
     * 保存由setConfig设置的参数
     */
    @Command("CONFIG REWRITE")
    String rewriteConfig();

    /**
     * 设置参数值
     */
    @Command("CONFIG SET :name :value")
    String setConfig(@Param("name")String name, @Param("value")String value);

    /**
     * 清除所有数据
     */
    @Command("FLUSHDB")
    String flushDb();

    @Command("GC")
    String gc();

    /**
     * 设置是否进入只读模式
     */
    @Command("READONLY :mode")
    String readonly(@Param("mode")String mode);

    /**
     * 显示服务器信息
     */
    @Command("SERVER")
    String server();
}
