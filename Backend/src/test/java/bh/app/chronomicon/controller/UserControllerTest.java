package bh.app.chronomicon.controller;


import bh.app.chronomicon.dto.UserDTO;
import bh.app.chronomicon.model.entities.UserEntity;
import bh.app.chronomicon.model.enums.Rank;

import bh.app.chronomicon.security.JwtUtil;
import bh.app.chronomicon.service.AuthService;
import bh.app.chronomicon.service.UserService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;
    @MockitoBean
    private UserService userService;
    @MockitoBean
    private JwtUtil jwtUtil;


    private static List<UserDTO> getUserDTOList() {
        List<UserDTO> mockUserDTOList = new ArrayList<>();
        mockUserDTOList.add(new UserDTO(new UserEntity (Rank.CAPITAO, "AAAA", (short) 0, "Levi Ackerman", "Levi",
                true, true, true, true)));
        mockUserDTOList.add(new UserDTO(new UserEntity (Rank.PRIMEIRO_SGT, "BBBB", (short) 1, "Mikasa Ackerman", "Mikasa A.",
                true, false, true, true)));
        mockUserDTOList.add(new UserDTO(new UserEntity (Rank.SEGUNDO_SGT, "CCCC", (short) 2, "Eren Jager", "Jager",
                true, true, false, true)));
        mockUserDTOList.add(new UserDTO(new UserEntity (Rank.TERCEIRO_SGT, "DDDD", (short) 3, "Armin Arlert", "A. Arlert",
                false, true, true, true)));
        mockUserDTOList.add(new UserDTO(new UserEntity (Rank.TERCEIRO_SGT, "EEEE", (short) 4, "Bertoto Roover", "Bertorto",
                false, false, false, true)));
        mockUserDTOList.add(new UserDTO(new UserEntity (Rank.TERCEIRO_SGT, "FFFF", (short) 1001, "Sasha Braus", "Sasha",
                false, false, true, false)));
        return mockUserDTOList;
    }

    @Test
    @DisplayName ("Should execute findUsers and return 200")
    @WithMockUser(username = "1234567", roles = {"USER"})
    void  shouldReturnAllActiveUsers () throws Exception{
        List<UserDTO> mockUserDTOList = getUserDTOList ();
        doReturn(mockUserDTOList).when(userService).findUsers ();
        mockMvc.perform(get("/api/v1/staff")
                .contentType (MediaType.APPLICATION_JSON)
        )
                .andExpect (status().isOk ())
                .andExpect (jsonPath("$[0].lpna_identifier").value ("AAAA"))
                .andExpect (jsonPath("$[2].lpna_identifier").value ("CCCC"));
        verify (userService).findUsers ();

    }

    @Test
    @DisplayName ("Should execute findSupervisors and return 200")
    @WithMockUser(username = "1234567", roles = {"USER"})
    void  shouldReturnSupervisors () throws Exception{
        List<UserDTO> mockUserDTOList = getUserDTOList ();
        doReturn(mockUserDTOList).when(userService).findSupervisors ();
        mockMvc.perform(get("/api/v1/staff/sups")
                        .contentType (MediaType.APPLICATION_JSON)
                )
                .andExpect (status().isOk ())
                .andExpect (jsonPath("$[0].lpna_identifier").value ("AAAA"))
                .andExpect (jsonPath("$[2].lpna_identifier").value ("CCCC"));
        verify (userService).findSupervisors ();
    }

    @Test
    @DisplayName ("Should execute findInstructors and return 200")
    @WithMockUser(username = "1234567", roles = {"USER"})
    void  shouldReturnInstructors () throws Exception{
        List<UserDTO> mockUserDTOList = getUserDTOList ();
        doReturn(mockUserDTOList).when(userService).findInstructors ();
        mockMvc.perform(get("/api/v1/staff/instrutores")
                        .contentType (MediaType.APPLICATION_JSON)
                )
                .andExpect (status().isOk ())
                .andExpect (jsonPath("$[0].lpna_identifier").value ("AAAA"))
                .andExpect (jsonPath("$[2].lpna_identifier").value ("CCCC"));
        verify (userService).findInstructors ();
    }

    @Test
    @DisplayName ("Should execute findTrainees and return 200")
    @WithMockUser(username = "1234567", roles = {"USER"})
    void  shouldReturnTrainees () throws Exception{
        List<UserDTO> mockUserDTOList = getUserDTOList ();
        doReturn(mockUserDTOList).when(userService).findTrainees ();
        mockMvc.perform(get("/api/v1/staff/estagiarios")
                        .contentType (MediaType.APPLICATION_JSON)
                )
                .andExpect (status().isOk ())
                .andExpect (jsonPath("$[0].lpna_identifier").value ("AAAA"))
                .andExpect (jsonPath("$[2].lpna_identifier").value ("CCCC"));
        verify (userService).findTrainees ();
    }

    @Test
    @DisplayName ("Should execute findOnlyOperators and return 200")
    @WithMockUser(username = "1234567", roles = {"USER"})
    void  shouldReturnOnlyOperators () throws Exception{
        List<UserDTO> mockUserDTOList = getUserDTOList ();
        doReturn(mockUserDTOList).when(userService).findOnlyOperators ();
        mockMvc.perform(get("/api/v1/staff/operadores")
                        .contentType (MediaType.APPLICATION_JSON)
                )
                .andExpect (status().isOk ())
                .andExpect (jsonPath("$[0].lpna_identifier").value ("AAAA"))
                .andExpect (jsonPath("$[2].lpna_identifier").value ("CCCC"));
        verify (userService).findOnlyOperators ();
    }

    @Test
    @DisplayName ("Should execute findUserById and return 200")
    @WithMockUser(username = "1234567", roles = {"USER"})
    void  shouldReturnUserFilteredByID () throws Exception{
        UserDTO  userDTO = new UserDTO(new UserEntity (Rank.CAPITAO, "AAAA", (short) 0, "Levi Ackerman", "Levi",
                true, true, true, true));
        doReturn(userDTO).when(userService).findUserById (0L);
        mockMvc.perform(get("/api/v1/staff/0")
                        .contentType (MediaType.APPLICATION_JSON)
                )
                .andExpect (status().isOk ())
                .andExpect (jsonPath("$.lpna_identifier").value ("AAAA"));
        verify (userService).findUserById (0L);
    }

}
