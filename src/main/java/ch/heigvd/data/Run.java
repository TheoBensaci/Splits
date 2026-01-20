package ch.heigvd.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Run {
  public transient LocalDateTime lastUpdate;
  public final List<RunEntry> entries = new ArrayList<>();
  private final GameEntry _gameEntry;
  public final int id;
  public transient String ownerToken = "";

  public Run(GameEntry gameEntry, int id) {
    this._gameEntry = gameEntry;
    lastUpdate = LocalDateTime.now();
    this.id = id;
  }

  public boolean isRunning() {
    if (entries.isEmpty()) return false;
    for (RunEntry ent : entries) {
      if (!ent.isRunning()) return false;
    }
    return true;
  }

  public boolean isFinish() {
    if (entries.isEmpty()) return false;
    for (RunEntry ent : entries) {
      if (!ent.isFinish()) return false;
    }
    return true;
  }

  public RunEntry getRunEntry(String username) {
    for (RunEntry ent : entries) {
      if (ent.player.name.equals(username)) {
        return ent;
      }
    }
    return null;
  }

  public boolean putSplit(String token, int flagIndex, float time) {
    return putSplit(token, _gameEntry.generateSplit(flagIndex, time));
  }

  public boolean putSplit(String token, Split newSplit) {
    if (!isRunning() || isFinish()) return false;
    Player pl = PlayerBase.getPlayerByToken(token);
    if (pl == null) return false;
    for (RunEntry ent : entries) {
      if (ent.player.name.equals(pl.name)) {
        if (ent.putSplit(newSplit)) {
          lastUpdate = LocalDateTime.now();
          return true;
        }
        return false;
      }
    }
    return false;
  }

  public boolean startRun(String token) {
    if (isRunning() || isFinish()) return false;
    if (entries.isEmpty() || !ownerToken.equals(token)) return false;
    for (RunEntry ent : entries) {
      ent.startRun();
    }
    lastUpdate = LocalDateTime.now();
    return true;
  }

  public boolean createRunEntry(String token) {
    if (isRunning() || isFinish()) return false;
    Player pl = PlayerBase.getPlayerByToken(token);
    if (pl == null) return false;
    for (RunEntry ent : entries) {
      if (ent.player.name.equals(pl.name)) {
        return false;
      }
    }
    entries.add(new RunEntry(pl, _gameEntry));
    // if no owner
    if (ownerToken.equals("")) ownerToken = token;

    lastUpdate = LocalDateTime.now();
    return true;
  }
}
