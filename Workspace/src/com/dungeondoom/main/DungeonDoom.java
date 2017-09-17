package com.dungeondoom.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dungeondoom.screens.MainMenuScreen;

/**
 * @author Roshan Munjal
 * This is the main DungeonDoom class that extends Game.
 * It contains objects such as the camera, the batch, and
 * the ShapeRenderer, all of which are objects that are
 * needed throughout all the classes.
 */
public class DungeonDoom extends Game {
	
	// This declares the mainMenu and the Orthographic camera needed.
	public MainMenuScreen mainMenuScreen;
	public OrthographicCamera camera;
	
	// The SpriteBatch and the ShapeRenderer are both initialized in this class.
	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	
	// Adds game music to the game.
	public Music gameMusic;
	
	/**
	 * Constructor for the DungeonDoom Game.
	 */
	public DungeonDoom() {
		
	}

	/**
	 * Gets the game music from the main class.
	 * @return gameMusic
	 */
	public Music getGameMusic() {
		return gameMusic;
	}

	/**
	 * Sets the game music in the main class.
	 * @paran gameMusic : the music to put in the game
	 */
	public void setGameMusic(Music gameMusic) {
		this.gameMusic = gameMusic;
	}
	
	/**
	 * Gets the shapeRenderer from the main class.
	 * @return shapeRenderer
	 */
	public ShapeRenderer getShapeRenderer() {
		return shapeRenderer;
	}

	/**
	 * Returns the sprite batch (the only one in the project) to be used my classes.
	 * @return batch : the sprite batch used to draw things
	 */
	public SpriteBatch getBatch() {
		return batch;
	}

	/**
	 * Creates all the main objects needed in this class.
	 */
	public void create() {
		// Creates a camera, a batch, and a shapeRenderer.
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		// Creates the main menu, sets it to the current screen, and runs the music.
		mainMenuScreen = new MainMenuScreen(this);
		setScreen(mainMenuScreen);
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Theme Music.mp3"));
	}

	/**
	 * Renders the objects needed to be rendered.
	 */
	public void render() {
		super.render();
		
		// Constantly loops the music around.
		gameMusic.setLooping(true);
		gameMusic.play();
	}

	public void resize(int width, int height) {
		super.resize(width, height);
	}

	public void dispose() {
		super.dispose();
	}

	public void pause() {
		super.pause();
	}

	public void resume() {
		super.resume();
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

}
