package local.happysixplus.backendcodeanalysis.data;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import local.happysixplus.backendcodeanalysis.po.GroupUserRelPo;

public interface GroupUserRelData extends JpaRepository<GroupUserRelPo, Long> {

    List<GroupUserRelPo> findByUserId(Long userId);

    List<GroupUserRelPo> findByGroupId(Long groupId);

    GroupUserRelPo findByGroupIdAndUserId(Long groupId, Long userId);

    @Modifying
    @Transactional
    void deleteByGroupId(Long groupId);

    @Modifying
    @Transactional
    void deleteByGroupIdAndUserId(Long groupId, Long userId);

}