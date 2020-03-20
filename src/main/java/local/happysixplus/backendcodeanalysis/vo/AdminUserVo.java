package local.happysixplus.backendcodeanalysis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminUserVo {

    Long id;

    String username;

    String pwdMd5;

    String inviteCode;
}