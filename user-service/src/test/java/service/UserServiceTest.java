package service;

import com.example.dto.UserDTO;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.UserProducer;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserProducer userProducer;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUserSuccessfully() {

        UserDTO dto = new UserDTO(null, "Ivan", "ivan@test.com", 25);
        User savedUser = new User("Ivan", "ivan@test.com", 25);
        savedUser.setId(1L);


        when(userRepository.save(any(User.class))).thenReturn(savedUser);


        UserDTO result = userService.create(dto);


        assertNotNull(result.getId());
        assertEquals("Ivan", result.getName());


        verify(userRepository, times(1)).save(any(User.class));


        verify(userProducer, times(1)).sendEvent(eq("ivan@test.com"), eq("CREATE"));
    }

    @Test
    void shouldDeleteUserAndSendEvent() {

        Long userId = 1L;
        User user = new User("Ivan", "ivan@test.com", 25);
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));


        userService.delete(userId);


        verify(userRepository, times(1)).deleteById(userId);
        verify(userProducer, times(1)).sendEvent(eq("ivan@test.com"), eq("DELETE"));
    }
}
