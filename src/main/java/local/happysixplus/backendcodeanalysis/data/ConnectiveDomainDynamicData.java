package local.happysixplus.backendcodeanalysis.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import local.happysixplus.backendcodeanalysis.po.ConnectiveDomainDynamicPo;

public interface ConnectiveDomainDynamicData extends JpaRepository<ConnectiveDomainDynamicPo, Long> {

    Integer countByProjectId(Long projectId);

    List<ConnectiveDomainDynamicPo> findByProjectId(Long projectId);

    boolean existsByProjectId(Long projectId);

    @Modifying
    @Transactional
    void deleteByProjectId(Long projectId);

}