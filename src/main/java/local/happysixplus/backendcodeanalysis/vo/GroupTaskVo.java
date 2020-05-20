package local.happysixplus.backendcodeanalysis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupTaskVo {
    Long id;

    Long groupId;

    String name;

    String info;

    String deadline;

    int isFinished;
}