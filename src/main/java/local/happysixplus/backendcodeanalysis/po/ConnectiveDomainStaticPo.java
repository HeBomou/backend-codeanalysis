package local.happysixplus.backendcodeanalysis.po;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConnectiveDomainStaticPo {

    List<Long> vertexIds;

    List<Long> edgeIds;
}