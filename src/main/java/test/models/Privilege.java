package test.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.List;

@Entity
public class Privilege {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int code;

  @Column private String privilege;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      joinColumns = @JoinColumn(name = "privilegeCode"),
      inverseJoinColumns = @JoinColumn(name = "email"))
  private List<User> users;

  public Privilege(int code, String privilege) {
    super();
    this.code = code;
    this.privilege = privilege;
  }

  public Privilege() {
    super();
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getPrivilege() {
    return privilege;
  }

  public void setPrivilege(String privilege) {
    this.privilege = privilege;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }
}
