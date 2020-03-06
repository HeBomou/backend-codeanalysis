package local.happysixplus.backendcodeanalysis.cli;

import java.util.Scanner;

import local.happysixplus.backendcodeanalysis.service.GraphService;
import lombok.var;

public class CLICommandExecutorSetClosenessMin implements CLICommandExecutor {

    @Override
    public void execute(String[] params, Scanner scanner, GraphService graphService) {
        graphService.setClosenessMin(Double.valueOf(params[0]));
        System.out.println("-------Threshold------");
        System.out.println("Number of connective domains in inital graph: " + graphService.getConnectiveDomainNum());
        System.out.println("Set closeness threshold " + Double.valueOf(params[0]));

        var domains = graphService.getConnectiveDomainsWithClosenessMin();
        int cnt = 0;
        for (var domain : domains)
            if (domain.getVertexVos().size() != 1) cnt++;
        System.out.println("Number of connective domains after setting threshold (including domains with only one vertex): " + domains.size());
        System.out.println("Number of connective domains after setting threshold (not including domain with only one vertex): " + cnt);
        System.out.println("----------------------");
    }
}