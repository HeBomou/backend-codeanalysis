package local.happysixplus.backendcodeanalysis.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import local.happysixplus.backendcodeanalysis.service.SessionService;
import local.happysixplus.backendcodeanalysis.vo.SessionVo;
import lombok.var;
import local.happysixplus.backendcodeanalysis.data.UserData;
import local.happysixplus.backendcodeanalysis.exception.MyRuntimeException;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    UserData userData;

    @Override
    public Long addSession(SessionVo vo, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        var po = userData.findByUsername(vo.getUsername());
        if (po == null)
            throw new MyRuntimeException("该用户不存在");
        if (!po.getPwdMd5().equals(vo.getPwdMd5())) {
            throw new MyRuntimeException("密码错误");
        }
        Object value = session.getAttribute("user");
        if (value == null) {
            session.setAttribute("user", po.getId().toString());
        }
        return po.getId();
    };

    @Override
    public void removeSession(String id, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null && session.getAttribute("user").equals(id)) {
            session.removeAttribute("user");
        }
    };
}