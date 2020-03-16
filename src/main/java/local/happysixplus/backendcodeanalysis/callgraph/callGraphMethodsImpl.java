package local.happysixplus.backendcodeanalysis.callgraph;
import local.happysixplus.backendcodeanalysis.callgraph.stat.JCallGraph;
import local.happysixplus.backendcodeanalysis.callgraph.shell.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class CallGraphMethodsImpl implements CallGraphMethods{
    private static final String rm="src/main/resources/Scripts/rm.sh";
    private static final String clone="src/main/resources/Scripts/clone.sh";
    public CallGraphMethodsImpl(){
        JavaShellUtil.InitCommand(rm);
        JavaShellUtil.InitCommand(clone);
    }
    @Override
    /**
     * @param classPath 函数所在的完整路径
     *
     *
     * 如果函数不合法则返回-1
     */

    //TODO：根据完整路径获取函数源码
    public String[] getSourceCode(String username,String projectName, String methodPath, ArrayList<String> parameters){
        return null;
    }
    @Override
    public int initGraph(String username,String githubLink,String projectName) {
        cloneProject(githubLink,projectName);
        String jarName=null; //TODO:获取jar包的名称
        if(getGraphFromJar("src/main/resources/temp/"+projectName+"/target/"+"Hello-1.0-SNAPSHOT.jar","src/main/resources/dependencies/"+projectName+"/"+projectName+".txt",projectName)==-1){
            return -1;
        }
        loadSourceCode();
        deleteFile(projectName);
        return 0;
    }

    private int getGraphFromJar(String jarLocation,String targetTxtLocation,String projectName){
        return JCallGraph.getGraphFromJar(jarLocation,targetTxtLocation,projectName);
    }
    private void cloneProject(String githubLink,String projectName){
        int retCode=JavaShellUtil.ExecCommand(clone,projectName+" "+githubLink);
        //System.out.println(retCode);
    }
    private void deleteFile(String projectName){
        int retCode=JavaShellUtil.ExecCommand(rm,projectName);
        //System.out.println(retCode);

    }
    //TODO:加载函数源码到txt
    private void loadSourceCode(){}

}


