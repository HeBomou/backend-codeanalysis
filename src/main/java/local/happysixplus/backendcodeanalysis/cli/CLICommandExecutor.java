package local.happysixplus.backendcodeanalysis.cli;

import local.happysixplus.backendcodeanalysis.service.GraphService;

public interface CLICommandExecutor {

    void Execute(String[] params, GraphService graphService);
}