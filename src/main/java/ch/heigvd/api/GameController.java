package ch.heigvd.api;

import ch.heigvd.data.Flag;
import ch.heigvd.data.GameEntry;
import ch.heigvd.data.Run;
import io.javalin.http.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;



public class GameController {

    private final Map<String,GameEntry> _games=new HashMap<>();

    public GameController(){
        _games.put("borris",new GameEntry("Borris",6,
                new Flag("sword",0),
                new Flag("magic burst",0),
                new Flag("fast travel",0),
                new Flag("sword dash",0),
                new Flag("master key",1),
                new Flag("ascension",2),
                new Flag("gg ?",3)
        ));
    }

    record FlagData(String token, int flagIndex, float time) implements IToken{
        @Override
        public String getToken() {
            return token;
        }
    }


    private GameEntry getGameEntry(Context ctx){
        String gameName = ctx.pathParamAsClass("gameName", String.class).get();
        GameEntry g = _games.get(gameName);
        if(g==null){
            throw new NotFoundResponse();
        }
        return g;
    }

    private Run getRuns(Context ctx,GameEntry game){
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        Run r = game.getRun(id);
        if(r==null){
            throw new NotFoundResponse();
        }
        return r;
    }



    public void getGame(Context ctx){
        GameEntry g = getGameEntry(ctx);
        ctx.status(HttpStatus.OK).json(g);
    }

    public void getGameLists(Context ctx){
        ctx.status(HttpStatus.OK).json(_games.keySet());
    }

    public void getRunLists(Context ctx){
        GameEntry g = getGameEntry(ctx);
        ctx.status(HttpStatus.OK).json(g.getRuns());
    }

    public void createRun(Context ctx){
        GameEntry g = getGameEntry(ctx);

        // check body
        userData pl = PlayerController.checkBodyToken(ctx,ctx.bodyValidator(TokenData.class).get());

        // create run
        int id = g.createNewRun();
        Run r = g.getRun(id);

        System.out.println(id);

        if(!r.createRunEntry(pl.token())){
            throw new ConflictResponse();
        }

        class A{
            public int runId = id;
        }

        ctx.status(HttpStatus.CREATED).json(new A());
    }

    public void joinRun(Context ctx){
        Run r = getRuns(ctx,getGameEntry(ctx));

        // check body
        userData pl = PlayerController.checkBodyToken(ctx,ctx.bodyValidator(TokenData.class).get());
        if(!r.createRunEntry(pl.token())){
            throw new ConflictResponse();
        }

        ctx.status(HttpStatus.OK);
    }

    public void getRunState(Context ctx){
        Run r = getRuns(ctx,getGameEntry(ctx));

        // check if update needed
        LocalDateTime lastKnownModification = ctx.headerAsClass("If-Modified-Since", LocalDateTime.class).getOrDefault(null);
        if(r.lastUpdate.equals(lastKnownModification)){
            ctx.status(HttpStatus.NOT_MODIFIED);
            return;
        }

        ctx.status(HttpStatus.OK).header("Last-Modified", String.valueOf(r.lastUpdate)).json(r);
    }

    public void startRun(Context ctx){
        Run r = getRuns(ctx,getGameEntry(ctx));

        userData pl = PlayerController.checkBodyToken(ctx,ctx.bodyValidator(TokenData.class).get());
        if(!r.startRun(pl.token())){
            throw new MethodNotAllowedResponse();
        }

        ctx.status(HttpStatus.OK);
    }

    public void postSplit(Context ctx){

        System.out.println(ctx.body());

        Run r = getRuns(ctx,getGameEntry(ctx));

        FlagData fd = ctx.bodyValidator(FlagData.class).get();

        userData pl = PlayerController.checkBodyToken(ctx,fd);

        System.out.println(fd);

        if(!r.putSplit(pl.token(),fd.flagIndex(),fd.time())){
            throw new MethodNotAllowedResponse();
        }

        ctx.status(HttpStatus.OK);
    }

    public void deleteRun(Context ctx){
        GameEntry g = getGameEntry(ctx);
        Run r = getRuns(ctx,g);

        // check body
        userData pl = PlayerController.checkBodyToken(ctx,ctx.bodyValidator(TokenData.class).get());
        if(!g.deleteRun(r.id, pl.token()))throw new MethodNotAllowedResponse();

        ctx.status(HttpStatus.OK);
    }

}
