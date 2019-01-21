import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class Flower implements Scenery
{
	static Image mid,left,right;
	Image image;
	int delay, counter;
	int locx, locy;
	final static int WAIT = 0;
	final static int LEFT = 1;
	final static int RIGHT = 2;
	final static int MID = 3;
	int state;
	BBeanie app;

	public Flower(BBeanie a, int x, int y, int d, int p)
	{
		app = a;
		locx = x; locy = y;
		delay = d;
		counter = p;
		state = WAIT;
		image = mid;
	}

	public static void setStaticImages(Image f1, Image f2, Image f3)
	{
		mid = f1;
		left = f2;
		right = f3;
	}

	public void paint(Graphics gr)
	{
			gr.drawImage(image,locx-app.vleft, locy, app);
	}

	public void update()
	{
		if (--counter > 0)
			return;
		switch(state) // use a little FSM to implement "dancing"
		{
			case WAIT:	state = LEFT;
						image = left;
						counter = 4;
						break;
			case LEFT:	state = MID;
						image = mid;
						counter = 4;
						break;
			case MID:	state = RIGHT;
						image = right;
						counter = 4;
						break;
			case RIGHT:	state = WAIT;
						image = mid;
						counter = delay;
						break;
		}
	}

}