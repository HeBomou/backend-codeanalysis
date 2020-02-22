package local.happysixplus.backendcodeanalysis.controller;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class DemoController {

    @GetMapping(value="/")
    public String getMethodName() {
        return "Hello world";
    }
    
}