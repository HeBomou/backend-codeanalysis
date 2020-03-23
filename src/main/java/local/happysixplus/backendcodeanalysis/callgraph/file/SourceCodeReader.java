package local.happysixplus.backendcodeanalysis.callgraph.file;



import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SourceCodeReader {
    private static final String defaultPrePath = "src/main/resources/temp/";
    private static final String defaultSufPath = "/src/main/java/";
    private char[] c;
    private boolean commitSign1 = false;
    private boolean commitSign2 = false;
    private boolean stringSign = false;
    private boolean charSign = false;
    private boolean isInMethod = false;
    private boolean isInMember = false;
    private String projectName;

    public SourceCodeReader(String projectName) {
        this.projectName = projectName;
    }

    //TODO:加载函数源码到数组里
    public Map<String,String> getSourceCodeFromFile(String filePath) {
        String path = defaultPrePath + projectName + defaultSufPath;
        String className = getClassName(filePath);
        Map<String,String> res=new HashMap<>();
        try {
            //将.java文件的内容加载到char数组中
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String tempStr;
            StringBuilder tempStringBuilder = new StringBuilder();
            while ((tempStr = br.readLine()) != null)
                tempStringBuilder.append(tempStr).append('\n');
            tempStr = tempStringBuilder.toString();
            int len = tempStr.length();
            c = new char[len];
            for (int i = 0; i < len; i++) {
                c[i] = tempStr.charAt(i);
            }
            int beginPos = -1;
            int endPos;
            int num = 0;// 花括号的数量
            boolean isInClass = false;
            for (int i = 0; i < len; i++) {
                //段注释结束
                if (commitSign2 && c[i] == '/' && c[i - 1] == '*') {
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

                if (commitSign1 || commitSign2 || stringSign || charSign) {
                    continue;
                }

                //遇到= 表示直到分号都不属于某个函数
                if (c[i] == '=' && !isInMethod) {
                    isInMember = true;
                    continue;
                }
                //遇到; 且前边有'=' 表明有成员变量的声明
                if (isInMember && c[i] == ';') {
                    isInMember = false;
                    beginPos = i + 1;
                    continue;
                }

                if (c[i] == ';' && num == 0) {
                    beginPos = i + 1;
                    isInMember = false;

                    continue;
                }
                if (!isInMethod && c[i] == ';') {
                    isInMember = false;
                    beginPos = i + 1;
                    continue;
                }
                //方法内的域
                if (c[i] == '{' && isInClass) {
                    if (num == 0) {
                        isInMethod = true;
                    }
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
                    String str = getMethod(beginPos, endPos);

                    if (str != null){
                        String s=filePath.replace(path, "").replaceAll("/", ".");
                        res.put(s.substring(0,s.length()-5)+ ":" + getMethodName(str, className) + getMethodParameters(str),str);
                    }
                    beginPos = i + 1;
                    num = 0;
                    resetSigns();
                }


                if (c[i] == '{' && !isInClass) {
                    isInClass = true;
                    isInMethod = true;
                    isInMember = false;
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
        isInMember = false;
    }

    private String getClassName(String filePath) {
        String[] strings = filePath.split("/");
        String[] strs = strings[strings.length - 1].split("\\.");
        return strs[0];
    }

    private String getMethod(int beginPos, int endPos) {
        StringBuilder sb = new StringBuilder();
        for (int i = beginPos; i <= endPos; i++) {
            sb.append(c[i]);
        }
        return parse(sb.toString());
    }

    private String parse(String str) {
        int len = str.length();
        char[] durex = new char[len];
        for (int i = 0; i < len; i++) {
            durex[i] = str.charAt(i);
        }
        //ArrayList<String> res = new ArrayList<>();
        boolean c1 = false;
        boolean c2 = false;
        for (int i = 0; i < len; i++) {
            if (durex[i] == '/' && durex[i + 1] == '/') {
                c1 = true;
                continue;
            }
            if (c1 && durex[i] == '\n') {
                c1 = false;
                continue;
            }
            if (durex[i] == '/' && durex[i + 1] == '*') {
                c2 = true;
                continue;
            }
            if (c2 && durex[i] == '/' && durex[i - 1] == '*') {
                c2 = false;
                continue;
            }


            if(!c1 && !c2){
                if (Character.isAlphabetic(str.charAt(i)) || str.charAt(i)=='@') {
                    return str.substring(i);
                }
            }


        }
        return null;
    }

    private String getMethodName(String str, String className) {
        int len = str.length();
        char[] durex = new char[len];
        for (int i = 0; i < len; i++) {
            durex[i] = str.charAt(i);
        }
        StringBuilder sb = new StringBuilder();
        boolean c1 = false;
        boolean c2 = false;
        boolean seprated = false;
        for (int i = 0; i < len; i++) {
            if (durex[i] == '/' && durex[i + 1] == '/') {
                c1 = true;
                continue;
            }
            if (c1 && durex[i] == '\n') {
                c1 = false;
                continue;
            }
            if (durex[i] == '/' && durex[i + 1] == '*') {
                c2 = true;
                continue;
            }
            if (c2 && durex[i] == '/' && durex[i - 1] == '*') {
                c2 = false;
                continue;
            }
            if (!c1 & !c2) {
                if (Character.isAlphabetic(durex[i])) {
                    if (seprated) {
                        sb = new StringBuilder().append(durex[i]);
                        seprated = false;
                    } else {
                        sb.append(durex[i]);
                    }
                } else if (durex[i] == ' ' || durex[i] == '\n') {
                    seprated = true;
                } else if (durex[i] == '(') {
                    return sb.toString().equals(className)?"<init>":sb.toString();
                }
            }

        }
        return null;
    }

    private String getMethodParameters(String str) {
        int len = str.length();
        char[] durex = new char[len];
        for (int i = 0; i < len; i++) {
            durex[i] = str.charAt(i);
        }
        ArrayList<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean c1 = false;
        boolean c2 = false;
        boolean started = false;
        boolean seprated = false;
        int signNum = 0;
        for (int i = 0; i < len; i++) {
            if (durex[i] == '/' && durex[i + 1] == '/') {
                c1 = true;
                continue;
            }
            if (c1 && durex[i] == '\n') {
                c1 = false;
                continue;
            }
            if (durex[i] == '/' && durex[i + 1] == '*') {
                c2 = true;
                continue;
            }
            if (c2 && durex[i] == '/' && durex[i - 1] == '*') {
                c2 = false;
                continue;
            }
            if (!c1 & !c2) {
                if (durex[i] == '(') {
                    started = true;
                } else if (durex[i] == ')' && started) {
                    break;
                } else if (started) {
                    if (durex[i] == '<') {
                        signNum++;
                        continue;
                    }
                    if (signNum != 0) {
                        if (durex[i] == '>') {
                            signNum--;
                        }
                        continue;
                    }
                    if (durex[i] == ' ' && sb.length() != 0) {
                        seprated = true;
                        continue;
                    }
                    if(durex[i]==','){
                        //res.add(sb.toString().replace(" ",""));
                        sb=new StringBuilder();
                        continue;
                    }
                    if (seprated && (Character.isAlphabetic(durex[i]) || durex[i] == '$' || durex[i] == '¥' || durex[i] == '_')) {
                        res.add(sb.toString().replace(" ", ""));
                        sb = new StringBuilder();
                        seprated = false;
                        continue;
                    }
                    if (!seprated && durex[i] != ' ') {
                        sb.append(durex[i]);
                        continue;
                    }

                    if (durex[i] == ',') {
                        seprated = false;
                        sb = new StringBuilder();
                    }

                }
            }

        }
        if (res.size() == 0) {
            return "()";
        }

        sb = new StringBuilder().append("(");
        sb.append(res.get(0));
        for (int i = 1; i < res.size(); i++) {
            sb.append(",").append(res.get(i));
        }
        return sb.append(")").toString();


    }
}
