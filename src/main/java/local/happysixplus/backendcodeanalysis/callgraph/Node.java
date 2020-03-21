package local.happysixplus.backendcodeanalysis.callgraph;

import java.util.ArrayList;

public class Node {
    public String name;
    public boolean isFolder;
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

    /**
     * 打印，方便测试使用
     * @param num 当前的深度
     */
    public void printNode(int num){
        for(int i = 0; i < num; i++){
            System.out.printf("\t");
        }
        System.out.println("Name : " + this.name);

        for(int i = 0; i < num; i++){
            System.out.printf("\t");
        }
        System.out.println(this.isFolder);
        
        for(Node node : this.contents){
            node.printNode(num + 1);
        }

    }
}
