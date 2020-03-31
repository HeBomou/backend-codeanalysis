package local.happysixplus.backendcodeanalysis.po;

import javax.persistence.Column;
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
public class VertexPo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String functionName;

    @Column(columnDefinition = "MEDIUMTEXT")
    String sourceCode;

    String anotation;

    Float x;

    Float y;

}