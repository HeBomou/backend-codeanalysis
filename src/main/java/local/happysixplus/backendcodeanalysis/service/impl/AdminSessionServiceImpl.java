package local.happysixplus.backendcodeanalysis.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import local.happysixplus.backendcodeanalysis.data.AdminUserData;
import local.happysixplus.backendcodeanalysis.service.AdminSessionService;
import local.happysixplus.backendcodeanalysis.vo.AdminSessionVo;
import lombok.var;

@Service
public class AdminSessionServiceImpl implements AdminSessionService {

    @Autowired
    AdminUserData adminUserData;

    @Override
    public Long addSession(AdminSessionVo vo, HttpServletRequest request) {
        HttpSession session = request.getSession();
        var po = adminUserData.findByUsername(vo.getUsername());
        if (po == null) {
            // 抛出该用户不存在异常
        }
        if (!po.getPwdMd5().equals(vo.getPwdMd5())) {
            // 抛出密码错误异常
        }
        Object value = session.getAttribute("admin");
        if (value == null) {
            session.setAttribute("admin", po.getId().toString());
        }
        return po.getId();
    };

    @Override
    public void removeSession(String id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") != null && session.getAttribute("admin").equals(id)) {
            session.removeAttribute("admin");
        }
    };
}