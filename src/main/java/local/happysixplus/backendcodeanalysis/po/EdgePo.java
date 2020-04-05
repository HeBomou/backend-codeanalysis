package local.happysixplus.backendcodeanalysis.po;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = {@Index(columnList = "projectId")})
public class EdgePo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long projectId;

    Long fromId;

    Long toId;

    Double closeness;

}