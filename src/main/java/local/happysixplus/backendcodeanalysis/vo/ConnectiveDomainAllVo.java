package local.happysixplus.backendcodeanalysis.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectiveDomainAllVo {

    Long id;

    List<Long> vertexIds;

    List<Long> edgeIds;

    ConnectiveDomainDynamicVo domainDynamicVo;

}