package ch.heig;

import ch.heig.data.Flag;
import ch.heig.data.GameEntry;

public class Main {
    public static void main(String[] args) {
        GameEntry defaultGame=new GameEntry("Borris",7,
                new Flag("sword",0),
                new Flag("magic burst",0),
                new Flag("fast travel",0),
                new Flag("sword dash",0),
                new Flag("master key",1),
                new Flag("ascension",2),
                new Flag("gg ?",3)
        );
    }
}