package cn.jinhx.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 矩形位置参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoundsParam {
    private double minLat;
    private double minLon;
    private double maxLat;
    private double maxLon;
}
