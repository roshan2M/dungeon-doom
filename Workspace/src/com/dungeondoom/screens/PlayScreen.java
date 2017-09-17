package com.dungeondoom.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.dungeondoom.actors.Player;
import com.dungeondoom.currency.Coin;
import com.dungeondoom.enemies.Ghost;
import com.dungeondoom.main.DungeonDoom;
import com.dungeondoom.actors.Direction;

/**
 * @author Roshan Munjal
 * The PlayScreen class draws the major aspects of the screen.
 * This class implements the the Screen interface, which makes
 * it much easier to move between different screens simply using
 * buttons. Thus, a game state manager is not required, as the logic
 * is hard coded into each individual screen for navigating them.
 */
public class PlayScreen implements Screen {
	
	// Makes a public static reference to main character so other classes can access its variables as needed.
	public static Player mainCharacter;
	
	// Declares the base speed of the main character.
	private float baseSpeed = 100;
	
	// The DungeonDoom game is passed through the constructor.
	private DungeonDoom game;

	// Objects used to render the TiledMap and Images/Text on the screen.
	private TiledMap map;
	private TiledMapRenderer renderer;
	private BitmapFont font;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;

	// Objects to put the characters on the screen.
	private ArrayList<Ghost> ghosts;
	private ArrayList<Coin> groundCoins;
	private Rectangle mainCharacterRectangle;

	// Used to detect obstacles on the screen.
	private MapObjects obstacles;

	// Textures needed for additional features.
	private Texture coin;

	// Variables to get the width and height of the screen.
	private static int width = Gdx.graphics.getWidth();
	private static int height = Gdx.graphics.getHeight();
	private int speed = 70;
	
	// Counters to determine when certain numbers of frames.
	private int lastCount = 0;
	private int attackCount = 0; // Count since character was attacked.
	private int coinCount = 0; // Count since a coin was spawned.
	private int staminaCount = 0; // Count since stamina was used.

	/**
	 * Constructor for the Play class.
	 * @param game : the DungeonDoom game that is passed through from the game itself
	 */
	public PlayScreen(DungeonDoom game) {
		
		// Imports the game.
		this.game = game;

		// The create method initializes everything on the screen.
		create();

	}

	/**
	 * The create method initializes all the objects on the screen.
	 */
	public void create() {
		// Initializing the sprite batch.
		batch = game.getBatch();

		// Initializing the map.
		map = new TiledMap();

		// Initializes the shape renderer.
		shapeRenderer = game.getShapeRenderer();

		// Initializing BitmapFont.
		font = new BitmapFont();
		font.setScale(2.0f);

		// Initializing the texture for the coin and health bar.
		coin = new Texture(Gdx.files.internal("assets/CoinImage.png"));

		// Initializing the main character.
		mainCharacter = new Player("Roshan");
		mainCharacter.setPosition(getWidth() / 2 - 14, getHeight() / 2 - 90);

		// Initializes the list of enemies.
		ghosts = new ArrayList<>();

		// Adds ghosts to an arrayList of ghosts.
		for (int i = 0; i < 7; i++) {
			Ghost ghost = new Ghost();
			ghost.setBaseSpeed(speed);
			ghost.setX(Math.round(Math.random()) * getWidth());
			ghost.setY(Math.round(Math.random() * getHeight()));
			ghosts.add(ghost);
		}
		
		// Initializes the list of coins.
		groundCoins = new ArrayList<>();
		
		// Adds coins to an arrayList of coins.
		for (int i = 0; i < 10; i++) {
			Coin coin = new Coin();
			coin.setX((int) (30 + Math.random() * (getWidth() - 60)));
			coin.setY((int) (30 + Math.random() * (getHeight() - 60)));
			groundCoins.add(coin);
		}
	}

	/**
	 * The dispose method gets rid of used resources that are no longer needed.
	 */
	@Override
	public void dispose() {
		
	}

	/**
	 * The hide method is used when objects are cleared off the screen.
	 */
	@Override
	public void hide() {
		dispose();
	}

	/**
	 * The pause method is used when the game is paused.
	 */
	@Override
	public void pause() {
		
	}
	
	/**
	 * The render method is used to draw everything on the screen.
	 */
	@Override
	public void render(float delta) {
		// Counts the number of frames that have passed.
		coinCount++;
		attackCount++;
		staminaCount++;

		// Clears the screen and puts a black background.
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Updates the camera.
		camera.update();
		renderer.setView(camera);
		renderer.render();
		
		// Checks if the player is alive.
		if (mainCharacter.isPlayerAlive() == false) {
			lastCount++;
			
			// Clears the screen and puts a black background.
			Gdx.gl.glClearColor(0, 0, 0, 0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			// Tells the player how many coins they have collected and how many kills they had.
			batch.begin();
			font.draw(batch, "You have accomplished: ", Gdx.graphics.getWidth() / 2 - 160, Gdx.graphics.getHeight() / 2 + 50);
			
			batch.draw(coin, Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 - 30, coin.getWidth() / 5, coin.getHeight() / 5);
			font.draw(batch, Integer.toString(mainCharacter.getCoinsCollected()), Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
			
			font.draw(batch, "Kills: ", Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 - 50);
			font.draw(batch, Integer.toString(mainCharacter.getKills()), Gdx.graphics.getWidth() / 2 + 20, Gdx.graphics.getHeight() / 2 - 50);
			batch.end();
			
			// Sets the screen to the main menu after a certain amount of time.
			if (lastCount >= 300) {
				((DungeonDoom) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
			}
		}
		// If the player is alive.
		else {
			// Creates the bounding rectangle for the main character.
			mainCharacterRectangle = mainCharacter.getBoundingRectangle();
			
			// Adds to the stamina of the player if needed.
			addStamina();
	
			// Checks if the character collides with an obstacle.
			characterObstacleCollision();
			
			// Checks if a ghost collides with a character.
			ghostCharacterCollision();
			
			// Checks if a character collides with a ghost.
			characterGhostCollision();
			
			// Checks if a character overlaps with a coin.
			characterCoinCollision();
			
			// Updates the state of the game and players by taking input.
			update();
			
			// Draws all the objects on the screen.
			drawObjects(delta);
			
			// Draws features like the health bar and stamina bar.
			drawUI();
		}
	}
	
	/**
	 * Draws all the objects on the screen.
	 * @param delta : the time that has passed
	 */
	private void drawObjects(float delta) {
		// All draw and act methods.
		mainCharacter.act(delta);
		mainCharacter.draw(batch);
		
		for (Coin coin : groundCoins) {
			coin.act(delta);
			coin.draw(batch);
		}
		
		for (Ghost ghost : ghosts) {
			ghost.act(delta);
			ghost.draw(batch);
		}
	}

	/**
	 * Adds stamina to the main character.
	 */
	private void addStamina() {
		// If 100 counts have gone by and stamina has not been used, and it is less than 400, then add to the stamina.
		if (staminaCount > 100 && mainCharacter.getStamina() < 400) {
			mainCharacter.setStamina(mainCharacter.getStamina() + 1);
		}
	}

	/**
	 * Draws the coin image, number of coins, number of kills, health bar, and stamina bar.
	 */
	private void drawUI() {
		// Draws the coin image and number of coins and kills by the player.
		batch.begin();
		batch.draw(coin, getWidth() - 220, getHeight() - coin.getHeight() / 5 - 40, coin.getWidth() / 5, coin.getHeight() / 5);
		font.draw(batch, Integer.toString(mainCharacter.getCoinsCollected()), getWidth() - 150, getHeight() - 50);
		font.draw(batch, Integer.toString(mainCharacter.getHealthPercentage()), 220, getHeight() - 15);
		font.draw(batch, "Kills: ", getWidth() - 220, getHeight() - 10);
		font.draw(batch, Integer.toString(mainCharacter.getKills()), getWidth() - 150, getHeight() - 10);
		batch.end();

		// Draws the health bar and the stamina bars.
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(10, getHeight() - 40, (int) (200 * ((float) mainCharacter.getHealthPercentage() / 100)), 20);
		
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.rect(10, getHeight() - 70, (int) (200 * ((float) mainCharacter.getStamina()) / 400), 20);
		shapeRenderer.end();
		
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.rect(10, getHeight() - 40, (int) (200 * ((float) mainCharacter.getHealthPercentage() / 100)), 20);
		
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.rect(10, getHeight() - 70, (int) (200 * ((float) mainCharacter.getStamina()) / 400), 20);
		shapeRenderer.end();
	}

	/**
	 * Checks if the character is hit by the ghost.
	 */
	private void characterGhostCollision() {
		// Gets the damage rating done by the ghost.
		int damage = 0;
		mainCharacter.setTimeSinceAttacked(mainCharacter.getTimeSinceAttacked() + Gdx.graphics.getDeltaTime());
		
		// Checks if the main character overlaps with enemies.
		mainCharacter.setOverlapsWithEnemies(false);
		for (Ghost ghost : ghosts) {
			if (mainCharacterRectangle.overlaps(ghost.getBoundingRectangle())) {
				mainCharacter.setOverlapsWithEnemies(true);
				damage = ghost.getDamageRating();
			}
		}
		
		// If the time since attacked is more than 3 seconds.
		if (mainCharacter.getTimeSinceAttacked() >= 3) {
			// If the main character has health less than 100.
			if (mainCharacter.getHealthPercentage() < 100) {
				// This increases the health by a non-linear rate.
				if (attackCount % Math.abs(20 - (int) mainCharacter.getTimeSinceAttacked()) == 0) {
					mainCharacter.setHealthPercentage(mainCharacter.getHealthPercentage() + 1);
					attackCount = 0;
				}
			}
		}
		// If the time since attacked is more than 1 second.
		if (mainCharacter.getTimeSinceAttacked() >= 1) {
			// If the main character overlaps with enemies.
			if (mainCharacter.getOverlapsWithEnemies() == true) {
				// The main character loses 10 hit points for each second with the enemies.
				if (mainCharacter.getHealthPercentage() >= 10) {
					mainCharacter.setHealthPercentage(mainCharacter.getHealthPercentage() - damage);
				} else {
					mainCharacter.setHealthPercentage(0);
				}
				// Resets time since attacked.
				mainCharacter.setTimeSinceAttacked(0);
			}
		}
	}

	/**
	 * Checks for collisions between the character and obstacles.
	 */
	public void characterObstacleCollision() {
		// Checks if the main character overlaps with obstacles.
		mainCharacter.setOverlapsWithObstacles(false);
		for (Ghost ghost : ghosts) {
			ghost.setOverlapsWithObstacles(false);
		}
		// Goes through the obstacle layer of the TiledMap.
		obstacles = map.getLayers().get("collision").getObjects();
		
		// For each obstacle, it checks it the main character or any enemy overlaps with it.
		for (MapObject object : obstacles.getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			// Checks main character overlap.
			if (mainCharacterRectangle.overlaps(rect)) {
				mainCharacter.setOverlapsWithObstacles(true);
			}
			
			// Checks if enemy overlaps.
			for (Ghost ghost : ghosts) {
				if (ghost.getBoundingRectangle().overlaps(rect)) {
					ghost.setOverlapsWithObstacles(true);
				}
			}
		}
	}
	
	/**
	 * Checks the collisions of ghosts and main character
	 */
	public void ghostCharacterCollision() {
		if (ghosts.size() > 0) {
			// Goes through each ghost.
			for (int i = 0; i < ghosts.size(); i++) {
				// If main character and ghost overlap and the character is attacking, then:
				// Remove the ghost and add a new ghost at a faster speed.
				if (mainCharacterRectangle.overlaps(ghosts.get(i).getBoundingRectangle())) {
					if (mainCharacter.getDirection() == Direction.LEFT) {
						if (ghosts.get(i).getX() < mainCharacter.getX()) {
							if (mainCharacter.isAttacking() == true) {
								ghosts.remove(i);
								mainCharacter.addKill();
								spawnEnemy();
							}
						}
					}
					else if (mainCharacter.getDirection() == Direction.RIGHT) {
						if (ghosts.get(i).getX() > mainCharacter.getX()) {
							if (mainCharacter.isAttacking() == true) {
								ghosts.remove(i);
								mainCharacter.addKill();
								spawnEnemy();
							}
						}
					}
					else if (mainCharacter.getDirection() == Direction.DOWN) {
						if (ghosts.get(i).getY() < mainCharacter.getY()) {
							if (mainCharacter.isAttacking() == true) {
								ghosts.remove(i);
								mainCharacter.addKill();
								spawnEnemy();
							}
						}
					}
					else if (mainCharacter.getDirection() == Direction.UP) {
						if (ghosts.get(i).getY() > mainCharacter.getY()) {
							if (mainCharacter.isAttacking() == true) {
								ghosts.remove(i);
								mainCharacter.addKill();
								spawnEnemy();
							}
						}
					}
				}
				
			}
		}
	}
	
	/**
	 * Spawns a new ghost enemy.
	 */
	private void spawnEnemy() {
		Ghost ghost = new Ghost();
		
		// Increases the speed of the new ghost.
		if (speed <= 100) {
			speed++;
		}
		
		// Sets the properties of the ghost and adds it to the screen.
		ghost.setBaseSpeed(speed);
		ghost.setX(Math.round(Math.random()) * getWidth());
		ghost.setY(Math.round(Math.random() * getHeight()));
		ghosts.add(ghost);
	}

	/**
	 * Checks to see if a character picks up a coin.
	 */
	public void characterCoinCollision() {
		if (groundCoins.size() > 0) {
			// If the coin overlaps with the main character, it is removed and added to the main character.
			for (int i = 0; i < groundCoins.size(); i++) {
				if (mainCharacterRectangle.overlaps(groundCoins.get(i).getBoundingRectangle())) {
					mainCharacter.addCoins(1);
					groundCoins.remove(i);
				}
			}
		}
		// After certain time intervals, coins are automatically added.
		if (coinCount > 300) {
			spawnCoin();
			coinCount = 0;
		}
	}

	/**
	 * Spawns a new coin.
	 */
	private void spawnCoin() {
		Coin coin = new Coin();
		coin.setX((int) (15 + Math.random() * (getWidth() - 30)));
		coin.setY((int) (15 + Math.random() * (getHeight() - 30)));
		groundCoins.add(coin);
	}

	/**
	 * Takes in user input (keyboard input) and responds appropriately.
	 */
	public void update() {
		// If the main character doesn't overlap with obstacles.
		if (mainCharacter.getOverlapsWithObstacles() == false) {
			if (Gdx.input.isKeyPressed(Keys.A)) {
				mainCharacter.setMoving(true);
				mainCharacter.setAttacking(false);
				mainCharacter.setDirection(Direction.LEFT);
				
				// If Space is pressed and stamina is more than 0, character can run.
				if (Gdx.input.isKeyPressed(Keys.SPACE) && mainCharacter.getStamina() > 0) {
					mainCharacter.setVelocityX(-2 * baseSpeed);
					mainCharacter.setVelocityY(0);
					mainCharacter.setMovingFast(true);
					mainCharacter.setStamina(mainCharacter.getStamina() - 1);
					staminaCount = 0;
				} else {
					mainCharacter.setVelocityX(-baseSpeed);
					mainCharacter.setVelocityY(0);
					mainCharacter.setMovingFast(false);
				}
			} else if (Gdx.input.isKeyPressed(Keys.D)) {
				mainCharacter.setMoving(true);
				mainCharacter.setAttacking(false);
				mainCharacter.setDirection(Direction.RIGHT);
				
				// If Space is pressed and stamina is more than 0, character can run.
				if (Gdx.input.isKeyPressed(Keys.SPACE) && mainCharacter.getStamina() > 0) {
					mainCharacter.setVelocityX(2 * baseSpeed);
					mainCharacter.setVelocityY(0);
					mainCharacter.setMovingFast(true);
					mainCharacter.setStamina(mainCharacter.getStamina() - 1);
					staminaCount = 0;
				} else {
					mainCharacter.setVelocityX(baseSpeed);
					mainCharacter.setVelocityY(0);
					mainCharacter.setMovingFast(false);
				}
			} else if (Gdx.input.isKeyPressed(Keys.W)) {
				mainCharacter.setMoving(true);
				mainCharacter.setAttacking(false);
				mainCharacter.setDirection(Direction.UP);
				
				// If Space is pressed and stamina is more than 0, character can run.
				if (Gdx.input.isKeyPressed(Keys.SPACE) && mainCharacter.getStamina() > 0) {
					mainCharacter.setVelocityX(0);
					mainCharacter.setVelocityY(2 * baseSpeed);
					mainCharacter.setMovingFast(true);
					mainCharacter.setStamina(mainCharacter.getStamina() - 1);
					staminaCount = 0;
				} else {
					mainCharacter.setVelocityX(0);
					mainCharacter.setVelocityY(baseSpeed);
					mainCharacter.setMovingFast(false);
				}
			} else if (Gdx.input.isKeyPressed(Keys.S)) {
				mainCharacter.setMoving(true);
				mainCharacter.setAttacking(false);
				mainCharacter.setDirection(Direction.DOWN);
				
				// If Space is pressed and stamina is more than 0, character can run.
				if (Gdx.input.isKeyPressed(Keys.SPACE) && mainCharacter.getStamina() > 0) {
					mainCharacter.setVelocityX(0);
					mainCharacter.setVelocityY(-2 * baseSpeed);
					mainCharacter.setMovingFast(true);
					mainCharacter.setStamina(mainCharacter.getStamina() - 1);
					staminaCount = 0;
				} else {
					mainCharacter.setVelocityX(0);
					mainCharacter.setVelocityY(-baseSpeed);
					mainCharacter.setMovingFast(false);
				}
			} 
			// The character attacks if J is pressed.
			else if (Gdx.input.isKeyJustPressed(Keys.J)) {
				mainCharacter.setMoving(false);
				mainCharacter.setMovingFast(false);
				mainCharacter.setAttacking(true);
				mainCharacter.setVelocityX(0);
				mainCharacter.setVelocityY(0);
			} else {
				mainCharacter.setMoving(false);
				mainCharacter.setMovingFast(false);
				mainCharacter.setVelocityX(0);
				mainCharacter.setVelocityY(0);
			}
		}
		// If the character overlaps with obstacles.
		else {
			mainCharacter.setMoving(false);
			mainCharacter.setMovingFast(false);
			mainCharacter.setAttacking(false);
			mainCharacter.setVelocityX(0);
			mainCharacter.setVelocityY(0);
		}
		
		// If the player presses escape or the main character dies.
		if (mainCharacter.getHealthPercentage() == 0 || Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			mainCharacter.setPlayerAlive(false);
		}
	}

	/**
	 * The resize method updates the camera.
	 */
	@Override
	public void resize(int arg0, int arg1) {
		// Sets the camera view port.
		camera.viewportWidth = getWidth();
		camera.viewportHeight = getHeight();
		camera.update();
	}

	/**
	 * The resume method resumes the game after it is paused.
	 */
	@Override
	public void resume() {
		
	}

	/**
	 * The show method loads the map onto the screen.
	 */
	@Override
	public void show() {
		// Loads the map.
		map = (new TmxMapLoader()).load("assets/MapLevelNew.tmx");

		// Renders the map.
		renderer = new OrthogonalTiledMapRenderer(map);

		// Gets the camera and sets it to the screen.
		camera = game.getCamera();
		camera.setToOrtho(false, getWidth(), getHeight());
	}

	/**
	 * Gets the height of the screen.
	 * @return height
	 */
	public static int getHeight() {
		return height;
	}

	/**
	 * Gets the width of the screen.
	 * @return width
	 */
	public static int getWidth() {
		return width;
	}

}
