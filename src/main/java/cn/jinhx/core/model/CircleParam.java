package cn.jinhx.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 圆形位置参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CircleParam {
    private double lat;
    private double lon;
    private long meters;
}
