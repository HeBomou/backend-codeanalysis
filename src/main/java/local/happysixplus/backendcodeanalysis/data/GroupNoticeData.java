package local.happysixplus.backendcodeanalysis.data;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.GroupNoticePo;

public interface GroupNoticeData extends JpaRepository<GroupNoticePo, Long> {

}