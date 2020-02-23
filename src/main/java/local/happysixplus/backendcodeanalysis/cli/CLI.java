package local.happysixplus.backendcodeanalysis.cli;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import lombok.var;

public class CLI {

    Map<String, CLICommandExecutor> executorMap = new HashMap<>();

    public CLI() {
        executorMap.put("echo", new CLICommandExecutorEcho());
    }

    public void Deal(String cmd) {
        String[] strs = cmd.split(" ");
        var cmdType = strs[0];
        String[] params = Arrays.copyOfRange(strs, 1, strs.length);
        executorMap.get(cmdType).Execute(params);
    }
}