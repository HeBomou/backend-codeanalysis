package local.happysixplus.backendcodeanalysis.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.ConnectException;
import java.sql.SQLException;

@RestControllerAdvice(annotations = { RestController.class, Controller.class })
public class MyExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);

    /**
     * 请求参数类型错误异常的捕获
     * 
     * @param e
     * @return
     */
    @ExceptionHandler(value = { BindException.class })
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorBean badRequest(BindException e) {
        logger.error("occurs error when execute method ,message {}", e.getMessage());
        return new ErrorBean("错误的请求参数");
    }

    /**
     * 404错误异常的捕获
     * 
     * @param e
     * @return
     */
    @ExceptionHandler(value = { NoHandlerFoundException.class })
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorBean badRequestNotFound(BindException e) {
        logger.error("occurs error when execute method ,message {}", e.getMessage());
        return new ErrorBean("找不到请求路径");
    }

    /**
     * 自定义异常的捕获 自定义抛出异常。统一的在这里捕获返回JSON格式的友好提示。
     * 
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(value = { MyRuntimeException.class })
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorBean sendError(MyRuntimeException exception, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        logger.error("occurs error when execute url ={} ,message {}", requestURI, exception.getMessage());
        return new ErrorBean(exception.getMessage());
    }

    /**
     * 数据库操作出现异常
     * 
     * @param e
     * @return
     */
    @ExceptionHandler(value = { SQLException.class, DataAccessException.class })
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorBean systemError(Exception e) {
        logger.error("occurs error when execute method ,message {}", e.getMessage());
        return new ErrorBean("数据库异常");
    }

    /**
     * 网络连接失败！
     * 
     * @param e
     * @return
     */
    @ExceptionHandler(value = { ConnectException.class })
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorBean connect(Exception e) {
        logger.error("occurs error when execute method ,message {}", e.getMessage());
        return new ErrorBean("网络连接请求失败");
    }

    @ExceptionHandler(value = { Exception.class })
    @ResponseBody
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorBean notAllowed(Exception e) {
        logger.error("occurs error when execute method ,message {}", e.getMessage());
        return new ErrorBean("不合法的请求方式");
    }

}