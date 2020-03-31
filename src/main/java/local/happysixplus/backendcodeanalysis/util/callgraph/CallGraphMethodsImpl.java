package local.happysixplus.backendcodeanalysis.util.callgraph;

import local.happysixplus.backendcodeanalysis.util.callgraph.stat.JCallGraph;
import local.happysixplus.backendcodeanalysis.util.callgraph.shell.*;
import local.happysixplus.backendcodeanalysis.util.callgraph.file.*;

import java.io.File;
import java.util.*;

import org.springframework.stereotype.Component;

@Component
public class CallGraphMethodsImpl implements CallGraphMethods {
    /*public CallGraphMethodsImpl() {
        JavaShellUtil.InitCommand(rm);
        JavaShellUtil.InitCommand(clone);
    }*/

    @Override
    public ProjectInfo initGraph(String githubLink, String projectName) {
        deleteFile(projectName);
        cloneProject(githubLink, projectName);
        Map<String,String> srcCode = new HashMap<>();
        loadSourceCode(srcCode, projectName);
        Set<String> rpn=new HashSet<>();
        for(String s:srcCode.keySet()){
            rpn.add(s.split("\\.")[0]);
        }
        ArrayList<String> cg = new ArrayList<>();
        String[] list = new File("temp/" + projectName + "/target").list();
        if (list == null)
            return null;
        for (String s : list) {
            //System.out.println(s);
            if (s.endsWith(".jar")) {
                String[] tp = JCallGraph.getGraphFromJar("temp/" + projectName + "/target/" + s, rpn);
                if (tp == null) {
                    return null;
                }
                List<String> tempList = Arrays.asList(tp);
                cg.addAll(tempList);
            }
        }
        //JCallGraph.getGraphFromJar("src/main/resources/temp/" + projectName + "/target/" + "Hello-1.0-SNAPSHOT.jar", projectName);  
        /*for(String key:srcCode.keySet()){
            System.out.println("-----------------");
            System.out.println(key);
            System.out.println("-----");
            System.out.println(srcCode.get(key));
            System.out.println("-----------------");
        }*/

        
        String[] callGraph=cg.toArray(new String[0]);
        //System.out.println(callGraph.length);
        /*for(String s:callGraph){
            System.out.println(s);
        }*/
        Map<String,String> code=match(callGraph,srcCode);
        deleteFile(projectName);

        /*for(String key:code.keySet()){
            System.out.println("-----------------");
            System.out.println(key);
            System.out.println("-----");
            System.out.println(code.get(key)==null?" ":code.get(key));
            System.out.println("-----------------");
        }*/

        return new ProjectInfo(callGraph, code);
    }

    private void cloneProject(String githubLink, String projectName) {
        //JavaShellUtil.ExecCommand(clone, projectName + " " + githubLink);
        JavaShellUtil.ExecClone(projectName,githubLink);
        //System.out.println(retCode);
    }

    private void deleteFile(String projectName) {
        JavaShellUtil.ExecRm(projectName);
        //System.out.println(retCode);
    }
    private void loadSourceCode(Map<String,String> map, String projectName) {

        ArrayList<String> javaFilePaths = getAllJavaFile("temp/" + projectName + "/src");
        SourceCodeReader scReader = new SourceCodeReader();
        if (javaFilePaths == null) return;
        /*for (int i = 0; i < javaFilePaths.size(); i++) {
            ArrayList<String> tempStrings = scReader.getSourceCodeFromFile(javaFilePaths.get(i));
            res.addAll(tempStrings);
        }*/
        for (String s : javaFilePaths) {
            Map<String,String> temp= scReader.getSourceCodeFromFile(s);
            map.putAll(temp);
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


    private Map<String,String> match(String[] graph, Map<String,String> map){
        Map<String,String> res=new HashMap<>();
        for(String s:graph){
            //System.out.println(s);
            String[] durex=s.split(" ");
            String method1=durex[0].substring(2);
            String method2=durex[1].substring(3);
            //System.out.println("1:"+method1);
            //System.out.println("2:"+method2);
            String simplifiedMethod1 = parse(durex[0].substring(2));
            String simplifiedMethod2 = parse(durex[1].substring(3));
            if(!res.containsKey(simplifiedMethod1)){
                String dau=map.get(simplifiedMethod1);
                res.put(method1,dau==null?"":dau);
            }
            if(!res.containsKey(simplifiedMethod2)){
                String dau=map.get(simplifiedMethod2);
                res.put(method2,dau==null?"":dau);
            }

        }
        return res;
    }
    private String parse(String str1){
        StringBuilder sb=new StringBuilder();
        int index1 = str1.indexOf('(');
        int index2 = str1.indexOf(')');
        sb.append(str1, 0, index1+1);
        if(index2==index1+1){
            sb.append(')');
            return sb.toString();
        }
        String parameters=str1.substring(index1+1,index2);
        String[] parameterList=parameters.split(",");
        for(int i=0;i<parameterList.length;i++){
            String[] tempStrs=parameterList[i].split("\\.");
            parameterList[i]=tempStrs[tempStrs.length-1];
        }
        sb.append(String.join(",",parameterList)).append(")");
        return sb.toString();
    }

}


