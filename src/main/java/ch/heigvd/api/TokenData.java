package ch.heigvd.api;

public record TokenData(String token) implements IToken {
  @Override
  public String getToken() {
    return token;
  }
}
