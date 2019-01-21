import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;

public class Creative extends JPanel implements Runnable,KeyListener,MouseListener
{
	HeroSprite hero;
	BadGuy badguy1;
	BadGuy badguy2;
	BadGuy badguy3;
	Conveyor convey;


	Scenery scene[] = new Scenery[7];
	Burst bursts[] = new Burst[7];


	Grid grid;
	int score;
	JFrame frame=new JFrame();
	boolean killed = false;
	Thread anim;
	Image buffer;
	Graphics bufgr;
	Image saved_i;
	int cut = 50;  // cutpoint in background scenery (to scroll)
	Font font;
	boolean intro = true; // init instruction screen
	int FRAME_DELAY = 50;
	public static final int VWIDTH = 800;
	public static final int VHEIGHT = 500;
	public static final int SCROLL = 400;  // Set edge limit for scrolling
	int vleft = 0;	// Pixel coord of left edge of viewable area
					// (used for scrolling)

	public Creative()
	{
		addMouseListener(this);
		addKeyListener(this);

		font = new Font("TimesRoman",Font.ITALIC,30);
		Image heros =new ImageIcon(this.getClass().getResource("heros.gif")).getImage();
		Image herou = new ImageIcon(this.getClass().getResource("herou.gif")).getImage();
		Image herod = new ImageIcon(this.getClass().getResource("herod.gif")).getImage();
		Image dude= new ImageIcon(this.getClass().getResource("dude.gif")).getImage();
		Image blockImage = new ImageIcon(this.getClass().getResource("concrete.png")).getImage();
		saved_i=new ImageIcon(this.getClass().getResource("back2.gif")).getImage();

		Image f1 = new ImageIcon(this.getClass().getResource("rose.gif")).getImage();
		Image f2 = new ImageIcon(this.getClass().getResource("rose.gif")).getImage();
		Image f3 = new ImageIcon(this.getClass().getResource("rose.gif")).getImage();
		Image b1 = new ImageIcon(this.getClass().getResource("bfly1.gif")).getImage();
		Image b2 = new ImageIcon(this.getClass().getResource("bfly2.gif")).getImage();
		Image burst = new ImageIcon(this.getClass().getResource("power.gif")).getImage();
		Image banman = new ImageIcon(this.getClass().getResource("skulldude.gif")).getImage();



		MediaTracker t = new MediaTracker(this);
				t.addImage(heros, 0);
				t.addImage(herou, 0);
				t.addImage(herod, 0);
				t.addImage(blockImage, 0);
				t.addImage(saved_i, 0);
				t.addImage(f1, 0);
				t.addImage(f2, 0);
				t.addImage(f3, 0);
				t.addImage(b1, 0);
				t.addImage(b2, 0);
				t.addImage(burst, 0);
				t.addImage(banman, 0);
				try
				{
					t.waitForID(0);
				} catch (InterruptedException e)
					{}
		grid = new Grid(this, blockImage);
		// Store images with classes rather than a reference
		// in every object.
		HeroSprite.setStaticImages(heros,herou,herod);
		Flower.setStaticImages(f1,f2,f3);
		BFly.setStaticImages(b1,b2);
		Burst.setStaticImage(burst);
		Conveyor.setStaticImage(blockImage);

		BadGuy.setStaticImage(banman);
		frame.add(this);
		frame.setSize(600,340);
   		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLevel1();
		score = 0;
		setSize(800,500);
		anim=new Thread(this);
		anim.start();
	}
	public void setLevel1()
	{
		// Put in a ground level
		for (int i = 0; i < grid.MWIDTH; i++)
			grid.setBlock(i, grid.MHEIGHT-1);

		// Now place specific blocks (depends on current map size)
		grid.setBlock(10,13);
		grid.setBlock(11,13); grid.setBlock(11,12);
		grid.setBlock(12,13); grid.setBlock(12,12); grid.setBlock(12,11);
		grid.setBlock(13,13);
		grid.setBlock(22,13); grid.setBlock(24,13);
		grid.setBlock(25,11); grid.setBlock(26,11);
		grid.setBlock(23,9); grid.setBlock(24,9);
		grid.setBlock(25,7); grid.setBlock(26,7);
		grid.setBlock(22,5); grid.setBlock(23,5); grid.setBlock(24,5);
		grid.setBlock(20,8); grid.setBlock(19,8);
		grid.setBlock(39,13); grid.setBlock(38,13); grid.setBlock(39,12);

		// Setup foreground scenery
		scene[0] = new Flower(this,60,269,100,0);
		scene[1] = new Flower(this,90,269,100,20);
		scene[2] = new Flower(this,120,269,100,40);
		scene[3] = new Flower(this,650,269,120,30);
		scene[4] = new Flower(this,680,269,120,0);
		scene[5] = new BFly(this,70,120);
		scene[6] = new BFly(this,383,87);
		// Setup up scoring bursts
		bursts[0] = new Burst(this,320,150);
		bursts[1] = new Burst(this,220,150);
		bursts[2] = new Burst(this,500,60);
		bursts[3] = new Burst(this,720,160);
		bursts[4] = new Burst(this,735,140);
		bursts[5] = new Burst(this,750,155);
		bursts[6] = new Burst(this,199,31);

		// And, the stars of our show...
		hero = new HeroSprite(this,grid,50,249);
		badguy1 = new BadGuy(this,550,249,520,620);
		badguy2 = new BadGuy(this,550,250,300,450);
		badguy3= new BadGuy(this, 550,800,600,300);

		convey = new Conveyor(this,100,250,50,150);


	}

	public void run()
	{
		int i;

		while(true)
		{
			if (!killed)
				hero.updateSprite();
			badguy1.updateSprite();
			badguy2.updateSprite();
			badguy3.updateSprite();

			convey.updateSprite();
			for (i = 0; i < scene.length; i++)
				scene[i].update();

			// Check for collisions (Note: we don't hit scenery)
			Rectangle cb = hero.collisionBox();
			for (i = 0; i <= 6; i++)
				if (bursts[i].isActive() && cb.intersects(bursts[i].collisionBox()))
					bursts[i].hit();


			if (cb.intersects(badguy1.collisionBox()))
				setDead();


			if (cb.intersects(badguy2.collisionBox()))
				setDead();


			if (cb.intersects(badguy3.collisionBox()))
				setDead();

			if (cb.intersects(bursts[6].collisionBox()))
			{
				hero.changeGrav();
			}

			if (cb.intersects(convey.collisionBox())&& hero.getDY()>0)
			{
				hero.setOn();
			}
			if(hero.isOn())
			{
				hero.setX(hero.getX()+convey.getDX());
				hero.setY(convey.getY()-29);
			}
			if(!cb.intersects(convey.collisionBox()))
				hero.setOff();

			if(hero.getX()>254 && hero.getX()<264 && hero.getY()<235 && hero.getY()>230)
			{
				for (int x=10;x<17;x++)
				{
					grid.setBlock(x,x-7);
				}
			}
			checkScrolling();
			repaint();
			try
			{
				Thread.sleep(FRAME_DELAY);
			} catch (InterruptedException e)
				{}
		}
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		int i;

		if (intro)
		{
			// startup screen
			g2d.setColor(Color.white);
			g2d.fillRect(0,0,400,300);
			g2d.setColor(Color.black);
			g2d.drawRect(0,0,399,299);
			g2d.setFont(font);
			g2d.drawString("BOUNCING BEANIE!",50,40);
			g2d.drawString("Use arrow keys to",50,100);
			g2d.drawString("move left and right,",40,130);
			g2d.drawString("space bar to jump.",60,160);
			g2d.drawString("Click applet to start game.",30,250);
			return;
		}
		/*
		 * Begin main paint
		 */
		g2d.setClip(0, 0, 800, 500);
		cut = vleft>>1; // setting cut to half the main scroll factor
						// gives the parallax effect
		g2d.drawImage(saved_i, -100-cut, 0, this);
		g2d.drawImage(saved_i, 400-cut, 0, this);

		grid.paint(g);

		badguy1.drawSprite(g);
		badguy2.drawSprite(g);
		badguy3.drawSprite(g);
		convey.drawSprite(g);

		for (i = 0; i < bursts.length; i++)
			bursts[i].paint(g);

		if (!killed)
			hero.drawSprite(g);
		for (i = 0; i < scene.length; i++)
			scene[i].paint(g);
		g2d.setColor(Color.black);
		g2d.setFont(font);
		g2d.drawString("Score:"+score,250,25);
		if (wonGame())
		{
			g2d.setColor(Color.black);
			g2d.setFont(font);
			g2d.drawString("You win!",100,100);
		}
		if (lostGame())
		{
			g2d.setColor(Color.black);
			g2d.setFont(font);
			g2d.drawString("You lose.",100,100);
		}
	}
	void checkScrolling()
	{
		// Test if hero is at edge of view window and scroll appropriately
		if (hero.getX() < (vleft+SCROLL))
		{
			vleft = hero.getX()-SCROLL;
			if (vleft < 0)
				vleft = 0;
		}
		if ((hero.getX() + hero.width()) > (vleft+VWIDTH-SCROLL))
		{
			vleft = hero.getX()+hero.width()-VWIDTH+SCROLL;
			if (vleft > (grid.width()-VWIDTH))
				vleft = grid.width()-VWIDTH;
		}
	}



	private boolean lostGame()
	{
		return killed;
	}

	private boolean wonGame()
	{
		return (score == 10*bursts.length);
	}

	public void update(Graphics g)
	{
		paint(bufgr);
		g.drawImage(buffer,0,0,this);
	}
	public void setDead()
	{
		killed = true;
	}



	public void bumpScore(int p)
	{
		score += p;
	}
	public void mousePressed(MouseEvent e)
	{
	}

	public void mouseReleased(MouseEvent e)
	{
	}
	public void mouseExited(MouseEvent e)
	{
	}
	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseClicked(MouseEvent e)
	{
		requestFocus();
		intro = false;
	}
	public void keyTyped(KeyEvent e)
	{

	}
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		switch (key)
		{
			// Duplicate keys are defined to maintain
			// the original bindings (as well as provide
			// more sensible ones)
			//
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_J: hero.dir = 1;
								break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_K: hero.dir = 2;
								break;
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_A: hero.jmp = 1;
								hero.setOff();
								break;
		}
	}
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		if ((key == KeyEvent.VK_J)||(key == KeyEvent.VK_K)||
				(key == KeyEvent.VK_LEFT)||(key == KeyEvent.VK_RIGHT))
			hero.dir = 0;
	}

	public static void main(String args[])
	{
		Creative app=new Creative();


	}
}



