package local.happysixplus.backendcodeanalysis.callgraph;
public interface callGraphMethods{
    /**
     *
     * @param githubLink 项目的github连接，要求public项目，且完全符合maven规范
     */
    public void initGraph(String githubLink);
}
