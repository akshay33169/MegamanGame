/*
 * Simplified version of Sprite, mcs - feb 2000
 * Modified to use (x,y) for top-left corner
 */

import java.awt.*;

class ImageSprite extends SpriteObject
{
	protected Image image;

	ImageSprite (Image image, double x, double y)
	{
		super(x, y, 0, 0);
		setImage(image);
	}

	public void setImage (Image img)
	{
		if ((image = img) == null)
			width = height = 0;
		else {
			width = img.getWidth(null);
			height = img.getHeight(null);
		}
	}

	public void drawSprite (Graphics g)
	{
		g.drawImage(image, (int)x, (int)y, null);
	}
}  // class ImageSprite

// EOF
