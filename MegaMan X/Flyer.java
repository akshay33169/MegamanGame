import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class Flyer extends ImageSprite
{
	int dx = 2;
	int left, right; // movement limits
	boolean active=true;
	static final int LEFT = 0;
	static final int RIGHT = 1;
	int state = RIGHT;
	static Image img;
	BBeanie app;

	public Flyer(BBeanie a, int x, int y, int l, int r)
		{
			super(img, x, y);
			app = a;
			left = l;
			right = r;
	}

	public static void setStaticImage(Image im)
	{
		img = im;
	}

	public boolean isActive()
		{
				return active;
		}
		public void hit()
		{
				active = false;
				app.bumpScore(10);
		}

	public int getY()
		{
			return(int)y;
		}
		public int getX()
		{
			return (int)x;
		}

		public void setY(double a)
			{
				y=a;
			}
			public void setX(double a)
			{
				x=a;
			}

	//public Rectangle collisionBox()
	//{
	//		 We get better gameplay with a 90% collison box
	//		return new Rectangle((int)(x+0.05*width), (int)(y+0.05*height),
	//					(int)(0.9*width), (int)(0.9*height));
	//}

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