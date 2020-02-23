package local.happysixplus.backendcodeanalysis.cli;

import local.happysixplus.backendcodeanalysis.service.GraphService;
import lombok.var;

public class CLICommandExecutorBasicAttribute implements CLICommandExecutor {

    @Override
    public void Execute(String[] params, GraphService graphService) {
        var edgeNum = graphService.GetEdgeNum();
        var vertexNum = graphService.GetVertexNum();
        var connectiveDomainNum = graphService.GetConnectiveDomainNum();
        System.out.println("Edge: " + edgeNum);
        System.out.println("Vertex: " + vertexNum);
        System.out.println("ConnectiveDomain: " + connectiveDomainNum);
    }
}