package local.happysixplus.backendcodeanalysis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.vo.ConnectiveDomainVo;
import local.happysixplus.backendcodeanalysis.vo.PathVo;
import local.happysixplus.backendcodeanalysis.vo.VertexVo;

@Service
public class GraphServiceImpl implements GraphService {

    @Override
    public void LoadCode(String path) {
    }

    @Override
    public int GetVertexNum() {
        return -1;
    }

    @Override
    public int GetEdgeNum() {
        return -1;
    }

    @Override
    public int GetConnectiveDomainNum() {
        return -1;
    }

    @Override
    public List<ConnectiveDomainVo> GetConnectiveDomains() {
        return null;
    }

    @Override
    public void SetClosenessMin(double closeness) {
    }

    @Override
    public List<ConnectiveDomainVo> GetConnectiveDomainsWithClosenessMin() {
        return null;
    }

    @Override
    public PathVo GetShortestPath(VertexVo start, VertexVo end) {
        return null;
    }
}