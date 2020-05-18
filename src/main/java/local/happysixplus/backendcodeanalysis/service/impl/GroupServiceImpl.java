package local.happysixplus.backendcodeanalysis.service.impl;

import java.util.ArrayList;
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
import local.happysixplus.backendcodeanalysis.po.GroupPo;
import local.happysixplus.backendcodeanalysis.po.GroupUserRelPo;
import local.happysixplus.backendcodeanalysis.service.GroupService;
import local.happysixplus.backendcodeanalysis.vo.GroupNoticeVo;
import local.happysixplus.backendcodeanalysis.vo.GroupVo;
import local.happysixplus.backendcodeanalysis.vo.UserVo;
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
        groupNoticeData.deleteById(id);
        groupUserRelData.deleteById(id);
        groupTaskData.deleteById(id);
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

    }

    @Override
    public void removeMember(Long groupId, Long userId) {

    }

    @Override
    public void updateMember(Long groupId, Long userId, int level) {

    }

    @Override
    public int getMemberLevel(Long groupId, Long userId) {
        return 0;
    }

    @Override
    public List<UserVo> getMembers(Long groupId) {
        return null;
    }

    @Override
    public void addNotice(GroupNoticeVo vo) {

    }

    @Override
    public void removeNotice(Long id) {

    }

    @Override
    public void updateNotice(GroupNoticeVo vo) {

    }

    @Override
    public List<GroupNoticeVo> getNotice(Long groupId) {
        return null;
    }

}