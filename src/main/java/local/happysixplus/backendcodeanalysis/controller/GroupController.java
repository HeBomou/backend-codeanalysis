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

    @PostMapping
    public void postGroup(@RequestBody GroupVo vo) throws Exception{
        service.addGroup(vo);
    }

    @DeleteMapping(value="/{id}")
    public void deleteGroup(@PathVariable Long id){
        service.removeGroup(id);
    }

    @PutMapping(value="/{groupId}")
    public void putGroup(@PathVariable Long groupId,@RequestBody GroupVo vo){
        vo.setId(groupId);
        service.updateGroup(vo);
    }

    @GetMapping(value = "/getgroup/{userId}")
    public List<GroupVo> getAllGroup(@PathVariable Long userId){
        return service.getAllGroup(userId);
    }

    @PostMapping(value="/{groupId}/add/{userId}")
    public void postMember(@PathVariable Long groupId, @PathVariable Long userId, @RequestParam String inviteCode){
        service.addMember(groupId, inviteCode, userId);
    }

    @DeleteMapping(value="/{groupId}/remove/{userId}")
    public void deleteMember(@PathVariable Long groupId,@PathVariable Long userId){
        service.removeMember(groupId, userId);
    }

    @PutMapping(value="/{groupId}/authority/{userId}")
    public void putMember(@PathVariable Long groupId,@PathVariable Long userId,@RequestParam int level){
        service.updateMember(groupId, userId, level);
    }

    @GetMapping(value="/{groupId}/authority/{userId}")
    public int getAuthorityLevel(@PathVariable Long groupId,@PathVariable Long userId){
        return service.getMemberLevel(groupId, userId);
    }

    @GetMapping(value="/getuser/{groupId}")
    public List<UserVo> getMembers(@PathVariable Long groupId){
        return service.getMembers(groupId);
    }

    @PostMapping(value="/notice")
    public void postNotice(@RequestBody GroupNoticeVo vo){
        service.addNotice(vo);
    }

    @DeleteMapping(value="/notice/{id}")
    public void deleteNotice(@PathVariable Long id){
        service.removeNotice(id);
    }

    @PutMapping(value="/notice/{id}")
    public void putNotice(@PathVariable Long id,@RequestBody GroupNoticeVo vo){
        vo.setId(id);
        service.updateNotice(vo);
    }

    @GetMapping(value="/notice/{groupId}")
    public List<GroupNoticeVo> getNotice(@PathVariable Long groupId){
        return service.getNotice(groupId);
    }

    
}