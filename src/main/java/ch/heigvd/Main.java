package ch.heigvd;

import ch.heigvd.api.GameController;
import ch.heigvd.api.PlayerController;
import ch.heigvd.data.*;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import java.time.LocalDateTime;

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

    Javalin app =
        Javalin.create(
            config -> {
              config.bundledPlugins.enableCors(
                  cors -> {
                    cors.addRule(
                        it -> {
                          it.anyHost();
                        });
                  });
              config.validation.register(LocalDateTime.class, LocalDateTime::parse);
            });

    app.before(
        ctx -> {
          ctx.header("Access-Control-Allow-Origin", "*");
        });

    app.get(
        "/",
        context -> {
          context.status(HttpStatus.OK).json("alive");
        });

    // GAME routes

    // get a list of all avaliable game
    app.get("/games", gc::getGameLists);

    // get data from the game {gameName}
    app.get("/game/{gameName}", gc::getGame);

    // ### RUN

    // get the runs list of the game {gameName}
    app.get("/game/{gameName}/runs", gc::getRunLists);

    // create a new runs of the game {gameName}
    app.post("/game/{gameName}/create", gc::createRun);

    // get data from the run {id} of the game {gameName}
    app.get("/game/{gameName}/{id}", gc::getRunState);

    // join the run {id} of the game {gameName}
    app.post("/game/{gameName}/{id}/join", gc::joinRun);

    // start the run {id} of the game {gameName}
    app.post("/game/{gameName}/{id}/start", gc::startRun);

    // post a new split to the run {id} of the game {gameName}
    app.post("/game/{gameName}/{id}/put", gc::postSplit);

    // delete the run {id} of the game {gameName}
    app.delete("/game/{gameName}/{id}", gc::deleteRun);

    // PLAYER routes
    // create a new player
    app.post("/user/create", pc::createPlayer);

    // get a user data
    app.get("/user", pc::getPlayerState);

    // change the username of a player
    app.patch("/user/change", pc::changePlayerName);

    app.start(PORT);
  }
}
