package com.vicsergeev.SpringHW.controllers;

import com.vicsergeev.SpringHW.dto.UserDTO;
import com.vicsergeev.SpringHW.dto.UserResponseDTO;
import com.vicsergeev.SpringHW.repositories.UserRepository;
import com.vicsergeev.SpringHW.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Victor 09.10.2025
 */

@RestController
@RequestMapping("/users")
@Tag(name = "User operations API", description = "API для CRUD операций над пользователями")
public class UserController {

    private final UserService userService;
    private final String codeDescription = "internal server error - check if docker with kafka is up and running";

    public UserController(UserService userService,
                          UserRepository userRepository) {
        this.userService = userService;
    }

    @Operation(summary = "show all users in DB", description = "return a list of existing users in DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "users found"),
            @ApiResponse(responseCode = "404", description = "no users found"),
            @ApiResponse(responseCode = "500", description = codeDescription)
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @Operation(summary = "get user data by its ID", description = "return user data in json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user found"),
            @ApiResponse(responseCode = "404", description = "user not found"),
            @ApiResponse(responseCode = "500", description = codeDescription)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        try {
            if (userService.userExists(id)) {
                return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userService.findById(id));
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "create user", description = "operation creates user with entered data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "user created successfully"),
            @ApiResponse(responseCode = "500", description = codeDescription)
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserDTO createDTO) {
        UserResponseDTO response = userService.createUser(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "update user by ID", description = "operation update user with entered data by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user updated successfully"),
            @ApiResponse(responseCode = "404", description = "user not found"),
            @ApiResponse(responseCode = "500", description = codeDescription)
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO updateDTO) {
        try {
            if (userService.userExists(id)) {
                UserResponseDTO response = userService.updateUser(id, updateDTO);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "delete user", description = "delete user data by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "user deleted successfully"),
            @ApiResponse(responseCode = "404", description = "user not found"),
            @ApiResponse(responseCode = "500", description = codeDescription)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Valid Long id) {
        try {
            if (userService.userExists(id)) {
                userService.deleteUser(id);
                return ResponseEntity.accepted().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
