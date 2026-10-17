package test.apis;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import test.repositories.PrivilegeRepository;
import test.services.PrivilegeService;

@WebMvcTest(PrivilegeController.class)
public class PrivilegeControllerTests {
	
	@Autowired private MockMvc mockMvc;
	@MockBean PrivilegeService privilegeService;
	@MockBean private JWTService jwtService;
	@MockBean private PrivilegeRepository privilegeRepo;
	
	@Value("${apiPrefix}")
	  private String apiPrefix;
	
	@Test
	public void addPrivilegeJWTValid() throws Exception {
		when(privilegeRepo.findByPrivilege("manager")).thenReturn(new Privilege(1, "test, manager"));
		mockMvc
	      .perform(post(apiPrefix + "/privilege/")
	    		  .header("Authorization", "Bearer fake token")
	    		  .contentType(MediaType.APPLICATION_JSON)
	    		  .content("""
	    		  		{
	    		  			"privilege": "new test privilege"
	    		  		}
	    		  		""")
	    		  )
	      .andExpect(status().isOk());
	}
	
	@Test
	public void addPrivilegeJWTNotValid() throws Exception {
		Privilege privilege = new Privilege(1, "test, manager");
		when(privilegeRepo.findByPrivilege("manager")).thenReturn(privilege);
		
		doThrow(new JWTVerificationException("Token not valid"))
	      .when(jwtService)
	      .verifyJWT("Bearer fake token", privilege);
		
		mockMvc
	      .perform(post(apiPrefix + "/privilege/")
	    		  .header("Authorization", "Bearer fake token")
	    		  .contentType(MediaType.APPLICATION_JSON)
	    		  .content("""
	    		  		{
	    		  			"privilege": "new test privilege"
	    		  		}
	    		  		""")
	    		  )
	      .andExpect(status().isForbidden());
	}
}
