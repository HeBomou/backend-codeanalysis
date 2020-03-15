package local.happysixplus.backendcodeanalysis.callgraph;
public interface CallGraphMethods{
    /**
     *
     * @param githubLink 项目的github连接，要求public项目，且完全符合maven规范
     *
     * @param projectName 项目名称，要求和github项目根目录名称相同
     *
     *
     * 输出的代码依赖图在
     */
    public int initGraph(String githubLink,String projectName);
    public String[] getSourceCode(String projectName,String classPath);
}
