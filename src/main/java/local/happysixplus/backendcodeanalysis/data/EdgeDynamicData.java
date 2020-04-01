package local.happysixplus.backendcodeanalysis.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.EdgeDynamicPo;

public interface EdgeDynamicData extends JpaRepository<EdgeDynamicPo, Long> {
    Integer countByProjectId(Long projectId);
    List<EdgeDynamicPo> findByProjectId(Long projectId);
    void deleteByProjectId(Long projectId);
}