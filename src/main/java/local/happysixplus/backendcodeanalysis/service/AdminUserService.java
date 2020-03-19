package local.happysixplus.backendcodeanalysis.service;

import java.util.List;

import local.happysixplus.backendcodeanalysis.vo.AdminUserVo;

public interface AdminUserService{

    /**
     * 注册
     */
    void addAdmin(AdminUserVo vo);

    /**
     * 删除
     */
    void removeAdmin(Long id);

    /**
     * 管理员信息修改
     */
    void updateAdmin(AdminUserVo vo);

    /**
     * 获取所有管理员
     */
    List<AdminUserVo> getAllAdmin();

    /**
     * 获取单个管理员
     */
    AdminUserVo getOneUser(Long id);
}