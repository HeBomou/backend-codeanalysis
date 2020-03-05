package local.happysixplus.backendcodeanalysis.cli;

import java.util.Scanner;

import local.happysixplus.backendcodeanalysis.service.GraphService;
import lombok.var;

public class CLICommandExecutorConnectiveDomainWithClosenessMin implements CLICommandExecutor {

    @Override
    public void execute(String[] params, Scanner scanner, GraphService graphService) {
        var domains = graphService.getConnectiveDomainsWithClosenessMin();
        int cnt = 0;
        // TODO: 应当给联通域打上id用于识别，输出
        for (int i = 0; i < domains.size(); i++) {
            if (domains.get(i).getVertexNum() == 1) continue;
            System.out.println("Domain " + ++cnt + " , vertex:" + domains.get(i).getVertexNum());
            var edges = domains.get(i).getEdgeVos();
            for (var edge : edges)
                System.out.println(edge.getFrom().getFunctionName() + "--" + edge.getCloseness() + "-->"
                        + edge.getTo().getFunctionName());
            System.out.println();
        }
    }
}