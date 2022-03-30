package br.com.raospower.app.exceptions.base;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Set;

public class ErrorResponse {

    private Integer code;
    private String status;
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date timestamp;
    private Set<ErrorResponse> errors;

    public ErrorResponse(Integer code, String status, String message, Date timestamp, Set<ErrorResponse> errors) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.errors = (errors == null) ? Set.of() : errors;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Set<ErrorResponse> getErrors() {
        return errors;
    }

    public void setErrors(Set<ErrorResponse> errors) {
        this.errors = errors;
    }
}
