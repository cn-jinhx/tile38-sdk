package cn.jinhx.commands;

import cn.jinhx.core.EvalArgs;
import io.lettuce.core.dynamic.Commands;
import io.lettuce.core.dynamic.annotation.Command;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * 脚本相关指令
 * @author y-z-f
 * @since 2023/1/20
 */
public interface ScriptingCommands extends Commands {

    /**
     * 执行Lua脚本
     */
    @Command("EVAL :script :args")
    String eval(@Param("script")String script, @Param("args") EvalArgs args);

    /**
     * 非原子方式执行Lua脚本
     */
    @Command("EVALNA :script :args")
    String evalNa(@Param("script")String script, @Param("args")EvalArgs args);

    /**
     * 以非原子的方式执行服务器缓存的脚本
     */
    @Command("EVALNASHA :sha1 :args")
    String evalNaSha(@Param("sha1")String sha1, @Param("args")EvalArgs args);

    /**
     * 执行服务器缓存的脚本
     */
    @Command("EVALSHA :sha1 :args")
    String evalSha(@Param("sha1")String sha1, @Param("args")EvalArgs args);

    /**
     * 判断脚本是否存在服务器端
     */
    @Command("SCRIPT EXISTS :sha1")
    String exists(@Param("sha1")String sha1);

    /**
     * 刷新服务器端缓存脚本
     */
    @Command("SCRIPT FLUSH")
    String flush();

    /**
     * 将脚本缓存至服务器端，其会返回一个SHA1
     */
    @Command("SCRIPT LOAD @script")
    String load(@Param("script")String script);
}
