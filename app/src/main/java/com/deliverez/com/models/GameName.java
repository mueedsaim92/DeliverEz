package com.deliverez.com.models;


import android.content.Intent;

import java.io.Serializable;

public class GameName implements Serializable {

    public String gameId;
    public String gameName;
    public String gameTime;
    public Integer image;
    public String gameImage;
    public String gameShortName;
    public String gameTimeIn24hrs;

    public boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameTime() {
        return gameTime;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }

    public String getGameImage() {
        return gameImage;
    }

    public void setGameImage(String gameImage) {
        this.gameImage = gameImage;
    }

    public String getGameShortName() {
        return gameShortName;
    }

    public void setGameShortName(String gameShortName) {
        this.gameShortName = gameShortName;
    }

    public String getGameTimeIn24hrs() {
        return gameTimeIn24hrs;
    }

    public void setGameTimeIn24hrs(String gameTimeIn24hrs) {
        this.gameTimeIn24hrs = gameTimeIn24hrs;
    }

    @Override
    public String toString() {
        return "GameName{" +
                "gameId='" + gameId + '\'' +
                ", gameName='" + gameName + '\'' +
                ", gameTime='" + gameTime + '\'' +
                ", gameImage='" + gameImage + '\'' +
                ", gameShortName='" + gameShortName + '\'' +
                ", gameTimeIn24hrs='" + gameTimeIn24hrs + '\'' +
                '}';
    }
}
