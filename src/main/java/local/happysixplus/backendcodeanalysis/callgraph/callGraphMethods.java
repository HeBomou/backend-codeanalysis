package local.happysixplus.backendcodeanalysis.callgraph;

import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface CallGraphMethods{
    /*
     *
     * @param githubLink 项目的github连接，要求public项目，且完全符合maven规范
     *
     * @param projectName 项目名称，要求和github项目根目录名称相同
     *
     *
     * 输出的代码依赖图在
     * @return 正常则返回0 否则返回-1
     */
    public Pair<String[],ArrayList<String>> initGraph(String githubLink, String projectName);
}
