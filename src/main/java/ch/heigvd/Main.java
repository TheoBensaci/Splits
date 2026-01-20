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

    GameController gc = new GameController();
    PlayerController pc = new PlayerController();

    Javalin app =
        Javalin.create(
            config -> {
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
