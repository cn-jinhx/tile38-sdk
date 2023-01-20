package cn.jinhx.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 漫游参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoamParam {
    /**
     * 集合名称
     */
    private String key;
    /**
     * 表达式
     */
    private String pattern;
    /**
     * 半径，单位米
     */
    private long meters;
}
