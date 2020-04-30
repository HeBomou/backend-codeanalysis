package local.happysixplus.backendcodeanalysis.data;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.TaskUserRelPo;

public interface TaskUserRelData extends JpaRepository<TaskUserRelPo, Long> {

}