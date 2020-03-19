package local.happysixplus.backendcodeanalysis.callgraph;

import java.util.ArrayList;

public class ProjectData {
    private String[] callGraph;
    private ArrayList<String> sourceCode;
    private Node rootNode;
    public ProjectData(String[] callGraph,ArrayList<String> sourceCode,Node rootNode){
        this.callGraph=callGraph;
        this.sourceCode=sourceCode;
        this.rootNode=rootNode;
    }

    public String[] getCallGraph(){
        return callGraph;
    }
    public ArrayList<String> getSourceCode(){
        return sourceCode;
    }
    public Node getRootNode(){return rootNode;}

}
