package local.happysixplus.backendcodeanalysis.callgraph;

import java.util.Map;


public class ProjectInfo {
    private String[] callGraph;
    private Map<String,String> sourceCode;

    public ProjectInfo(String[] callGraph,Map<String,String> sourceCode){
        this.callGraph=callGraph;
        this.sourceCode=sourceCode;
    }

    public String[] getCallGraph(){
        return callGraph;
    }

    public Map<String,String> getSourceCode(){
        return sourceCode;
    }

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
