package local.happysixplus.backendcodeanalysis.service;

import java.util.List;

import local.happysixplus.backendcodeanalysis.vo.UserVo;

public interface UserService {

    /**
     * 用户注册
     */
    void addUser(UserVo vo) throws Exception;

    /**
     * 删除用户
     */
    void removeUser(Long id);

    /**
     * 用户信息修改
     * 
     * @param vo
     */
    void updateUser(UserVo vo);

    /**
     * 获取所有用户
     * 
     * @return
     *
     */
    List<UserVo> getAllUsers();

    /**
     * 获取单个用户
     */
    UserVo getUser(Long id);

    boolean existUser(Long id);
}