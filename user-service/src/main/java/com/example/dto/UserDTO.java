package com.example.dto;

import org.springframework.hateoas.RepresentationModel;
import java.util.Objects;

public class UserDTO extends RepresentationModel<UserDTO> {
        private Long id;
        private String name;
        private String email;
        private Integer age;

        // Конструктор без аргументов (нужен для Jackson/Swagger)
        public UserDTO() {}

        // Конструктор со всеми аргументами
        public UserDTO(Long id, String name, String email, Integer age) {
                this.id = id;
                this.name = name;
                this.email = email;
                this.age = age;
        }

        // Геттеры и Сеттеры (обязательны для Swagger!)
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public Integer getAge() { return age; }
        public void setAge(Integer age) { this.age = age; }

        // Equals и HashCode (нужны для HATEOAS)
        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof UserDTO userDTO)) return false;
                if (!super.equals(o)) return false;
                return Objects.equals(id, userDTO.id) && Objects.equals(name, userDTO.name) && Objects.equals(email, userDTO.email) && Objects.equals(age, userDTO.age);
        }

        @Override
        public int hashCode() {
                return Objects.hash(super.hashCode(), id, name, email, age);
        }
}
