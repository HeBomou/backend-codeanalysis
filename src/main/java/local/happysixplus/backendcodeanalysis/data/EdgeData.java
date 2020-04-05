package local.happysixplus.backendcodeanalysis.data;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import local.happysixplus.backendcodeanalysis.po.EdgePo;

public interface EdgeData extends JpaRepository<EdgePo, Long> {

    List<EdgePo> findByProjectId(Long projectId);

    boolean existsByProjectId(Long projectId);

    @Modifying
    @Transactional
    void deleteByProjectId(Long projectId);

}