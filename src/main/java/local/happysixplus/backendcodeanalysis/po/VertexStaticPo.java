package local.happysixplus.backendcodeanalysis.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VertexStaticPo {

    Long id;

    String functionName;

    String sourceCode;
}