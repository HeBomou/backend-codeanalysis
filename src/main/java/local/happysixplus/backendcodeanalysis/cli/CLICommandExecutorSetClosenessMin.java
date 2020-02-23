package local.happysixplus.backendcodeanalysis.cli;

import org.springframework.beans.factory.annotation.Autowired;

import local.happysixplus.backendcodeanalysis.service.GraphService;

public class CLICommandExecutorSetClosenessMin implements CLICommandExecutor {
    @Autowired
    GraphService graphService;

    @Override
    public void Execute(String[] params) {
        graphService.SetClosenessMin(Double.valueOf(params[0]));
    }
}