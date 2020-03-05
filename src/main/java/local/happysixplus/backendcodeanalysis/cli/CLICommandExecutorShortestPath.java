package local.happysixplus.backendcodeanalysis.cli;

import java.io.PrintStream;
import java.util.Scanner;

import local.happysixplus.backendcodeanalysis.service.GraphService;
import local.happysixplus.backendcodeanalysis.vo.PathVo;
import local.happysixplus.backendcodeanalysis.vo.VertexVo;
import lombok.var;

public class CLICommandExecutorShortestPath implements CLICommandExecutor {

    String getFullFuncName(GraphService service, Scanner scanner, PrintStream ps, String hint) {
        ps.println(hint);
        String text = scanner.nextLine();
        var fullFuncs = service.getSimilarVertex(text);
        if (fullFuncs.size() == 0) {
            ps.println("There is no function name matching your input.");
            return null;
        }
        int idx = 0;
        if (fullFuncs.size() != 1) {
            ps.println("There are many function matching your input, please type the index and enter.");
            for (int i = 0; i < fullFuncs.size(); i++)
                ps.println(i + " :" + fullFuncs.get(i));
            idx = Integer.parseInt(scanner.nextLine());
        }
        if (0 <= idx && idx < fullFuncs.size()) {
            ps.println("The full function is " + fullFuncs.get(idx));
            return fullFuncs.get(idx);
        } else {
            ps.println("The index " + idx + " is invalid.");
            return null;
        }
    }

    void printPath(String funcA, String funcB, PathVo pathVo) {
        System.out.println("From " + funcA + " to " + funcB);
        System.out.println("Total path num: " + pathVo.getPathNum());
        var edges = pathVo.getPath();
        if (edges.size() > 0) {
            System.out.println("The shortest path:");
            System.out.println(edges.get(0).getFrom().getFunctionName());
            for (var edge : edges)
                System.out.println("--" + edge.getCloseness() + "-->"
                        + edge.getTo().getFunctionName());
        }
    }

    @Override
    public void execute(String[] params, Scanner scanner, GraphService graphService) {
        exec: {
            var funcA = getFullFuncName(graphService, scanner, System.out, "Please input a function name.");
            if (funcA == null)
                break exec;
            var funcB = getFullFuncName(graphService, scanner, System.out, "Please input the other function name.");
            if (funcB == null)
                break exec;
            var path = graphService.getShortestPath(new VertexVo(funcA), new VertexVo(funcB));
            printPath(funcA, funcB, path);
            path = graphService.getShortestPath(new VertexVo(funcB), new VertexVo(funcA));
            printPath(funcB, funcA, path);
        }
    }
}