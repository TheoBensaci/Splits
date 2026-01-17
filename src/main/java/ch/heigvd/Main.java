package ch.heigvd;

import ch.heigvd.data.*;
import ch.heigvd.web.GameController;
import ch.heigvd.web.PlayerController;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.staticfiles.Location;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {

    public static int PORT = 8000;

    public static void main(String[] args) {
        /*
        String token = PlayerBase.createNewPlayer("test");
        int index = defaultGame.createNewRun();
        Run run = defaultGame.getRun(index);
        run.createRunEntry(token);
        defaultGame.getRun(index).startRun();
        run.putSplit(token,0, (float)(System.currentTimeMillis()+66000f));

        run.putSplit(token,1, (float)(System.currentTimeMillis()+76000f));

        run.putSplit(token,5, (float)(System.currentTimeMillis()+86000f));


        run.putSplit(token,6, (float)(System.currentTimeMillis()+106000f));*/

        GameController gc = new GameController();
        PlayerController pc = new PlayerController();
        /*
        Javalin app = Javalin.create(config -> {
                    config.staticFiles.add("/public", Location.CLASSPATH);
                }
        );*/

        Javalin app = Javalin.create();


        // GAME routes
        app.get("/games",gc::getGameLists);
        app.get("/{gameName}",gc::getGame);
        app.get("/{gameName}/flags",gc::getFlagsLists);

        // runs
        app.get("/{gameName}/runs",gc::getRunLists);
        app.post("/{gameName}/create",gc::createRun);
        app.get("/{gameName}/{id}",gc::getRunState);
        app.post("/{gameName}/{id}/join",gc::joinRun);
        app.delete("/{gameName}/{id}/dell",gc::deleteRun);
        app.post("/{gameName}/{id}/start",gc::startRun);
        app.put("/{gameName}/{id}/put",gc::putSplit);

        // PLAYER routes
        app.post("/user/create",pc::createPlayer);
        app.post("/user",pc::getPlayerState);


        app.start(PORT);
    }
}