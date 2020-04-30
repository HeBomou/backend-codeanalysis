package local.happysixplus.backendcodeanalysis.data;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.GroupUserRelPo;

public interface GroupUserRelData extends JpaRepository<GroupUserRelPo, Long> {

}