package com.rgs.moviechat.ChatModule;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.rgs.moviechat.Main;
import com.rgs.moviechat.NetworkModule.ChatRequests.ExitChatRequest;
import com.rgs.moviechat.NetworkModule.ChatRequests.SendMessage;

public class ChatScreen {

    private Movie movie;
    private ChatRoom chatRoom;
    private Image backgroundImage;
    private Image navBarImage;
    private Label navLabel;
    private Label backLabel;
    private Image backButton;
    private TextField messageField;
    private Label sendLabel;
    private Image sendButton;
    private Table browser = new Table();
    private Table table = new Table();
    private ScrollPane scrollPane;
    private Label timeLabel;

    //Constructor for initializing the chat screen; Call after assets are loaded.
    public ChatScreen() {
        backgroundImage = new Image(Main.assets.getAssetManager().get("black-background.png", Texture.class));
        backgroundImage.setPosition(-backgroundImage.getWidth()/2, 0);
        navBarImage = new Image(Main.assets.getAssetManager().get("redbar.png", Texture.class));
        navBarImage.setPosition(-navBarImage.getWidth()/2, Main.height-navBarImage.getHeight());
        backLabel = new Label("Back", Main.assets.blackLabelStyle);
        backLabel.setPosition(-Main.width/2+Main.height*3/100, Main.height-navBarImage.getHeight()/2-backLabel.getHeight()/2);
        backLabel.setAlignment(Align.center);
        backButton = new Image(Main.assets.getAssetManager().get("gray-button-background.png", Texture.class));
        backButton.setPosition(backLabel.getX()-backLabel.getWidth()/2, backLabel.getY()-backButton.getHeight()/6);
        backLabel.addListener(new ClickListener() {
            @Override   //Listener detects click event on back button; Send an exit request to the server and display movie screen; Display reconnect screen on server request failed.
            public void clicked(InputEvent event, float x, float y) {
                messageField.setText("Enter Message");
                if(Main.connectionManager.getClient().sendTCP(new ExitChatRequest(chatRoom.getID())) != 0) {
                    Main.screenManager.movieScreen.showMovieScreen(movie);
                } else {
                    Main.screenManager.reconnectScreen.showScreen();
                }
            }
        });
        navLabel = new Label("Nav Label Text", Main.assets.largeWhiteLabelStyle);
        navLabel.setPosition(-Main.width/2+backButton.getWidth(), Main.height - navBarImage.getHeight()/2 - navLabel.getHeight()/2);
        navLabel.setAlignment(Align.left);
        timeLabel = new Label("Start Time", Main.assets.whiteLabelStyle);
        timeLabel.setPosition(-Main.width/2, Main.height-navBarImage.getHeight()-timeLabel.getHeight());
        sendLabel = new Label("Send", Main.assets.whiteLabelStyle);
        sendLabel.setPosition(Main.width/2 - sendLabel.getWidth()-Main.height*3/100, Main.height-navBarImage.getHeight()-timeLabel.getHeight()-sendLabel.getHeight()-Main.height*1/100);
        sendLabel.setAlignment(Align.center);
        sendButton = new Image(Main.assets.getAssetManager().get("gray-button-background.png", Texture.class));
        sendButton.setPosition(sendLabel.getX()-sendLabel.getWidth()/2, sendLabel.getY()-sendLabel.getHeight()/4);
        messageField = new TextField("Enter Message", new TextField.TextFieldStyle(Main.assets.blackLabelStyle.font, Color.BLACK,
                new Image(Main.assets.getAssetManager().get("textfield-cursor.png", Texture.class)).getDrawable(),
                new Image(Main.assets.getAssetManager().get("textfield-selection.png", Texture.class)).getDrawable(),
                new Image(Main.assets.getAssetManager().get("textfield-background.png", Texture.class)).getDrawable()));
        messageField.setMaxLength(160);
        messageField.setSize(Main.width-sendButton.getWidth(), Main.height*1/20);
        messageField.setPosition(-Main.width/2+Main.height*1/200, Main.height-navBarImage.getHeight()-timeLabel.getHeight()-messageField.getHeight()-Main.height*1/200);
        messageField.addListener(new ClickListener() {
            @Override   //Listener detects click event on the text field; If contents are default, select contents for easier usability.
            public void clicked(InputEvent event, float x, float y) {
                if(messageField.getText().equalsIgnoreCase("Enter Message"))
                    messageField.selectAll();
            }
        });
        sendLabel.addListener(new ClickListener() {
            @Override   //Listener detects click event on send button; Resets message field, hides keyboard, and sends message to server; Display reconnect screen on server request failed.
            public void clicked(InputEvent event, float x, float y) {
                if(!messageField.getText().equalsIgnoreCase("") && !messageField.getText().equalsIgnoreCase("Enter Message")) {
                    Message message = new Message(Main.user.getName(), messageField.getText());
                    messageField.setText("Enter Message");
                    Main.stage.unfocus(messageField);
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    if(Main.connectionManager.getClient().sendTCP(new SendMessage(chatRoom.getID(), message)) != 0) {
                        showMessage(message);
                    } else {
                        Main.screenManager.reconnectScreen.showScreen();
                    }
                }
            }
        });
        scrollPane = new ScrollPane(table, new ScrollPane.ScrollPaneStyle());
        scrollPane.setScrollingDisabled(true, false);
        browser.add(scrollPane);
        browser.setBounds(-Main.width/2, 0, Main.width, Main.height-navBarImage.getHeight()-messageField.getHeight()-timeLabel.getHeight());
        scrollPane.layout();
    }

    //Updates the scroll pane with a new message that is drawn on the screen.
    public void showMessage(Message message) {
        String msg = message.getName() + ": " + message.getText();
        Label text = new Label(msg, Main.assets.whiteLabelStyle);
        text.setWrap(true);
        table.add(text).width(Main.width).padBottom(Main.height*1/100);;
        table.row();
        browser.remove();
        scrollPane.layout();
        scrollPane.layout();
        scrollPane.setScrollY(table.getHeight());
        Main.stage.addActor(browser);
    }

    //Clears the previous screen and displays itself; Input of movie object, chatroom object, and time are required to display the correct chat room.
    public void showChatScreen(Movie movie, ChatRoom chatRoom, String time) {
        Gdx.input.setOnscreenKeyboardVisible(false);
        this.movie = movie;
        this.chatRoom = chatRoom;
        table.reset();
        Main.stage.addActor(backgroundImage);
        Main.stage.addActor(navBarImage);
        navLabel.setText(movie.getTitle());
        Main.stage.addActor(navLabel);
        Main.stage.addActor(backButton);
        Main.stage.addActor(backLabel);
        Main.stage.addActor(sendButton);
        Main.stage.addActor(sendLabel);
        Main.stage.addActor(messageField);
        timeLabel.setText(time);
        Main.stage.addActor(timeLabel);
    }
}
