package local.happysixplus.backendcodeanalysis.controller;

import org.springframework.web.bind.annotation.RestController;

import local.happysixplus.backendcodeanalysis.vo.GroupVo;
import local.happysixplus.backendcodeanalysis.vo.UserVo;
import local.happysixplus.backendcodeanalysis.vo.GroupNoticeVo;

import local.happysixplus.backendcodeanalysis.service.GroupService;

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
@RequestMapping(value = "/group")
public class GroupController {
    @Autowired
    GroupService service;


    //添加新的小组
    @PostMapping
    public void postGroup(@RequestBody GroupVo vo) throws Exception{
        service.addGroup(vo);
    }

    //删除小组
    @DeleteMapping(value="/{id}")
    public void deleteGroup(@PathVariable Long id){
        service.removeGroup(id);
    }

    //更新xiao zu xin xi
    @PutMapping(value="/{groupId}")
    public void putGroup(@PathVariable Long groupId,@RequestBody GroupVo vo){
        vo.setId(groupId);
        service.updateGroup(vo);
    }

    //获取某用户小组列表
    @GetMapping(value = "/getgroup/{userId}")
    public List<GroupVo> getAllGroup(@PathVariable Long userId){
        return service.getAllGroup(userId);
    }

    //添加新的小组成员
    @PostMapping(value="/{groupId}/add/{userId}")
    public void postMember(@PathVariable Long groupId, @PathVariable Long userId, @RequestParam String inviteCode){
        service.addMember(groupId, inviteCode, userId);
    }

    //删除小组成员
    @DeleteMapping(value="/{groupId}/remove/{userId}")
    public void deleteMember(@PathVariable Long groupId,@PathVariable Long userId){
        service.removeMember(groupId, userId);
    }

    //修改组员权限
    @PutMapping(value="/{groupId}/authority/{userId}")
    public void putMember(@PathVariable Long groupId,@PathVariable Long userId,@RequestParam String level){
        service.updateMember(groupId, userId, level);
    }

    //获取组员等级，判断是否有权限进行操作
    @GetMapping(value="/{groupId}/authority/{userId}")
    public String getAuthorityLevel(@PathVariable Long groupId,@PathVariable Long userId){
        return service.getMemberLevel(groupId, userId);
    }

    //获取小组成员列表
    @GetMapping(value="/getuser/{groupId}")
    public List<UserVo> getMembers(@PathVariable Long groupId){
        return service.getMembers(groupId);
    }

    //添加公告
    @PostMapping(value="/notice")
    public void postNotice(@RequestBody GroupNoticeVo vo){
        service.addNotice(vo);
    }

    //删除公告
    @DeleteMapping(value="/notice/{id}")
    public void deleteNotice(@PathVariable Long id){
        service.removeNotice(id);
    }

    //修改公告
    @PutMapping(value="/notice/{id}")
    public void putNotice(@PathVariable Long id,@RequestBody GroupNoticeVo vo){
        vo.setId(id);
        service.updateNotice(vo);
    }

    //获取小组公告
    @GetMapping(value="/notice/{groupId}")
    public List<GroupNoticeVo> getNotice(@PathVariable Long groupId){
        return service.getNotice(groupId);
    }

    
}