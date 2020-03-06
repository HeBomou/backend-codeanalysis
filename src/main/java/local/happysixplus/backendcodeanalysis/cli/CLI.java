package local.happysixplus.backendcodeanalysis.cli;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import local.happysixplus.backendcodeanalysis.service.GraphService;
import local.happysixplus.backendcodeanalysis.service.GraphServiceImpl;
import lombok.var;

public class CLI {
    GraphService graphService = new GraphServiceImpl();

    Map<String, CLICommandExecutor> executorMap = new HashMap<>();

    public CLI() {
        executorMap.put("basic-attribute", new CLICommandExecutorBasicAttribute());
        executorMap.put("connective-domain", new CLICommandExecutorConnectiveDomain());
        executorMap.put("connective-domain-num", new CLICommandExecutorConnectiveDomainNum());
        executorMap.put("connective-domain-with-closeness-min",
                new CLICommandExecutorConnectiveDomainWithClosenessMin());
        executorMap.put("echo", new CLICommandExecutorEcho());
        executorMap.put("help", new CLICommandExecutorHelp());
        executorMap.put("init", new CLICommandExecutorInit());
        executorMap.put("set-closeness-min", new CLICommandExecutorSetClosenessMin());
        executorMap.put("shortest-path", new CLICommandExecutorShortestPath());
    }

    public void printHelloMessage() {
		System.out.println("Code Analysis");
		System.out.println("version 0.1");
		System.out.println("by Happy6+");
		System.out.println();
		System.out.println("Example usage:");
		System.out.println("init [PATH]");
		System.out.println("basic-attribute");
		System.out.println("connective-domain");
		System.out.println("set-closeness-min [VALUE]");
		System.out.println();
		System.out.println("For further help, you can type 'help' and then enter");
    }

    public void deal(String[] args, Scanner scanner) {
        if (args.length == 0)
            return;
        var cmdType = args[0];
        String[] params = Arrays.copyOfRange(args, 1, args.length);
        executorMap.get(cmdType).execute(params, scanner, graphService);
    }
}