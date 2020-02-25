package local.happysixplus.backendcodeanalysis.cli;

import local.happysixplus.backendcodeanalysis.service.GraphService;
import lombok.var;

public class CLICommandExecutorBasicAttribute implements CLICommandExecutor {

    @Override
    public void execute(String[] params, GraphService graphService) {
        var edgeNum = graphService.getEdgeNum();
        var vertexNum = graphService.getVertexNum();
        var connectiveDomainNum = graphService.getConnectiveDomainNum();
        System.out.println("Edge: " + edgeNum);
        System.out.println("Vertex: " + vertexNum);
        System.out.println("ConnectiveDomain: " + connectiveDomainNum);
    }
}