package local.happysixplus.backendcodeanalysis.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PathVo {
    /**
     * 外层是所有的路径，内层是每一条路径从起点出发到终点的所有边的Id
     */
    List<List<Long>> paths;
}