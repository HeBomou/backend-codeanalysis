package local.happysixplus.backendcodeanalysis.callgraph.file;


import java.io.*;
import java.util.ArrayList;

public class SourceCodeReader {
    private final String defaultPrePath = "src/main/resource/temp/";
    private final String defaultSufPath = "/main/java";
    private char[] c;
    private boolean commitSign1 = false;
    private boolean commitSign2 = false;
    private boolean stringSign = false;
    private boolean charSign = false;
    private boolean isInMethod = false;
    private String projectName;

    public SourceCodeReader(String projectName) {
        this.projectName = projectName;
    }

    //TODO:加载函数源码到数组里
    public ArrayList<String> getSourceCodeFromFile(String filePath) {
        String path = defaultPrePath + projectName + defaultSufPath;
        String className = getClassName(filePath);
        ArrayList<String> res = new ArrayList<>();
        try {
            //将.java文件的内容加载到char数组中
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String tempStr = null;
            StringBuilder tempStringBuilder = new StringBuilder();
            while ((tempStr = br.readLine()) != null)
                tempStringBuilder.append(tempStr).append('\n');
            tempStr = tempStringBuilder.toString();
            int len = tempStr.length();
            c = new char[len];
            for (int i = 0; i < len; i++) {
                c[i] = tempStr.charAt(i);
            }
            StringBuilder method = new StringBuilder();
            int beginPos = -1;
            int endPos = -1;
            int num = 0;// 花括号的数量
            boolean isInClass = false;
            for (int i = 0; i < len; i++) {
                //段注释结束
                if (!commitSign2 && c[i] == '*' && c[i + 1] == '/') {
                    commitSign2 = false;
                    continue;
                }
                //行注释结束
                if (commitSign1 && c[i] == '\n') {
                    commitSign1 = false;
                    continue;
                }

                //不在注释中，字符串结尾
                if (!commitSign1 && !commitSign2 && stringSign && c[i] == '\"' && c[i - 1] != '\\') {
                    stringSign = false;
                    continue;
                }

                //不在注释中，字符结尾
                if (!commitSign1 && !commitSign2 && charSign && c[i] == '\'' && c[i - 1] != '\\') {
                    charSign = false;
                    continue;
                }

                //遇到字符串
                if (!commitSign1 && !commitSign2 && !stringSign && c[i] == '\"') {
                    stringSign = true;
                    continue;
                }

                //遇到字符
                if (!commitSign1 && !commitSign2 && !charSign && c[i] == '\'') {
                    charSign = true;
                    continue;
                }

                //遇到行注释
                if (!commitSign1 && !commitSign2 && c[i] == '/' && c[i + 1] == '/') {
                    commitSign1 = true;
                    continue;
                }

                //遇到段注释
                if (!commitSign1 && !commitSign2 && c[i] == '/' && c[i + 1] == '*') {
                    commitSign2 = true;
                    continue;
                }
                //方法内的域
                if (c[i] == '{' && isInClass) {
                    num++;
                    continue;
                }
                //方法内的域结束
                if (c[i] == '}' && isInClass && num != 1) {
                    num--;
                    continue;
                }
                //方法结束
                if (c[i] == '}' && isInClass && num == 1) {
                    endPos = i;
                    String str=getMethod(beginPos, endPos);
                    if(str!=null)
                        res.add(str);
                    beginPos = i + 1;
                    num=0;
                    resetSigns();
                }


                if (c[i] == '{' && !isInClass) {
                    isInClass = true;
                    isInMethod = true;
                    num = 0;
                    beginPos = i + 1;
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    private void resetSigns() {
        commitSign1 = false;
        commitSign2 = false;
        stringSign = false;
        charSign = false;
        isInMethod = false;
    }

    private String getClassName(String filePath) {
        String[] strings = filePath.split("/");
        String[] strs= strings[strings.length - 1].split("\\.");
        return strs[0];
    }

    private String getMethod(int beginPos, int endPos) {


        char[] durex = new char[endPos - beginPos + 1];
        for (int i = beginPos; i <= endPos; i++) {
            durex[i - beginPos] = c[i];
        }
        int len = endPos - beginPos + 1;
        int begin = 0;
        boolean cs2 = false;
        boolean cs1 = false;
        boolean cs = false;
        boolean ss = false;
        StringBuilder res=new StringBuilder();
        for (int i = 0; i < len; i++) {
            if (!cs2 && durex[i] == '*' && durex[i + 1] == '/') {
                cs2 = false;
                beginPos=i+2;
                continue;
            }
            //行注释结束
            if (cs1 && durex[i] == '\n') {
                cs1 = false;
                beginPos=i+1;
                continue;
            }

            //不在注释中，字符串结尾
            if (!cs1 && !cs2 && ss && durex[i] == '\"' && durex[i - 1] != '\\') {
                ss = false;
                continue;
            }

            //不在注释中，字符结尾
            if (!cs2 && !cs1 && cs && durex[i] == '\'' && durex[i - 1] != '\\') {
                cs = false;
                continue;
            }

            //遇到字符串
            if (!cs1 && !cs2 && !ss && durex[i] == '\"') {
                ss = true;
                continue;
            }

            //遇到字符
            if (!cs1 && !cs2 && !ss && durex[i] == '\'') {
                cs = true;
                continue;
            }

            //遇到行注释
            if (!cs1 && !cs2 && durex[i] == '/' && durex[i + 1] == '/') {
                cs1 = true;
                continue;
            }

            //遇到段注释
            if (!cs1 && !cs2 && durex[i] == '/' && durex[i + 1] == '*') {
                cs2 = true;
                continue;
            }

            if(durex[i]==';'){
                begin=i+1;
            }

            if(durex[i]=='{'){
                break;
            }

        }
        if(beginPos==-1){
            return null;
        }
        for(int i=begin;i<len;i++){
            res.append(durex[i]);
        }

        return parse(parse(res.toString()));
    }
    private String parse(String str){
        for(int i=0;i<str.length();i++){
            if(Character.isAlphabetic(str.charAt(i))){
                return "\t"+str.substring(i);
            }
        }
        return null;
    }
}
