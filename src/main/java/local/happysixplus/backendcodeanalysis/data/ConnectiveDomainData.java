package local.happysixplus.backendcodeanalysis.data;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.VertexPo;

public interface ConnectiveDomainData extends JpaRepository<VertexPo, Long> {
}