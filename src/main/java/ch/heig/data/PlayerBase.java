package ch.heig.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class PlayerBase {
    private static HashMap<String, Player> _players=new HashMap<>();          // use to manage tokens

    private static final String _TOKEN_CHAR_SET="1234567890qwertzuiopasdfghjklyxcvbnmQWERTZUIOPASDFGHJKLYXCVBNM,.-!?+*%&(){}[]";
    private static final int _TOKEN_LENGTH=30;


    public static Player createNewPlayer(String username){
        Set<Map.Entry<String,Player>> plSet=_players.entrySet();

        for (Map.Entry<String,Player> pl : plSet){
            if(pl.getValue().username.equals(username)){
                return null;
            }
        }

        // generate token
        String token=generateToken();
        Player newPlayer=new Player(username);
        _players.put(token,newPlayer);
        return newPlayer;
    }


    private static String generateToken(){
        Random rnd=new Random(System.currentTimeMillis());

        String token;
        do{
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < _TOKEN_LENGTH; i++) {
                str.append(_TOKEN_CHAR_SET.charAt(rnd.nextInt(0, _TOKEN_CHAR_SET.length())));
            }
            token=str.toString();
        }while (_players.containsKey(token));

        return token;
    }

    public static Player getPlayerByToken(String token){
        if(!_players.containsKey(token))return null;

        return _players.get(token);
    }

    public static Player getPlayerByUsername(String username){
        Set<Map.Entry<String,Player>> plSet=_players.entrySet();

        for (Map.Entry<String,Player> pl : plSet){
            if(pl.getValue().username.equals(username)){
                return pl.getValue();
            }
        }

        return null;
    }
}
