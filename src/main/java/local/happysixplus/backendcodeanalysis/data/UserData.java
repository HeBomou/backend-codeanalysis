package local.happysixplus.backendcodeanalysis.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.UserPo;

public interface UserData extends JpaRepository<UserPo, Long> {

    List<UserPo> findByIdIn(List<Long> ids);

    UserPo findByUsername(String username);
}