package local.happysixplus.backendcodeanalysis.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import local.happysixplus.backendcodeanalysis.data.AdminUserData;
import local.happysixplus.backendcodeanalysis.exception.MyRuntimeException;
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
            throw new MyRuntimeException("该账号不存在");
        }
        if (!po.getPwdMd5().equals(vo.getPwdMd5())) {
            throw new MyRuntimeException("密码错误");
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
        if (session.getAttribute("admin") != null && session.getAttribute("admin").equals(id))
            session.removeAttribute("admin");
        else
            throw new MyRuntimeException("该用户未登录");
    };
}