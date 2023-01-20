package cn.jinhx.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 扇形参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectorParam {
    private double lat;
    private double lon;
    private long meters;
    private double bearing1;
    private double bearing2;
}
