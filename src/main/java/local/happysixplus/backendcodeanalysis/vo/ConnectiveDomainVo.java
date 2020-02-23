package local.happysixplus.backendcodeanalysis.vo;

import java.util.List;

import lombok.Data;

@Data
public class ConnectiveDomainVo {

    int vertexNum;
    List<EdgeVo> edgeVos;
}