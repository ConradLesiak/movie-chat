package com.rgs.moviechat.MainModule;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class User {

    public static Preferences prefs;

    //Class used to save user preferences file internally.
    public User() {
        prefs = Gdx.app.getPreferences("MovieChatPrefs");
    }

    //Method for setting user name.
    public void editName(String name) {
        prefs.putString("name", name);
        prefs.flush();
    }

    //Method for getting user name.
    public String getName() {
        return prefs.getString("name");
    }
}
