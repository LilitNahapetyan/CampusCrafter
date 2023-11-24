package academy.campuscrafter.service;

import academy.campuscrafter.model.User;
import academy.campuscrafter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findByID(UUID uuid){
        return userRepository.findById(uuid);
    }
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
