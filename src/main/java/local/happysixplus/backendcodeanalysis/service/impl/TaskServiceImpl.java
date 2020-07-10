package local.happysixplus.backendcodeanalysis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.data.GroupTaskData;
import local.happysixplus.backendcodeanalysis.data.TaskUserRelData;
import local.happysixplus.backendcodeanalysis.po.GroupTaskPo;
import local.happysixplus.backendcodeanalysis.po.TaskUserRelPo;
import local.happysixplus.backendcodeanalysis.service.TaskService;
import local.happysixplus.backendcodeanalysis.vo.GroupTaskVo;
import lombok.var;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    GroupTaskData taskData;

    @Autowired
    TaskUserRelData taskUserRelData;

    @Override
    public void addTask(GroupTaskVo vo) {
        taskData.save(new GroupTaskPo(null, vo.getGroupId(), vo.getName(), vo.getInfo(), vo.getDeadline(),
                vo.getIsFinished()));
    }

    @Override
    public void removeTask(Long id) {
        taskData.deleteById(id);
        taskUserRelData.deleteByTaskId(id);
    }

    @Override
    public void updateTask(GroupTaskVo vo) {
        taskData.save(new GroupTaskPo(vo.getId(), vo.getGroupId(), vo.getName(), vo.getInfo(), vo.getDeadline(),
                vo.getIsFinished()));
    }

    @Override
    public void assignTask(Long taskId, Long userId, Long groupId) {
        taskUserRelData.save(new TaskUserRelPo(null, taskId, userId, groupId));
    }

    @Override
    public List<Long> getAllExecutor(Long taskId) {
        var pos = taskUserRelData.findByTaskId(taskId);
        List<Long> res = new ArrayList<Long>(pos.size());
        for (var po : pos) {
            res.add(po.getUserId());
        }
        return res;
    }

    @Override
    public void updateExecutor(Long taskId, Long groupId, List<Long> userIds) {
        taskUserRelData.deleteByTaskIdAndGroupId(taskId, groupId);
        for (int i = 0; i < userIds.size(); i++) {
            taskUserRelData.save(new TaskUserRelPo(null, taskId, userIds.get(i), groupId));
        }
    }

    @Override
    public List<GroupTaskVo> getAllTask(Long groupId) {
        var pos = taskData.findAllByGroupId(groupId);
        var res = new ArrayList<GroupTaskVo>(pos.size());
        for (var po : pos)
            res.add(new GroupTaskVo(po.getId(), po.getGroupId(), po.getName(), po.getInfo(), po.getDeadline(),
                    po.getIsFinished()));
        return res;
    }

    @Override
    public List<GroupTaskVo> getTask(Long groupId, Long userId) {
        var pos = taskUserRelData.findByGroupIdAndUserId(groupId, userId);
        var taskIds = new ArrayList<Long>(pos.size());
        for (var po : pos)
            taskIds.add(po.getTaskId());
        var res = new ArrayList<GroupTaskVo>(pos.size());
        var taskPos = taskData.findByIdIn(taskIds);
        for (var po : taskPos)
            res.add(new GroupTaskVo(po.getId(), po.getGroupId(), po.getName(), po.getInfo(), po.getDeadline(),
                    po.getIsFinished()));
        return res;
    }

}