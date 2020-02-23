package local.happysixplus.backendcodeanalysis;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import local.happysixplus.backendcodeanalysis.cli.CLI;
import lombok.var;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		// SpringApplication.run(MainApplication.class, args);
		var cli = new CLI();
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			try {
				var cmd = br.readLine();
				if(cmd.equals("quit")) break;
				cli.Deal(cmd.split(" "));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
