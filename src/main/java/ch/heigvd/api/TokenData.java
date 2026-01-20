package ch.heigvd.api;

import ch.qos.logback.core.subst.Token;

import java.io.Serializable;



public record TokenData(String token) implements IToken{
    @Override
    public String getToken() {
        return token;
    }
}
