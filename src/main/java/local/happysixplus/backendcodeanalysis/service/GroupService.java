package local.happysixplus.backendcodeanalysis.service;

import java.util.List;

import local.happysixplus.backendcodeanalysis.vo.GroupNoticeVo;
import local.happysixplus.backendcodeanalysis.vo.GroupVo;
import local.happysixplus.backendcodeanalysis.vo.UserVo;

public interface GroupService {

    /**
     * 添加新的小组
     * 
     * @param vo
     */
    void addGroup(GroupVo vo);

    /**
     * 删除小组
     * 
     * @param id
     */
    void removeGroup(Long id);

    /**
     * 更新小组信息
     * 
     * @param vo
     */
    void updateGroup(GroupVo vo);

    /**
     * 获取某用户小组列表
     */
    List<GroupVo> getAllGroup(Long userId);

    /**
     * 添加新的小组成员
     */
    void addMember(Long groupId, String inviteCode, Long userId);

    /**
     * 删除小组成员
     * 
     * @param groupId
     * @param userId
     */
    void removeMember(Long groupId, Long userId);

    /**
     * 修改组员权限
     * 
     * @param groupId
     * @param userId
     * @param level
     */
    void updateMember(Long groupId, Long userId, String level);

    /**
     * 获取组员等级，判断是否有权限进行操作
     * 
     * @param groupId
     * @param userId
     * @return
     */
    String getMemberLevel(Long groupId, Long userId);

    /**
     * 获取小组成员列表
     * 
     * @param groupId
     * @return
     */
    List<UserVo> getMembers(Long groupId);

    /**
     * 添加公告
     * 
     * @param vo
     */
    void addNotice(GroupNoticeVo vo);

    /**
     * 删除公告
     * 
     * @param id
     */
    void removeNotice(Long id);

    /**
     * 修改公告
     * 
     * @param vo
     */
    void updateNotice(GroupNoticeVo vo);

    /**
     * 获取小组公告
     * 
     * @param groupId
     * @return
     */
    List<GroupNoticeVo> getNotice(Long groupId);

}