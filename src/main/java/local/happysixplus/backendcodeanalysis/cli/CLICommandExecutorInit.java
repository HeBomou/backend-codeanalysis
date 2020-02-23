package local.happysixplus.backendcodeanalysis.cli;

import org.springframework.beans.factory.annotation.Autowired;

import local.happysixplus.backendcodeanalysis.service.GraphService;

public class CLICommandExecutorInit implements CLICommandExecutor {
    @Autowired
    GraphService graphService;

    @Override
    public void Execute(String[] params) {
        graphService.LoadCode(params[0]);
    }
}