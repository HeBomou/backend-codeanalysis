package local.happysixplus.backendcodeanalysis;

import java.util.Scanner;

// import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import local.happysixplus.backendcodeanalysis.cli.CLI;
import lombok.var;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		//SpringApplication.run(MainApplication.class, args);
		var cli = new CLI();
		cli.printHelloMessage();
		var scanner=new Scanner(System.in);
		while(true) {
			try {
				var cmd = scanner.nextLine();
				if(cmd.equals("quit")) break;
				cli.deal(cmd.split(" "), scanner);
			} catch (Exception e) {
				System.out.println("命令输入有误");
				e.printStackTrace();
			}
		}
		scanner.close();
	}

}
