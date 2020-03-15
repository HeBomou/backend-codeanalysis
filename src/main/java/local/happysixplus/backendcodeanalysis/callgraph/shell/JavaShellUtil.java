package local.happysixplus.backendcodeanalysis.callgraph.shell;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class JavaShellUtil {

    public static int InitCommand(String command) {
        int retCode = 0;
        try {
            Process process=Runtime.getRuntime().exec(new String[]{"/bin/sh","-c","chmod +x "+command},null,null);
            retCode = process.waitFor();
            ExecOutput(process);
        } catch (Exception e) {
            retCode = -1;
        }
        return retCode;
    }



    public static int ExecCommand(String command,String parameters) {
        int retCode = 0;
        try {
            //Process process0=Runtime.getRuntime().exec(new String[]{"/bin/sh","-c","chmod +x "+command},null,null);
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command+" "+parameters}, null, null);
            retCode = process.waitFor();
            ExecOutput(process);
        } catch (Exception e) {
            retCode = -1;
        }
        return retCode;
    }

    public static boolean ExecOutput(Process process) throws Exception {
        if (process == null) {
            return false;
        } else {
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            String output = "";
            while ((line = input.readLine()) != null) {
                output += line + "\n";
            }
            input.close();
            ir.close();
            if (output.length() > 0) {
            }
        }
        return true;
    }
}