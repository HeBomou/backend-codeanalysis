package local.happysixplus.backendcodeanalysis.data;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.ConnectiveDomainPo;

public interface ConnectiveDomainData extends JpaRepository<ConnectiveDomainPo, Long> {
}