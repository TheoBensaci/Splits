package ch.heigvd;

import ch.heigvd.data.Flag;
import ch.heigvd.data.GameEntry;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.staticfiles.Location;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {

    public static int PORT = 8000;

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

        Javalin app = Javalin.create(config -> {
                    config.staticFiles.add("/public", Location.CLASSPATH);
                    config.router.apiBuilder(() -> {
                        path("/api", () -> {
                            /*
                            get(UserController::getAllUsers);
                            post(UserController::createUser);
                            path("/{id}", () -> {
                                get(UserController::getUser);
                                patch(UserController::updateUser);
                                delete(UserController::deleteUser);
                            });
                            ws("/events", UserController::webSocketEvents);
                            */
                        });
                    });
                }
        );

        app.get(
                "/api",
                ctx ->
                        ctx.result(
                                "Hello, world from a GET request method with a `HttpStatus.OK` response status!"));
        app.post(
                "/api",
                ctx ->
                        ctx.result(
                                        "Hello, world from a POST request method with a `HttpStatus.CREATED` response status!")
                                .status(HttpStatus.CREATED));
        app.patch(
                "/api",
                ctx ->
                        ctx.result(
                                        "Hello, world from a PATCH request method with a `HttpStatus.OK` response status!")
                                .status(HttpStatus.OK));
        app.delete(
                "/api",
                ctx ->
                        ctx.result(
                                        "Hello, world from a DELETE request method with a `HttpStatus.NO_CONTENT` response status!")
                                .status(HttpStatus.NO_CONTENT));

        app.get(
                "/api/test/{param}",
                ctx -> {
                    String pathParameter = ctx.pathParam("param");
                    if(pathParameter==null){
                        throw new BadRequestResponse();
                    }
                    ctx.result(
                            "Boop : "
                                    + pathParameter
                                    + "!");
                });

        app.start(PORT);
    }
}