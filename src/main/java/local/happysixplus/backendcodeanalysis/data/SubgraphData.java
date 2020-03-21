package local.happysixplus.backendcodeanalysis.data;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.SubgraphPo;

public interface SubgraphData extends JpaRepository<SubgraphPo, Long> {
}