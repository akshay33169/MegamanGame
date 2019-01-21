import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class Bullet extends ImageSprite
{
	int dx = 2;
	int dir=0;
	int left, right; // movement limits
	boolean active = true;
	static final int LEFT = 0;
	static final int RIGHT = 1;
	int state = RIGHT;
	static Image regshot;
	BBeanie app;
	Grid g;

	public Bullet(BBeanie a, Grid grid, int x, int y, int direc)
	{
		super(regshot, x, y);
		app = a;
		g=grid;
		dir=direc;
	}

	public static void setStaticImage(Image im)
	{
		regshot = im;
	}

	public Rectangle collisionBox()
	{
			// We get better gameplay with a 90% collison box
			return new Rectangle((int)(x+0.05*width), (int)(y+0.05*height),
						(int)(0.9*width), (int)(0.9*height));
	}

	public int getY()
		{
			return(int)y;
		}
	public int getX()
	{
			return (int)x;
	}
	public void drawSprite(Graphics gr)
	{
		gr.drawImage(image,(int)x-app.vleft, (int)y, app);
	}


		public void updateSprite()
			{
				if (dir == 1)
							dx = -10;
				else if (dir == 2)
							dx = 10;
				//		else
				//		dx = 0;
				   if (active)
				 updatePosition();
			}

		public void updatePosition()
		{
			if (dx > 0)
					{
						dx = g.moveRight(collisionBox(), dx);

					}
					else if (dx < 0)
				{
					dx = -g.moveLeft(collisionBox(), -dx);
				}

				//System.out.println(" Inside Bullet Update position " + dx);

					if (dx != 0)
						x += dx;
					else
						active=false;

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

}