package local.happysixplus.backendcodeanalysis.data;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.AdminUserPo;

public interface AdminUserData extends JpaRepository<AdminUserPo, Long> {

    AdminUserPo findByUsername(String username);
}