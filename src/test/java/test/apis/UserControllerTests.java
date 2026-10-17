package test.apis;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.auth0.jwt.exceptions.JWTVerificationException;

import apisecurity.services.JWTService;
import test.models.Privilege;
import test.models.User;
import test.repositories.PrivilegeRepository;
import test.repositories.UserRepository;
import test.services.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTests {

  @Autowired private MockMvc mockMvc;

  @MockBean private UserService userService;

  @MockBean private JWTService jwtService;

  @MockBean private UserRepository userRepo;
  
  @MockBean private PrivilegeRepository privilegeRepo;

  @Value("${apiPrefix}")
  private String apiPrefix;

  @Test
  void getUser() throws Exception {
    String email = "john.safeman@myemail.com";

    when(userService.getUserService(email)).thenReturn("John Safeman");

    mockMvc
        .perform(get(apiPrefix + "/user/" + email))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.user").value("John Safeman"));
  }
  
  @Test
  void editUserFirstNameJWTValid() throws Exception {
	  String email = "john.Safeman@myemail.com";
	  String token = "fake token";
	  User user = new User();
	  user.setEmail(email);
	  
	  when(userRepo.findById(email)).thenReturn(Optional.of(user));
	  
	  mockMvc
      .perform(put(apiPrefix + "/user/edit/" + email)
    		  .header("Authorization", "Bearer " + token)
    		  .contentType(MediaType.APPLICATION_JSON)
    		  .content("""
    		  		{
    		  			"firstName": "Joe"
    		  		}
    		  		""")
    		  ) .andExpect(status().isOk());
	  
	  when(userService.getUserService(email)).thenReturn("Joe Safeman");
	  
	  mockMvc
      .perform(get(apiPrefix + "/user/" + email))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.user").value("Joe Safeman"));
	  
  }
  
  @Test
  void editUserFirstNameJWTNotValid() throws Exception {
	  String email = "john.Safeman@myemail.com";
	  String token = "fake token";
	  User user = new User();
	  user.setEmail(email);
	  
	  when(userRepo.findById(email)).thenReturn(Optional.of(user));
	  
	  doThrow(new JWTVerificationException("Token not valid"))
      .when(jwtService)
      .verifyJWT("Bearer " + token, user);
	  
	  mockMvc
      .perform(put(apiPrefix + "/user/edit/" + email)
    		  .header("Authorization", "Bearer " + token)
    		  .contentType(MediaType.APPLICATION_JSON)
    		  .content("""
    		  		{
    		  			"firstName": "Joe"
    		  		}
    		  		""")
    		  ) .andExpect(status().isForbidden());
	  
	  when(userService.getUserService(email)).thenReturn("John Safeman");
	  
	  mockMvc
      .perform(get(apiPrefix + "/user/" + email))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.user").value("John Safeman"));
	  
  }
  
  @Test
  public void addPrivilegeJWTValid() throws Exception {
	  String email = "john.Safeman@myemail.com";
	  
	  mockMvc
      .perform(post(apiPrefix + "/user/" +  email + "/privilege")
    		  .header("Authorization", "Bearer fake token valid")
    		  .contentType(MediaType.APPLICATION_JSON)
    		  .content("""
    		  		{
    		  			"privilege": "Test privilege"
    		  		}
    		  		""")
    		  )
      .andExpect(status().isOk());
	  
  }
  @Test
  public void addPrivilegeJWTNotValid() throws Exception {
	  String email = "john.Safeman@myemail.com";
	  Privilege privilege = new Privilege(1, "CEO");
	  
	  when(privilegeRepo.findByPrivilege("CEO")).thenReturn(privilege);
	  
	  doThrow(new JWTVerificationException("Token not valid"))
		.when(jwtService)
		.verifyJWT("Bearer fake not token valid", privilege);
	  
	  mockMvc
      .perform(post(apiPrefix + "/user/" +  email + "/privilege")
    		  .header("Authorization", "Bearer fake not token valid")
    		  .contentType(MediaType.APPLICATION_JSON)
    		  .content("""
    		  		{
    		  			"privilege": "Test privilege"
    		  		}
    		  		""")
    		  )
      .andExpect(status().isForbidden());
  }
}


