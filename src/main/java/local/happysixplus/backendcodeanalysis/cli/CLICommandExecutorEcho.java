package local.happysixplus.backendcodeanalysis.cli;

import lombok.var;

public class CLICommandExecutorEcho implements CLICommandExecutor {

    @Override
    public void Execute(String[] params) {
        for (var string : params) {
            System.out.print(string);
            System.out.print(" ");
        }
        System.out.println();
    }
}