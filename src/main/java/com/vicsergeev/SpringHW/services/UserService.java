package com.vicsergeev.SpringHW.services;

import com.vicsergeev.SpringHW.dto.UserDTO;
import com.vicsergeev.SpringHW.dto.UserEventDTO;
import com.vicsergeev.SpringHW.dto.UserResponseDTO;
import com.vicsergeev.SpringHW.exception.UserNotFoundException;
import com.vicsergeev.SpringHW.model.User;
import com.vicsergeev.SpringHW.repositories.UserRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final KafkaTemplate<String, UserEventDTO> kafkaTemplate;

    public UserService(UserRepository userRepository, KafkaTemplate<String, UserEventDTO> kafkaTemplate) {
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
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

    public UserResponseDTO createUser(UserDTO createDTO) {
        User user = new User();
        user.setName(createDTO.getName());
        user.setAge(createDTO.getAge());
        user.setEmail(createDTO.getEmail());
        User savedUser = userRepository.save(user);
        UserEventDTO event = new UserEventDTO(UserEventDTO.CREATE, createDTO.getEmail(), createDTO.getName());
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("Sending event to Kafka: " + event);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        kafkaTemplate.send("user-events", event).whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Kafka send SUCCESS: " + result.getRecordMetadata());
            } else {
                System.out.println("Kafka send FAILED: " + ex.getMessage());
            }
        });
        return toResponseDTO(savedUser);
    }

    public UserResponseDTO updateUser(Long id, UserDTO updateDTO) {
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
        User updatedUser = userRepository.save(user);
        UserEventDTO event = new UserEventDTO(UserEventDTO.UPDATE, updateDTO.getEmail() != null ? updateDTO.getEmail() : user.getEmail(), updateDTO.getName() != null ? updateDTO.getName() : user.getName());
        System.out.println("Sending event to Kafka: " + event);
        kafkaTemplate.send("user-events", event);
        return toResponseDTO(updatedUser);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        UserEventDTO event = new UserEventDTO(UserEventDTO.DELETE, user.getEmail(), user.getName());
        System.out.println("Sending event to Kafka: " + event);
        kafkaTemplate.send("user-events", event);
        userRepository.delete(user);
    }

    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setAge(user.getAge());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}