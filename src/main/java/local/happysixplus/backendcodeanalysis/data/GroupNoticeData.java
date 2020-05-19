package local.happysixplus.backendcodeanalysis.data;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import local.happysixplus.backendcodeanalysis.po.GroupNoticePo;

public interface GroupNoticeData extends JpaRepository<GroupNoticePo, Long> {

    List<GroupNoticePo> findAllByGroupId(Long groupId);

    @Modifying
    @Transactional
    void deleteByGroupId(Long groupId);

}