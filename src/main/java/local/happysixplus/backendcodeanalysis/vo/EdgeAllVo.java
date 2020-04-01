package local.happysixplus.backendcodeanalysis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EdgeAllVo {

    Long id;

    Long fromId;

    Long toId;

    Double closeness;

    EdgeDynamicVo dynamicVo;

}