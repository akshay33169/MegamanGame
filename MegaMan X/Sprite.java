/*
 * Simplified version, mcs - feb 2000
 */

import java.awt.*;

public interface Sprite
{
	void     updateSprite    ();
	void   drawSprite      (Graphics g);
	Rectangle   collisionBox    ();
	void   collideWith     (Object obj);
}  // public interface Sprite

// EOF
