import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class Grid
{
	// A Grid stores a cell-based map of blocks (and maybe other goodies).
	// The Grid know how many pixels each block is, so can relate pixel-based
	// coords to the map.

	public static final int MWIDTH = 240;
	public static final int MHEIGHT = 15;
	int map[] = new int[MWIDTH*MHEIGHT]; 	// A 2-D map stored in row-major
											//form (0..MWIDTH-1 by 0..MHEIGHT-1)
											//where first subscript is col (x) and
											// second is row (y)
	static final int CELLSIZE = 20; // Number of pixels per map cell
	BBeanie app;
	Image image;

	public Grid(BBeanie a, Image img)
	{
		app = a;
		image = img;
		for (int i = 0; i < MHEIGHT; i++)
		 for (int j = 0; j < MWIDTH; j++)
			map[MWIDTH*i+j] = 0;  // begin with an empty map
	}

	public int width()
	{
		return MWIDTH*CELLSIZE;
	}


	public int moveRight(Rectangle r, int d)
	{
		// Return the number of pixels (not exceeding d) that
		// the Rectangle r can move to the right without hitting
		// a block.
		// Assume d is less than CELLSIZE.
		int col = (r.x+r.width)/CELLSIZE;
		int row1 = (r.y)/CELLSIZE;
		int row2 = (r.y+r.height)/CELLSIZE;
		if (row2 >= MHEIGHT)
			row2 = MHEIGHT-1;
		int edge = CELLSIZE*(col+1);
		if ((r.x+r.width+d) < edge)
			return d;
		if (col == (MWIDTH-1))
			return width()-(r.x+r.width)-1;
		for (int i = row1; i <= row2; i++)
			if (map[MWIDTH*i+col+1] != 0)
				return edge-(r.x+r.width)-1;
		return d;
	}

	public int moveLeft(Rectangle r, int d)
	{
		int col = (r.x)/CELLSIZE;
		int row1 = (r.y)/CELLSIZE;
		int row2 = (r.y+r.height)/CELLSIZE;
		if (row2 >= MHEIGHT)
			row2 = MHEIGHT-1;
		int edge = CELLSIZE*col;
		if ((r.x-d) >= edge)
			return d;
		if (col == 0)
			return r.x;
		for (int i = row1; i <= row2; i++)
			if (map[MWIDTH*i+col-1] != 0)
				return r.x - edge;
		return d;
	}

	public int moveDown(Rectangle r, int d)
	{
		int rbottom = r.y+r.height;
		int row = rbottom/CELLSIZE;
		int col1 = (r.x)/CELLSIZE;
		int col2 = (r.x+r.width)/CELLSIZE;
		if (col2 >= MWIDTH)
			col2 = MWIDTH-1;
		int edge = CELLSIZE*(row+1);
		if (rbottom+d < edge)
			return d;
		for (int i = col1; i <= col2; i++)
			if (map[MWIDTH*(row+1)+i] != 0)
				return edge - rbottom - 1;
		return d;
	}

	public int moveUp(Rectangle r, int d)
	{
		return d;
	}

	public boolean onGround(Rectangle r)
	{
		// Return true if Rectangle r is resting on the ground (or a block)
		int rbottom = r.y+r.height;
		int row = rbottom/CELLSIZE;
		int edge = CELLSIZE*(row+1);
		if ((rbottom+1) != edge)
			return false;
		int col1 = (r.x)/CELLSIZE;
		int col2 = (r.x+r.width)/CELLSIZE;
		if (col2 >= MWIDTH)
			col2 = MWIDTH-1;
		for (int i = col1; i <= col2; i++)
			if (map[MWIDTH*(row+1)+i] != 0)
				return true;
		return false;
	}

	public boolean atTop(Rectangle r)
	{
		return false;
	}

	public void setBlock(int x, int y)
	{
		map[MWIDTH*y+x] = 1;
	}

	public void paint(Graphics g)
	{
		g.setColor(Color.blue);
		// Only draw the visible blocks (to save time)
		int col1 = (app.vleft)/CELLSIZE;
		int col2 = (app.vleft + app.VWIDTH)/CELLSIZE;
		if (col2 >= MWIDTH)
			col2 = MWIDTH-1;
		for (int i = 0; i < MHEIGHT; i++)
		 for (int j = col1; j <= col2; j++)
			if (map[MWIDTH*i+j] == 1)
				g.drawImage(image, j*CELLSIZE-app.vleft, i*CELLSIZE, app);
	}
}
