package com.lm.SpringSecLogin.payload.response;

/*
    Classe che ci consente di operare sul messaggio di risposta
*/

public class MessageResponse
{
    private String message;

    public MessageResponse(String message)
    {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}