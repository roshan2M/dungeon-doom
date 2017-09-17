package com.dungeondoom.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeondoom.main.DungeonDoom;

/**
 * This is the Instructions Screen, which implements all methods from Screen.
 * @author Roshan Munjal
 *
 * The Instructions Screen shows the user what options there are during the game.
 * These include moving quickly, hitting the opponent, etc.
 */
public class InstructionsScreen implements Screen {
	
	// Initializes the game, batch, and bitmapFont.
	private DungeonDoom game;
	private SpriteBatch batch;
	private BitmapFont font;
	
	// Initializes the background.
	private Texture background;
	
	// Initializes the different textures needed in the screen.
	private Texture wasdKeys;
	private Texture spacebar;
	private Texture jKey;
	private Texture escKey;
	
	// This sets the base values for the x and y of the options and images.
	private int baseX = 50;
	private int baseY = 150;
	private int width = 450;
	private int height = 74;
	
	// This is the second base value for y.
	private int baseY2 = 505;
	
	// This is a boolean to check if the mouse is hovering over this button.
	private boolean lightBack = false;
	
	// Gets the textures for the "Back" button and the lighter version.
	private Texture backButton;
	private Texture backButtonLight;
	
	/**
	 * Constructor for the InstructionsScreen class.
	 * @param game : the game that is common to all classes
	 */
	public InstructionsScreen(DungeonDoom game) {
		// Initializes necessary variables.
		this.game = game;
		batch = game.getBatch();
		font = new BitmapFont(); // Initizlies a bitmap font.
		font.setScale(1.5f);
		
		// Initializes the textures.
		background = new Texture(Gdx.files.internal("assets/BackgroundDungeon.jpg"));
		wasdKeys = new Texture(Gdx.files.internal("assets/WASD.png"));
		spacebar = new Texture(Gdx.files.internal("assets/spacebar.png"));
		jKey = new Texture(Gdx.files.internal("assets/letter-j-icon.png"));
		escKey = new Texture(Gdx.files.internal("assets/esc-icon.png"));
		
		backButton = new Texture(Gdx.files.internal("assets/Back.png"));
		backButtonLight = new Texture(Gdx.files.internal("assets/Back_Light.png"));
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Updates the state of the screen.
	 * @param delta : the time that has passed
	 */
	public void update(float delta) {
		/*
		if (Gdx.input.getX() > baseX && Gdx.input.getX() < baseX + width && Gdx.input.getY() > baseY && Gdx.input.getY() < baseY + height) {
			lightBack = true;
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				((DungeonDoom) Gdx.app.getApplicationListener()).setScreen(new PlayScreen(game));
			}
		}
		else {
			lightBack = false;
		}
		*/
		
		// If the mouse is within the defined region for the button.
		if (Gdx.input.getX() > baseX && Gdx.input.getX() < baseX + width && Gdx.input.getY() > baseY2 && Gdx.input.getY() < baseY2 + height) {
			// The button is then highlighted.
			lightBack = true;
			
			// When Left click is done in this region, then the game switches to the MainMenuScreen.
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				((DungeonDoom) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
			}
		}
		// If the mouse is not over the defined region, the button is not highlighted.
		else {
			lightBack = false;
		}
		
		// If escape is pressed, the entire program terminates.
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}

	@Override
	public void render(float delta) {
		// Clears the screen and puts a white background.
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Begins the batch
		batch.begin();
		
		// Draws all the textures in their respective locations.
		batch.draw(background, 0, 0, background.getWidth(), background.getHeight());
		
		batch.draw(wasdKeys, 100, 500, wasdKeys.getWidth(), wasdKeys.getHeight());
		batch.draw(spacebar, 500, 500, spacebar.getWidth(), spacebar.getHeight());
		batch.draw(jKey, 950, 500, jKey.getWidth(), jKey.getHeight());
		batch.draw(escKey, 950, 300, escKey.getWidth(), escKey.getHeight());
		
		// Draws some quick explanations beneath the textures.
		font.draw(batch, "Move Up/Down, Left/Right", 110, 480);
		font.draw(batch, "Move Quickly (This Reduces Stamina)", 480, 480);
		font.draw(batch, "Hit the Enemy Ghosts", 910, 490);
		font.draw(batch, "Escape from the Dungeon", 900, 290);
		font.draw(batch, "At Any Time", 960, 265);
		
		// Takes in user input.
		update(delta);
		
		// Draws a back button, depending on if the user is hovering over it or not.
		if (lightBack) {
			batch.draw(backButtonLight, baseX, baseY);
		}
		else {
			batch.draw(backButton, baseX, baseY);
		}
		
		// Ends the batch.
		batch.end();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

}
