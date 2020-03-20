package local.happysixplus.backendcodeanalysis.data;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.UserPo;

public interface UserData extends JpaRepository<UserPo, Long> {

    UserPo findByUsername(String username);
}