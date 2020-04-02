package local.happysixplus.backendcodeanalysis.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import local.happysixplus.backendcodeanalysis.po.ConnectiveDomainColorDynamicPo;

public interface ConnectiveDomainColorDynamicData extends JpaRepository<ConnectiveDomainColorDynamicPo, Long> {
    Integer countByProjectId(Long projectId);
    List<ConnectiveDomainColorDynamicPo> findByProjectId(Long projectId);
    @Modifying
    @Transactional
    void deleteByProjectId(Long projectId);
}