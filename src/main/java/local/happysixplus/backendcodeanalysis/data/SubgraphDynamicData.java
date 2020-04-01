package local.happysixplus.backendcodeanalysis.data;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import local.happysixplus.backendcodeanalysis.po.SubgraphDynamicPo;

public interface SubgraphDynamicData extends JpaRepository<SubgraphDynamicPo, Long> {
    List<SubgraphDynamicPo> findByProjectId(Long projectId);
    @Modifying
    @Transactional
    void deleteByProjectId(Long projectId);
}