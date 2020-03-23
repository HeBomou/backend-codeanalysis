package local.happysixplus.backendcodeanalysis.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.service.SessionService;
import local.happysixplus.backendcodeanalysis.vo.SessionVo;
import lombok.var;
import local.happysixplus.backendcodeanalysis.data.UserData;

@Service
public class SessionServiceImpl implements SessionService {
    private UserData userData;

    @Override
    public void addSession(SessionVo vo, HttpServletRequest request) {
        HttpSession session = request.getSession();
        var po = userData.findByUsername(vo.getUsername());
        if (po == null) {
            // 抛出该用户不存在异常
        }
        if (!po.getPwdMd5().equals(vo.getPwdMd5())) {
            // 抛出密码错误异常
        }
        Object value = session.getAttribute("user");
        if (value == null) {
            session.setAttribute("user", po.getId());
        }
        if (value != null && session.getAttribute("user").equals(session.getId())) {
            System.out.println("don't need login");
        }
    };

    @Override
    public void removeSession(HttpServletRequest request) {
        System.out.println("remove Session");
    };
}