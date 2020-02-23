package local.happysixplus.backendcodeanalysis.cli;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import lombok.var;

public class CLI {

    Map<String, CLICommandExecutor> executorMap = new HashMap<>();

    public CLI() {
        executorMap.put("basic-attribute", new CLICommandExecutorBasicAttribute());
        executorMap.put("connective-domain", new CLICommandExecutorConnectiveDomain());
        executorMap.put("connective-domain-with-closeness-min", new CLICommandExecutorConnectiveDomainWithClosenessMin());
        executorMap.put("echo", new CLICommandExecutorEcho());
        executorMap.put("init", new CLICommandExecutorInit());
        executorMap.put("set-closeness-min", new CLICommandExecutorSetClosenessMin());
    }

    public void Deal(String cmd) {
        String[] strs = cmd.split(" ");
        var cmdType = strs[0];
        String[] params = Arrays.copyOfRange(strs, 1, strs.length);
        executorMap.get(cmdType).Execute(params);
    }
}