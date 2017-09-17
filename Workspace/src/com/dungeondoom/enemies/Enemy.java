package com.dungeondoom.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.dungeondoom.actors.Direction;

/**
 * @author Roshan Munjal
 * This is an abstract class originally meant for enemies to
 * be derived from. However, now, it just serves as a filler
 * class, since the only enemy implemented is Ghost.
 */
public abstract class Enemy extends Actor {
	
	// Declares the necessary values for every Enemy.
	protected TextureRegion region;
	protected Rectangle boundary;
	protected float velocityX;
	protected float velocityY;
	protected Direction direction;
	protected boolean overlapsWithObstacles;
	private int damageRating;
	
	/**
	 * Empty constructor for abstract Enemy.
	 */
	protected Enemy() {
		
	}
	
	/*
	 * Tells the enemy to act (move).
	 */
	public void act(float dt) {
		super.act(dt);
		moveBy(velocityX * dt, velocityY * dt);
	}

	/**
	 * Sets the texture of the enemy.
	 * @param texture : the assigned texture
	 */
	public void setTexture(Texture texture) {
		int w = texture.getWidth();
		int h = texture.getHeight();
		setWidth(w);
		setHeight(h);
		region.setRegion(texture);
	}
	
	/**
	 * Draws the texture on the screen (abstract).
	 * @param batch : the batch needed to draw textures
	 */
	public abstract void draw(SpriteBatch batch);
	
	/**
	 * Gets the x-velocity of the enemy.
	 * @return velocityX
	 */
	public float getVelocityX() {
		return this.velocityX;
	}
	
	/**
	 * Sets the x-velocity of the enemy.
	 * @param velocityX : the velocity to be set for the x-direction
	 */
	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}
	
	/**
	 * Gets the y-velocity of the enemy.
	 * @return velocityY
	 */
	public float getVelocityY() {
		return this.velocityY;
	}
	
	/**
	 * Sets the y-velocity of the enemy.
	 * @param velocityY : the velocity to be set for the y-direction
	 */
	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}
	
	/**
	 * Gets the direction of the enemy.
	 * @return direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Sets the direction of the enemy.
	 * @param direction : the direction the enemy should go in.
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * Gets the bounding rectangle around the enemy.
	 * @return boundary
	 */
	public Rectangle getBoundingRectangle() {
		boundary.set(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		return boundary;
	}

	/**
	 * Checks if the enemy overlaps with obstacles.
	 * @return overlapsWithObstacles
	 */
	public boolean overlapsWithObstacles() {
		return overlapsWithObstacles;
	}

	/**
	 * Sets if the enemy overlaps with obstacles.
	 * @param overlapsWithObstacles : the variable to set if the enemy overlaps with obstacles
	 */
	public void setOverlapsWithObstacles(boolean overlapsWithObstacles) {
		this.overlapsWithObstacles = overlapsWithObstacles;
	}

	/**
	 * Gets the damage rating.
	 * @return damageRating
	 */
	public int getDamageRating() {
		return damageRating;
	}

	/**
	 * Sets the damage rating
	 * @param damageRating : the damage rating
	 */
	public void setDamageRating(int damageRating) {
		this.damageRating = damageRating;
	}
	
}
