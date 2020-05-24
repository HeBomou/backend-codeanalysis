package local.happysixplus.backendcodeanalysis.controller;

import local.happysixplus.backendcodeanalysis.service.ProjectService;
import local.happysixplus.backendcodeanalysis.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value = "/project")
public class ProjectController {
    @Autowired
    ProjectService service;

    /**
     * 根据创建项目
     */
    @PostMapping
    public ProjectAllVo postProject(@RequestParam String projectName, @RequestParam String url,
            @RequestParam long userId) {
        System.out.println("Post Project: " + projectName + ", URL: " + url + ", UserId: " + userId);
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
     * 获取所有项目的名称等动态信息，如果传了用户id，就是返回该用户的所有项目，否则返回所有项目
     */
    @GetMapping
    public List<ProjectBasicAttributeVo> getProjectBasicAttribute(@RequestParam(required = false) Long userId) {
        return service.getProjectBasicAttribute(userId);
    }

    /**
     * 获取项目的所有统计信息，包括点数、边数、连通域数以及相应注释数量
     */
    @GetMapping(value = "/{id}/profile")
    public ProjectProfileVo getProjectProfile(@PathVariable Long id) {
        return service.getProjectProfile(id);
    }

    /**
     * 根据项目id获取项目的全部信息
     * 
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public ProjectAllVo getProject(@PathVariable Long id) {
        return service.getProjectAll(id);
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
     * 更新连通域名等动态信息
     */
    @PutMapping(value = "/{projectId}/subgraph/{subgraphId}/connectiveDomain/{id}/dynamic")
    public void putConnectiveDomain(@PathVariable Long projectId, @PathVariable Long subgraphId,
            @RequestBody ConnectiveDomainDynamicVo vo) {
        service.updateConnectiveDomainDynamic(projectId, subgraphId, vo);
    }

    /**
     * 更新连通域内所有顶点的位置信息
     */
    @PutMapping(value = "/{projectId}/subgraph/{subgraphId}/connectiveDomain/{connectiveDomainId}/position")
    public void putConnectiveDomainPosition(@PathVariable Long projectId, @PathVariable Long subgraphId, @PathVariable Long connectiveDomainId,
            @RequestParam float relativeX, @RequestParam float relativeY) {
        service.updateConnectiveDomainAllVertex(projectId, subgraphId, connectiveDomainId, relativeX, relativeY);
    }

    /**
     * 更新顶点名等动态信息
     */
    @PutMapping(value = "/{projectId}/vertex/{id}/dynamic")
    public void putVertex(@PathVariable Long projectId, @RequestBody VertexDynamicVo vo) {
        service.updateVertexDynamic(projectId, vo);
    }

    /**
     * 更新子图中点的位置
     */
    @PutMapping(value = "/{projectId}/subgraph/{subgraphId}/vertex/{id}/position")
    public void putVertexPosition(@PathVariable Long projectId, @RequestBody VertexPositionDynamicVo vo) {
        service.updateVertexPositionDynamic(projectId, vo);
    }

    /**
     * 更新边名等动态信息
     */
    @PutMapping(value = "/{projectId}/edge/{id}/dynamic")
    public void putEdge(@PathVariable Long projectId, @RequestBody EdgeDynamicVo vo) {
        service.updateEdgeDynamic(projectId, vo);
    }

    /**
     * 获取原图从起始点到终止点的所有路径，至多50条
     */
    @GetMapping(value = "/{projectId}/originalGraphPath")
    public PathVo getOriginalGraphPath(@PathVariable long projectId, @RequestParam Long startVertexId,
            @RequestParam Long endVertexId) {
        return service.getOriginalGraphPath(projectId, startVertexId, endVertexId);
    }

    /**
     * 获取重复函数名列表
     */
    @GetMapping(value = "/{projectId}/similarFunction")
    public List<String> getSimilarFunction(@RequestParam String funcName, @PathVariable Long projectId) {
        return service.getSimilarFunction(projectId, funcName);
    }

}
