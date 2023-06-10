package com.rgs.moviechatserver;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

//Main class is the starting point for the server.
public class Main implements ApplicationListener {

	public static ConnectionManager connectionManager;
	public static DatabaseManager databaseManager;
	public static ChatManager chatManager;

	//Called automatically on server start; constructs objects necessary for server processing.
	@Override
	public void create () {
		connectionManager = new ConnectionManager();
		databaseManager = new DatabaseManager();
		databaseManager.createMovieList();
		databaseManager.createChatRoomsList();
		databaseManager.updateChatRoomsTable();
		chatManager = new ChatManager();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose () {
		databaseManager.disconnect();
	}
}
