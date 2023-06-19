package dongduck.screw.handler;

import dongduck.screw.apiResponse.ApiResponse;
import dongduck.screw.handler.exeption.CustomBusinessApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * javax.validation.Valid 또는 @Validated binding error가 발생할 경우
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<?> handleBindException(BindException e){
        log.error("handleBindException",e);
        ApiResponse apiResponse = ApiResponse.createErrorResponse(HttpStatus.BAD_REQUEST.toString(), e.getBindingResult());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        ApiResponse apiResponse = ApiResponse.createErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.toString(), e.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(apiResponse);
    }

    /**
     * 비즈니스 로직 에러 -> API전달
     */
    @ExceptionHandler(CustomBusinessApiException.class)
    protected ResponseEntity<?> handleBusinessApiException(CustomBusinessApiException e){
        log.error("handleCustomBusinessAPIException",e);
        ApiResponse apiResponse = ApiResponse.createErrorResponse(e.getErrorCode().getErrorCode(),e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(apiResponse);
    }


    /**
     * 그 외 에러
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleException(Exception e) {
        log.error("Exception", e);
        ApiResponse apiResponse = ApiResponse.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }
}
