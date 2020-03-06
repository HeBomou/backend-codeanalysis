package local.happysixplus.backendcodeanalysis.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConnectiveDomainVo {
    List<VertexVo> vertexVos;
    List<EdgeVo> edgeVos;
}