package local.happysixplus.backendcodeanalysis.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConnectiveDomainStaticVo {

    Long id;

    List<Long> vertexIds;

    List<Long> edgeIds;
}