package local.happysixplus.backendcodeanalysis.data;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.GroupPo;

public interface GroupData extends JpaRepository<GroupPo, Long> {

}