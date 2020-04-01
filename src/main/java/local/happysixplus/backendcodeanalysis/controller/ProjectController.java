package local.happysixplus.backendcodeanalysis.controller;

import local.happysixplus.backendcodeanalysis.service.ProjectService;
import local.happysixplus.backendcodeanalysis.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "/project")
public class ProjectController {
    @Autowired
    ProjectService service;

    /**
     * 根据创建项目
     */
    @PostMapping(value = "")
    public ProjectAllVo postProject(@RequestParam String projectName, @RequestParam String url,
            @RequestParam long userId) {
        return service.addProject(projectName, url, userId);
    }

    /**
     * 删除一个项目
     */
    @DeleteMapping(value = "/{id}")
    public void deleteProject(@PathVariable Long id) {
        service.removeProject(id);
    }

    /**
     * 更新项目名等动态信息
     */
    @PutMapping(value = "/{projectId}/dynamic")
    public void putProject(@PathVariable Long projectId, @RequestBody ProjectDynamicVo vo) {
        service.updateProjectDynamic(projectId, vo);
    }

    /**
     * 根据用户id获取他所有项目的概要
     */
    @GetMapping(value = "")
    public List<ProjectDynamicVo> getProjectByUserId(@RequestParam Long userId) {
        return service.getProjectDynamicByUserId(userId);
    }

    /**
     * 根据项目id获取项目的全部信息
     * 
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public ProjectAllVo getProject(@PathVariable Long id) {
        return service.getProjectAllById(id);
    }

    /**
     * 根据前端的阈值与子图名新增一张子图
     */
    @PostMapping(value = "/{projectId}/subgraph")
    public SubgraphAllVo postSubgraph(@PathVariable Long projectId, @RequestParam Double threshold,
            @RequestParam String name) {
        return service.addSubgraph(projectId, threshold, name);
    }

    /**
     * 删除一张子图
     */
    @DeleteMapping(value = "/{projectId}/subgraph/{id}")
    public void deleteSubgraph(@PathVariable Long projectId, @PathVariable Long id) {
        service.removeSubgraph(id);
    }

    /**
     * 更新子图名等动态信息
     */
    @PutMapping(value = "/{projectId}/subgraph/{id}/dynamic")
    public void putSubgraph(@PathVariable Long projectId, @RequestBody SubgraphDynamicVo vo) {
        service.updateSubGraphDynamic(projectId, vo);
    }

    /**
     * 获取原图从起始点到终止点的所有路径，至多50条
     */
    @GetMapping(value = "/{projectId}/originalGraphPath")
    public PathVo getOriginalGraphPath(@PathVariable long projectId, @RequestParam Long startVertexId,
            @RequestParam Long endVertexId) {
        return service.getOriginalGraphPath(projectId, startVertexId, endVertexId);
    }

    @GetMapping(value = "/{projectId}/similarFunction")
    public List<String> getSimilarFunction(@RequestParam String funcName, @PathVariable Long projectId) {
        return service.getSimilarFunction(projectId, funcName);
    }

}
