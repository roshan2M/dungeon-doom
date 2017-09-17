package com.dungeondoom.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * @author Roshan Munjal
 * This is simply the launcher class for the DungeonDoom game.
 * It configures the size of the application and launcher it.
 */
public class DungeonDoomLauncher {

	/**
	 * The only main method in this project, because it needs to be called first.
	 * @param args
	 */
	public static void main(String[] args) {

		// Creates new game called my Duneoon.
		DungeonDoom myProgram = new DungeonDoom();
		
		// Creates the jwlApplicatio to l
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		// Creates a title, initial widthand height of the student, and wait sof him t
		config.title = "Dungeon Doom";
		config.width = 1280;
		config.height = 960;
		
		// Starts the new application.
		new LwjglApplication(myProgram, config);

	}

}
