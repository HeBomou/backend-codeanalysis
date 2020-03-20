package local.happysixplus.backendcodeanalysis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VertexStaticVo {

    Long id;
    String functionName;
    String sourceCode;
}