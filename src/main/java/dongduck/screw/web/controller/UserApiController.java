package dongduck.screw.web.controller;

import dongduck.screw.dto.user.SignupDto;
import dongduck.screw.dto.user.UserDto;
import dongduck.screw.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@Api(tags = "User")
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;


    @ApiOperation(value = "회원가입", notes = "닉네임,패스워드,이름으로만 회원가입 진행한다.")
    @PostMapping("/api/user")
    public UserDto signup(@RequestBody SignupDto signupDto){
        UserDto userDto = userService.signup(signupDto);
        return userDto;
    }

    @ApiOperation(value = "전체 회원 조회", notes = "전체 회원을 조회한다.")
    @GetMapping("/api/users")
    public List<UserDto> findAll(){
        return userService.findAll();
    }

}
