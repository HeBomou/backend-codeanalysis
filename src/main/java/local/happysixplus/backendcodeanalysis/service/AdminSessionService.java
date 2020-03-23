package local.happysixplus.backendcodeanalysis.service;

import javax.servlet.http.HttpServletRequest;
import local.happysixplus.backendcodeanalysis.vo.AdminSessionVo;

public interface AdminSessionService {

    /**
     * 管理员登陆获取一个Session
     * @param vo
     */
    void addSession(AdminSessionVo vo, HttpServletRequest request);

    /**
     * 管理员登出移除对应的Session
     */
    void removeSession(String id, HttpServletRequest request);
}