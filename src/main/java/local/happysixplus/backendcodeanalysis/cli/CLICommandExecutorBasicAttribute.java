package local.happysixplus.backendcodeanalysis.cli;

import org.springframework.beans.factory.annotation.Autowired;

import local.happysixplus.backendcodeanalysis.service.GraphService;
import lombok.var;

public class CLICommandExecutorBasicAttribute implements CLICommandExecutor {
    @Autowired
    GraphService graphService;

    @Override
    public void Execute(String[] params) {
        var edgeNum = graphService.GetEdgeNum();
        var vertexNum = graphService.GetVertexNum();
        var connectiveDomainNum = graphService.GetConnectiveDomainNum();
        System.out.println("Edge: " + edgeNum);
        System.out.println("Vertex: " + vertexNum);
        System.out.println("ConnectiveDomain: " + connectiveDomainNum);
    }
}