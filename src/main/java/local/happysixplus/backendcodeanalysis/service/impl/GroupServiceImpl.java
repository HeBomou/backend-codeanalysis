package local.happysixplus.backendcodeanalysis.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.data.GroupData;
import local.happysixplus.backendcodeanalysis.data.GroupNoticeData;
import local.happysixplus.backendcodeanalysis.data.GroupTaskData;
import local.happysixplus.backendcodeanalysis.data.GroupUserRelData;
import local.happysixplus.backendcodeanalysis.data.TaskUserRelData;
import local.happysixplus.backendcodeanalysis.data.UserData;
import local.happysixplus.backendcodeanalysis.exception.MyRuntimeException;
import local.happysixplus.backendcodeanalysis.po.GroupNoticePo;
import local.happysixplus.backendcodeanalysis.po.GroupPo;
import local.happysixplus.backendcodeanalysis.po.GroupUserRelPo;
import local.happysixplus.backendcodeanalysis.service.GroupService;
import local.happysixplus.backendcodeanalysis.vo.GroupMemberVo;
import local.happysixplus.backendcodeanalysis.vo.GroupNoticeVo;
import local.happysixplus.backendcodeanalysis.vo.GroupVo;
import lombok.var;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupData groupData;

    @Autowired
    GroupNoticeData groupNoticeData;

    @Autowired
    GroupUserRelData groupUserRelData;

    @Autowired
    UserData userData;

    @Autowired
    GroupTaskData groupTaskData;

    @Autowired
    TaskUserRelData taskUserRelData;

    @Override
    public void addGroup(GroupVo vo) {
        char[] CHARS = new char[] { 'F', 'L', 'G', 'W', '5', 'X', 'C', '3', '9', 'Z', 'M', '6', '7', 'Y', 'R', 'T', '2',
                'H', 'S', '8', 'D', 'V', 'E', 'J', '4', 'K', 'Q', 'P', 'U', 'A', 'N', 'B' };
        String inviteCode = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int k = Math.abs(random.nextInt() % 32);
            inviteCode += String.valueOf(CHARS[k]);
        }
        var po = new GroupPo(null, vo.getLeaderId(), vo.getName(), inviteCode);
        po = groupData.save(po);
        groupUserRelData.save(new GroupUserRelPo(null, po.getId(), po.getLeaderId(), "leader"));
    }

    @Override
    public void removeGroup(Long id) {
        groupData.deleteById(id);
        groupNoticeData.deleteByGroupId(id);
        groupUserRelData.deleteByGroupId(id);
        groupTaskData.deleteByGroupId(id);
        taskUserRelData.deleteByGroupId(id);
    }

    @Override
    public void updateGroup(GroupVo vo) {
        groupData.save(new GroupPo(vo.getId(), vo.getLeaderId(), vo.getName(), vo.getInviteCode()));
    }

    @Override
    public List<GroupVo> getAllGroup(Long userId) {
        var pos = groupUserRelData.findByUserId(userId);
        var groupIds = new ArrayList<Long>(pos.size());
        for (var po : pos)
            groupIds.add(po.getGroupId());
        var groupPos = groupData.findByIdIn(groupIds);
        var res = new ArrayList<GroupVo>();
        for (var po : groupPos)
            res.add(new GroupVo(po.getId(), po.getLeaderId(), po.getName(), po.getInviteCode()));
        return res;
    }

    @Override
    public void addMember(Long groupId, String inviteCode, Long userId) {
        var po = groupData.findById(groupId).orElse(null);
        if (!inviteCode.equals(po.getInviteCode()))
            throw new MyRuntimeException("邀请码错误");
        groupUserRelData.save(new GroupUserRelPo(null, groupId, userId, "member"));
    }

    @Override
    public void removeMember(Long groupId, Long userId) {
        groupUserRelData.deleteByGroupIdAndUserId(groupId, userId);
        taskUserRelData.deleteByUserIdAndGroupId(userId, groupId);
    }

    @Override
    public void updateMember(Long groupId, Long userId, String level) {
        var po = groupUserRelData.findByGroupIdAndUserId(groupId, userId);
        po.setLevel(level);
        groupUserRelData.save(po);
    }

    @Override
    public String getMemberLevel(Long groupId, Long userId) {
        var po = groupUserRelData.findByGroupIdAndUserId(groupId, userId);
        return po.getLevel();
    }

    @Override
    public List<GroupMemberVo> getMembers(Long groupId) {
        var pos = groupUserRelData.findByGroupId(groupId);
        var userIds = new ArrayList<Long>(pos.size());
        var mapList = new HashMap<Long, String>();
        for (var po : pos) {
            userIds.add(po.getUserId());
            mapList.put(po.getUserId(), po.getLevel());
        }
        var userPos = userData.findByIdIn(userIds);
        var res = new ArrayList<GroupMemberVo>();
        for (var po : userPos)
            res.add(new GroupMemberVo(po.getId(), po.getUsername(), mapList.get(po.getId())));
        return res;
    }

    @Override
    public void addNotice(GroupNoticeVo vo) { 
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        var po = new GroupNoticePo(null, vo.getGroupId(), vo.getTitle(), vo.getContent(), df.format(new Date()));
        groupNoticeData.save(po);
    }

    @Override
    public void removeNotice(Long id) {
        groupNoticeData.deleteById(id);
    }

    @Override
    public void updateNotice(GroupNoticeVo vo) {
        var po = groupNoticeData.findById(vo.getId()).orElse(null);
        po.setContent(vo.getContent());
        po.setTitle(vo.getTitle());
        groupNoticeData.save(po);
    }

    @Override
    public List<GroupNoticeVo> getNotice(Long groupId) {
        var pos = groupNoticeData.findAllByGroupId(groupId);
        var res = new ArrayList<GroupNoticeVo>();
        for (var po : pos)
            res.add(new GroupNoticeVo(po.getId(), po.getGroupId(), po.getTitle(), po.getContent(), po.getTime()));
        return res;
    }

}