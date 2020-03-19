package local.happysixplus.backendcodeanalysis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EdgeBaseVo {

    Long id;
    Long fromId;
    Long toId;
    Double closeness;
}