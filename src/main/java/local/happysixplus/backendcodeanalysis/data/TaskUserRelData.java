package local.happysixplus.backendcodeanalysis.data;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import local.happysixplus.backendcodeanalysis.po.TaskUserRelPo;

public interface TaskUserRelData extends JpaRepository<TaskUserRelPo, Long> {

    @Modifying
    @Transactional
    void deleteByGroupId(Long groupId);

    @Modifying
    @Transactional
    void deleteByUserIdAndGroupId(Long userId, Long groupId);

}