package local.happysixplus.backendcodeanalysis.cli;

import org.springframework.beans.factory.annotation.Autowired;

import local.happysixplus.backendcodeanalysis.service.GraphService;
import lombok.var;

public class CLICommandExecutorConnectiveDomainWithClosenessMin implements CLICommandExecutor {
    @Autowired
    GraphService graphService;

    @Override
    public void Execute(String[] params) {
        var domains = graphService.GetConnectiveDomainsWithClosenessMin();
        for (var domain : domains) {
            System.out.println(domain.getVertexNum());
        }
    }
}