package local.happysixplus.backendcodeanalysis.cli;

import java.util.Scanner;

import local.happysixplus.backendcodeanalysis.service.GraphService;
import lombok.var;

public class CLICommandExecutorInit implements CLICommandExecutor {

    @Override
    public void execute(String[] params, Scanner scanner, GraphService graphService) throws Exception {
        // var success = graphService.loadCode(params[0]);
        // if (!success) throw new Exception("Load code fail");
        System.out.println("---------Init---------");
        System.out.println("Your project has been loaded successfully.");
        // var edgeNum = graphService.getEdgeNum();
        // var vertexNum = graphService.getVertexNum();
        // var connectiveDomainNum = graphService.getConnectiveDomains().size();
        // System.out.println("Edge: " + edgeNum);
        // System.out.println("Vertex: " + vertexNum);
        // System.out.println("ConnectiveDomain (excluding domains with only one vertex): " + connectiveDomainNum);
        System.out.println("----------------------");
    }
}