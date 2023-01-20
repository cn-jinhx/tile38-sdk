package cn.jinhx.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 瓦片参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TileParam {
    private long x;
    private long y;
    private long z;
}
