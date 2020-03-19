package local.happysixplus.backendcodeanalysis.callgraph;

import java.util.ArrayList;

public class Node {
    public String name;
    public boolean isFolder=false;
    public ArrayList<Node> contents;
    public Node(String name,boolean isFolder){
        this.name=name;
        this.isFolder=isFolder;
        if(isFolder){
            contents=new ArrayList<>();
        }
        else{
            contents=null;
        }
    }
    public void addNode(Node n){
        contents.add(n);

    }

}
