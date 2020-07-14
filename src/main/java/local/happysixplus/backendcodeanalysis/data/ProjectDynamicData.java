package local.happysixplus.backendcodeanalysis.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.ProjectDynamicPo;

public interface ProjectDynamicData extends JpaRepository<ProjectDynamicPo, Long> {

    List<ProjectDynamicPo> findByUserId(Long userId);

    List<ProjectDynamicPo> findByGroupId(Long groupId);

}