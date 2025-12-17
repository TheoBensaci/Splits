package ch.heigvd.data;

import java.util.ArrayDeque;

public class RunEntry {
    public final Player player;
    private boolean _isRunning =false;
    private boolean _isFinish =false;
    private boolean _dnf =false;
    public float startTime;     // moment the run as started (server wise) in milli, only use to estimate run time during the run
    public final ArrayDeque<Split> splits=new ArrayDeque<>();
    public Split actualSplit;       // when a run is finish, the final time will be the actual split time
    public final GameEntry game;

    public RunEntry(Player player,GameEntry game){
        this.player=player;
        this.game=game;
    }


    public void startRun(){
        if(_isRunning)return;
        startTime=System.currentTimeMillis();
        _isRunning =true;
    }

    public void dnf(){
        if(_dnf || _isFinish || !_isRunning)return;
        _dnf=true;
        _isFinish=true;
        _isRunning=false;
    }

    public boolean isRunning() {
        return _isRunning;
    }

    public boolean isFinish(){
        return _isFinish;
    }

    public boolean asDNF(){
        return _dnf;
    }

    public boolean putSplit(Split newSplit){

        if(!_isRunning)return false;

        if(splits.isEmpty()){
            splits.push(newSplit);
            actualSplit=newSplit;
            return true;
        }

        if(splits.getLast().time() > newSplit.time()){
            return false;
        }

        splits.push(newSplit);


        if(newSplit.flag().priority() >= actualSplit.flag().priority()){
            actualSplit=newSplit;
        }

        if(newSplit.flagIndex()== game.finalFlagIndex()){
            _isFinish=true;
            _isRunning=false;
            actualSplit=newSplit;
        }

        return true;
    }
}
