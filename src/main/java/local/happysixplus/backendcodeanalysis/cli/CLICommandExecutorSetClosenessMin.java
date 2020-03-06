package local.happysixplus.backendcodeanalysis.cli;

import java.util.Scanner;

import local.happysixplus.backendcodeanalysis.service.GraphService;

public class CLICommandExecutorSetClosenessMin implements CLICommandExecutor {

    @Override
    public void execute(String[] params, Scanner scanner, GraphService graphService) {
        graphService.setClosenessMin(Double.valueOf(params[0]));
        System.out.println("-------Threshold------");
        System.out.println("Number of connective domains in inital graph: " + graphService.getConnectiveDomainNum());
        System.out.println("Set closeness threshold " + Double.valueOf(params[0]));

        System.out.println("Number of connective domains after setting threshold (not including domain with only one vertex): " + graphService.getConnectiveDomainsWithClosenessMin().size());
        System.out.println("----------------------");
    }
}