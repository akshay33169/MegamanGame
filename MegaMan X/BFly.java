import java.awt.*;
import java.awt.event.*;
import java.applet.*;



public class BFly implements Scenery
{
	static Image images[] = new Image[2];
	int counter;
	int locx, locy;
	int state;
	BBeanie app;
	static final int DELAY = 5;

	public BFly(BBeanie a, int x, int y)
	{
		app = a;
		locx = x; locy = y;
		counter = DELAY;
		state = 0;
	}

	public static void setStaticImages(Image b1, Image b2)
	{
		images[0] = b1;
		images[1] = b2;
	}

	public void paint(Graphics gr)
	{
		gr.drawImage(images[state],locx-app.vleft, locy, app);
	}

	public void update()
	{
		if (--counter > 0)
		{
		//	System.out.println("Inside bfly-update coiunter");
			return;

		}
		// System.out.println("Outside  bfly-update");
		state = 1 - state; // Flap those wings!
		counter = DELAY;
	}
}
