package local.happysixplus.backendcodeanalysis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VertexPositionDynamicVo {

    Long id;

    Long subgraphId;

    Float x = 0f;

    Float y = 0f;
}