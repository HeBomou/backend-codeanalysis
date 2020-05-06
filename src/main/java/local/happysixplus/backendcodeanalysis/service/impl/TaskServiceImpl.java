package local.happysixplus.backendcodeanalysis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.data.GroupTaskData;
import local.happysixplus.backendcodeanalysis.data.TaskUserRelData;
import local.happysixplus.backendcodeanalysis.service.TaskService;
import local.happysixplus.backendcodeanalysis.vo.GroupTaskVo;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    GroupTaskData taskData;

    @Autowired
    TaskUserRelData taskUserRelData;

    @Override
    public void addTask(GroupTaskVo vo) {

    }

    @Override
    public void removeTask(Long id) {

    }

    @Override
    public void updateTask(GroupTaskVo vo) {

    }

    @Override
    public void assignTask(Long taskId, Long userId) {
    }

    @Override
    public List<GroupTaskVo> getAllTask(Long groupId) {
        return null;
    }

    @Override
    public List<GroupTaskVo> getTask(Long groupId, Long userId) {
        return null;
    }

}