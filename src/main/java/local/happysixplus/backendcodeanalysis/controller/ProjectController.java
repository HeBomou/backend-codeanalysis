package local.happysixplus.backendcodeanalysis.controller;

import local.happysixplus.backendcodeanalysis.service.ProjectService;
import local.happysixplus.backendcodeanalysis.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/project")
public class ProjectController {
    @Autowired
    ProjectService service;

    @PostMapping(value = "")
    public ProjectAllVo postProject(@RequestParam String projectName, @RequestParam String url,
            @RequestParam long userId) {
        return service.addProject(projectName, url, userId);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteProject(@PathVariable Long id) {
        service.removeProject(id);
    }

    @PutMapping(value = "/{projectId}/dynamic")
    public void putProject(@PathVariable Long projectId, @RequestBody ProjectDynamicVo vo) {
        service.updateProjectDynamic(projectId, vo);
    }

    @GetMapping(value = "")
    public List<ProjectDynamicVo> getProject(@RequestParam Long userId) {
        return service.getProjectDynamicByUserId(userId);
    }

    @PostMapping(value = "/{projectId}/subgraph")
    public SubgraphAllVo postSubgraph(@PathVariable Long projectId, @RequestParam Double threshold, @RequestParam String name) {
        return service.addSubgraph(projectId, threshold, name);
    }

    @DeleteMapping(value = "/{projectId}/subgraph/{id}")
    public void deleteSubgraph(@PathVariable Long projectId, @PathVariable Long id) {
        service.removeSubgraph(id);
    }

    @PutMapping(value = "/{projectId}/subgraph/{id}/dynamic")
    public void putSubgraph(@PathVariable Long projectId, @RequestBody SubgraphDynamicVo vo) {
        service.updateSubGraphDynamic(projectId, vo);
    }

    @GetMapping(value = "/{projectId}/originalGraphShortestPath")
    public PathVo getOriginalGraphShortestPath(@RequestParam Long startVertexId, @RequestParam Long endVertexId,
            @PathVariable long projectId) {
        return service.getOriginalGraphShortestPath(projectId, startVertexId, endVertexId);
    }

    @GetMapping(value = "/{projectId}/similarFunction")
    public List<String> getSimilarFunction(@RequestParam String funcName, @PathVariable Long projectId) {
        return service.getSimilarFunction(projectId, funcName);
    }

}
