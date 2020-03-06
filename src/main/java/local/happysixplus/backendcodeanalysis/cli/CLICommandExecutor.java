package local.happysixplus.backendcodeanalysis.cli;

import java.util.Scanner;

import local.happysixplus.backendcodeanalysis.service.GraphService;

public interface CLICommandExecutor {

    void execute(String[] params, Scanner scanner, GraphService graphService) throws Exception;
}