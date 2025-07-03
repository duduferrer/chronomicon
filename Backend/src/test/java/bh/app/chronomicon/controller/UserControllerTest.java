package bh.app.chronomicon.controller;


import bh.app.chronomicon.dto.CreateUserDTO;
import bh.app.chronomicon.dto.UpdateUserDTO;
import bh.app.chronomicon.dto.UserDTO;
import bh.app.chronomicon.model.entities.UserEntity;
import bh.app.chronomicon.model.enums.Rank;

import bh.app.chronomicon.security.JwtUtil;
import bh.app.chronomicon.service.AuthService;
import bh.app.chronomicon.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;
    @MockitoBean
    private UserService userService;
    @MockitoBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;


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
    @DisplayName ("Should execute findUserById, return 200 and userDTO")
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

    @Test
    @DisplayName ("Should execute createNewUser and return 201 and saved user")
    @WithMockUser(username = "1234567", roles = {"ADMIN"})
    void  shouldCreateUser () throws Exception{
        CreateUserDTO createUserDTO = new CreateUserDTO(new UserEntity (Rank.TERCEIRO_SGT, "EEEE", (short) 4, "Bertoto Roover", "Bertorto",
                false, false, false, true));
        UserDTO userDTO = new UserDTO (new UserEntity (Rank.TERCEIRO_SGT, "EEEE", (short) 4, "Bertoto Roover", "Bertorto",
                false, false, false, true));
        doReturn(userDTO).when(userService).createNewUser (createUserDTO);
        mockMvc.perform(post ("/api/v1/staff")
                        .contentType (MediaType.APPLICATION_JSON)
                        .content (objectMapper.writeValueAsString (createUserDTO))
                        .with(csrf())
                )
                .andExpect (status().isCreated ())
                .andExpect (jsonPath("$.lpna_identifier").value ("EEEE"));
        verify (userService).createNewUser (createUserDTO);
    }

    @Test
    @DisplayName ("Should execute createNewUser as USER_ROLE and return 403")
    @WithMockUser(username = "1234567", roles = {"USER"})
    void  shouldGet403WhenTryToCreateUser () throws Exception{
        CreateUserDTO createUserDTO = new CreateUserDTO(new UserEntity (Rank.TERCEIRO_SGT, "EEEE", (short) 4, "Bertoto Roover", "Bertorto",
                false, false, false, true));
        UserDTO userDTO = new UserDTO (new UserEntity (Rank.TERCEIRO_SGT, "EEEE", (short) 4, "Bertoto Roover", "Bertorto",
                false, false, false, true));
        doReturn(userDTO).when(userService).createNewUser (createUserDTO);
        mockMvc.perform(post ("/api/v1/staff")
                        .contentType (MediaType.APPLICATION_JSON)
                        .content (objectMapper.writeValueAsString (createUserDTO))
                        .with(csrf())
                )
                .andExpect (status().isForbidden ());
    }

    @Test
    @DisplayName ("Should execute findUserByLpna return 200 and UserDTO")
    @WithMockUser(username = "1234567", roles = {"USER"})
    void  shouldReturnUserFilteredByLPNA () throws Exception{
        UserDTO  userDTO = new UserDTO(new UserEntity (Rank.CAPITAO, "AAAA", (short) 0, "Levi Ackerman", "Levi",
                true, true, true, true));
        doReturn(userDTO).when(userService).findUserByLPNAReturnDTO ("AAAA");
        mockMvc.perform(get("/api/v1/staff/lpna/AAAA")
                        .contentType (MediaType.APPLICATION_JSON)
                )
                .andExpect (status().isOk ())
                .andExpect (jsonPath("$.lpna_identifier").value ("AAAA"));
        verify (userService).findUserByLPNAReturnDTO ("AAAA");
    }

    @Test
    @DisplayName ("Should execute activateUser and return 204")
    @WithMockUser(username = "1234567", roles = {"ADMIN"})
    void  shouldActivateUser() throws Exception{
        UserDTO userDTO = new UserDTO (new UserEntity (Rank.TERCEIRO_SGT, "EEEE", (short) 4, "Bertoto Roover", "Bertorto",
                false, false, false, true));
        doNothing ().when(userService).activateUser ("EEEE");
        mockMvc.perform(put ("/api/v1/staff/EEEE/status?active=true")
                        .contentType (MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect (status().isNoContent ());
        verify (userService).activateUser ("EEEE");
    }

    @Test
    @DisplayName ("Should execute activateUser as USER_ROLE and return 403")
    @WithMockUser(username = "1234567", roles = {"USER"})
    void  shouldGet403TryToActivateUser() throws Exception{
        UserDTO userDTO = new UserDTO (new UserEntity (Rank.TERCEIRO_SGT, "EEEE", (short) 4, "Bertoto Roover", "Bertorto",
                false, false, false, true));
        doNothing ().when(userService).activateUser ("EEEE");
        mockMvc.perform(put ("/api/v1/staff/EEEE/status?active=true")
                        .contentType (MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect (status().isForbidden ());
    }

    @Test
    @DisplayName ("Should execute updateUser and return 204")
    @WithMockUser(username = "1234567", roles = {"ADMIN"})
    void  shouldUpdateUserData () throws Exception{
        CreateUserDTO createUserDTO = new CreateUserDTO(new UserEntity (Rank.TERCEIRO_SGT, "EEEE", (short) 4, "Bertoto Roover", "Bertorto",
                false, false, false, true));
        UpdateUserDTO updateUserDTO = new UpdateUserDTO (null, null, null, "Bertoto", null, null, null);
        doNothing ().when(userService).updateUser ("EEEE", updateUserDTO);
        mockMvc.perform(patch ("/api/v1/staff/EEEE")
                        .contentType (MediaType.APPLICATION_JSON)
                        .content (objectMapper.writeValueAsString (updateUserDTO))
                        .with(csrf())
                )
                .andExpect (status().isNoContent ());
        verify (userService).updateUser ("EEEE", updateUserDTO);
    }

    @Test
    @DisplayName ("Should execute updateUser as USER_ROLE and return 403")
    @WithMockUser(username = "1234567", roles = {"USER"})
    void  shouldGet403UpdateUserData () throws Exception{
        CreateUserDTO createUserDTO = new CreateUserDTO(new UserEntity (Rank.TERCEIRO_SGT, "EEEE", (short) 4, "Bertoto Roover", "Bertorto",
                false, false, false, true));
        UpdateUserDTO updateUserDTO = new UpdateUserDTO (null, null, null, "Bertoto", null, null, null);
        doNothing ().when(userService).updateUser ("EEEE", updateUserDTO);
        mockMvc.perform(patch ("/api/v1/staff/EEEE")
                        .contentType (MediaType.APPLICATION_JSON)
                        .content (objectMapper.writeValueAsString (updateUserDTO))
                        .with(csrf())
                )
                .andExpect (status().isForbidden ());
    }


}
