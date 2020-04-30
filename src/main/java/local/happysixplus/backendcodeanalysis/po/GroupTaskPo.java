package local.happysixplus.backendcodeanalysis.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GroupTaskPo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long groupId;

    String name;

    String info;

    String deadline;

    Boolean isFinished;

}