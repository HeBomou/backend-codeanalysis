package local.happysixplus.backendcodeanalysis.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class DemoController {
    @GetMapping(value="/")
    public String getMethodName() {
        return "Hello world";
    }
}