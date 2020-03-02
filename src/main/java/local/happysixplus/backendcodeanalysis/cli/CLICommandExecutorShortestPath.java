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
        String text = scanner.next();
        var fullFuncs = service.getSimilarVertex(text);
        if (fullFuncs.size() == 0)
            return null;
        int idx = 0;
        if (fullFuncs.size() != 1) {
            // ps.println("There are many function matching your input, please type the index and enter.");
            // for (int i = 0; i < fullFuncs.size(); i++)
            //     ps.println(i + " :" + fullFuncs.get(i));
            // int idx = scanner.nextInt();
            // if (0 < idx && idx < fullFuncs.size())

        }
        ps.println("The full function is " + fullFuncs.get(idx));
        return fullFuncs.get(idx);
        // return null;
    }

    void printPath(PathVo pathVo) {
        System.out.print(pathVo.getPathNum());
    }

    @Override
    public void execute(String[] params, GraphService graphService) {
        {
            var scanner = new Scanner(System.in);
            var funcA = getFullFuncName(graphService, scanner, System.out, "Please input a function name.");
            var funcB = getFullFuncName(graphService, scanner, System.out, "Please input the other function name.");
            scanner.close();
            var path = graphService.getShortestPath(new VertexVo(funcA), new VertexVo(funcB));
            printPath(path);
            path = graphService.getShortestPath(new VertexVo(funcA), new VertexVo(funcB));
            printPath(path);
        }
    }
}