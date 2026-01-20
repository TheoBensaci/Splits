package ch.heigvd.data;

public class TimeUtil {
  public static float getActualTime(float startTime) {
    return System.currentTimeMillis() - startTime;
  }

  public static String timeToString(float time) {
    int seconds = (int) (time / 1000) % 60;
    int minutes = (int) ((time / (1000 * 60)) % 60);
    int hours = (int) ((time / (1000 * 60 * 60)) % 24);
    return hours + ":" + minutes + ":" + seconds;
  }
}
