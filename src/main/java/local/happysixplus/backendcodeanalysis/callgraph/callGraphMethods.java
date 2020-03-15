package local.happysixplus.backendcodeanalysis.callgraph;

import java.util.ArrayList;

public interface CallGraphMethods{
    /**
     *
     * @param githubLink 项目的github连接，要求public项目，且完全符合maven规范
     *
     * @param projectName 项目名称，要求和github项目根目录名称相同
     *
     *
     * 输出的代码依赖图在
     * @retrun 正常则返回0 否则返回-1
     */
    public int initGraph(String githubLink,String projectName);

    /**
     *
     * @param projectName 项目名
     * @param methodPath 方法的完整路径
     * @param parameters 方法的参数列表，只需要提供类名，例如：Java.util.Scanner，则只需要提供"Scanner"
     * @return 返回需要的源码内容
     */


    public String[] getSourceCode(String projectName, String methodPath, ArrayList<String> parameters);
}
