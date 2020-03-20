package local.happysixplus.backendcodeanalysis.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设置了阈值的子图
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubgraphDynamicVo {

    Long id;

    String name;

    /**
     * 各联通域注释
     */
    List<ConnectiveDomainDynamicVo> connectiveDomains;
}