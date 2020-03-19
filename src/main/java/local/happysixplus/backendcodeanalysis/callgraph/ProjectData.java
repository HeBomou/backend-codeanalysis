package local.happysixplus.backendcodeanalysis.callgraph;

import java.util.ArrayList;

public class ProjectData {
    private String[] callGraph;
    private ArrayList<String> sourceCode;
    public ProjectData(String[] callGraph,ArrayList<String> sourceCode){
        this.callGraph=callGraph;
        this.sourceCode=sourceCode;
    }

    public String[] getCallGraph(){
        return callGraph;
    }
    public ArrayList<String> getSourceCode(){
        return sourceCode;
    }

}
