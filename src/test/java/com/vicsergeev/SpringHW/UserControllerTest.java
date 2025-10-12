package com.vicsergeev.SpringHW;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.vicsergeev.SpringHW.controllers.UserController;
import com.vicsergeev.SpringHW.dto.UserCreateDTO;
import com.vicsergeev.SpringHW.services.UserService;
import com.vicsergeev.SpringHW.dto.UserResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by Victor 12.10.2025
 */

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllUsers() throws Exception {
        UserResponseDTO dto = new UserResponseDTO(1L, "test", 30, "aa@aa.aa", LocalDateTime.now());
        when(userService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("test"))
                .andExpect(jsonPath("$[0].age").value(30));
    }

    @Test
    void createUser() throws Exception {
        UserCreateDTO createDto = new UserCreateDTO("test", 30, "aa@aa.aa", LocalDateTime.now());
        UserResponseDTO responseDto = new UserResponseDTO(1L, "test", 30, "aa@aa.aa", createDto.createdAt());

        when(userService.createUser(any(UserCreateDTO.class))).thenReturn(responseDto);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.age").value(30));
    }
}
