package local.happysixplus.backendcodeanalysis.callgraph;

import javafx.util.Pair;
import local.happysixplus.backendcodeanalysis.callgraph.stat.JCallGraph;
import local.happysixplus.backendcodeanalysis.callgraph.shell.*;
import local.happysixplus.backendcodeanalysis.callgraph.file.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CallGraphMethodsImpl implements CallGraphMethods {
    private static final String rm = "src/main/resources/Scripts/rm.sh";
    private static final String clone = "src/main/resources/Scripts/clone.sh";

    public CallGraphMethodsImpl() {
        JavaShellUtil.InitCommand(rm);
        JavaShellUtil.InitCommand(clone);
    }

    @Override
    public Pair<String[], ArrayList<String>> initGraph(String githubLink, String projectName) {
        cloneProject(githubLink, projectName);
        ArrayList<String> cg=new ArrayList<>();
        String[] list=new File("src/main/resources/temp/"+projectName+"/target").list();
        for (String s:list){
            //System.out.println(s);
            if (s.endsWith(".jar")){
                String[] tp=JCallGraph.getGraphFromJar("src/main/resources/temp/" + projectName + "/target/"+s , projectName);
                if(tp==null){
                    return null;
                }
                List<String> tempList=Arrays.asList(tp);
                cg.addAll(tempList);
            }
        }
        //JCallGraph.getGraphFromJar("src/main/resources/temp/" + projectName + "/target/" + "Hello-1.0-SNAPSHOT.jar", projectName);

        Pair<String[], ArrayList<String>> result = new Pair<String[], ArrayList<String>>(cg.toArray(new String[cg.size()]), new ArrayList<String>());
        loadSourceCode(result.getValue(), projectName);
        deleteFile(projectName);
        /*for(int i=0;i<cg.size();i++){
            System.out.println(cg.get(i));
        }*/
        return null;
    }

    private void cloneProject(String githubLink, String projectName) {
        int retCode = JavaShellUtil.ExecCommand(clone, projectName + " " + githubLink);
        //System.out.println(retCode);
    }

    private void deleteFile(String projectName) {
        int retCode = JavaShellUtil.ExecCommand(rm, projectName);
        //System.out.println(retCode);

    }

    private void loadSourceCode(ArrayList<String> res, String projectName) {
        ArrayList<String> javaFilePaths = getAllJavaFile("src/main/resources/temp/" + projectName + "/src/main/java");
        SourceCodeReader scReader = new SourceCodeReader(projectName);
        for (int i = 0; i < javaFilePaths.size(); i++) {
            ArrayList<String> tempStrings = scReader.getSourceCodeFromFile(javaFilePaths.get(i));
            res.addAll(tempStrings);
        }

        /*for(int i=0;i<res.size();i++){
            System.out.println("-------------");
            System.out.println(res.get(i));
            System.out.println("-------------");

        }*/
    }

    private ArrayList<String> getAllJavaFile(String path) {
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        LinkedList<File> list = new LinkedList<>();
        ArrayList<String> res = new ArrayList<>();
        if (file.exists()) {
            if (null == file.listFiles()) {
                return null;
            }
            list.addAll(Arrays.asList(file.listFiles()));
            while (!list.isEmpty()) {
                File[] files = list.removeFirst().listFiles();
                if (null == files) {
                    continue;
                }
                for (File f : files) {
                    if (f.isDirectory()) {
                        //System.out.println("文件夹:" + f.getAbsolutePath());
                        list.add(f);
                        folderNum++;
                    } else if (f.getName().endsWith(".java")) {
                        //System.out.println("文件:" + f.getPath());
                        res.add(f.getPath());
                        fileNum++;
                    }
                }
            }
        } else {
            //System.out.println("文件不存在!");
        }
        //System.out.println("文件夹数量:" + folderNum + ",文件数量:" + fileNum);
        return res;
    }
}


