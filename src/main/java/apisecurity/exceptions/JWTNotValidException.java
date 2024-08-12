package apisecurity.exceptions;

public class JWTNotValidException extends Exception {
  public JWTNotValidException(String requirement) {
    super("you don't have permissions, requied:" + requirement);
  }
}
