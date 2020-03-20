package local.happysixplus.backendcodeanalysis.po;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class UserPo {

    @Id
    Long id;

    String username;

    String pwdMd5;
}