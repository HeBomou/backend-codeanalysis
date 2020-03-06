package local.happysixplus.backendcodeanalysis.cli;

import java.util.Scanner;

import local.happysixplus.backendcodeanalysis.service.GraphService;
import lombok.var;

public class CLICommandExecutorInit implements CLICommandExecutor {

    @Override
    public void execute(String[] params, Scanner scanner, GraphService graphService) {
        graphService.loadCode(params[0]);
        System.out.println("---------Init---------");
        System.out.println("Your project has been loaded successfully.");
        var edgeNum = graphService.getEdgeNum();
        var vertexNum = graphService.getVertexNum();
        var connectiveDomainNum = graphService.getConnectiveDomainNum();
        System.out.println("Edge: " + edgeNum);
        System.out.println("Vertex: " + vertexNum);
        System.out.println("ConnectiveDomain: " + connectiveDomainNum);
        System.out.println("----------------------");
    }
}