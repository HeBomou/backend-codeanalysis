package local.happysixplus.backendcodeanalysis.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.VertexDynamicPo;

public interface VertexDynamicData extends JpaRepository<VertexDynamicPo, Long> {
    Integer countByProjectId(Long projectId);
    List<VertexDynamicPo> findByProjectId(Long projectId);
    void deleteByProjectId(Long projectId);
}