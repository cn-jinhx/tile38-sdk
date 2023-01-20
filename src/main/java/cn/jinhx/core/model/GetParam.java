package cn.jinhx.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 采用自身数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetParam {
    private String key;
    private String id;
}
