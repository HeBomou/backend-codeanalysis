package local.happysixplus.backendcodeanalysis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import local.happysixplus.backendcodeanalysis.service.ProjectService;
import local.happysixplus.backendcodeanalysis.vo.PathVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectAllVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.SubgraphAllVo;
import local.happysixplus.backendcodeanalysis.vo.SubgraphDynamicVo;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Override
    public ProjectAllVo addProject(String projectName, String url){
        System.out.println("add project");
        return new ProjectAllVo();
    };

    @Override
    public void removeProject(Long id){
        System.out.println("remove project");
    };

    @Override
    public void updateProject(ProjectDynamicVo vo){
        System.out.println("update project");
    };

    @Override
    public List<ProjectAllVo> getProjectAllByUserId(Long userId){
        System.out.println("get Project All By UserId");
        return new ArrayList<>();
    };

    @Override
    public SubgraphAllVo addSubgraph(Long projectId, Double threshold){
        System.out.println("add subgraph");
        return new SubgraphAllVo();
    };

    @Override
    public void removeSubgraph(Long id){
        System.out.println("remove subgraph");
    };

    @Override
    public void updateSubGraph(Long projectId, SubgraphDynamicVo vo){
        System.out.println("update subgraph");
    };

    @Override
    public List<SubgraphAllVo> getSubgraphAllByProjectId (Long projectId){
        System.out.println("get subgraph All By ProjectId");
        return new ArrayList<>();
    };

    @Override
    public PathVo getOriginalGraphShortestPath(Long projectId, Long startVertexId, Long endVertexId){
        System.out.println("get OriginalGraph Shortest Path");
        return new PathVo();
    };

    @Override
    public PathVo getSubgraphShortestPath(Long projectId, Long subgraphId, Long startVertexId, Long endVertexId){
        System.out.println("get Subgraph Shortest Path");
        return new PathVo();
    };

    @Override
    public List<String> getSimilarFunction(Long projectId, String funcName){
        System.out.println("get similar function");
        return new ArrayList<>();
    };
}