package local.happysixplus.backendcodeanalysis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VertexBaseVo {

    Long id;
    String functionName;
    String sourceCode;
}