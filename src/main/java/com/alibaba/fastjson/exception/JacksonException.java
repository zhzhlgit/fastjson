package com.alibaba.fastjson.exception;


/**
 * @author duanxinyuan
 * 2019/4/10 22:35
 */
public class JacksonException extends FormativeException {
    private static final long serialVersionUID = -7699177038668159397L;

    public JacksonException() {
        super();
    }

    public JacksonException(String message) {
        super(message);
    }

    public JacksonException(Throwable cause) {
        super(cause);
    }

    public JacksonException(String format, Object... arguments) {
        super(format, arguments);
    }
}
