package local.happysixplus.backendcodeanalysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.springframework.boot.test.context.SpringBootTest;

import local.happysixplus.backendcodeanalysis.cli.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Scanner;
import java.io.*;

import lombok.var;

@SpringBootTest
class CliTests {
    CLI cli = new CLI();

    //输出流
	static ByteArrayOutputStream outContent;
	//系统原控制台输出流
    static PrintStream consoleOut = null;
    //系统原输入流
    static InputStream consoleIn = null;


	@BeforeAll
	public static void setUp(){

		consoleOut = System.out;
		//把标准输出定向至ByteArrayOutputStream中
		outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        consoleIn = System.in;
   
	}

	@AfterAll              // 后处理
	public static void tearDown() {
   
       System.setOut(consoleOut);
       System.setIn(consoleIn);
	}

    @Test
    void SetClosenessMin(){
        try{
            testCLI("testcases/testcase6/test_case6.txt", "testcases/testcase6/expected6.txt");
        }catch (Exception e){
            //读取测试文件异常
            e.printStackTrace();
            assert(false);
        }
    }
    @Test
    void BasicAttribute(){
        try{
            testCLI("testcases/testcase4/test_case4.txt", "testcases/testcase4/expected4.txt");
        }catch (Exception e){
            //读取测试文件异常
            e.printStackTrace();
            assert(false);
        }

    }


    @Test
    void BasicAttribute2(){
        //多边的情况
        try{
            testCLI("testcases/testcase10/test_case10.txt", "testcases/testcase10/expected10.txt");
        }catch (Exception e){
            //读取测试文件异常
            e.printStackTrace();
            assert(false);
        }

    }


    @Test
    void ConnectiveDomain(){
        try{
            testCLI("testcases/testcase5/test_case5.txt", "testcases/testcase5/expected5.txt");
        }catch (Exception e){
            //读取测试文件异常
            e.printStackTrace();
            assert(false);
        }

    }

    @Test
    void ShortestPath(){
        try{
            testCLI("testcases/testcase9/test_case9.txt", "testcases/testcase9/expected9.txt");
            

        }catch (Exception e){
            //读取测试文件异常
            e.printStackTrace();
            assert(false);
        }finally{
            System.setIn(consoleIn);
        }

    }

    @Test
    void WrongeArg(){
        try{
            testCLI("testcases/testcase17/test_case17.txt", "testcases/testcase17/expected17.txt");
            

        }catch (Exception e){
            //读取测试文件异常
            e.printStackTrace();
            assert(false);
        }finally{
            System.setIn(consoleIn);
        }

    }


    /**
     * 读取文件对CLI测试
     * @param argFileName 输入文件路径
     * @param ExpFileName 结果文件路径
     */
    void testCLI(String argFileName, String ExpFileName) throws Exception{
        outContent.reset();

        try{
            //输入重定向到测试用例文件
            FileInputStream fis = new FileInputStream(argFileName);
            System.setIn(fis);
            Scanner scanner=new Scanner(System.in);

            while (true) {
                instruction: {
                    try {
                        System.out.println("Welcome to Code Analysis by Happy6+");
                        System.out.println("First let's go through checkpoint 2, 4 and 5.");
                        // 依次调用检查点2、4、5的命令
                        System.out.println();
                        System.out.print("Please input the path to your project: ");
                        var path = scanner.nextLine().trim();
                        if (!cli.deal(("init " + path).split(" "), scanner))
                            break instruction;
                        System.out.println();
                        System.out.print("Please input the closeness threshold: ");
                        var threshold = Double.valueOf(scanner.nextLine().trim());
                        if (!cli.deal(("set-closeness-min " + threshold).split(" "), scanner))
                            break instruction;
                        System.out.print("Do you want to show vertices of each domain? (y/N) ");
                        if (scanner.nextLine().trim().toLowerCase().equals("y"))
                            cli.deal("connective-domain-with-closeness-min".split(" "), scanner);
                        System.out.println();
                        if (!cli.deal("shortest-path".split(" "), scanner))
                            break instruction;
                        System.out.println();
                    } catch (Exception e) {
                        break instruction;
                    }
                    break;
                }
    
                System.out.println("There're some errors in your input, we will restart the check.");
                System.out.println();
            }
    
            System.out.print("The check is finished, then you can try our interactive program. (y/N) ");
            if (scanner.nextLine().trim().toLowerCase().equals("y")){
                cli.printHelloMessage();
                while (true) {
                    try {
                        var cmd = scanner.nextLine().trim();
                        if (cmd.equals(""))
                            continue;
                        if (cmd.equals("quit"))
                            break;
                        cli.deal(cmd.split(" "), scanner);
                    } catch (Exception e) {
                        System.out.println("命令有误");
                        e.printStackTrace();
                    }
                }
                scanner.close();
            }
    
            
            //输出写入到文件
            File file =new File("test_appendfile.txt");
 
            if(!file.exists()){
        	    file.createNewFile();
             }
 
            //使用true，即进行append file 
 
            FileWriter fileWritter = new FileWriter(file.getName(), false);
 
 
            fileWritter.write(outContent.toString());
 
            fileWritter.close();

            String exp = readFile(ExpFileName);
            assertEquals(exp, outContent.toString());

        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }finally{
            //输入流重新定位到System.in
            System.setIn(consoleIn);
        }
    }

    /**
     * 读取文本文件，转为string返回
     * @param fileName 文件路径
     * @return
     * @throws Exception
     */
    String readFile(String fileName) throws Exception{
        try{
            InputStream is = new FileInputStream(fileName);
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            is.close();
            return new String(bytes);

        } catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        
    }

    
}