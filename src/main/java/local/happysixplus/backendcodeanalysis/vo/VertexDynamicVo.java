package local.happysixplus.backendcodeanalysis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VertexDynamicVo {

    Long id;

    String anotation = "";

    Float x = 0f;

    Float y = 0f;
}