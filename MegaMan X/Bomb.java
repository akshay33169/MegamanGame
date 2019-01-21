import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class Bomb extends ImageSprite
{
	int dy = 2;
	int dir=0;
	int left, right; // movement limits
	boolean active = true;
	static final int LEFT = 0;
	static final int RIGHT = 1;
	int state = RIGHT;
	static Image regshot;
	BBeanie app;
	Grid g;

	public Bomb(BBeanie a, Grid grid, int x, int y, int direc)
	{
		super(regshot, x, y);
		app = a;
		g=grid;
		dir=direc;
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

	public void drawSprite(Graphics gr)
	{
		gr.drawImage(image,(int)x-app.vleft, (int)y, app);
	}


		public void updateSprite()
			{

						if (dir == 2)
							dy = 10;

							if (y>=92)
								dy=0;
				//		else
				//		dx = 0;
				   if (active)
				 updatePosition();

			}

		public void updatePosition()
		{
			if (dy > 0)
					{
						dy = g.moveRight(collisionBox(), dy);

					}
					else if (dy < 0)
				{
					dy = -g.moveLeft(collisionBox(), -dy);
				}

			//	System.out.println(" Inside Bomb Update position " + dy);

					if (dy != 0)
						y += dy;
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

			}

}