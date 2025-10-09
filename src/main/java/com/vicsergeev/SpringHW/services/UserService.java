package com.vicsergeev.SpringHW.services;

import com.vicsergeev.SpringHW.dto.UserCreateDTO;
import com.vicsergeev.SpringHW.dto.UserResponseDTO;
import com.vicsergeev.SpringHW.dto.UserUpdateDTO;
import com.vicsergeev.SpringHW.exception.UserNotFoundException;
import com.vicsergeev.SpringHW.model.User;
import com.vicsergeev.SpringHW.repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Victor 09.10.2025
 */

@Service
public class UserService {
    private final UserRepository userRepository;

    // DI - automatically, Autowired optional
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return toResponseDTO(user);
    }

    public UserResponseDTO createUser(@NotNull UserCreateDTO createDTO) {
        User user = new User();
        user.setName(createDTO.name());
        user.setAge(createDTO.age());
        user.setEmail(createDTO.email());
        user.setCreatedAt(createDTO.createdAt());

        return toResponseDTO(userRepository.save(user));
    }

    public UserResponseDTO updateUser(Long id, @NotNull UserUpdateDTO updateDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        if (updateDTO.getName() != null) {
            user.setName(updateDTO.getName());
        }
        if (updateDTO.getAge() != null) {
            user.setAge(updateDTO.getAge());
        }
        if (updateDTO.getEmail() != null) {
            user.setEmail(updateDTO.getEmail());
        }

        return toResponseDTO(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    private @NotNull UserResponseDTO toResponseDTO(@NotNull User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setAge(user.getAge());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());

        return dto;
    }
}
