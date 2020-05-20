package local.happysixplus.backendcodeanalysis.data;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import local.happysixplus.backendcodeanalysis.po.GroupTaskPo;

public interface GroupTaskData extends JpaRepository<GroupTaskPo, Long> {

    List<GroupTaskPo> findAllByGroupId(Long groupId);

    List<GroupTaskPo> findByIdIn(List<Long> ids);

    @Modifying
    @Transactional
    void deleteByGroupId(Long groupId);

}