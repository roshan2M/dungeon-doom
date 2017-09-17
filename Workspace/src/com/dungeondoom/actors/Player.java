package com.dungeondoom.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.dungeondoom.screens.PlayScreen;
import com.dungeondoom.weapons.Weapon;
import com.dungeondoom.weapons.WeaponType;

/**
 * @author Roshan Munjal
 * The Player class is used to create the main character in the game.
 * It is quite comprehensive because all these features/characteristics
 * are necessary for the character to have. Also, the player does not extend
 * BaseActor or AnimatedActor because it is simpler to draw those animations
 * here instead of using those classes.
 */
public class Player extends Actor {

	// Important textures to draw the player.
	private Rectangle boundary;
	private String playerName;

	// Variables to know the number of columns and rows in the sprite sheet.
	private static final int FRAME_COLS = 9, FRAME_ROWS = 4;

	// Variable to account the amount of time that has passed.
	private float elapsedTime = 0;

	// Animations for the character.
	private Animation walkLeftAnimation, walkRightAnimation, walkUpAnimation, walkDownAnimation;

	// Textures and TextureRegions needed for main character.
	private Texture walkSheet;
	private TextureRegion[][] allWalkFrames;
	private TextureRegion[] walkUpFrames = new TextureRegion[9];
	private TextureRegion[] walkDownFrames = new TextureRegion[9];
	private TextureRegion[] walkRightFrames = new TextureRegion[9];
	private TextureRegion[] walkLeftFrames = new TextureRegion[9];

	// Properties of the main character.
	private boolean playerAlive = true; // Checks if the player alive.
	private boolean attacking = false; // Checks if the player is attacking.
	private boolean moving = false; // Checks if the player is moving.
	private boolean movingFast = false; // Checks if the player is moving quickly.
	private int kills = 0; // The number of kills that the player has.
	private int healthPercentage = 100; // The health of the player.
	private int stamina = 400; // The stamina of the player.
	private float velocityX = 0; // The x-velocity of the player.
	private float velocityY = 0; // The y-velocity of the player.
	private int coinsCollected = 0; // The number of coins the player has collected.
	private Direction direction = Direction.DOWN; // The direction the player is facing.
	private Weapon currentWeapon; // The weapon that the player currently holds.
	private boolean overlapsWithEnemies = false; // Checks if the player overlaps with enemies.
	private boolean overlapsWithObstacles = false; // Checks if the player overlaps with obstacles.
	private boolean overlapsWithCoins = false; // Checks if the player overlaps with coins.
	private float timeSinceAttacked = 0; //  The time since the player was attacked.

	/**
	 * This is the constructor for the player class.
	 * @param playerName : takes in the name of the player
	 */
	public Player(String playerName) {
		super();
		this.playerName = playerName;
		create();
	}

	/**
	 * This is the create class, that initializes all aspects of the player.
	 */
	private void create() {
		
		// Creates the boundary for the Player.
		boundary = new Rectangle();

		// Initializes the walking sprite sheet.
		walkSheet = new Texture(Gdx.files.internal("assets/MainCharacterWalkingSprite.png"));

		// Initializes all the walk frames.
		allWalkFrames = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS,
				walkSheet.getHeight() / FRAME_ROWS);

		// Initializing frames to be used to create the animations.
		for (int i = 0; i < FRAME_COLS; i++) {
			walkUpFrames[i] = allWalkFrames[0][i];
			walkLeftFrames[i] = allWalkFrames[1][i];
			walkDownFrames[i] = allWalkFrames[2][i];
			walkRightFrames[i] = allWalkFrames[3][i];
		}

		// Creating all animations for the walking sprite.
		walkUpAnimation = new Animation(1 / 15f, walkUpFrames);
		walkLeftAnimation = new Animation(1 / 15f, walkLeftFrames);
		walkDownAnimation = new Animation(1 / 15f, walkDownFrames);
		walkRightAnimation = new Animation(1 / 15f, walkRightFrames);
		
		// Sets the current weapon to NONE.
		currentWeapon = new Weapon(WeaponType.NONE);

	}
	
	/**
	 * Tells the character to move.
	 * @param delta : a variable to keep track of time passed;
	 */
	public void act(float delta) {
		moveBy(velocityX * delta, velocityY * delta);
	}

	/**
	 * This is the draw method for the player. This checks for the current state of the player and then draws an image based on that state.
	 * @param batch : the batch to draw all the images to the screen
	 */
	public void draw(SpriteBatch batch) {
		batch.begin();
		elapsedTime += Gdx.graphics.getDeltaTime(); // Calculates time that has passed. Needed for animations.
		
		// Checks if the player overlaps with obstacles.
		if (this.getOverlapsWithObstacles() == false) {
			// Checks if the player is currently moving (normal or fast speed).
			if (this.isMoving() == true) {
				// If the player is going left.
				if (this.getDirection() == Direction.LEFT) {
					// If the player is moving quickly.
					if (this.isMovingFast() == true) {
						walkLeftAnimation.setFrameDuration(1 / 30f); // Increases the frame rate of the animation.
					} else {
						walkLeftAnimation.setFrameDuration(1 / 15f); // Keeps the standard frame rate of the animation.
					}
					
					// Draws the key frame in the animation.
					batch.draw(walkLeftAnimation.getKeyFrame(elapsedTime, true), this.getX(), this.getY());
				} 
				// If the player is going right.
				else if (this.getDirection() == Direction.RIGHT) {
					// If the player is moving quickly.
					if (this.isMovingFast() == true) {
						walkRightAnimation.setFrameDuration(1 / 30f);
					} else {
						walkRightAnimation.setFrameDuration(1 / 15f);
					}
					
					// Draws the key frame in the animation.
					batch.draw(walkRightAnimation.getKeyFrame(elapsedTime, true), this.getX(), this.getY());
				}
				// If the player is going up.
				else if (this.getDirection() == Direction.UP) {
					// If the player is moving quickly.
					if (this.isMovingFast() == true) {
						walkUpAnimation.setFrameDuration(1 / 30f);
					} else {
						walkUpAnimation.setFrameDuration(1 / 15f);
					}
					
					// Draws the key frame in the animation.
					batch.draw(walkUpAnimation.getKeyFrame(elapsedTime, true), this.getX(), this.getY());
				}
				// If the player is going down.
				else if (this.getDirection() == Direction.DOWN) {
					// If the character is moving quickly.
					if (this.isMovingFast() == true) {
						walkDownAnimation.setFrameDuration(1 / 30f);
					} else {
						walkDownAnimation.setFrameDuration(1 / 15f);
					}
					
					// Draws the key frame in the animation.
					batch.draw(walkDownAnimation.getKeyFrame(elapsedTime, true), this.getX(), this.getY());
				}
			}
			// If the character is not moving, but attacking.
			else if (this.isAttacking() == true) {
				batch.end();
				currentWeapon.draw(batch);
				batch.begin();
			}
			// If the character is stationary.
			else {
				// If the character is looking left.
				if (this.getDirection() == Direction.LEFT) {
					batch.draw(walkLeftFrames[0], this.getX(), this.getY());
				}
				// If the character is looking right.
				else if (this.getDirection() == Direction.RIGHT) {
					batch.draw(walkRightFrames[0], this.getX(), this.getY());
				}
				// If the character is looking up.
				else if (this.getDirection() == Direction.UP) {
					batch.draw(walkUpFrames[0], this.getX(), this.getY());
				}
				// If the character is looking down.
				else if (this.getDirection() == Direction.DOWN) {
					batch.draw(walkDownFrames[0], this.getX(), this.getY());
				}
			}
		}
		// If the character overlaps with obstacles.
		else {
			// If the character is facing right.
			if (this.getDirection() == Direction.RIGHT) {
				// Shifts the character back and draws the appropriate frame.
				this.setX(this.getX() - 1);
				batch.draw(walkRightFrames[0], this.getX(), this.getY());
			} else if (this.getDirection() == Direction.LEFT) {
				// Shifts the character back and draws the appropriate frame.
				this.setX(this.getX() + 1);
				batch.draw(walkLeftFrames[0], this.getX(), this.getY());
			} else if (this.getDirection() == Direction.UP) {
				// Shifts the character back and draws the appropriate frame.
				this.setY(this.getY() - 1);
				batch.draw(walkUpFrames[0], this.getX(), this.getY());
			} else if (this.getDirection() == Direction.DOWN) {
				// Shifts the character back and draws the appropriate frame.
				this.setY(this.getY() + 1);
				batch.draw(walkDownFrames[0], this.getX(), this.getY());
			}
		}
		batch.end();

		// bound main character to the rectangle defined by mapWidth, mapHeight
		this.setX(MathUtils.clamp(this.getX(), 20, PlayScreen.getWidth() - this.getWidth() - 80));
		this.setY(MathUtils.clamp(this.getY(), 30, PlayScreen.getHeight() - this.getHeight() - 60));
	}
	
	/**
	 * Creates the bounding rectangle for the player.
	 * @return boundary : returns the boundary of the player
	 */
	public Rectangle getBoundingRectangle() {
		boundary.set(this.getX(), this.getY(), walkLeftFrames[0].getRegionWidth(), walkLeftFrames[0].getRegionHeight());
		return boundary;
	}

	/**
	 * Returns the player.
	 * @return player
	 */
	public Player getPlayer() {
		return this;
	}

	/**
	 * Returns the name of the player.
	 * @return playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Sets the playerName to a String that is passed in.
	 * @param playerName
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * Checks to see if the player is alive.
	 * @return playerAlive
	 */
	public boolean isPlayerAlive() {
		return playerAlive;
	}

	/**
	 * Sets the player to alive (true) or dead (false).
	 * @param isPlayerAlive
	 */
	public void setPlayerAlive(boolean isPlayerAlive) {
		this.playerAlive = isPlayerAlive;
	}

	/**
	 * Returns the health of the player.
	 * @return health
	 */
	public int getHealthPercentage() {
		return healthPercentage;
	}

	/**
	 * Sets the health of the player.
	 * @param healthPercentage
	 */
	public void setHealthPercentage(int healthPercentage) {
		this.healthPercentage = healthPercentage;
	}

	/**
	 * Returns the x-velocity of the player.
	 * @return velocityX
	 */
	public float getVelocityX() {
		return velocityX;
	}

	/**
	 * Sets the y-velocity of the player.
	 * @param velocityX
	 */
	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}

	/**
	 * Returns the y-velocity of the player.
	 * @return velocityY
	 */
	public float getVelocityY() {
		return velocityY;
	}

	/**
	 * Sets the y-velocity of the player.
	 * @param velocityY
	 */
	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}

	/**
	 * Gets the direction that the player is facing.
	 * @return direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Sets the direction that the player is facing.
	 * @param direction
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * Gets the coins the player has collected.
	 * @return coinsCollected
	 */
	public int getCoinsCollected() {
		return coinsCollected;
	}

	/**
	 * Sets the coins the player has collected.
	 * @param coinsCollected
	 */
	public void setCoinsCollected(int coinsCollected) {
		this.coinsCollected = coinsCollected;
	}
	
	/**
	 * Adds coins to the coins collected.
	 * @param coins
	 */
	public void addCoins(int coins) {
		this.coinsCollected += coins;
	}
	
	/**
	 * Adds coins to the coins collected.
	 * @param coins
	 */
	public void removeCoins(int coins) {
		this.coinsCollected -= coins;
	}

	/**
	 * Gets the weapon the player currently has.
	 * @return currentWeapon
	 */
	public Weapon getCurrentWeapon() {
		return currentWeapon;
	}

	/**
	 * Sets the weapon the player currently has.
	 * @param currentWeapon
	 */
	public void setCurrentWeapon(Weapon currentWeapon) {
		this.currentWeapon = currentWeapon;
	}

	/**
	 * Checks if the player is moving or not.
	 * @return moving
	 */
	public boolean isMoving() {
		return moving;
	}

	/**
	 * Sets the player to moving (true) or not (false).
	 * @param isMoving
	 */
	public void setMoving(boolean isMoving) {
		this.moving = isMoving;
	}

	/**
	 * Checks if the player overlaps with obstacles.
	 * @return overlapsWithObstacles
	 */
	public boolean getOverlapsWithObstacles() {
		return overlapsWithObstacles;
	}

	/**
	 * Sets the player to overlap with obstacles or not.
	 * @param overlapsWithObstacles
	 */
	public void setOverlapsWithObstacles(boolean overlapsWithObstacles) {
		this.overlapsWithObstacles = overlapsWithObstacles;
	}

	/**
	 * Checks if the player is currently attacking.
	 * @return attacking
	 */
	public boolean isAttacking() {
		return attacking;
	}

	/**
	 * Sets the player to attacking or not attacking.
	 * @param isAttacking
	 */
	public void setAttacking(boolean isAttacking) {
		this.attacking = isAttacking;
	}
	
	/**
	 * Checks if the player is currently moving quickly.
	 * @return movingFast.
	 */
	public boolean isMovingFast() {
		return movingFast;
	}

	/**
	 * Sets the player to moving fast or not moving fast.
	 * @param movingFast
	 */
	public void setMovingFast(boolean movingFast) {
		this.movingFast = movingFast;
	}

	/**
	 * Checks if the player overlaps with enemies.
	 * @return overlapsWithEnemies
	 */
	public boolean getOverlapsWithEnemies() {
		return overlapsWithEnemies;
	}

	/**
	 * Sets the player to overlap with enemies or not.
	 * @param overlapsWithEnemies
	 */
	public void setOverlapsWithEnemies(boolean overlapsWithEnemies) {
		this.overlapsWithEnemies = overlapsWithEnemies;
	}

	/**
	 * Gets the time since the player was last attacked.
	 * @return timeSinceAttacked
	 */
	public float getTimeSinceAttacked() {
		return timeSinceAttacked;
	}

	/**
	 * Sets the time since the player was last attacked.
	 * @param timeSinceAttacked
	 */
	public void setTimeSinceAttacked(float timeSinceAttacked) {
		this.timeSinceAttacked = timeSinceAttacked;
	}

	/**
	 * Checks if the player overlaps with coins.
	 * @return overlapsWithCoins
	 */
	public boolean isOverlapsWithCoins() {
		return overlapsWithCoins;
	}

	/**
	 * Sets the character to if it overlaps with coins.
	 * @param overlapsWithCoins
	 */
	public void setOverlapsWithCoins(boolean overlapsWithCoins) {
		this.overlapsWithCoins = overlapsWithCoins;
	}
	
	/**
	 * Adds a kill to the player's kills.
	 */
	public void addKill() {
		this.kills++;
	}

	/**
	 * Returns the amount of kills the player has.
	 * @return kills
	 */
	public int getKills() {
		return kills;
	}

	/**
	 * Returns the stamina of the player.
	 * @return stamina
	 */
	public int getStamina() {
		return stamina;
	}

	/**
	 * Sets the stamina of the player.
	 * @param stamina
	 */
	public void setStamina(int stamina) {
		this.stamina = stamina;
	}
	
}
