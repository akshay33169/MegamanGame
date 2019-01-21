import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class Conveyor extends ImageSprite
{
	int dx = 2;
	int left, right; // movement limits
	static final int LEFT = 0;
	static final int RIGHT = 1;
	int state = RIGHT;
	static Image img;
	BBeanie app;

	public Conveyor(BBeanie a, int x, int y, int l, int r)
	{
		super(img, x, y);
		app = a;
		left = l;
		right = r;
	}
	public int getDX()
	{
		return dx;
	}

	public double getY()
	{
		return y;
	}
	public double getX()
	{
		return x;
	}


	public static void setStaticImage(Image im)
	{
		img = im;
	}

	public void drawSprite(Graphics gr)
	{
		gr.drawImage(image,(int)x-app.vleft, (int)y, app);
	}

	public void updateSprite()
	{
		// Simple state machine walks back and forth
		x += dx;
		if ((state == RIGHT) && (x > right))
		{
			state = LEFT;
			dx = -dx;
		}
		else if ((state == LEFT) && (x < left))
		{
			state = RIGHT;
			dx = -dx;
		}
	}
}
