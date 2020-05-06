package local.happysixplus.backendcodeanalysis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.data.GroupData;
import local.happysixplus.backendcodeanalysis.data.GroupNoticeData;
import local.happysixplus.backendcodeanalysis.data.GroupUserRelData;
import local.happysixplus.backendcodeanalysis.data.UserData;
import local.happysixplus.backendcodeanalysis.service.GroupService;
import local.happysixplus.backendcodeanalysis.vo.GroupNoticeVo;
import local.happysixplus.backendcodeanalysis.vo.GroupVo;
import local.happysixplus.backendcodeanalysis.vo.UserVo;

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

    @Override
    public void addGroup(GroupVo vo) {

    }

    @Override
    public void removeGroup(Long id) {

    }

    @Override
    public void updateGroup(GroupVo vo) {

    }

    @Override
    public List<GroupVo> getAllGroup(Long userId) {
        return null;
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