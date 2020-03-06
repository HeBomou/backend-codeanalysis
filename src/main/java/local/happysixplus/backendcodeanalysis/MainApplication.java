package local.happysixplus.backendcodeanalysis;

import java.util.Scanner;

// import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import local.happysixplus.backendcodeanalysis.cli.CLI;
import lombok.var;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		// SpringApplication.run(MainApplication.class, args);
		var cli = new CLI();
		var scanner = new Scanner(System.in);

		while (true) {
			try {
				System.out.println("Welcome to Code Analysis by Happy6+");
				System.out.println("Then we will go through checkpoint 2, 4 and 5.");
				// TODO: 依次调用检查点2、4、5的命令
				System.out.print("Please input the path to your project: ");
				var path = scanner.nextLine();
				cli.deal(("init " + path).split(" "), scanner);
				System.out.print("Please input the closeness threshold: ");
				var threshold = scanner.nextDouble();
				cli.deal(("set-closeness-min " + threshold).split(" "), scanner);
				cli.deal("connective-domain-with-closeness-min".split(" "), scanner);
				cli.deal("shortest-path".split(" "), scanner);
			} catch (Exception e) {
				System.out.println("There're some errors in your command, we will restart the check.");
				continue;
			}
			break;
		}

		cli.printHelloMessage();
		while (true) {
			try {
				var cmd = scanner.nextLine().trim();
				if (cmd.equals(""))
					continue;
				if (cmd.equals("quit"))
					break;
				cli.deal(cmd.split(" "), scanner);
			} catch (Exception e) {
				System.out.println("命令有误");
				e.printStackTrace();
			}
		}
		scanner.close();
	}

}
