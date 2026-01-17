package ch.heigvd.web;

import ch.heigvd.data.Flag;
import ch.heigvd.data.GameEntry;
import io.javalin.http.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController {

    private final Map<String,GameEntry> _games=new HashMap<>();

    public GameController(){
        _games.put("Borris",new GameEntry("Borris",7,
                new Flag("sword",0),
                new Flag("magic burst",0),
                new Flag("fast travel",0),
                new Flag("sword dash",0),
                new Flag("master key",1),
                new Flag("ascension",2),
                new Flag("gg ?",3)
        ));
    }

    public void getGame(Context ctx){

    }

    public void getGameLists(Context ctx){

    }

    public void getFlagsLists(Context ctx){

    }

    public void getRunLists(Context ctx){

    }

    public void createRun(Context ctx){

    }

    public void joinRun(Context ctx){

    }

    public void getRunState(Context ctx){

    }

    public void startRun(Context ctx){

    }

    public void putSplit(Context ctx){

    }

    public void deleteRun(Context ctx){

    }

}
