package local.happysixplus.backendcodeanalysis.controller;

import org.springframework.web.bind.annotation.RestController;

import local.happysixplus.backendcodeanalysis.service.TaskService;
import local.happysixplus.backendcodeanalysis.vo.GroupTaskVo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(value = "/task")
public class TaskController {
    @Autowired
    TaskService service;

    // 添加任务
    @PostMapping
    public void postTask(@RequestBody GroupTaskVo vo) {
        service.addTask(vo);
    }

    // 删除任务
    @DeleteMapping(value = "/{id}")
    public void deleteTask(@PathVariable Long id) {
        service.removeTask(id);
    }

    // 更新任务
    @PutMapping
    public void putTask(@RequestBody GroupTaskVo vo) throws Exception {
        service.updateTask(vo);
    }

    // 分配任务
    @PutMapping(value = "/{groupId}/{taskId}/assign/{userId}")
    public void assignTask(@PathVariable Long taskId, @PathVariable Long userId, @PathVariable Long groupId) {
        service.assignTask(taskId, userId, groupId);
    }

    // 获取任务的所有执行者
    @GetMapping(value = "/executor/{taskId}")
    public List<Long> getAllExecutor(@PathVariable Long taskId) {
        return service.getAllExecutor(taskId);
    }

    // 更新任务的执行者
    @GetMapping(value = "/{groupId}/{taskId}")
    public void updateExecutor(@PathVariable Long groupId, @PathVariable Long taskId, @RequestBody List<Long> userIds) {
        service.updateExecutor(taskId, groupId, userIds);
    }

    // 获取小组所有任务
    @GetMapping(value = "/{groupId}")
    public List<GroupTaskVo> getAllTask(@PathVariable Long groupId) {
        return service.getAllTask(groupId);
    }

    // 获取小组内某组员任务
    @GetMapping(value = "/{groupId}/{userId}")
    public List<GroupTaskVo> getTask(@PathVariable Long groupId, @PathVariable Long userId) {
        return service.getTask(groupId, userId);
    }
}