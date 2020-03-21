package local.happysixplus.backendcodeanalysis.callgraph;

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
    public ProjectData initGraph(String githubLink, String projectName) {
        cloneProject(githubLink, projectName);
        ArrayList<String> cg = new ArrayList<>();
        String[] list = new File("src/main/resources/temp/" + projectName + "/target").list();
        if (list == null)
            return null;
        for (String s : list) {
            //System.out.println(s);
            if (s.endsWith(".jar")) {
                String[] tp = JCallGraph.getGraphFromJar("src/main/resources/temp/" + projectName + "/target/" + s, projectName);
                if (tp == null) {
                    return null;
                }
                List<String> tempList = Arrays.asList(tp);
                cg.addAll(tempList);
            }
        }
        //JCallGraph.getGraphFromJar("src/main/resources/temp/" + projectName + "/target/" + "Hello-1.0-SNAPSHOT.jar", projectName);
        ArrayList<String> srcCode = new ArrayList<>();
        loadSourceCode(srcCode, projectName);
        File[] rootPackage0 = new File("src/main/resources/temp/" + projectName + "/src/main/java").listFiles();
        File rootPackage1=null;
        if(rootPackage0==null) return null;
        for(File f:rootPackage0){
            if(f.isDirectory() && !f.getName().startsWith(".")){
                rootPackage1=f;
            }
        }
        if(rootPackage1==null) return null;
        if(rootPackage1.list()==null) return null;
        File rootPackage = new File("src/main/resources/temp/" + projectName + "/src/main/java/" + rootPackage1.getName());
        String rootPackageName = rootPackage.getName();
        Node root = new Node(rootPackageName, true);
        loadProjectStructure(root, rootPackage);


        deleteFile(projectName);
        /*for(int i=0;i<cg.size();i++){
            System.out.println(cg.get(i));
        }*/


        return new ProjectData(cg.toArray(new String[0]), srcCode, root);
    }

    private void cloneProject(String githubLink, String projectName) {
        JavaShellUtil.ExecCommand(clone, projectName + " " + githubLink);
        //System.out.println(retCode);
    }

    private void deleteFile(String projectName) {
        JavaShellUtil.ExecCommand(rm, projectName);
        //System.out.println(retCode);

    }

    private void loadSourceCode(ArrayList<String> res, String projectName) {
        ArrayList<String> javaFilePaths = getAllJavaFile("src/main/resources/temp/" + projectName + "/src/main/java");
        SourceCodeReader scReader = new SourceCodeReader(projectName);
        if (javaFilePaths == null) return;
        /*for (int i = 0; i < javaFilePaths.size(); i++) {
            ArrayList<String> tempStrings = scReader.getSourceCodeFromFile(javaFilePaths.get(i));
            res.addAll(tempStrings);
        }*/
        for (String s : javaFilePaths) {
            ArrayList<String> tempStrings = scReader.getSourceCodeFromFile(s);
            res.addAll(tempStrings);
        }

        /*for (int i = 0; i < res.size(); i++) {
            System.out.println("-------------");
            System.out.println(res.get(i));
            System.out.println("-------------");

        }*/
    }

    private ArrayList<String> getAllJavaFile(String path) {
        File file = new File(path);
        LinkedList<File> list = new LinkedList<>();
        ArrayList<String> res = new ArrayList<>();
        if (file.exists()) {
            if (null == file.listFiles()) {
                return null;
            }
            File[] durexxd = file.listFiles();
            if (durexxd == null) {
                return null;
            }
            list.addAll(Arrays.asList(durexxd));
            while (!list.isEmpty()) {
                File[] files = list.removeFirst().listFiles();
                if (null == files) {
                    continue;
                }
                for (File f : files) {
                    if (f.isDirectory()) {
                        //System.out.println("文件夹:" + f.getAbsolutePath());
                        list.add(f);
                    } else if (f.getName().endsWith(".java")) {
                        //System.out.println("文件:" + f.getPath());
                        res.add(f.getPath());
                    }
                }
            }
        }
        //System.out.println("文件夹数量:" + folderNum + ",文件数量:" + fileNum);
        return res;
    }

    private void loadProjectStructure(Node node, File file) {
        File[] sktfaker = file.listFiles();
        if (sktfaker != null)
            for (File f : sktfaker) {
                String name = f.getName();
                if (!name.startsWith(".")) {
                    Node n = new Node(f.getName(), f.isDirectory());
                    if (f.isDirectory()) {
                        loadProjectStructure(n, f);
                    }
                    node.contents.add(n);
                }
            }
    }
}


