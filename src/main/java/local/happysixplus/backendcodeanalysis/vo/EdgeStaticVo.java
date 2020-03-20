package local.happysixplus.backendcodeanalysis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EdgeStaticVo {

    Long id;
    Long fromId;
    Long toId;
    Double closeness;
}