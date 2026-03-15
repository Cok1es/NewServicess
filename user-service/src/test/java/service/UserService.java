package service;

import com.example.dto.UserDTO;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.UserProducer;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProducer userProducer; // Тот самый отдельный слой для Kafka

    public UserService(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }

    // Метод для получения всех (который искал контроллер)
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getAge()))
                .collect(Collectors.toList());
    }

    public UserDTO create(UserDTO dto) {
        User user = new User(dto.getName(), dto.getEmail(), dto.getAge());
        User saved = userRepository.save(user);

        // Отправка через отдельный слой
        userProducer.sendEvent(saved.getEmail(), "CREATE");

        return new UserDTO(saved.getId(), saved.getName(), saved.getEmail(), saved.getAge());
    }

    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.deleteById(id);

        // Отправка через отдельный слой
        userProducer.sendEvent(user.getEmail(), "DELETE");
    }
}
