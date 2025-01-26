package com.freeman.partyplanner.Model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Chat {
    private int idChat;
    private String chatContent;
    private int idUser;

    public int getIdUser() {return idUser;}

    public void setIdUser(int idUser) {this.idUser = idUser;}

    public String getChatContent() { return chatContent; }

    public void setChatContent(String chatContent) { this.chatContent = chatContent; }

    public int getIdChat() { return idChat; }

    public void setIdChat(int idChat) { this.idChat = idChat; }
}
