package local.happysixplus.backendcodeanalysis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VertexAllVo {

    Long id;

    String functionName;

    String sourceCode;

    VertexDynamicVo dynamicVo;
}