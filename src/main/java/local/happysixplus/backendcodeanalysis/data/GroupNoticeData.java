package local.happysixplus.backendcodeanalysis.data;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import local.happysixplus.backendcodeanalysis.po.GroupNoticePo;

public interface GroupNoticeData extends JpaRepository<GroupNoticePo, Long> {

    @Modifying
    @Transactional
    void deleteByGroupId(Long groupId);

}