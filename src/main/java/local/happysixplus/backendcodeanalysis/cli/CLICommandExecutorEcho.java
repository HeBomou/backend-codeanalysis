package local.happysixplus.backendcodeanalysis.cli;

import local.happysixplus.backendcodeanalysis.service.GraphService;
import lombok.var;

public class CLICommandExecutorEcho implements CLICommandExecutor {

    @Override
    public void Execute(String[] params, GraphService graphService) {
        for (var string : params) {
            System.out.print(string);
            System.out.print(" ");
        }
        System.out.println();
    }
}