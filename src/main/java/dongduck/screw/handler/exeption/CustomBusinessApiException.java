package dongduck.screw.handler.exeption;

import dongduck.screw.handler.ErrorCode;
import lombok.Getter;

@Getter
public class CustomBusinessApiException extends RuntimeException{

    private ErrorCode errorCode;
    public CustomBusinessApiException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
