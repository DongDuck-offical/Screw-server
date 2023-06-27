package dongduck.screw.web.controller;

import dongduck.screw.apiResponse.ApiResponse;
import dongduck.screw.config.token.Token;
import dongduck.screw.config.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequiredArgsConstructor
public class TokenApiController {
    private final TokenService tokenService;

    //프론트 서버로부터 인가코드를 받아서 로그인 처리
    // -> 시큐리티가 요청을 먼저 낚아 채서 컨트롤러가 별도로 필요 없다고 앎.


    //로그인 완료 후에 토큰 발급이 제대로 되었는지 확인
    @GetMapping("/getToken")
    public ResponseEntity<?> success(Model model,
                                     @RequestParam(value = "username", required = false) String username,
                                     @RequestParam(value = "token", required = false) String token){
        model.addAttribute("username",username);
        model.addAttribute("token",token);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("1","토큰 발급 성공",token));
    }



    //토큰 재발급 요청
    @GetMapping("/token/refresh")
    public String refreshAuth(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Refresh");

        if (token != null && tokenService.verifyToken(token)) {
            String email = tokenService.getUid(token);
            Token newToken = tokenService.generateToken(email, "USER");

            response.addHeader("Auth", newToken.getToken());
            response.addHeader("Refresh", newToken.getRefreshToken());
            response.setContentType("application/json;charset=UTF-8");

            return "HAPPY NEW TOKEN";
        }

        return "error !!";
    }
}
