package ch.heigvd.data;

import java.util.ArrayList;
import java.util.List;

public class Run {
    public final List<RunEntry> entries=new ArrayList<>();
    private final GameEntry _gameEntry;

    public Run(GameEntry gameEntry){
        this._gameEntry=gameEntry;
    }


    public boolean isRunning(){
        if(entries.isEmpty())return false;
        for (RunEntry ent : entries){
            if(!ent.isRunning())return false;
        }
        return true;
    }

    public boolean isFinish(){
        if(entries.isEmpty())return false;
        for (RunEntry ent : entries){
            if(!ent.isFinish())return false;
        }
        return true;
    }

    public RunEntry getRunEntry(String username){
        for (RunEntry ent : entries){
            if(ent.player.username.equals(username)){
                return ent;
            }
        }
        return null;
    }


    public boolean putSplit(String token, int flagIndex,float time){
        return putSplit(token,_gameEntry.generateSplit(flagIndex,time));
    }

    public boolean putSplit(String token,Split newSplit){
        if(!isRunning() || isFinish())return false;
        Player pl = PlayerBase.getPlayerByToken(token);
        if(pl==null)return false;
        for (RunEntry ent : entries){
            if(ent.player.username.equals(pl.username)){
                return ent.putSplit(newSplit);
            }
        }
        return false;
    }

    public boolean startRun(){
        if(isRunning() || isFinish())return false;
        for (RunEntry ent : entries){
            ent.startRun();
        }
        return true;
    }

    public boolean createRunEntry(String token){
        if(isRunning() || isFinish())return false;
        Player pl = PlayerBase.getPlayerByToken(token);
        if(pl==null)return false;
        for (RunEntry ent : entries){
            if(ent.player.username.equals(pl.username)){
                return false;
            }
        }
        entries.add(new RunEntry(pl,_gameEntry));
        return true;
    }


}
