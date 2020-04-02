
package local.happysixplus.backendcodeanalysis.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageNodeVo {

    /**
     * 当前节点的包路径字符串
     */
    String str;

    /**
     * 子节点
     */
    List<PackageNodeVo> children;

    /**
     * 当前包下的函数节点id
     */
    List<Long> functions;

}