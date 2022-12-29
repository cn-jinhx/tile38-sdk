package cn.jinhx.commands;

import io.lettuce.core.dynamic.Commands;
import io.lettuce.core.dynamic.annotation.Command;

/**
 * Key相关指令
 * @author y-z-f
 * @date 2022/12/30
 */
public interface KeysCommands extends Commands {

    /**
     * 返回包含key中所有对象的最小边界框坐标
     */
    @Command("BOUNDS ?0")
    String bounds(String key);


}
