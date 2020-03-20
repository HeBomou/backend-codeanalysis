package local.happysixplus.backendcodeanalysis.po;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VertexStaticPo {

    Long id;

    String functionName;

    String sourceCode;
}