package local.happysixplus.backendcodeanalysis.callgraph;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;


public class ProjectData {
    private String[] callGraph;
    private Map<String,String> sourceCode;
    private Node rootNode;

    public ProjectData(String[] callGraph,Map<String,String> sourceCode,Node rootNode){
        this.callGraph=callGraph;
        this.sourceCode=sourceCode;
        this.rootNode=rootNode;
    }

    public String[] getCallGraph(){
        return callGraph;
    }

    public Map<String,String> getSourceCode(){
        return sourceCode;
    }

    public Node getRootNode(){return rootNode;}

    /**
     * 打印这个对象，方便测试使用。
     */
    /*public void printProjectData(){
        for(String s : this.callGraph){
            System.out.println(s);
            System.out.println();
        }
        for(String s : this.sourceCode){
            System.out.println(s);
            System.out.println();
        }
        rootNode.printNode(0);
    }*/

}
