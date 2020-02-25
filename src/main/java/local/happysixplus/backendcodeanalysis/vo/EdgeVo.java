package local.happysixplus.backendcodeanalysis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EdgeVo {
    VertexVo from;
    VertexVo to;
    Double closeness;
    
}