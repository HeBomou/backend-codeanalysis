package local.happysixplus.backendcodeanalysis.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.ProjectStaticAttributePo;

public interface ProjectStaticAttributeData extends JpaRepository<ProjectStaticAttributePo, Long> {
    List<ProjectStaticAttributePo> findByUserId(Long userId);
}