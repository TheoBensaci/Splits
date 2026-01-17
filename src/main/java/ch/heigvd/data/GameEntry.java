package ch.heigvd.data;

import java.util.ArrayList;
import java.util.Arrays;

public class GameEntry {
    public final String name;
    public final int finalFlagIndex;
    public final Flag[] flags;
    private final ArrayList<Run> _runs=new ArrayList<>();

    public GameEntry(String name, int finalFlagIndex, Flag... flags) {
        this.name = name;
        this.finalFlagIndex = finalFlagIndex;
        this.flags = Arrays.copyOf(flags, flags.length);
    }


    public int createNewRun(){
        int index=_runs.size();
        _runs.add(new Run(this));
        return index;
    }

    public Run getRun(int index){
        if(index<0 || index>=getNumberOfRun()){
            return null;
        }
        return _runs.get(index);
    }

    public int getNumberOfRun(){
        return _runs.size();
    }


    public Flag getFlag(int index){
        if(index<0 || index>=flags.length)return null;
        return flags[index];
    }

    public Flag[] getFlags(){
        return flags;
    }

    public int getNumberOfFlags(){
        return flags.length;
    }

    public Split generateSplit(int flagIndex,float time){
        return new Split(flagIndex, getFlag(flagIndex),time);
    }



}
