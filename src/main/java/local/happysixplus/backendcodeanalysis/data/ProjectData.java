package local.happysixplus.backendcodeanalysis.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.ProjectPo;

public interface ProjectData extends JpaRepository<ProjectPo, Long> {
    List<ProjectPo> findByUserId(Long userId);
}