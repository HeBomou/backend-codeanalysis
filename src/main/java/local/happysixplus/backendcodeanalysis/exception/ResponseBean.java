package local.happysixplus.backendcodeanalysis.exception;

import lombok.Data;

@Data
public class ResponseBean<T> {
    private Boolean success;
    private T data;
    private String errCode;
    private String errMsg;

    public ResponseBean() {
    }

    public ResponseBean(Boolean success, T data) {
        super();
        this.success = success;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseBean{" + "success=" + success + ", data=" + data + ", errCode='" + errCode + '\'' + ", errMsg='"
                + errMsg + '\'' + '}';
    }

    public ResponseBean(Boolean success, T data, String errCode, String errMsg) {
        super();
        this.success = success;
        this.data = data;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ResponseBean(Boolean success, String errCode, String errMsg) {
        this.success = success;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ResponseBean(Boolean success, ResponseEnums enums) {
        this.success = success;
        this.errCode = enums.getCode();
        this.errMsg = enums.getMsg();
    }

    public ResponseBean(Boolean success, T data, ResponseEnums enums) {
        this.success = success;
        this.data = data;
        this.errCode = enums.getCode();
        this.errMsg = enums.getMsg();
    }
}