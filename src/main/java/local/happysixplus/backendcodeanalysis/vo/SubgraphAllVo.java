package local.happysixplus.backendcodeanalysis.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设置了阈值的子图，包括子图的各个联通域的点等静态信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubgraphAllVo {

    Long id;

    Double threshold;

    /**
     * 联通域的点与边等静态信息与注释等动态信息
     */
    List<ConnectiveDomainAllVo> connectiveDomains;

    SubgraphDynamicVo dynamicVo;
}