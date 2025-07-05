package bh.app.chronomicon.controller;

import bh.app.chronomicon.dto.*;
import bh.app.chronomicon.model.entities.UserEntity;
import bh.app.chronomicon.model.enums.Rank;
import bh.app.chronomicon.model.enums.Role;
import bh.app.chronomicon.repository.SystemUserRepository;
import bh.app.chronomicon.security.JwtUtil;
import bh.app.chronomicon.service.AuthService;
import bh.app.chronomicon.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private AuthService authService;
	@MockitoBean
	private UserService userService;
	@MockitoBean
	private JwtUtil jwtUtil;
	@MockitoBean
	private AuthenticationManager authenticationManager;
	@MockitoBean
	private SystemUserRepository systemUserRepository;
	@MockitoBean
	private URI uri;

	@Autowired
	private ObjectMapper objectMapper;
	
	private AuthenticationDTO authenticationDTO;
	private AuthResponseDTO authResponseDTO;
	private UpdatePasswordDTO updatePasswordDTO;
	private Authentication auth;
	private RegisterSystemUserDTO registerSystemUserDTO;
	
	
	@BeforeEach
	void setUp() {
		// Configurações comuns para cada teste
		authenticationDTO = new AuthenticationDTO("testuser", "password123");
		authResponseDTO = new AuthResponseDTO("mockAccessToken");
		updatePasswordDTO = new UpdatePasswordDTO("newStrongPassword123!", "password123");
		registerSystemUserDTO = new RegisterSystemUserDTO(Role.USER, "ABCD", "email@email.com", "1234567");
		auth = null;
		
	}
	
	@Test
	@DisplayName("Should execute login and return AuthResponseDTO, 200")
	void  shouldLoginSuccessfully() throws Exception {
		UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken ("user", "password");
		doReturn (auth).when (authenticationManager).authenticate(usernamePassword);
		doReturn(authResponseDTO.token()).when(jwtUtil).generateToken(anyString());
		mockMvc.perform (post ("/api/v1/auth/login")
						.contentType (MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(authenticationDTO))
				)
				.andExpect (status ().isOk ())
				.andExpect (jsonPath ("$.token").value ("mockAccessToken"));
	}
	
	@Test
	@DisplayName("Should execute login without password and return 400 bad request")
	void  shouldFailLoginWithoutPassword() throws Exception {
		AuthenticationDTO authenticationDTONoPassword = new AuthenticationDTO("saram","");
		mockMvc.perform (post ("/api/v1/auth/login")
						.contentType (MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(authenticationDTONoPassword))
				)
				.andExpect (status ().isBadRequest ());
	}
	
	@Test
	@DisplayName("Should execute login without username and return 400 bad request")
	void  shouldFailLoginWithoutUsername() throws Exception {
		AuthenticationDTO authenticationDTONoUsername = new AuthenticationDTO("","123456789");
		mockMvc.perform (post ("/api/v1/auth/login")
						.contentType (MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(authenticationDTONoUsername))
				)
				.andExpect (status ().isBadRequest ());
	}
	

	
}
