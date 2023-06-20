package dongduck.screw.service;

import dongduck.screw.domain.user.User;
import dongduck.screw.domain.user.UserRepository;
import dongduck.screw.dto.user.SignupDto;
import dongduck.screw.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public List<UserDto> findAll(){
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }


}
