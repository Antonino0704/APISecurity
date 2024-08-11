package apisecurity.exceptions;

public class JWTNotValidException extends Exception {
  public JWTNotValidException(String privilege) {
    super("you don't have permissions, requied:" + privilege);
  }
}
