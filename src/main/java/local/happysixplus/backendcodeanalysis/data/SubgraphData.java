package local.happysixplus.backendcodeanalysis.data;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import local.happysixplus.backendcodeanalysis.po.SubgraphPo;

public interface SubgraphData extends JpaRepository<SubgraphPo, Long> {
    Integer countByProjectId(Long projectId);
    List<SubgraphPo> findByProjectId(Long projectId);
    @Modifying
    @Transactional
    void deleteByProjectId(Long projectId);
}