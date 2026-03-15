package com.example.service;

import com.example.dto.UserDTO;
import com.example.dto.UserEvent;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProducer userProducer; // Наш отдельный слой для Kafka

    public UserService(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }

    // НОВЫЙ МЕТОД: необходим для формирования ссылок HATEOAS в контроллере
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден с id: " + id));
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getAge());
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getAge()))
                .collect(Collectors.toList());
    }

    public UserDTO create(UserDTO dto) {
        User user = new User(dto.getName(), dto.getEmail(), dto.getAge());
        User saved = userRepository.save(user);

        // Отправка события в Kafka через продюсер
        userProducer.sendEvent(saved.getEmail(), "CREATE");

        return new UserDTO(saved.getId(), saved.getName(), saved.getEmail(), saved.getAge());
    }

    public UserDTO update(Long id, UserDTO dto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        existingUser.setName(dto.getName());
        existingUser.setEmail(dto.getEmail());
        existingUser.setAge(dto.getAge());

        User updatedUser = userRepository.save(existingUser);
        return new UserDTO(updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail(), updatedUser.getAge());
    }

    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        userRepository.deleteById(id);

        // Отправка события об удалении в Kafka
        userProducer.sendEvent(user.getEmail(), "DELETE");
    }
}
