package local.happysixplus.backendcodeanalysis.data;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import local.happysixplus.backendcodeanalysis.po.TaskUserRelPo;

public interface TaskUserRelData extends JpaRepository<TaskUserRelPo, Long> {

    List<TaskUserRelPo> findByGroupIdAndUserId(Long groupId, Long userId);

    List<TaskUserRelPo> findByTaskId(Long taskId);

    @Modifying
    @Transactional
    void deleteByGroupId(Long groupId);

    @Modifying
    @Transactional
    void deleteByUserIdAndGroupId(Long userId, Long groupId);

    @Modifying
    @Transactional
    void deleteByTaskIdAndGroupId(Long taskId, Long groupId);

    @Modifying
    @Transactional
    void deleteByTaskId(Long taskId);

}