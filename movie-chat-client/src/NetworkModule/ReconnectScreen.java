package com.rgs.moviechat.NetworkModule;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.rgs.moviechat.Main;

public class ReconnectScreen {

    private Image backgroundImage;
    private Label textLabel;
    private Label buttonLabel;
    private Image buttonBackground;

    //Constructor for initializing the reconnect screen; Can be created before assets are loaded.
    public ReconnectScreen(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
        textLabel = new Label("There was a problem connecting\nwith the server. Please check your\nconnection and try again.", Main.assets.whiteLabelStyle);
        textLabel.setPosition(-textLabel.getWidth()/2, Main.height*6/10);
        textLabel.setAlignment(Align.center);
        buttonLabel = new Label("Retry", Main.assets.blackLabelStyle);
        buttonLabel.setPosition(-buttonLabel.getWidth()/2, Main.height*4/10);
        buttonLabel.addListener(new ClickListener() {
            @Override   //Listen detects click event and attempts to connect to server.
            public void clicked(InputEvent event, float x, float y) {
                Main.connectionManager.connectToServer();
            }
        });
        buttonBackground = new Image(Main.assets.grayButtonTexture);
        buttonBackground.setPosition(-buttonBackground.getWidth()/2, buttonLabel.getY()-buttonLabel.getHeight()/4);
    }

    //Clears the screen and displays itself;
    public void showScreen() {
        Main.stage.clear();
        Main.stage.addActor(backgroundImage);
        Main.stage.addActor(textLabel);
        Main.stage.addActor(buttonBackground);
        Main.stage.addActor(buttonLabel);
    }
}
