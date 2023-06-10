package com.rgs.moviechat.ChatModule;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.rgs.moviechat.Main;
import com.rgs.moviechat.NetworkModule.ChatRequests.JoinChatRequest;

public class MovieScreen {

    private Image backgroundImage;
    private Image navBarImage;
    private Table browser = new Table();
    private Table table = new Table();
    private ScrollPane scrollPane;
    private Label textLabel;
    private Label backLabel;
    private Image backButton;
    private Label navLabel;

    //Constructor for initializing the movie screen; Call after assets are loaded.
    public MovieScreen() {
        backgroundImage = new Image(Main.assets.getAssetManager().get("black-background.png", Texture.class));
        backgroundImage.setPosition(-backgroundImage.getWidth()/2, 0);
        navBarImage = new Image(Main.assets.getAssetManager().get("redbar.png", Texture.class));
        navBarImage.setPosition(-navBarImage.getWidth()/2, Main.height-navBarImage.getHeight());
        textLabel = new Label("Text", Main.assets.whiteLabelStyle);
        textLabel.setAlignment(Align.center);
        backLabel = new Label("Back", Main.assets.blackLabelStyle);
        backLabel.setPosition(-Main.width/2+Main.height*3/100, Main.height-navBarImage.getHeight()/2-backLabel.getHeight()/2);
        backLabel.setAlignment(Align.center);
        backButton = new Image(Main.assets.getAssetManager().get("gray-button-background.png", Texture.class));
        backButton.setPosition(backLabel.getX()-backLabel.getWidth()/2, backLabel.getY()-backButton.getHeight()/6);
        backLabel.addListener(new ClickListener() {
            @Override   //Listener detects click event on back button and displays browse screen.
            public void clicked(InputEvent event, float x, float y) {
                Main.screenManager.browseScreen.showScreen();
            }
        });
        navLabel = new Label("Nav Label Text", Main.assets.largeWhiteLabelStyle);
        navLabel.setPosition(-Main.width/2+backButton.getWidth(), Main.height - navBarImage.getHeight()/2 - navLabel.getHeight()/2);
        navLabel.setAlignment(Align.left);
    }

    //Creates the table and scroll pane for the chat room browser
    public void createBrowser(final Movie movie) {
        table.reset();
        browser.reset();
        textLabel.setText(movie.getDescription() + " (" + movie.getYear() + ")");
        textLabel.setWrap(true);
        table.add(textLabel).width(Main.width).pad(Main.height*1/100).colspan(3);
        table.row();
        for(final ChatRoom chatRoom : movie.getChatRoomsList()) {
            Image bar = new Image(Main.assets.getAssetManager().get("gray-bar.png", Texture.class));
            final Label text = new Label("Start Time " + convertSecondsToTime(chatRoom.getStartTime()) + " CST", Main.assets.largeBlackLabelStyle);
            Group group = new Group();
            group.addActor(bar);
            group.addActor(text);
            text.setPosition(Main.width/2-text.getWidth()/2, bar.getHeight()/2-text.getHeight()/2);
            group.setSize(bar.getWidth(), bar.getHeight());
            group.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(Main.connectionManager.getClient().sendTCP(new JoinChatRequest(chatRoom.getID())) != 0) {
                        Main.screenManager.chatScreen.showChatScreen(movie, chatRoom, text.getText().toString());
                        text.getText();
                    } else {
                        Main.screenManager.reconnectScreen.showScreen();
                    }
                }
            });
            table.add(group).pad(Main.height*1/300).width(Main.width);
            table.row();
        }
        scrollPane = new ScrollPane(table, new ScrollPane.ScrollPaneStyle());
        scrollPane.setScrollingDisabled(true, false);
        browser.add(scrollPane);
        browser.setBounds(-Main.width/2, 0, Main.width, Main.height-navBarImage.getHeight());
        scrollPane.layout();
    }

    //Clears the previous screen and displays itself; Input of movie object is required to show associated chat rooms.
    public void showMovieScreen(Movie movie) {
        Gdx.input.setOnscreenKeyboardVisible(false);
        Main.stage.clear();
        Main.stage.addActor(backgroundImage);
        Main.stage.addActor(navBarImage);
        Main.stage.addActor(backButton);
        Main.stage.addActor(backLabel);
        navLabel.setText(movie.getTitle());
        Main.stage.addActor(navLabel);
        createBrowser(movie);
        Main.stage.addActor(browser);
    }

    //Converts integer of seconds into a String that represents clock time with AM/PM; Seconds must be less than 86400.
    public String convertSecondsToTime(int seconds) {
        String time = "";
        int hour = seconds/3600;
        int minute = (seconds%3600)/60;
        if(hour/12 == 1) {
            hour -= 12;
            if(hour == 0) {
                time = time.concat("12");
            } else {
                time = time.concat(Integer.toString(hour));
            }
            if(minute < 10) {
                time = time.concat(":0" + minute + " PM");
            } else {
                time = time.concat(":" + minute + " PM");
            }
        } else {
            if(hour == 0) {
                time = time.concat("12");
            } else {
                time = time.concat(Integer.toString(hour));
            }
            if(minute < 10) {
                time = time.concat(":0" + minute + " AM");
            } else {
                time = time.concat(":" + minute + " AM");
            }
        }
        return time;
    }
}
