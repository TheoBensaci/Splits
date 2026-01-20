package ch.heigvd.data;

public record Flag(String name, int priority) {
  @Override
  public String toString() {
    return name + " | " + priority;
  }
}
