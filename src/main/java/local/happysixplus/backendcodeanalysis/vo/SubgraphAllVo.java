package local.happysixplus.backendcodeanalysis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 设置了阈值的子图，包括子图的各个联通域的点等静态信息
 */
@Data
@AllArgsConstructor
public class SubgraphAllVo {

    Long id;
    
    SubgraphStaticVo staticVo;

    SubgraphDynamicVo dynamicVo;
}