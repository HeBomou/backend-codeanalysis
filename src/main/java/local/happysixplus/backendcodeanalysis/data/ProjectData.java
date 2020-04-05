package local.happysixplus.backendcodeanalysis.data;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import local.happysixplus.backendcodeanalysis.po.ProjectPo;

public interface ProjectData extends JpaRepository<ProjectPo, Long> {
    @Override
    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<ProjectPo> findById(Long id);
}