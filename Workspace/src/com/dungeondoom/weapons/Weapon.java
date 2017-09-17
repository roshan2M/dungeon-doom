package com.dungeondoom.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.dungeondoom.actors.Direction;
import com.dungeondoom.screens.PlayScreen;

/**
 * @author Roshan Munjal
 * The Weapon class was used to be a stepping stone to build some of the attacks.
 * Although I wanted to originally build several attacks into the game, I realized
 * that this was unrealistic due to some of the timelines. Thus, I had to stick with
 * simple moves that were possible to implement in the game.
 */
public class Weapon extends Actor {

	// Fundamental properties of each weapon.
	private WeaponType weaponType;

	// Variable to record the time passed.
	private float elapsedTime = 0;

	// Variables to know the number of columns and rows in the sprite sheet.
	private static final int FRAME_COLS_ATTACK = 6, FRAME_ROWS_ATTACK = 4;

	// Animations for the character.
	private Animation attackLeftAnimation, attackRightAnimation, attackUpAnimation, attackDownAnimation;

	// Textures and TextureRegions needed for main character's physical attack.
	private Texture physicalAttackSheet;
	private TextureRegion[][] physicalAttackFrames;
	private TextureRegion[] attackUpFrames = new TextureRegion[9];
	private TextureRegion[] attackDownFrames = new TextureRegion[9];
	private TextureRegion[] attackRightFrames = new TextureRegion[9];
	private TextureRegion[] attackLeftFrames = new TextureRegion[9];

	// Used to check if the current texture region is not null.
	private TextureRegion temp;

	/**
	 * Constructor for the Weapon class.
	 * @param weaponType : the type of weapon that it is
	 */
	public Weapon(WeaponType weaponType) {
		this.weaponType = weaponType;
		create();
	}

	/**
	 * Initializes some of the variables that still need to be initialized.
	 */
	private void create() {
		// Initializing the physical attacking sprite sheet.
		physicalAttackSheet = new Texture(Gdx.files.internal("assets/MainCharacterPhysicalAttackSprite.png"));

		// Initializing frames for the attacking sprite.
		physicalAttackFrames = TextureRegion.split(physicalAttackSheet,
				physicalAttackSheet.getWidth() / FRAME_COLS_ATTACK,
				physicalAttackSheet.getHeight() / FRAME_ROWS_ATTACK);

		// Initializing frames to be used to create the animations.
		for (int i = 0; i < FRAME_COLS_ATTACK; i++) {
			attackUpFrames[i] = physicalAttackFrames[0][i];
			attackLeftFrames[i] = physicalAttackFrames[1][i];
			attackDownFrames[i] = physicalAttackFrames[2][i];
			attackRightFrames[i] = physicalAttackFrames[3][i];
		}

		// Creating all animations for the walking sprite.
		attackUpAnimation = new Animation(1 / 40f, attackUpFrames);
		attackLeftAnimation = new Animation(1 / 40f, attackLeftFrames);
		attackDownAnimation = new Animation(1 / 40f, attackDownFrames);
		attackRightAnimation = new Animation(1 / 40f, attackRightFrames);
	}

	/**
	 * Returns the type of weapon.
	 * @return weaponType
	 */
	public WeaponType getWeaponType() {
		return weaponType;
	}

	/**
	 * Sets the weapon that the character has.
	 * @param weaponType : the weapon set the Character
	 */
	public void setWeaponType(WeaponType weaponType) {
		this.weaponType = weaponType;
	}

	/**
	 * This method draws the main portion of the screen based on user input and the state of the game.
	 * @param batch
	 */
	public void draw(SpriteBatch batch) {
		batch.begin();
		elapsedTime += Gdx.graphics.getDeltaTime();

		// If the weapon type is NONE.
		if (this.getWeaponType() == WeaponType.NONE) {
			if (PlayScreen.mainCharacter.getDirection() == Direction.LEFT) {
				// Creates a temporary variable that stores the current frame.
				temp = attackLeftAnimation.getKeyFrame(elapsedTime, false);

				// Checks if the animation has reached the last frame in the
				// sprite sheet.
				if (temp.equals(attackLeftFrames[5])) {
					PlayScreen.mainCharacter.setAttacking(false); // If so, the attack
															// is stopped.
					elapsedTime = 0; // The time is reset to 0.
				}
				// Prevents errors from occurring with the sprite image being
				// drawn.
				if (temp != null) {
					batch.draw(temp, PlayScreen.mainCharacter.getX(), PlayScreen.mainCharacter.getY());
				}
			} else if (PlayScreen.mainCharacter.getDirection() == Direction.RIGHT) {
				// Creates a temporary variable that stores the current frame.
				temp = attackRightAnimation.getKeyFrame(elapsedTime, false);

				// Checks if the animation has reached the last frame in the
				// sprite sheet.
				if (temp.equals(attackRightFrames[5])) {
					PlayScreen.mainCharacter.setAttacking(false);
					elapsedTime = 0;
				}
				if (temp != null) {
					batch.draw(temp, PlayScreen.mainCharacter.getX(), PlayScreen.mainCharacter.getY());
				}
			} else if (PlayScreen.mainCharacter.getDirection() == Direction.UP) {
				// Creates a temporary variable that stores the current frame.
				temp = attackUpAnimation.getKeyFrame(elapsedTime, false);

				// Checks if the animation has reached the last frame in the
				// sprite sheet.
				if (temp.equals(attackUpFrames[5])) {
					PlayScreen.mainCharacter.setAttacking(false);
					elapsedTime = 0;
				}
				if (temp != null) {
					batch.draw(temp, PlayScreen.mainCharacter.getX(), PlayScreen.mainCharacter.getY());
				}
			} else if (PlayScreen.mainCharacter.getDirection() == Direction.DOWN) {
				// Creates a temporary variable that stores the current frame.
				temp = attackDownAnimation.getKeyFrame(elapsedTime, false);

				// Checks if the animation has reached the last frame in the
				// sprite sheet.
				if (temp.equals(attackDownFrames[5])) {
					PlayScreen.mainCharacter.setAttacking(false);
					elapsedTime = 0;
				}
				if (temp != null) {
					batch.draw(temp, PlayScreen.mainCharacter.getX(), PlayScreen.mainCharacter.getY());
				}
			}
		}

		// Closes the batch.
		batch.end();
	}
	
}
