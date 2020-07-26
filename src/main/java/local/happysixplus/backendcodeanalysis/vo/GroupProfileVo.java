package local.happysixplus.backendcodeanalysis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupProfileVo {

    Long id;

    String name;

    Integer noticeNum;

    Integer taskNum;

    Integer memberNum ;
}