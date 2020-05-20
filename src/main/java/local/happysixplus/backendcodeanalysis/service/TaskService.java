package local.happysixplus.backendcodeanalysis.service;

import java.util.List;

import local.happysixplus.backendcodeanalysis.vo.GroupTaskVo;

public interface TaskService {

    /**
     * 添加小组任务
     */
    void addTask(GroupTaskVo vo);

    /**
     * 删除小组任务
     */
    void removeTask(Long id);

    /**
     * 修改小组任务
     */
    void updateTask(GroupTaskVo vo);

    /**
     * 把任务分配给组员
     * 
     * @param taskId
     * @param userId
     */
    void assignTask(Long taskId, Long userId, Long groupId);

    /**
     * 获取小组任务列表
     * 
     * @param groupId
     * @return
     */
    List<GroupTaskVo> getAllTask(Long groupId);

    /**
     * 获取某用户在小组中的任务列表
     * 
     * @param groupId
     * @param userId
     * @return
     */
    List<GroupTaskVo> getTask(Long groupId, Long userId);
}