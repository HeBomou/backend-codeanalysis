package local.happysixplus.backendcodeanalysis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VertexVo {

    Long id;
    String anotation;
    Float x;
    Float y;
}