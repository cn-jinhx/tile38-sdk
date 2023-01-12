package cn.jinhx.commands;

import io.lettuce.core.dynamic.Commands;
import io.lettuce.core.dynamic.annotation.Command;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * 频道相关指令
 * @author y-z-f
 * @date 2023/1/2
 */
public interface ChannelsCommands extends Commands {

    /**
     * 根据正则检索信道
     * @param pattern 表达式，支持<a href="https://en.wikipedia.org/wiki/Glob_(programming)">范围</a>
     * @return
     */
    @Command("CHANS :pattern")
    String chans(@Param("pattern")String pattern);

    /**
     * 删除指定名称的信道
     * @param name 信道名称
     */
    @Command("DELCHAN :name")
    String delChan(@Param("name")String name);

    /**
     * 根据正则删除匹配的信道
     * @param pattern 表达式，支持<a href="https://en.wikipedia.org/wiki/Glob_(programming)">范围</a>
     */
    @Command("PDELCHAN :pattern")
    String pDelChan(@Param("pattern")String pattern);


}
