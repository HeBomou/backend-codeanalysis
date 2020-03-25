package local.happysixplus.backendcodeanalysis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.callgraph.CallGraphMethods;
import local.happysixplus.backendcodeanalysis.data.ProjectData;
import local.happysixplus.backendcodeanalysis.data.SubgraphData;
import local.happysixplus.backendcodeanalysis.service.ProjectService;
import local.happysixplus.backendcodeanalysis.vo.PathVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectAllVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.SubgraphAllVo;
import local.happysixplus.backendcodeanalysis.vo.SubgraphDynamicVo;
import lombok.var;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    CallGraphMethods callGraphMethods;

    @Autowired
    ProjectData projectData;

    @Autowired
    SubgraphData subgraphData;

    @Override
    public ProjectAllVo addProject(String projectName, String url, long userId) {
        var project = callGraphMethods.initGraph(url, projectName);
        String[] callGraph = project.getCallGraph();
        System.out.println("add project");
        return new ProjectAllVo();
    };

    @Override
    public void removeProject(Long id) {
        projectData.deleteById(id);
    };

    @Override
    public void updateProject(ProjectDynamicVo vo) {

    };

    @Override
    public List<ProjectAllVo> getProjectAllByUserId(Long userId) {
        System.out.println("get Project All By UserId");
        return new ArrayList<>();
    };

    @Override
    public SubgraphAllVo addSubgraph(Long projectId, Double threshold) {
        System.out.println("add subgraph");
        return new SubgraphAllVo();
    };

    @Override
    public void removeSubgraph(Long id) {
        subgraphData.deleteById(id);
    };

    @Override
    public void updateSubGraph(Long projectId, SubgraphDynamicVo vo) {
        System.out.println("update subgraph");
    };

    @Override
    public List<SubgraphAllVo> getSubgraphAllByProjectId(Long projectId) {
        System.out.println("get subgraph All By ProjectId");
        return new ArrayList<>();
    };

    @Override
    public PathVo getOriginalGraphShortestPath(Long projectId, Long startVertexId, Long endVertexId) {
        System.out.println("get OriginalGraph Shortest Path");
        return new PathVo();
    };

    @Override
    public PathVo getSubgraphShortestPath(Long projectId, Long subgraphId, Long startVertexId, Long endVertexId) {
        System.out.println("get Subgraph Shortest Path");
        return new PathVo();
    };

    @Override
    public List<String> getSimilarFunction(Long projectId, String funcName) {
        System.out.println("get similar function");
        return new ArrayList<>();
    };
}