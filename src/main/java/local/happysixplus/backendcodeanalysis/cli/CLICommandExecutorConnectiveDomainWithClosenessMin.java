package local.happysixplus.backendcodeanalysis.cli;

import java.util.Scanner;

import local.happysixplus.backendcodeanalysis.service.GraphService;
import lombok.var;

public class CLICommandExecutorConnectiveDomainWithClosenessMin implements CLICommandExecutor {

    @Override
    public void execute(String[] params, Scanner scanner, GraphService graphService) {
        System.out.println("---Threshold-Domain---");
        var domains = graphService.getConnectiveDomainsWithClosenessMin();
        int cnt = 0;
        for (var domain : domains) {
            if (domain.getVertexVos().size() == 1)
                continue;
            if (cnt != 0)
                System.out.println("-----");
            System.out.println("Domain " + ++cnt + ", vertex num: " + domain.getVertexVos().size() + ", edge num: "
                    + domain.getEdgeVos().size());
            var vertexs = domain.getVertexVos();
            for (int i = 0; i < vertexs.size(); i++)
                System.out.println("Vertex " + i + ": " + vertexs.get(i).getFunctionName());
        }
        System.out.println("----------------------");
    }
}