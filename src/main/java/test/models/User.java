package test.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
public class User {
	@Id
	private String email;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
	private List<Privilege> privileges;

	@OneToOne(mappedBy = "user")
	private UserAuthentication userAuth;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}

	public UserAuthentication getUserAuth() {
		return userAuth;
	}

	public void setUserAuth(UserAuthentication userAuth) {
		this.userAuth = userAuth;
	}

}
