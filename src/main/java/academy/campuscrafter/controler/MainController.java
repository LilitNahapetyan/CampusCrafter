package academy.campuscrafter.controler;

import academy.campuscrafter.dto.CreateUserRequestDto;
import academy.campuscrafter.dto.UserAuthRequestDto;
import academy.campuscrafter.dto.UserAuthResponseDto;
import academy.campuscrafter.dto.UserDto;
import academy.campuscrafter.mapper.UserMapper;
import academy.campuscrafter.model.User;
import academy.campuscrafter.service.UserService;
import academy.campuscrafter.util.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtService;
    private final UserMapper userMapper;

    // Authentication endpoint
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

    // User registration endpoint
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CreateUserRequestDto createUserRequestDto) {
        // Check if the email is already registered
        Optional<User> byEmail = userService.findByEmail(createUserRequestDto.getEmail());
        if (byEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Email conflict
        }

        // Allow registration for roles: "STUDENT", "TEACHER", and "ADMIN"
        if (createUserRequestDto.getRole().equals("STUDENT") || createUserRequestDto.getRole().equals("TEACHER") || createUserRequestDto.getRole().equals("ADMIN")) {
            User user = userMapper.map(createUserRequestDto);
            user.setPassword(passwordEncoder.encode(createUserRequestDto.getPassword()));
            user.setJoinedDate(LocalDateTime.now());
            userService.save(user);
            return ResponseEntity.ok(userMapper.mapToDto(user)); // Registration success
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Invalid role
    }
}
