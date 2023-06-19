package dongduck.screw.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //test error
    NOT_FOUND_USER(HttpStatus.NOT_FOUND,"001","사용자를 찾을 수 없습니다."),

    ;
    ErrorCode(HttpStatus httpStatus, String errorCode,String message){
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }
    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
}
