package academy.campuscrafter.controler;

import academy.campuscrafter.dto.CreateUserRequestDto;
import academy.campuscrafter.dto.UserAuthRequestDto;
import academy.campuscrafter.dto.UserAuthResponseDto;
import academy.campuscrafter.dto.UserDto;
import academy.campuscrafter.mapper.UserMapper;
import academy.campuscrafter.model.Role;
import academy.campuscrafter.model.User;
import academy.campuscrafter.service.JwtService;
import academy.campuscrafter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @PostMapping("/auth")
    public ResponseEntity<UserAuthResponseDto> auth(@RequestBody UserAuthRequestDto userAuthRequestDto) {
        Optional<User> byEmail = userService.findByEmail(userAuthRequestDto.getEmail());
        if (byEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = byEmail.get();
        if (!passwordEncoder.matches(userAuthRequestDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new UserAuthResponseDto("Bearer " + token));
    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CreateUserRequestDto createUserRequestDto) {
        Optional<User> byEmail = userService.findByEmail(createUserRequestDto.getEmail());
        if (byEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User user = userMapper.map(createUserRequestDto);
        user.setPassword(passwordEncoder.encode(createUserRequestDto.getPassword()));
        if(createUserRequestDto.getRole().equals("STUDENT") || createUserRequestDto.getRole().equals("TEACHER")){
            user.setRole(Role.valueOf(createUserRequestDto.getRole()));
            userService.save(user);
            return ResponseEntity.ok(userMapper.mapToDto(user));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
}
