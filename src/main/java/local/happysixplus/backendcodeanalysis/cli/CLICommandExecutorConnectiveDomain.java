package local.happysixplus.backendcodeanalysis.cli;

import local.happysixplus.backendcodeanalysis.service.GraphService;
import lombok.var;

public class CLICommandExecutorConnectiveDomain implements CLICommandExecutor {

    @Override
    public void Execute(String[] params, GraphService graphService) {
        var domains = graphService.GetConnectiveDomains();
        for (var domain : domains) {
            System.out.println(domain.getVertexNum());
        }
    }
}