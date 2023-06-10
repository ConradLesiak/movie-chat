package com.rgs.moviechat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.rgs.moviechat.NetworkModule.ConnectionManager;
import com.rgs.moviechat.NetworkModule.MainRequests.DataListResponse;

public class Assets {

    AssetManager assetManager = new AssetManager();
    public Label.LabelStyle whiteLabelStyle;
    public Label.LabelStyle blackLabelStyle;
    public Label.LabelStyle largeWhiteLabelStyle;
    public Label.LabelStyle largeBlackLabelStyle;
    public Texture grayButtonTexture;
    public static boolean assetsDownloaded = false;
    public static boolean assetsCreated = false;
    public static DataListResponse dataList;  //Object that holds moviesList and chatRoomsList

    //Method for pushing assets into the load queue; Instantly loads assets which are required for splash and reconnect screens
    public void load() {
        loadFonts();
        grayButtonTexture = new Texture(Gdx.files.internal("gray-button-background.png"));
        grayButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextureLoader.TextureParameter textureParameter = new TextureLoader.TextureParameter();
        textureParameter.minFilter = Texture.TextureFilter.Linear;
        textureParameter.magFilter = Texture.TextureFilter.Linear;
        assetManager.load("black-background.png", Texture.class, textureParameter);
        assetManager.load("gray-button-background.png", Texture.class, textureParameter);
        assetManager.load("textfield-background.png", Texture.class, textureParameter);
        assetManager.load("textfield-cursor.png", Texture.class, textureParameter);
        assetManager.load("textfield-selection.png", Texture.class, textureParameter);
        assetManager.load("redbar.png", Texture.class, textureParameter);
        assetManager.load("gray-bar.png", Texture.class, textureParameter);
    }

    //Loads the fonts that will be used for text; NOT put into the queue, instead loaded instantly
    public void loadFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        BitmapFont font1 = generator.generateFont(parameter);
        parameter.size = 80;
        BitmapFont font2 = generator.generateFont(parameter);
        font1.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font2.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        whiteLabelStyle = new Label.LabelStyle(font1, Color.WHITE);
        blackLabelStyle = new Label.LabelStyle(font1, Color.BLACK);
        largeWhiteLabelStyle = new Label.LabelStyle(font2, Color.WHITE);
        largeBlackLabelStyle = new Label.LabelStyle(font2, Color.BLACK);
        generator.dispose();
    }

    //Finishes creating all of the assets and moves on to the next screen; Must be called in the main loop, Main.render()
    public void render() {
        if(!assetManager.isFinished()) {
            assetManager.update();  //Assets still laoding
        }
        if(assetManager.isFinished() && !assetsDownloaded && ConnectionManager.connected) {   //Assets finished loading from queue; User is connected
            System.out.println("Assets finished loading");
            //Objects created using assets from AssetManager
            Main.screenManager.createScreens();
            //Get data from server and download thumbnails
            Main.connectionManager.requestDataLists();
            //Keep splash screen open for atleast 2 seconds; required*
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assetsDownloaded = true;
        }
        //Finish creating assets and show new screen
        if(assetsDownloaded && ConnectionManager.connected && !assetsCreated) {
            Main.screenManager.browseScreen.createImagesList();
            if(Main.user.prefs.getString("name") == "") {
                ScreenManager.signInScreen.showScreen();
            } else {
                ScreenManager.browseScreen.showScreen();
            }
            assetsCreated = true;
        }
    }

    //Getter method is needed since AssetManager CANNOT be static as per the LibGDX docs
    public AssetManager getAssetManager() {
        return assetManager;
    }
}
