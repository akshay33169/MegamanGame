/*
 * SpriteObject class from Dummies book.
 * Simplified for initial introduction, mcs - feb, 2000
 * Change anchor point to top-left
 */

import java.awt.*;

public class SpriteObject implements Sprite {
	protected double        x, y;  // top-left of sprite
 	protected int           width, height;

	public SpriteObject (double x, double y, int w, int h) {
		this.x = x;  this.y = y;  width = w;  height = h;
	}

	public int    spriteWidth ()   { return width; }
	public int    spriteHeight ()  { return height; }

	public void updateSprite () { }
	public void drawSprite (Graphics g) { }

	public Rectangle collisionBox () {
		return new Rectangle((int)x, (int)y, width, height);
	}

	public void collideWith (Object obj) { }
}  // public class SpriteObject

// EOF
