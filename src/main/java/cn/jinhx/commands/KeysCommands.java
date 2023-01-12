package cn.jinhx.commands;

import cn.jinhx.core.*;
import io.lettuce.core.dynamic.Commands;
import io.lettuce.core.dynamic.annotation.Command;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * Key相关指令
 * @author y-z-f
 * @date 2022/12/30
 */
public interface KeysCommands extends Commands {

    /**
     * 返回包含key中所有对象的最小边界框坐标
     */
    @Command("BOUNDS :key")
    String bounds(@Param("key")String key);

    /**
     * 删除指定对象
     */
    @Command("DEL :key :id")
    String del(@Param("key")String key, @Param("id")String id);

    /**
     * 删除指定Key下所有对象
     */
    @Command("DROP :key")
    String drop(@Param("key")String key);

    /**
     * 设定指定对象的超时时间，单位秒
     */
    @Command("EXPIRE :key :id :time")
    String expire(@Param("key")String key, @Param("id")String id, @Param("time")int time);

    /**
     * 设置指定对象的附加字段
     * @param key 集合名称
     * @param id 数据名称
     * @return 返货有多少个字段变更成功
     */
    @Command("PSET :key :id :args")
    Long pSet(@Param("key")String key, @Param("id")String id, @Param("args") FieldArgs args);

    /**
     * 获取指定集合对象，默认范围GeoJson
     * @param key 集合名称
     * @param id 数据名称
     * @param args 参数值
     */
    @Command("GET :key :id :args")
    String get(@Param("key")String key, @Param("id")String id, @Param("args") GetArgs args);

    /**
     * 从GeoJson对象中删除一个值
     * @remark 如JDEL user 901 name.first
     */
    @Command("JDEL :key :id :gJson")
    String jDel(@Param("key")String key, @Param("id")String id, @Param("gJson")String gJson);

    /**
     * 从GeoJson对象中获取需要的值
     */
    @Command("JGET :key :id :gJson")
    String jGet(@Param("key")String key, @Param("id")String id, @Param("gJson")String gJson);

    /**
     * 设置JSON对方中对应的值
     */
    @Command("JSET :key :id :gJson :value")
    String jSet(@Param("key")String key, @Param("id")String id, @Param("gJson")String gJson, @Param("value")String value);

    /**
     * 查询匹配表达式的KEY
     * @param pattern 表达式，支持<a href="https://en.wikipedia.org/wiki/Glob_(programming)">范围</a>
     */
    @Command("KEYS :pattern")
    String keys(@Param("pattern")String pattern);

    /**
     * 删除Key匹配表达式的对象
     * @param pattern 表达式，支持<a href="https://en.wikipedia.org/wiki/Glob_(programming)">范围</a>
     */
    @Command("PDEL :key :pattern")
    String pDel(@Param("key")String key, @Param("pattern")String pattern);

    /**
     * 移除对象的超时设置
     */
    @Command("PERSIST :key :id")
    String persist(@Param("key")String key, @Param("id")String id);

    /**
     * 重命名key，如newKey存在则会删除之并在进行重命名
     */
    @Command("RENAME :key :newKey")
    String rename(@Param("key")String key, @Param("newKey")String newKey);

    /**
     * 重命名Key，如newKey存在则静默不做任何处理
     */
    @Command("RENAMENX :key :newKey")
    String renameNX(@Param("key")String key, @Param("newKey")String newKey);

    /**
     * 创建一个点在key集中
     * @param key 集合名称
     * @param id 点名称
     * @param args 参数
     */
    @Command("SET :key :id :args")
    String setPoint(@Param("key")String key, @Param("id")String id, @Param("args") SetPointArgs args);

    /**
     * 在指定集合中创建边界框
     * @param key 集合名称
     * @param id 边界框名称
     * @param args 参数
     */
    @Command("SET :key :id :args")
    String setBounds(@Param("key")String key, @Param("id")String id, @Param("args") SetBoundsArgs args);

    /**
     * 在指定集合中创建GeoHash
     * @param key 集合名称
     * @param id 数据名称
     * @param args 参数
     */
    @Command("SET :key :id :args")
    String setHash(@Param("key")String key, @Param("id")String id, @Param("args") SetHashArgs args);

    /**
     * 在指定集合中创建GeoJson对象
     * @param key 集合名称
     * @param id 数据名称
     * @param args 参数
     */
    @Command("SET :key :id OBJECT :val")
    String setObject(@Param("key")String key, @Param("id")String id, @Param("args")SetObjectArgs args);

    /**
     * 查询集合的统计信息
     */
    @Command("STATS :key")
    String stats(@Param("key")String key);

    /**
     * 查询对象的超时时间
     * @param key 集合名称
     * @param id 数据名称
     */
    @Command("TTL :key :id")
    String ttl(@Param("key")String key, @Param("id")String id);
}
