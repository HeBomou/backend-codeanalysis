package local.happysixplus.backendcodeanalysis.data;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import local.happysixplus.backendcodeanalysis.po.VertexPo;

public interface VertexData extends JpaRepository<VertexPo, Long> {
    List<VertexPo> findByProjectId(Long projectId);
    @Modifying
    @Transactional
    void deleteByProjectId(Long projectId);
}