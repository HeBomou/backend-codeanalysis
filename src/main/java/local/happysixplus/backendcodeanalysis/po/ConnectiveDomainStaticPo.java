package local.happysixplus.backendcodeanalysis.po;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectiveDomainStaticPo {

    List<Long> vertexIds;

    List<Long> edgeIds;
}