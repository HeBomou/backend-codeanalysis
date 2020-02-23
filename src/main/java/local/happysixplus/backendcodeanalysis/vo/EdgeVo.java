package local.happysixplus.backendcodeanalysis.vo;

import lombok.Data;

@Data
public class EdgeVo {
    VertexVo from;
    VertexVo to;
    Double closeness;
}