package local.happysixplus.backendcodeanalysis.controller;


import local.happysixplus.backendcodeanalysis.service.ProjectService;
import local.happysixplus.backendcodeanalysis.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/project")
public class ProjectController {
    @Autowired
    ProjectService service;

    @PutMapping(value="/{projectId}/subgraph/{id}")
    public void putSubgraph(@PathVariable long projectId, @RequestBody SubgraphDynamicVo vo){
        service.updateSubGraph(projectId,vo);
    }

    @PutMapping(value="/{id}")
    public void putProject(@PathVariable long projectId,@RequestBody ProjectDynamicVo vo){
        service.updateProject(vo);
    }

    @GetMapping(value="")
    public List<ProjectAllVo> getProject(@RequestParam long userId){
        return service.getProjectAllByUserId(userId);
    }

    @GetMapping(value="/{projectId}/subgraph/{subgraphId}/subgraphShortestPath")
    public PathVo getSubGraphShortestPath(@RequestParam long startVertexId, @RequestParam long endVertexId, @PathVariable long projectId,@PathVariable long subgraphId){
        return service.getSubgraphShortestPath(projectId,subgraphId,startVertexId,endVertexId);
    }

    @GetMapping(value="/{projectId}/originalGraphShortestPath")
    public PathVo getOriginalGraphShortestPath(@RequestParam long startVertexId, @RequestParam long endVertexId, @PathVariable long projectId){
        return service.getOriginalGraphShortestPath(projectId,startVertexId,endVertexId);
    }

    @GetMapping(value="/{projectId}/subgraph")
    public List<SubgraphAllVo> getSubgraph(@PathVariable long projectId){
        return service.getSubgraphAllByProjectId(projectId);
    }

    @GetMapping(value="/{projectId}/similarFunction")
    public List<String> getSimilarFunction(@RequestParam String funcName,@PathVariable long projectId){
        return service.getSimilarFunction(projectId,funcName);
    }
    @PostMapping(value="/{projectId}/subgraph")
    public SubgraphAllVo postSubgraph(@RequestParam double threshold, @PathVariable long projectId){
        return service.addSubgraph(projectId,threshold);
    }

    @PostMapping(value="/project")
    public ProjectAllVo postProject(@RequestParam String projectName, @RequestParam String url){
        return service.addProject(projectName,url);
    }

    @DeleteMapping(value="/{projectId}/subgraph/{id}")
    public void deleteSubgraph(@PathVariable long projectId, @PathVariable long id){
        service.removeSubgraph(id);
    }

    @DeleteMapping(value = "/project/{id}")
    public void deleteProject(@PathVariable long id){
        service.removeProject(id);
    }


}
