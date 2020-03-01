package local.happysixplus.backendcodeanalysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import local.happysixplus.backendcodeanalysis.service.GraphService;
import local.happysixplus.backendcodeanalysis.vo.PathVo;
import local.happysixplus.backendcodeanalysis.vo.VertexVo;
import local.happysixplus.backendcodeanalysis.vo.EdgeVo;
import local.happysixplus.backendcodeanalysis.cli.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import java.io.*;

import lombok.var;

@SpringBootTest
class CliTests {
    CLI cli = new CLI();

    //输出流
	static ByteArrayOutputStream outContent;
	//系统原控制台输出流
	static PrintStream console = null;

	@BeforeAll
	public static void setUp(){

		console = System.out;
		//把标准输出定向至ByteArrayOutputStream中
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
   
	}

	@AfterAll              // 后处理
	public static void tearDown() {
   
	   System.setOut(console);
	}

    @Test
    void BasicAttribute(){

        outContent.reset();
        String[] args;

        try{

            args = readFile("testcases/test_case4.txt").split("\n");
            String exp = readFile("testcases/expected4.txt");

            cli.deal(args[0].split(" "));
            cli.deal(args[1].split(" "));
            assertEquals(exp, outContent.toString());

        } catch(Exception e){
            e.printStackTrace();
        }



        
    }

    void ConnectiveDomain(){
        outContent.reset();

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