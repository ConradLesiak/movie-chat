package com.rgs.moviechat;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.rgs.moviechat.MainModule.User;
import com.rgs.moviechat.NetworkModule.ChatManager;
import com.rgs.moviechat.NetworkModule.ConnectionManager;

public class Main implements ApplicationListener {

	public static Stage stage;
	public static float width;
	public static float height;
	public static User user;
	public static Assets assets = new Assets();
	public static ScreenManager screenManager;
	public static ConnectionManager connectionManager = new ConnectionManager();
	public static ChatManager chatManager;

	//Starting method of the entire program; called once upon app open.
	@Override
	public void create () {
		//Setting up stage and viewport for Scene2D.
		stage = new Stage(new ExtendViewport(1080, 1920), new SpriteBatch());
		stage.getViewport().getCamera().position.set(0 ,stage.getCamera().viewportHeight/2, 1);
		Gdx.input.setInputProcessor(stage);
		width = stage.getWidth();
		height = stage.getHeight();
		//Create class objects before the loading process is started.
		user = new User();
		assets.load();
		screenManager = new ScreenManager();
		connectionManager.connectToServer();
		chatManager = new ChatManager();
	}

	//Main loop of the program; called continuously
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();

		assets.render();
	}

	//Called when the app is resized by the user; mobile apps usually do not get resized
	@Override
	public void resize(int width, int height) {

	}

	//Called when the app is not visible, but still running in background
	@Override
	public void pause() {
		//System.out.println("app paused");
	}

	//Called after Pause() when the app is visible again
	@Override
	public void resume() {
		//System.out.println("app resumed");
	}

	//Called when the app process is destroyed
	@Override
	public void dispose () {
		stage.dispose();
		//System.out.println("app closed");
	}
}
