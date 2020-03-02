package local.happysixplus.backendcodeanalysis.cli;

import java.util.Scanner;

import local.happysixplus.backendcodeanalysis.service.GraphService;
import local.happysixplus.backendcodeanalysis.vo.PathVo;
import local.happysixplus.backendcodeanalysis.vo.VertexVo;
import lombok.var;

public class CLICommandExecutorShortestPath implements CLICommandExecutor {

    void printPath(PathVo pathVo) {
        System.out.print(pathVo.getPathNum());
    }

    @Override
    public void execute(String[] params, GraphService graphService) {
        var scanner=new Scanner(System.in);
        System.out.println("Please input a function name.");
        String a = scanner.next();
        System.out.println("Please input the other function name.");
        String b = scanner.next();
        scanner.close();
        var path = graphService.getShortestPath(new VertexVo(a), new VertexVo(b));
        printPath(path);
        path = graphService.getShortestPath(new VertexVo(b), new VertexVo(a));
        printPath(path);
    }
}