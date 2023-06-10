package com.rgs.moviechat.MainModule;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.rgs.moviechat.Main;

public class SignInScreen {

    private Image backgroundImage;
    private Label signInText;
    private Image cursorImage;
    private Image highlightImage;
    private Image fieldBackground;
    private TextField nameField;
    private Image submitBackground;
    private Label submitText;

    //Constructor for initializing the sign in screen; Call affter assets are loaded.
    public SignInScreen(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
        signInText = new Label("Enter your name that will\nbe displayed to other users", Main.assets.whiteLabelStyle);
        signInText.setPosition(-signInText.getWidth()/2, Main.height*6/10);
        signInText.setAlignment(Align.center);
        cursorImage = new Image(Main.assets.getAssetManager().get("textfield-cursor.png", Texture.class));
        highlightImage = new Image(Main.assets.getAssetManager().get("textfield-selection.png", Texture.class));
        fieldBackground = new Image(Main.assets.getAssetManager().get("textfield-background.png", Texture.class));
        nameField = new TextField("Your name", new TextField.TextFieldStyle(Main.assets.blackLabelStyle.font, Color.BLACK,
                cursorImage.getDrawable(), highlightImage.getDrawable(), fieldBackground.getDrawable()));
        nameField.setSize(Main.height*3/10, Main.height*1/20);
        nameField.setPosition(-nameField.getWidth()/2, Main.height*4.5f/10);
        nameField.setAlignment(Align.center);
        nameField.setMaxLength(10);
        nameField.addListener(new ClickListener() {
           @Override    //Listener detects click event on the text field; If contents are default, select contents for easier usability.
           public void clicked(InputEvent event, float x, float y) {
               if(nameField.getText().equalsIgnoreCase("Your name"))
                   nameField.selectAll();
           }
        });
        submitBackground = new Image(Main.assets.getAssetManager().get("gray-button-background.png", Texture.class));
        submitText = new Label("Submit", Main.assets.blackLabelStyle);
        submitText.setPosition(-submitText.getWidth()/2, Main.height*3/10);
        submitText.setAlignment(Align.center);
        submitBackground.setPosition(-submitBackground.getWidth()/2, submitText.getY()-submitText.getHeight()/4);
        submitText.addListener(new ClickListener() {
            @Override   //Listener detects click event on the submit button; Saves the user name to preferences and display browse screen.
            public void clicked(InputEvent event, float x, float y) {
                if(!nameField.getText().equalsIgnoreCase("Your name")) {
                    Main.user.prefs.putString("name", nameField.getText());
                    Main.user.prefs.flush();
                    Main.screenManager.browseScreen.showScreen();
                }
            }
        });
    }

    //Clears the previous screen and displays itself; Retrieves user name from the internal preferences file.
    public void showScreen() {
        Main.stage.clear();
        Main.stage.addActor(backgroundImage);
        Main.stage.addActor(signInText);
        if(Main.user.prefs.getString("name").equalsIgnoreCase("")) {
            nameField.setText("Your name");
        } else {
            nameField.setText(Main.user.prefs.getString("name"));
        }
        Main.stage.addActor(nameField);
        Main.stage.addActor(submitBackground);
        Main.stage.addActor(submitText);
    }

}
