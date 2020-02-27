package local.happysixplus.backendcodeanalysis.cli;

import local.happysixplus.backendcodeanalysis.service.GraphService;
import lombok.var;

public class CLICommandExecutorConnectiveDomainNum implements CLICommandExecutor {

    @Override
    public void execute(String[] params, GraphService graphService) {
        var domainNum = graphService.getConnectiveDomainNum();
        System.out.println("Domain: " + domainNum);
    }
}