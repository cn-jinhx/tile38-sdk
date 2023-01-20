package cn.jinhx.commands;

import io.lettuce.core.dynamic.Commands;
import io.lettuce.core.dynamic.annotation.Command;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * 副本集群指令
 * @author y-z-f
 * @since 2023/1/20
 */
public interface ReplicationCommands extends Commands {

    /**
     * 从指令的偏移量下载AOF
     */
    @Command("AOF :pos")
    String aof(@Param("pos")long pos);

    /**
     * 计算指定部分的校验和
     */
    @Command("AOFMD5 :pos :size")
    String aofMd5(@Param("pos")long pos, @Param("size")long size);

    /**
     * 置于后台针对AOF进行压缩
     */
    @Command("AOFSHRINK")
    String aofShrink();

    /**
     * 加入集群
     */
    @Command("FOLLOW :host :port")
    String follow(@Param("host")String host, @Param("port")long port);
}
