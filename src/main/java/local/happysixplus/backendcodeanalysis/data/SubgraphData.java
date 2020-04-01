package local.happysixplus.backendcodeanalysis.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.SubgraphPo;

public interface SubgraphData extends JpaRepository<SubgraphPo, Long> {
    Integer countByProjectId(Long projectId);
    List<SubgraphPo> findByProjectId(Long projectId);
    void deleteByProjectId(Long projectId);
}