package cn.jinhx.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GeoHash参数值
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoHashParam {
    private String geoHash;
}
