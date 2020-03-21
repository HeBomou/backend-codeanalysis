package local.happysixplus.backendcodeanalysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.springframework.boot.test.context.SpringBootTest;
import local.happysixplus.backendcodeanalysis.callgraph.*;

@SpringBootTest
class CallGraphMethodsTests{
    CallGraphMethods cgm = new CallGraphMethodsImpl();
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
    
    @BeforeEach
	public void clearOutContent() {
        //清空outContent
		outContent.reset();
	}

    @Test
    void test_case1(){
        ProjectData result = cgm.initGraph("https://gitee.com/forsakenspirit/Linux","Linux");
        result.printProjectData();
        //assertEquals("haha", outContent.toString());
    }
}
