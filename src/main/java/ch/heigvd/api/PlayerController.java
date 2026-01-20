package ch.heigvd.api;

import ch.heigvd.data.Player;
import ch.heigvd.data.PlayerBase;
import ch.qos.logback.core.subst.Token;
import io.javalin.http.*;

public class PlayerController {

    record PlayerInfo(String name){}

    /**
     * check token form body
     * @param ctx
     * @return pair (username,token) of good
     */
    public static userData checkBodyToken(Context ctx,IToken tokenData){
        userData data=null;
        Player pl = PlayerBase.getPlayerByToken(tokenData.getToken());
        if(pl==null){
            throw new UnauthorizedResponse();
        }
        else{
            data=new userData(pl.username,tokenData.getToken());
        }
        return data;
    }


    public void createPlayer(Context ctx){
        PlayerInfo playerInfo = ctx.bodyValidator(PlayerInfo.class).check(obj -> {
            return obj.name!=null && !obj.name.isEmpty();
        }, "Missing name").get();
        String token = PlayerBase.createNewPlayer(playerInfo.name);

        if(token==null){
            throw new ConflictResponse();
        }


        ctx.status(HttpStatus.CREATED).json(new TokenData(token));
    }

    public void getPlayerState(Context ctx){
        String username = ctx.queryParam("username");

        if(username==null){
            throw new BadRequestResponse();
        }


        Player pl = PlayerBase.getPlayerByUsername(username);
        if(pl==null){
            throw new NotFoundResponse();
        }

        ctx.status(HttpStatus.OK).json(pl);
    }

    record ChangeNameData(String name, String token) implements IToken{

        @Override
        public String getToken() {
            return token();
        }
    }

    public void changePlayerName(Context ctx){
        ChangeNameData cn = ctx.bodyValidator(ChangeNameData.class).check(obj -> {
            return obj.name!=null && !obj.name.isEmpty();
        }, "Missing name").get();
        userData pl = PlayerController.checkBodyToken(ctx,cn);
        if(!cn.name.equals(pl.username()) && PlayerBase.getPlayerByUsername(cn.name)!=null){
            throw new ConflictResponse();
        }

        PlayerBase.getPlayerByToken(cn.token).username=cn.name;

        ctx.status(HttpStatus.OK);
    }
}
