package local.happysixplus.backendcodeanalysis.callgraph;
import local.happysixplus.backendcodeanalysis.callgraph.stat.JCallGraph;
import local.happysixplus.backendcodeanalysis.callgraph.shell.*;
public class callGraphMethodsImpl implements callGraphMethods{
    private static final String rm="src/main/resources/Scripts/rm.sh";
    private static final String clone="src/main/resources/Scripts/clone.sh";
    public callGraphMethodsImpl(){
        JavaShellUtil.InitCommand(rm);
        JavaShellUtil.InitCommand(clone);
    }

    @Override
    public void initGraph(String githubLink) {
        cloneProject("https://github.com/gousiosg/java-callgraph","SKTFaker");
        deleteFile("SKTFaker");
        //getGraphFromJar(null,"src/main/resources/Scripts/a.txt");
    }

    private void getGraphFromJar(String jarLocation,String targetTxtLocation){
        // JCallGraph.main(jarLocation,targetTxtLocation);
    }
    private void cloneProject(String githubLink,String projectName){
        int retCode=JavaShellUtil.ExecCommand(clone,projectName+" "+githubLink);
        //System.out.println(retCode);
    }
    private void deleteFile(String projectName){
        int retCode=JavaShellUtil.ExecCommand(rm,projectName);
        //System.out.println(retCode);

    }
}
