package local.happysixplus.backendcodeanalysis.po;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目所有信息，包括源代码，初始图的结构等静态信息，以及用户的注释等动态信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProjectPo {

    @Id
    Long id;

    Long userId;

    String projectName;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "projectId")
    Set<VertexPo> vertices;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "projectId")
    Set<EdgePo> edges;

    /**
     * 设置了阈值的所有子图，包括阈值为零的默认子图
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "projectId")
    Set<SubgraphPo> subgraphs;
}