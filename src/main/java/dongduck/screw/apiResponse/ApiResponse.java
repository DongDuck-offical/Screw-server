package dongduck.screw.apiResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;


//성공 시에 {
//  "code": "1",
//  "message": "~~기능 수행 성공",
//  "data": "반환할 데이터"
//}
//실패 시에 {
//  "code": "에러 코드",
//  "message": "~~기능 수행 실패",
//  "data": "공백"
//}
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ApiResponse<T> {

    private String code;
    private String message;
    private T data;

    //에러 코드 생성
    public static ApiResponse createErrorResponse(String errorCode, String errorMessage) {
        return ApiResponse.builder()
                .code(errorCode)
                .message(errorMessage)
                .build();
    }

    public static ApiResponse createErrorResponse(String errorCode, BindingResult bindingResult) {
        return ApiResponse.builder()
                .code(errorCode)
                .message(createErrorMessage(bindingResult))
                .build();
    }

    private static String createErrorMessage(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(!isFirst) {
                sb.append("\n");
            } else {
                isFirst = false;
            }
            sb.append("[");
            sb.append(fieldError.getField());
            sb.append("] ");
            sb.append(fieldError.getDefaultMessage());
        }

        return sb.toString();
    }
}
