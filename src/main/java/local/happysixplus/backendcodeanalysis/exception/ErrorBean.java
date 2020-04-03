package local.happysixplus.backendcodeanalysis.exception;

import lombok.Data;

@Data
public class ErrorBean {

    private String errMsg;

    public ErrorBean() {
    }

    public ErrorBean(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "ErrorBean{" + "errMsg='" + errMsg + "'}";
    }

}