package com.rgs.moviechat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.rgs.moviechat.ChatModule.BrowseScreen;
import com.rgs.moviechat.ChatModule.ChatScreen;
import com.rgs.moviechat.ChatModule.MovieScreen;
import com.rgs.moviechat.MainModule.SignInScreen;
import com.rgs.moviechat.MainModule.SplashScreen;
import com.rgs.moviechat.NetworkModule.ReconnectScreen;

public class ScreenManager {

    private Image redBackgroundImage;
    public static SplashScreen splashScreen;
    public static ReconnectScreen reconnectScreen;
    public static SignInScreen signInScreen;
    public static BrowseScreen browseScreen;
    public static MovieScreen movieScreen;
    public static ChatScreen chatScreen;

    //Class is used to hold screens for the app; Created upon app open; Splashscreen is shown;
    public ScreenManager() {
        Texture redBackgroundTexture = new Texture(Gdx.files.internal("red-background.png"));
        redBackgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        redBackgroundImage = new Image(redBackgroundTexture);
        redBackgroundImage.setPosition(-redBackgroundImage.getWidth()/2, 0);

        splashScreen = new SplashScreen(redBackgroundImage);
        splashScreen.showScreen();
        reconnectScreen = new ReconnectScreen(redBackgroundImage);
    }

    //Method for initializing screens that require ALL assets to be loaded first
    public void createScreens() {
        signInScreen = new SignInScreen(redBackgroundImage);
        browseScreen = new BrowseScreen();
        movieScreen = new MovieScreen();
        chatScreen = new ChatScreen();
    }
}
