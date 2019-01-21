import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class Bullet extends ImageSprite
{
		static Image regshot;
		int locx, locy;
		int dx = 0, dy = 0;
		public int dir = 0;
		BBeanie app;
		boolean active = true;
		Grid g;
		Rectangle cb; // store the collision box (since we never move)

		public Bullet(BBeanie a, int x, int y)
		{
			super(regshot, x, y);
			app = a;
			locx = x; locy = y;

		}

			static void setStaticImages(Image bu)
			{
				regshot=bu;
			}



			public int getY()
			{
				return locy;
			}
			public int getX()
			{
				return locx;
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
		}

		public void moveBullet()
		{
			locx+=5;
		}

		public void setY(double a)
			{
				y=a;
			}
			public void setX(double a)
			{
				x=a;
			}


			public int width()
			{
				return width;
			}

		public void drawSprite(Graphics gr)
			{
				gr.drawImage(image,(int)x-app.vleft, (int)y, app);
			}

		public void updateSprite()
			{
				if (dir == 1)
							dx = -3;
						else if (dir == 2)
							dx = 3;
						else
						dx = 0;
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
					if (dx != 0)
			x += dx;
		}




}