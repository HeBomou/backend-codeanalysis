package local.happysixplus.backendcodeanalysis.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EdgeStaticPo {

    Long id;

    Long fromId;

    Long toId;

    Double closeness;
}