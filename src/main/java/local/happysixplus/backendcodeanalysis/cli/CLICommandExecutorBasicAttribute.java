package local.happysixplus.backendcodeanalysis.cli;

import java.util.Scanner;

import local.happysixplus.backendcodeanalysis.service.GraphService;
import lombok.var;

public class CLICommandExecutorBasicAttribute implements CLICommandExecutor {

    @Override
    public void execute(String[] params, Scanner scanner, GraphService graphService) {
        System.out.println("------Basic-Attr------");
        var edgeNum = graphService.getEdgeNum();
        var vertexNum = graphService.getVertexNum();
        var connectiveDomainNum = graphService.getConnectiveDomains().size();
        System.out.println("Edge: " + edgeNum);
        System.out.println("Vertex: " + vertexNum);
        System.out.println("ConnectiveDomain (excluding domains with only one vertex): " + connectiveDomainNum);
        System.out.println("----------------------");
    }
}