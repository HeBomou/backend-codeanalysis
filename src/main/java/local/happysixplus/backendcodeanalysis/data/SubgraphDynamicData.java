package local.happysixplus.backendcodeanalysis.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.SubgraphDynamicPo;

public interface SubgraphDynamicData extends JpaRepository<SubgraphDynamicPo, Long> {
    List<SubgraphDynamicPo> findByProjectId(Long projectId);
    void deleteByProjectId(Long projectId);
}