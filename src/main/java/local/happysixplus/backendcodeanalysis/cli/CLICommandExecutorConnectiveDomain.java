package local.happysixplus.backendcodeanalysis.cli;

import java.util.Scanner;

import local.happysixplus.backendcodeanalysis.service.GraphService;
import lombok.var;

public class CLICommandExecutorConnectiveDomain implements CLICommandExecutor {

    @Override
    public void execute(String[] params, Scanner scanner, GraphService graphService) {
        var domains = graphService.getConnectiveDomains();
        for (int i = 0; i < domains.size(); i++) {
            System.out.println("Domain " + i + " , vertex:" + domains.get(i).getVertexNum());
            var edges = domains.get(i).getEdgeVos();
            for (var edge : edges)
                System.out.println(edge.getFrom().getFunctionName() + "--" + edge.getCloseness() + "-->"
                        + edge.getTo().getFunctionName());
            System.out.println();
        }
    }
}