package local.happysixplus.backendcodeanalysis.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import local.happysixplus.backendcodeanalysis.po.VertexPositionDynamicPo;

public interface VertexPositionDynamicData extends JpaRepository<VertexPositionDynamicPo, Long> {

    Integer countByProjectId(Long projectId);

    List<VertexPositionDynamicPo> findBySubgraphId(Long subgraphId);

    VertexPositionDynamicPo findByVertexIdAndSubgraphId(Long vertexId, Long subgraphId);

    boolean existsByProjectId(Long projectId);

    @Modifying
    @Transactional
    void deleteByProjectId(Long projectId);

}