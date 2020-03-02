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
        executorMap.put("init", new CLICommandExecutorInit());
        executorMap.put("set-closeness-min", new CLICommandExecutorSetClosenessMin());
        executorMap.put("shortest-path", new CLICommandExecutorShortestPath());
    }

    public void deal(String[] args, Scanner scanner) {
        if (args.length == 0)
            return;
        var cmdType = args[0];
        String[] params = Arrays.copyOfRange(args, 1, args.length);
        executorMap.get(cmdType).execute(params, scanner, graphService);
    }
}