package ru.kulakova.entities.mappers;

import ru.kulakova.entities.User;
import ru.kulakova.entities.dto.UserDTO;

import java.util.HashSet;

public class UserMapperM {
    public User toEntity(UserDTO dto) {
        if (dto == null) return null;

        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .roles(dto.getRoles() != null ? dto.getRoles() : new HashSet<>())
                .build();
    }

    public UserDTO toDto(User entity) {
        if (entity == null) return null;

        return new UserDTO(
                entity.getUsername(),
                null, // не возвращаем хеш пароля
                entity.getRoles()
        );
    }
}
