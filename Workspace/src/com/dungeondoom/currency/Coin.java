package com.dungeondoom.currency;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * @author Roshan Munjal
 * The Coin class is used to represent Coin objects in an abstract
 * way in the game itself. All the properties of Coin are stored
 * here for convenience and to implement the Waterfall effect.
 */
public class Coin extends Actor {
	
	// Important textures to draw the coin.
	private Rectangle boundary;
	
	// The coin texture.
	private Texture coinTexture;
	
	/**
	 * Constructor for the Coin class.
	 */
	public Coin() {
		// Sets the boundary and the coin texture.
		boundary = new Rectangle();
		coinTexture = new Texture(Gdx.files.internal("assets/CoinImage.png"));
	}
	
	/**
	 * Gets the bounding rectangle.
	 * @return boundary : the bounding rectangle
	 */
	public Rectangle getBoundingRectangle() {
		boundary.set(this.getX(), this.getY(), coinTexture.getWidth() / 8, coinTexture.getHeight() / 8);
		return boundary;
	}
	
	/**
	 * Draws the coin as needed.
	 * @param batch : the batch needed to draw the coin.
	 */
	public void draw(SpriteBatch batch) {
		batch.begin();
		batch.draw(coinTexture, this.getX(), this.getY(), coinTexture.getWidth() / 8, coinTexture.getHeight() / 8);
		batch.end();
	}
	
}
