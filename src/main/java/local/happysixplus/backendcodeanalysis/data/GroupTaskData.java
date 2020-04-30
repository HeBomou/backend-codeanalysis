package local.happysixplus.backendcodeanalysis.data;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.GroupTaskPo;

public interface GroupTaskData extends JpaRepository<GroupTaskPo, Long> {

}