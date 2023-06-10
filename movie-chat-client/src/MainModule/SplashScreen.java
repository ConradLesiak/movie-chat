package com.rgs.moviechat.MainModule;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.rgs.moviechat.Assets;
import com.rgs.moviechat.Main;
import com.rgs.moviechat.NetworkModule.ConnectionManager;

public class SplashScreen {

    private Image logoImage;
    private Image backgroundImage;
    private Label loadingLabel;

    //SplashScreen constructor: Loads its own assets to be displayed before starting to load the rest of the assets from AssetManager; Displays itself, then starts loading.
    public SplashScreen(Image backgroundImage) {
        Texture logoTexture = new Texture(Gdx.files.internal("logo.png"));
        logoTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        logoImage = new Image(logoTexture);
        logoImage.setPosition(-logoImage.getWidth()/2, Main.stage.getCamera().viewportHeight/2);
        this.backgroundImage = backgroundImage;
        loadingLabel = new Label("Loading...", Main.assets.whiteLabelStyle);
        loadingLabel.setAlignment(Align.center);
        loadingLabel.setPosition(-loadingLabel.getWidth()/2, Main.height*.5f/10);
    }

    //Clears the screen and displays itself; If assets are loaded and connection is established, display browse screen.
    public void showScreen() {
        Main.stage.clear();
        Main.stage.addActor(backgroundImage);
        Main.stage.addActor(logoImage);
        Main.stage.addActor(loadingLabel);
        if(Assets.assetsDownloaded && ConnectionManager.connected) {
            Main.screenManager.browseScreen.showScreen();
        }
    }

}
