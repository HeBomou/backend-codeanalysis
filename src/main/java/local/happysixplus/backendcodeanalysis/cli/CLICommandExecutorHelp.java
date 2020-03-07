package local.happysixplus.backendcodeanalysis.cli;

import java.util.Scanner;

import local.happysixplus.backendcodeanalysis.service.GraphService;

public class CLICommandExecutorHelp implements CLICommandExecutor {

    @Override
    public void execute(String[] params, Scanner scanner, GraphService graphService) {
        // TODO: 需要完成help命令
        System.out.println("init [PATH]");
        System.out.println("basic-attribute");
        System.out.println("connective-domain");
        System.out.println("connective-domain-num");
        System.out.println("set-closeness-min [VALUE]");
        System.out.println("connective-domain-with-closeness-min");
        System.out.println("shortest-path");
        System.out.println("quit");
    }
}