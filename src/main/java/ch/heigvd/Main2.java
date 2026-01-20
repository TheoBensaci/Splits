package ch.heigvd;

import ch.heigvd.data.*;
import io.javalin.Javalin;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.HttpStatus;
import io.javalin.http.staticfiles.Location;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Main2 {

    public static int PORT = 8000;

    public static void main(String[] args) {
        GameEntry defaultGame=new GameEntry("Borris",6,
                new Flag("sword",0),
                new Flag("magic burst",0),
                new Flag("fast travel",0),
                new Flag("sword dash",0),
                new Flag("master key",1),
                new Flag("ascension",2),
                new Flag("gg ?",3)
        );
        System.out.println("\ncreate player 'test'");
        String token = PlayerBase.createNewPlayer("test");
        Player pl = PlayerBase.getPlayerByToken(token);
        System.out.println("\nToken : "+token);
        System.out.println("\nCreate new run");
        int index = defaultGame.createNewRun();
        Run run = defaultGame.getRun(index);
        System.out.println("\nCreate new entry");
        System.out.println(run.createRunEntry(token));
        System.out.println(run.getRunEntry(pl.username));
        defaultGame.getRun(index).startRun(pl.username);

        System.out.println("\nStart run ");
        System.out.println(run.getRunEntry(pl.username));

        System.out.println("\nAdd new Split");
        run.putSplit(token,0, (float)(System.currentTimeMillis()+66000f));
        System.out.println(run.getRunEntry(pl.username));

        System.out.println("\nAdd new Split");
        run.putSplit(token,1, (float)(System.currentTimeMillis()+76000f));
        System.out.println(run.getRunEntry(pl.username));

        System.out.println("\nAdd new Split with protity");
        run.putSplit(token,5, (float)(System.currentTimeMillis()+86000f));
        System.out.println(run.getRunEntry(pl.username));

        System.out.println("\nAdd new Split with protity");
        run.putSplit(token,3, (float)(System.currentTimeMillis()+96000f));
        System.out.println(run.getRunEntry(pl.username));

        System.out.println("\nAdd final Split");
        run.putSplit(token,6, (float)(System.currentTimeMillis()+106000f));
        System.out.println(run.getRunEntry(pl.username));

    }
}