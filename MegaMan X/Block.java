import java.awt.*;
import java.awt.event.*;
import java.applet.*;



public class Block
{
	static Image image;
	int locx, locy;
	BBeanie app;
	boolean active = true;
	Rectangle cb; // store the collision box (since we never move)

	public Block(BBeanie a, int x, int y)
	{
		app = a;
		locx = x; locy = y;
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		cb = new Rectangle(locx+(int)(0.1*w),locy+(int)(0.1*h),(int)(0.8*w),(int)(0.8*h));
	}

	public static void setStaticImage(Image b)
	{
		image = b;
	}

	public boolean isActive()
	{
		return active;
	}

	public Rectangle collisionBox()
	{
		return cb;
	}

	public void hit()
	{
		active = false;
		app.bumpScore(10);
	}

	public void paint(Graphics gr)
	{
		if (isActive())
			gr.drawImage(image,locx-app.vleft, locy, app);
	}
}