package cn.jinhx.commands;

import io.lettuce.core.dynamic.Commands;
import io.lettuce.core.dynamic.annotation.Command;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * 连接相关指令
 * @author y-z-f
 * @since 2023/1/20
 */
public interface ConnectionCommands extends Commands {
    /**
     * 授权
     */
    @Command("AUTH :password")
    String auth(@Param("password")String password);

    /**
     * 设置当前连接的返回数据格式
     */
    @Command("OUTPUT :format")
    String output(@Param("format")String format);

    /**
     * 检测服务器
     */
    @Command("PING")
    String ping();

    /**
     * 退出连接
     */
    @Command("QUIT")
    String quit();

    /**
     * 采用具备超时的方式执行指令
     */
    @Command("TIMEOUT @seconds @command")
    String timeout(@Param("seconds")long seconds, @Param("command")String commands);
}
