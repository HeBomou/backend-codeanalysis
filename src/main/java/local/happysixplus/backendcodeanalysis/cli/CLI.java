package local.happysixplus.backendcodeanalysis.cli;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import local.happysixplus.backendcodeanalysis.service.GraphService;
import local.happysixplus.backendcodeanalysis.service.impl.GraphServiceImpl;
import lombok.var;

public class CLI {
    GraphService graphService = new GraphServiceImpl();

    Map<String, CLICommandExecutor> executorMap = new HashMap<>();

    public CLI() {
        executorMap.put("basic-attribute", new CLICommandExecutorBasicAttribute());
        executorMap.put("connective-domain", new CLICommandExecutorConnectiveDomain());
        executorMap.put("connective-domain-with-closeness-min",
                new CLICommandExecutorConnectiveDomainWithClosenessMin());
        executorMap.put("echo", new CLICommandExecutorEcho());
        executorMap.put("help", new CLICommandExecutorHelp());
        executorMap.put("init", new CLICommandExecutorInit());
        executorMap.put("set-closeness-min", new CLICommandExecutorSetClosenessMin());
        executorMap.put("shortest-path", new CLICommandExecutorShortestPath());
    }

    public void printHelloMessage() {
        System.out.println("Code Analysis version 0.1 by Happy6+");
        System.out.println();
        System.out.println("Example usage:");
        System.out.println("basic-attribute");
        System.out.println("connective-domain");
        System.out.println("set-closeness-min [VALUE]");
        System.out.println("quit");
        System.out.println("You can type 'help' for further help.");
        System.out.println();
        System.out.println("Type the following command to init your project.");
        System.out.println("init [PATH]");
    }

    public boolean deal(String[] args, Scanner scanner) {
        if (args.length == 0)
            return false;
        var cmdType = args[0];
        String[] params = Arrays.copyOfRange(args, 1, args.length);
        try {
            executorMap.get(cmdType).execute(params, scanner, graphService);
        } catch (Exception e) {
            System.out.println("There're some errors in your input.");
            return false;
        }
        return true;
    }
}