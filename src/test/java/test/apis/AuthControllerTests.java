package test.apis;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import apisecurity.services.JWTService;
import test.models.User;
import test.models.UserAuthentication;
import test.services.AuthService;

@WebMvcTest(AuthController.class)
public class AuthControllerTests {
	
	@Autowired private MockMvc mockMvc;
	@MockBean private AuthService authService;
	@MockBean private JWTService jwtService;
	
	
	@Value("${apiPrefix}")
	  private String apiPrefix;

	
	@Test
	public void registration() throws Exception {
		User user = new User("testemail@myemail.com", "test-firstname", "test-lastname");
		UserAuthentication userAuth = new UserAuthentication("testemail@myemail.com", "test-password");
		 mockMvc
	      .perform(post(apiPrefix + "/auth/registration")
	    		  .contentType(MediaType.APPLICATION_JSON)
	    		  .content("""
	    		  		{
	    		  			"email": "testemail@myemail.com",
	    		  			"password": "test-password",
	    		  			"firstName": "test-firstname",
	    		  			"lastName": "test-lastname"
	    		  		}
	    		  		""")
	    		  )
	      .andExpect(status().isOk());
		 
		verify(authService).registration(argThat(u -> u.getEmail().equals(user.getEmail())), 
				argThat(uA -> uA.getEmail().equals(userAuth.getEmail())));
	}
	
	@Test
	public void login() throws Exception {
		User user = new User("testemail@myemail.com", "test-firstname", "test-lastname");
		when(authService.login(any(UserAuthentication.class))).thenReturn(user);
		when(jwtService.generateToken(user)).thenReturn("test token");
		
		 mockMvc
	      .perform(post(apiPrefix + "/auth/login")
	    		  .contentType(MediaType.APPLICATION_JSON)
	    		  .content("""
	    		  		{
	    		  			"email": "testemail@myemail.com",
	    		  			"password": "test-password"
	    		  		}
	    		  		""")
	    		  )
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$.token").value("test token"));
		
	}
}
