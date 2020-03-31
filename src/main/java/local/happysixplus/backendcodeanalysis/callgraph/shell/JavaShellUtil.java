package local.happysixplus.backendcodeanalysis.callgraph.shell;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class JavaShellUtil {

    public static void InitCommand(String command) {
        try {
            Process process=Runtime.getRuntime().exec(new String[]{"/bin/sh","-c","chmod +x "+command},null,null);
            ExecOutput(process);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ExecRm(String projectName){
        try{
            String command="rm -rf temp/"+projectName;
            String[] commands=new String[]{"/bin/sh","-c",command};
            Process process=Runtime.getRuntime().exec(commands);
            ExecOutput(process);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void ExecClone(String projectName,String url){
        try{
            String command="mkdir temp/"+projectName+" ; git clone "+url+" temp/"+projectName+" ; cd temp/"+projectName+" ;"+" mvn package";
            String[] commands=new String[]{"/bin/sh","-c",command};
            Process process=Runtime.getRuntime().exec(commands,null,null);
            ExecOutput(process);


        }catch(Exception e){
            e.printStackTrace();
        }


    }

    public static void ExecCommand(String command,String parameters) {
        try {
            //Process process0=Runtime.getRuntime().exec(new String[]{"/bin/sh","-c","chmod +x "+command},null,null);
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command+" "+parameters}, null, null);
            if(!ExecOutput(process)){
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean ExecOutput(Process process) throws Exception {
        if (process == null) {
            return false;
        } else {
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = input.readLine()) != null) {
                output.append(line).append("\n");
            }
            if(output.toString().equals("ILovedHerasdasdasdashgdkjskhdfsjkfh")){
                return true;
            }
            input.close();
            ir.close();
        }
        return true;
    }
}