package local.happysixplus.backendcodeanalysis.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import local.happysixplus.backendcodeanalysis.po.EdgeDynamicPo;

public interface EdgeDynamicData extends JpaRepository<EdgeDynamicPo, Long> {
    List<EdgeDynamicPo> findByProjectId(Long projectId);
    @Modifying
    @Transactional
    void deleteByProjectId(Long projectId);
}