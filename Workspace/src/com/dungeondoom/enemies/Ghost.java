package com.dungeondoom.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.dungeondoom.actors.Direction;
import com.dungeondoom.screens.PlayScreen;

/**
 * @author Roshan Munjal
 * The Ghost is currently the only derived class from the
 * Enemy class. Thus, it is the only form of Enemy that was
 * created thus far. The Ghost is a special enemy that can
 * walk through walls and speeds up as more Ghosts die. The
 * only inappropriate thing about the Ghost class is that it
 * uses a static mainCharacter in order to access its positions.
 * This is needed for the game, and is only one instance of
 * bad programming principles.
 */
public class Ghost extends Enemy {

	// Variables to track speed of the ghost and time.
	private float elapsedTime = 0;
	private float baseSpeed;

	// Variables to know the number of columns and rows in the sprite sheet.
	private static final int FRAME_COLS_GHOST = 12, FRAME_ROWS_GHOST = 8;

	// Animations for the ghost.
	private Animation ghostLeftAnimation, ghostRightAnimation, ghostUpAnimation, ghostDownAnimation;
	private Animation lightGhostLeftAnimation, lightGhostRightAnimation, lightGhostUpAnimation, lightGhostDownAnimation;

	// Textures and TextureRegions needed for ghosts.
	private Texture lightGhostSheet;
	private TextureRegion[][] allLightGhostFrames;
	private TextureRegion[] lightGhostUpFrames = new TextureRegion[9];
	private TextureRegion[] lightGhostDownFrames = new TextureRegion[9];
	private TextureRegion[] lightGhostRightFrames = new TextureRegion[9];
	private TextureRegion[] lightGhostLeftFrames = new TextureRegion[9];
	
	// Textures and TextureRegions needed for ghosts.
	private Texture ghostSheet;
	private TextureRegion[][] allGhostFrames;
	private TextureRegion[] ghostUpFrames = new TextureRegion[9];
	private TextureRegion[] ghostDownFrames = new TextureRegion[9];
	private TextureRegion[] ghostRightFrames = new TextureRegion[9];
	private TextureRegion[] ghostLeftFrames = new TextureRegion[9];

	// Properties of the ghost.
	private boolean isEnemyAlive = true;

	/**
	 * Constructor for Ghost class.
	 */
	public Ghost() {
		super();
		
		// Creates all necessary textures and settings that need to be done.
		this.region = new TextureRegion();
		this.boundary = new Rectangle();
		this.setDirection(Direction.DOWN);
		this.setDamageRating(10);
		
		// Creates other variables.
		create();
	}

	/**
	 * Creates all necessary variables/animations/etc. needed for the Ghost.
	 */
	public void create() {
		// Initializes the sprite sheet of ghosts.
		ghostSheet = new Texture(Gdx.files.internal("assets/EnemySpriteSheet1.png"));

		// Cuts up the sprite sheet into frames.
		allGhostFrames = TextureRegion.split(ghostSheet, ghostSheet.getWidth() / FRAME_COLS_GHOST,
				ghostSheet.getHeight() / FRAME_ROWS_GHOST);

		// Initializing frames to be used to create the animations.
		for (int i = 0; i < 9; i++) {
			ghostDownFrames[i] = allGhostFrames[0][i];
			ghostLeftFrames[i] = allGhostFrames[1][i];
			ghostRightFrames[i] = allGhostFrames[2][i];
			ghostUpFrames[i] = allGhostFrames[3][i];
		}

		// Creating all animations for the walking sprite.
		ghostUpAnimation = new Animation(1 / 5f, ghostUpFrames);
		ghostLeftAnimation = new Animation(1 / 5f, ghostLeftFrames);
		ghostDownAnimation = new Animation(1 / 5f, ghostDownFrames);
		ghostRightAnimation = new Animation(1 / 5f, ghostRightFrames);
		
		// Creates the transparent ghost sheet.
		lightGhostSheet = new Texture(Gdx.files.internal("assets/EnemySpriteSheet1Light.png"));

		// Cuts the transparent ghost sheet into frames.
		allLightGhostFrames = TextureRegion.split(lightGhostSheet, ghostSheet.getWidth() / FRAME_COLS_GHOST,
				ghostSheet.getHeight() / FRAME_ROWS_GHOST);

		// Initializing frames to be used to create the animations.
		for (int i = 0; i < 9; i++) {
			lightGhostDownFrames[i] = allLightGhostFrames[0][i];
			lightGhostLeftFrames[i] = allLightGhostFrames[1][i];
			lightGhostRightFrames[i] = allLightGhostFrames[2][i];
			lightGhostUpFrames[i] = allLightGhostFrames[3][i];
		}

		// Creating all animations for the walking sprite.
		lightGhostUpAnimation = new Animation(1 / 5f, lightGhostUpFrames);
		lightGhostLeftAnimation = new Animation(1 / 5f, lightGhostLeftFrames);
		lightGhostDownAnimation = new Animation(1 / 5f, lightGhostDownFrames);
		lightGhostRightAnimation = new Animation(1 / 5f, lightGhostRightFrames);
	}
	
	/**
	 * Draws and updates the Ghost based on its state.
	 * @param batch : the batch used to draw things
	 */
	@Override
	public void draw(SpriteBatch batch) {
		batch.begin();
		elapsedTime += Gdx.graphics.getDeltaTime();

		// If the main character is to the right more than to the up/down.
		if (Math.abs(this.getX() - PlayScreen.mainCharacter.getX()) > Math.abs(this.getY() - PlayScreen.mainCharacter.getY())) {
			if (this.getX() <= PlayScreen.mainCharacter.getX()) {
				// The ghost moves horizontally right.
				this.setVelocityX(baseSpeed);
				this.setVelocityY(0);
				this.setDirection(Direction.RIGHT);
				
				if (this.overlapsWithObstacles() == false) {
					batch.draw(ghostRightAnimation.getKeyFrame(elapsedTime, true), this.getX(), this.getY());
				}
				else {
					batch.draw(lightGhostRightAnimation.getKeyFrame(elapsedTime, true), this.getX(), this.getY());
				}
			} else if (this.getX() > PlayScreen.mainCharacter.getX()) {
				// The ghost moves horizontally left.
				this.setVelocityX(-baseSpeed);
				this.setVelocityY(0);
				this.setDirection(Direction.LEFT);
				
				if (this.overlapsWithObstacles() == false) {
					batch.draw(ghostLeftAnimation.getKeyFrame(elapsedTime, true), this.getX(), this.getY());
				}
				else {
					batch.draw(lightGhostLeftAnimation.getKeyFrame(elapsedTime, true), this.getX(), this.getY());
				}
			}
		} else {
			if (this.getY() <= PlayScreen.mainCharacter.getY()) {
				// The ghost moves vertically up.
				this.setVelocityX(0);
				this.setVelocityY(baseSpeed);
				this.setDirection(Direction.UP);
				
				if (this.overlapsWithObstacles() == false) {
					batch.draw(ghostUpAnimation.getKeyFrame(elapsedTime, true), this.getX(), this.getY());
				}
				else {
					batch.draw(lightGhostUpAnimation.getKeyFrame(elapsedTime, true), this.getX(), this.getY());
				}
			} else if (this.getY() > PlayScreen.mainCharacter.getY()) {
				// The ghost moves vertically down.
				this.setVelocityX(0);
				this.setVelocityY(-baseSpeed);
				this.setDirection(Direction.DOWN);
				
				if (this.overlapsWithObstacles() == false) {
					batch.draw(ghostDownAnimation.getKeyFrame(elapsedTime, true), this.getX(), this.getY());
				}
				else {
					batch.draw(lightGhostDownAnimation.getKeyFrame(elapsedTime, true), this.getX(), this.getY());
				}
			}
		}

		batch.end();
	}

	/**
	 * Gets the bounding rectangle for the Ghost.
	 * @return boundary
	 */
	public Rectangle getBoundingRectangle() {
		boundary.set(this.getX(), this.getY(), allGhostFrames[0][0].getRegionWidth(), allGhostFrames[0][0].getRegionHeight());
		return boundary;
	}

	/**
	 * Checks if the enemy is alive.
	 * @return isEnemyAlive : boolean to test if the player is alive
	 */
	public boolean isEnemyAlive() {
		return isEnemyAlive;
	}

	/**
	 * Sets the enemy to alive (true) or not alive (false).
	 * @param isEnemyAlive : sets the enemy to alive or not alive
	 */
	public void setEnemyAlive(boolean isEnemyAlive) {
		this.isEnemyAlive = isEnemyAlive;
	}
	
	/**
	 * Gets the base speed of the ghost.
	 * @return baseSpeed
	 */
	public float getBaseSpeed() {
		return baseSpeed;
	}

	/**
	 * Sets the base speed of the ghost.
	 * @param baseSpeed : the speed to set the ghost at
	 */
	public void setBaseSpeed(float baseSpeed) {
		this.baseSpeed = baseSpeed;
	}

}
