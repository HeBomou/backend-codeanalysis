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
        String text = "";
        while (text.equals(""))
            text = scanner.nextLine().trim();
        var fullFuncs = service.getSimilarVertex(text);
        if (fullFuncs.size() == 0) {
            ps.println("There is no vertex matching your input.");
            return null;
        }
        int idx = 0;
        if (fullFuncs.size() != 1) {

            System.out.println("-----Match-Result-----");
            for (int i = 0; i < fullFuncs.size(); i++)
                ps.println((i + 1) + ". " + fullFuncs.get(i));
            System.out.println("----------------------");
            ps.println(
                    "There are some vertices matching your input, please input the number of the vertex you choose.");
            idx = Integer.parseInt(scanner.nextLine()) - 1;
        }
        if (0 <= idx && idx < fullFuncs.size()) {
            System.out.println("The vertex you choose is " + fullFuncs.get(idx));
            return fullFuncs.get(idx);
        } else {
            ps.println("The index " + idx + " is invalid.");
            return null;
        }
    }

    void printPath(String funcA, String funcB, PathVo pathVo) {
        System.out.println("---------Path---------");
        System.out.println("Source vertex: " + funcA);
        System.out.println("Target vertex: " + funcB);
        var paths = pathVo.getPaths();
        System.out.println("Total path num: " + paths.size());
        int cnt = 0;
        for (var edges : paths) {
            cnt++;
            if (cnt != 1)
                System.out.println("-----");
            if (edges.size() > 0) {
                System.out.print("Path " + cnt + ": ");
                System.out.println(edges.get(0).getFrom().getFunctionName());
                for (var edge : edges)
                    System.out.println("--" + edge.getCloseness() + "-->" + edge.getTo().getFunctionName());
            }
        }
        System.out.println("----------------------");
    }

    @Override
    public void execute(String[] params, Scanner scanner, GraphService graphService) {
        System.out.println("Follow the instruction and search the shortest path.");
        exec: {
            var funcA = getFullFuncName(graphService, scanner, System.out,
                    "Please input source vertex[class/method name]");
            if (funcA == null)
                break exec;
            var funcB = getFullFuncName(graphService, scanner, System.out,
                    "Please input target vertex[class/method name]");
            if (funcB == null)
                break exec;
            var path = graphService.getShortestPath(new VertexVo(funcA), new VertexVo(funcB));
            printPath(funcA, funcB, path);
        }
    }
}