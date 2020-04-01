package local.happysixplus.backendcodeanalysis.data;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.EdgePo;

public interface EdgeData extends JpaRepository<EdgePo, Long> {
}