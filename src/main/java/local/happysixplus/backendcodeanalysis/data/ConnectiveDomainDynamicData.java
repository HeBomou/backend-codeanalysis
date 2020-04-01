package local.happysixplus.backendcodeanalysis.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.ConnectiveDomainDynamicPo;

public interface ConnectiveDomainDynamicData extends JpaRepository<ConnectiveDomainDynamicPo, Long> {
    List<ConnectiveDomainDynamicPo> findByProjectId(Long projectId);
    void deleteByProjectId(Long projectId);
}