package local.happysixplus.backendcodeanalysis.service.impl;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.util.callgraph.CallGraphMethods;
import local.happysixplus.backendcodeanalysis.util.callgraph.ProjectInfo;
import lombok.var;

@Service
public class AsyncCallGraphForProjectServiceImpl {

    @Autowired
    CallGraphMethods callGraphMethods;

    @Async("GitPullExecutor")
    public CompletableFuture<ProjectInfo> asyncGitPull(String url) {
        var projectInfo = callGraphMethods.initGraph(url);
        return CompletableFuture.completedFuture(projectInfo);
    }
}