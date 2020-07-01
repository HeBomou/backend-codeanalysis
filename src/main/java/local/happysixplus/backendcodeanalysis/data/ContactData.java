package local.happysixplus.backendcodeanalysis.data;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import local.happysixplus.backendcodeanalysis.po.ContactPo;

@Repository
public interface ContactData extends JpaRepository<ContactPo, Long> {

    List<ContactPo> findByUserId(Long userId);

    Boolean existsByUserIdAndContactUserId(Long userId, Long contactUserId);

    @Modifying
    @Transactional
    void deleteByUserIdAndContactUserId(Long userId, Long contactUserId);

}