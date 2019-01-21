import java.awt.*;
import java.awt.event.*;
import java.applet.*;
public interface Scenery // a common way to deal with various scene elements
{
	public void paint(Graphics g);
	public void update();
}