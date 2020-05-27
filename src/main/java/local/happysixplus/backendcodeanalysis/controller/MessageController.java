package local.happysixplus.backendcodeanalysis.controller;

import org.springframework.web.bind.annotation.RestController;

import local.happysixplus.backendcodeanalysis.service.MessageService;
import local.happysixplus.backendcodeanalysis.vo.MessageVo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "/message")
public class MessageController {
    @Autowired
    MessageService service;

    //添加消息
    @PostMapping
    public void postMessage(@RequestBody MessageVo vo) {
        service.addMessage(vo);
    }

    //删除消息
    @DeleteMapping(value = "/{id}")
    public void deleteMessage(@PathVariable Long id) {
        service.removeMessage(id);
    }

    //更新消息
    @PutMapping(value = "/{id}")
    public void putMessage(@PathVariable Long id, @RequestBody MessageVo vo) {
        vo.setId(id);
        service.updateMessage(vo);
    }

    //获取消息
    @GetMapping
    public List<MessageVo> getMessage(@RequestParam Long senderId, @RequestParam Long receiverId) {
        return service.getMessage(senderId, receiverId);
    }

}