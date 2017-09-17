package com.dungeondoom.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeondoom.main.DungeonDoom;

/**
 * @author Roshan Munjal
 * This is the MainMenuScreen class. Here, most of the logic behind
 * screen changing is done. This is quite important as it switches
 * between the InstructionsScreen, MainMenuScreen, and the PlayScreen.
 * In the beginning, the players are directed to the MainMenuScreen.
 * From there, they can choose to access the InstructionsScreen,
 * PlayScreen, or exit altogether. In the InstructionsScreen, they are
 * allowed to go back to the MainMenuScreen. In the PlayScreen, until
 * the player dies, there is no mechanism for him to go back to the
 * MainMenuScreen. (Maybe this is one aspect that could have been
 * improved in the game).
 */
public class MainMenuScreen implements Screen {
	
	// References to game, batch, and bitmap font.
	private DungeonDoom game;
	private SpriteBatch batch;
	
	// Checks if the button are being hovered over by the user.
	private boolean lightBegin = false;
	private boolean lightInstructions = false;
	private boolean lightExit = false;
	
	// Texture for the background.
	private Texture background;
	
	// Texture for the buttons.
	private Texture beginButton;
	private Texture instructionsButton;
	private Texture exitButton;
	
	// Texture for light buttons.
	private Texture beginButtonLight;
	private Texture instructionsButtonLight;
	private Texture exitButtonLight;
	
	// Decides a base position of which to start.
	private int baseX = 400;
	private int baseY = 450;
	private int width = 450;
	private int height = 74;
	
	// A second declared y-position.
	private int baseY2 = 205;

	/**
	 * @param game
	 * Constructor for the MainMenuScreen class.
	 */
	public MainMenuScreen(DungeonDoom game) {
		super();
		
		// Inherits the game from the class that set the screen.
		this.game = game;
		batch = game.getBatch();
		
		// Loads textures for buttons and backgrounds.
		beginButton = new Texture(Gdx.files.internal("assets/Begin.png"));
		instructionsButton = new Texture(Gdx.files.internal("assets/Instructions.png"));
		exitButton = new Texture(Gdx.files.internal("assets/Exit.png"));
		
		beginButtonLight = new Texture(Gdx.files.internal("assets/Begin_Light.png"));
		instructionsButtonLight = new Texture(Gdx.files.internal("assets/Instructions_Light.png"));
		exitButtonLight = new Texture(Gdx.files.internal("assets/Exit_Light.png"));
		
		background = new Texture(Gdx.files.internal("assets/BackgroundDungeon.jpg"));
	}

	public void update(float delta) {
		/*
		if (Gdx.input.getX() > baseX && Gdx.input.getX() < baseX + width && Gdx.input.getY() > baseY && Gdx.input.getY() < baseY + height) {
			lightBegin = true;
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				((DungeonDoom) Gdx.app.getApplicationListener()).setScreen(new PlayScreen(game));
			}
		}
		else if (Gdx.input.getX() > baseX && Gdx.input.getX() < baseX + width && Gdx.input.getY() > baseY - 100 && Gdx.input.getY() < baseY + height - 100) {
			lightInstructions = true;
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				((DungeonDoom) Gdx.app.getApplicationListener()).setScreen(new InstructionsScreen());
			}
		}
		else if (Gdx.input.getX() > baseX && Gdx.input.getX() < baseX + width && Gdx.input.getY() > baseY - 200 && Gdx.input.getY() < baseY + height - 200) {
			lightExit = true;
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				Gdx.app.exit();
			}
		}
		*/
		// If the mouse hovers over a button, it becomes more transparent.		
		if (Gdx.input.getX() > baseX && Gdx.input.getX() < baseX + width && Gdx.input.getY() > baseY2 && Gdx.input.getY() < baseY2 + height) {
			lightBegin = true;
			// If the button is clicked, then a new screen is opened.
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				((DungeonDoom) Gdx.app.getApplicationListener()).setScreen(new PlayScreen(game));
			}
		}
		// If the mouse hovers over a button, it becomes more transparent.		
		else if (Gdx.input.getX() > baseX && Gdx.input.getX() < baseX + width && Gdx.input.getY() > baseY2 + 100 && Gdx.input.getY() < baseY2 + height + 100) {
			lightInstructions = true;
			// If the button is clicked, then a new screen is opened.
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				((DungeonDoom) Gdx.app.getApplicationListener()).setScreen(new InstructionsScreen(game));
			}
		}
		// If the mouse hovers over a button, it becomes more transparent.		
		else if (Gdx.input.getX() > baseX && Gdx.input.getX() < baseX + width && Gdx.input.getY() > baseY2 + 200 && Gdx.input.getY() < baseY2 + height + 200) {
			lightExit = true;
			// If the button is clicked, then a new screen is opened.
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				Gdx.app.exit();
			}
		}
		// If the mouse doesn't hover over a button.
		else {
			lightBegin = false;
			lightInstructions = false;
			lightExit = false;
		}
		
		// If the user presses escape, the game closes.
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}

	@Override
	public void dispose() {
		
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
	 * Draws the screen and updates the local variable in the screen.
	 */
	@Override
	public void render(float delta) {
		// Clears the screen and puts a white background.
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Takes in user input.
		update(delta);
		
		// Begins the batch.
		batch.begin();
		// Draws the background.
		batch.draw(background, 0, 0, background.getWidth(), background.getHeight());
		
		// Draws the appropriate buttons in the game.
		if (lightBegin) {
			batch.draw(beginButtonLight, baseX, baseY);
		}
		else {
			batch.draw(beginButton, baseX, baseY);
		}
		
		if (lightInstructions) {
			batch.draw(instructionsButtonLight, baseX, baseY - 100);
		}
		else {
			batch.draw(instructionsButton, baseX, baseY - 100);
		}
		
		if (lightExit) {
			batch.draw(exitButtonLight, baseX, baseY - 200);
		}
		else {
			batch.draw(exitButton, baseX, baseY - 200);
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
