package ch.heigvd.data;

import java.util.*;

public class GameEntry {
  public final String name;
  public final int finalFlagIndex;
  public final Flag[] flags;
  private final Map<Integer, Run> _runs = new HashMap<>();

  public GameEntry(String name, int finalFlagIndex, Flag... flags) {
    this.name = name;
    this.finalFlagIndex = finalFlagIndex;
    this.flags = Arrays.copyOf(flags, flags.length);
  }

  public int createNewRun() {
    int index = _runs.size();
    _runs.put(index, new Run(this, index));
    return index;
  }

  public Run getRun(int index) {
    return _runs.get(index);
  }

  public int getNumberOfRun() {
    return _runs.size();
  }

  public Flag getFlag(int index) {
    if (index < 0 || index >= flags.length) return null;
    return flags[index];
  }

  public Flag[] getFlags() {
    return flags;
  }

  public int getNumberOfFlags() {
    return flags.length;
  }

  public Split generateSplit(int flagIndex, float time) {
    return new Split(flagIndex, getFlag(flagIndex), time);
  }

  public Run[] getRuns() {
    Run[] runs = new Run[_runs.size()];
    int i = 0;
    for (Map.Entry<Integer, Run> r : _runs.entrySet()) {
      runs[i] = r.getValue();
      i++;
    }
    return runs;
  }

  public boolean deleteRun(int index, String token) {
    if (!_runs.containsKey(index)) return false;

    // check if has permition
    if (!_runs.get(index).ownerToken.equals(token)) return false;

    _runs.remove(index);
    return true;
  }
}
