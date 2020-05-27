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

    @PostMapping
    public void postTask(@RequestBody GroupTaskVo vo) {
        service.updateTask(vo);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteTask(@PathVariable Long id) {
        service.removeTask(id);
    }

    @PutMapping
    public void putTask(@RequestBody GroupTaskVo vo) throws Exception {
        service.updateTask(vo);
    }

    @PutMapping(value = "/{groupId}/{taskId}/assign/{userId}")
    public void assignTask(@PathVariable Long taskId, @PathVariable Long userId, @PathVariable Long groupId) {
        service.assignTask(taskId, userId, groupId);
    }

    @GetMapping(value = "/{groupId}")
    public List<GroupTaskVo> getAllTask(@PathVariable Long groupId) {
        return service.getAllTask(groupId);
    }

    @GetMapping(value = "/{groupId}/{userId}")
    public List<GroupTaskVo> getTask(@PathVariable Long groupId, @PathVariable Long userId) {
        return service.getTask(groupId, userId);
    }
}