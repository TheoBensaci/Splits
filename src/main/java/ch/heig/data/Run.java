package ch.heig.data;

import java.util.ArrayList;
import java.util.List;

public class Run {
    public List<RunEntry> entries=new ArrayList<>();


    public boolean isRunning(){
        for (RunEntry ent : entries){
            if(!ent.isRunning())return false;
        }
        return true;
    }

    public boolean isFinish(){
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

    public boolean putSplit(String token,Split newSplit){
        Player pl = PlayerBase.getPlayerByToken(token);
        if(pl==null)return false;
        for (RunEntry ent : entries){

            if(ent.player.username.equals(pl.username)){
                return ent.putSplit(newSplit);
            }
        }
        return false;
    }


}
