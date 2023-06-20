package dongduck.screw.web.controller;

import dongduck.screw.dto.user.UserDto;
import dongduck.screw.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@Api(tags = "User")
@Controller
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @GetMapping("/")
    public String getHome(){
        return "home";
    }
    @ApiOperation(value = "전체 회원 조회", notes = "전체 회원을 조회한다.")
    @GetMapping("/api/users")
    public List<UserDto> findAll(){
        return userService.findAll();
    }

}
