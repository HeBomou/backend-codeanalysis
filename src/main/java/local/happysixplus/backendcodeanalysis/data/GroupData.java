package local.happysixplus.backendcodeanalysis.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.GroupPo;

public interface GroupData extends JpaRepository<GroupPo, Long> {

    List<GroupPo> findByIdIn(List<Long> id);

}