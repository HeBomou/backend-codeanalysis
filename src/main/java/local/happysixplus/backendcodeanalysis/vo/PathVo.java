package local.happysixplus.backendcodeanalysis.vo;

import java.util.List;

import lombok.Data;

@Data
public class PathVo {
    VertexVo start;
    List<EdgeVo> path;
}