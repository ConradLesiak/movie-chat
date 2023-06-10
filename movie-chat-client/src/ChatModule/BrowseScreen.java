package com.rgs.moviechat.ChatModule;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.rgs.moviechat.Assets;
import com.rgs.moviechat.Main;
import com.rgs.moviechat.ScreenManager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

public class BrowseScreen {

    private Image backgroundImage;
    private Image navBarImage;
    private Label nameLabel;
    private Label textLabel;
    private ArrayList<Image> thumbnailsList = new ArrayList<Image>();
    private Table browser = new Table();
    private Table table = new Table();
    private ScrollPane scrollPane;

    //Constructor for initializing the browse screen; Call after assets are loaded.
    public BrowseScreen() {
        backgroundImage = new Image(Main.assets.getAssetManager().get("black-background.png", Texture.class));
        backgroundImage.setPosition(-backgroundImage.getWidth()/2, 0);
        navBarImage = new Image(Main.assets.getAssetManager().get("redbar.png", Texture.class));
        navBarImage.setPosition(-navBarImage.getWidth()/2, Main.height-navBarImage.getHeight());
        nameLabel = new Label("your name", Main.assets.largeWhiteLabelStyle);
        nameLabel.setPosition(-nameLabel.getWidth()/2, Main.height - navBarImage.getHeight()/2 - nameLabel.getHeight()/2);
        nameLabel.setAlignment(Align.center);
        nameLabel.addListener(new ClickListener() {
            @Override   //Listener detects click event on user name; Output displays the sign in screen.
            public void clicked(InputEvent event, float x, float y) {
                Main.screenManager.signInScreen.showScreen();
            }
        });
        textLabel = new Label("Movies Available", Main.assets.largeWhiteLabelStyle);
        textLabel.setAlignment(Align.center);
    }

    //Creates the table and scroll pane for the movie browser
    public void createBrowser() {
        table.add(textLabel).pad(Main.height*1/100).colspan(3);
        table.row();
        for(int i = 1; i <= thumbnailsList.size(); i++){
            table.add(thumbnailsList.get(i-1)).pad(Main.height*1/100).width(Main.width*7.25f /25).height(Main.width*10.75f /25);
            if(i % 3 == 0)
                table.row();
        }
        scrollPane = new ScrollPane(table, new ScrollPane.ScrollPaneStyle());
        scrollPane.setScrollingDisabled(true, false);
        browser.add(scrollPane);
        browser.setBounds(-Main.width/2, 0, Main.width, Main.height-navBarImage.getHeight());
        scrollPane.layout();
    }

    //Fills ArrayList with new Image objects; adds click listener to each image
    public boolean createImagesList() {
        String destinationFile;
        for(final Movie movie : Assets.dataList.getMoviesList()) {
            destinationFile = "Movies/movie" + movie.getID() + ".jpg";
            Texture texture = new Texture(Gdx.files.local(destinationFile));
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            Image image = new Image(texture);
            image.addListener(new ClickListener() {
                @Override   //Listener detects click event on the movie image; Output displays movie screen for the movie clicked.
                public void clicked(InputEvent event, float x, float y) {
                    ScreenManager.movieScreen.showMovieScreen(movie);
                }
            });
            thumbnailsList.add(image);
        }
        System.out.println("Thumbnails created");
        createBrowser();
        return true;
    }

    //Downloads all images/thumbnails for each movie
    public void downloadImages() {
        String destinationFile;
        for(final Movie movie : Assets.dataList.getMoviesList()) {
            destinationFile = "Movies/movie" + movie.getID() + ".jpg";
            try {
                downloadImage(movie.getThumbnailURL(), destinationFile);
            } catch (IOException e) {
                System.out.println("Download image failed exception");
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Thumbnails downloaded");
    }

    //Downloads a single image file from the url and saves it to the destination file path; called in iterator in method, this.createImagesList()
    public void downloadImage(String imageURL, String destinationFile) throws IOException {
        URL url = new URL(imageURL);
        InputStream is = url.openStream();
        OutputStream os = Gdx.files.local(destinationFile).write(false);//new FileOutputStream(destinationFile);
        byte[] b = new byte[2048];
        int length;
        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }
        is.close();
        os.close();
    }

    //Clears the previous screen and displays itself; Retrieve user name from preferences to be displayed.
    public void showScreen() {
        Gdx.input.setOnscreenKeyboardVisible(false);
        Main.stage.clear();
        Main.stage.addActor(backgroundImage);
        Main.stage.addActor(navBarImage);
        nameLabel.setText(Main.user.prefs.getString("name"));
        Main.stage.addActor(nameLabel);
        Main.stage.addActor(browser);
    }
}
