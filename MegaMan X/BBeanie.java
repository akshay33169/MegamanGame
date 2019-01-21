import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import java.util.ArrayList;

public class BBeanie extends JPanel implements Runnable,KeyListener,MouseListener
{
	HeroSprite hero;
	ArrayList<BadGuy> badguy_list=new ArrayList<BadGuy>();
	//BadGuy badguy1;
	BadGuy badguy2;
	//BadGuy badguy3;
	Conveyor convey;
	Bullet fireball;
	Bullet chargeball;
	Flyer flyman;
	Flyer flyman2;
	Flyer flyman3;
	Bomb bombOBJ;
	ArrayList<Bomb> bomb_list = new ArrayList<Bomb>();
	ArrayList<Bullet> bullet_list=new ArrayList<Bullet>();

	Rectangle fb;
	Rectangle cc;



	Scenery scene[] = new Scenery[7];
	Burst bursts[] = new Burst[7];


//	int megaX=hero.getX();
//		int megaY=hero.getY();

	Grid grid;
	//int temp=0;
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
	int vleft = 0;// Pixel coord of left edge of viewable area
	// int shot=0;// (used for scrolling)
	int charged=0;
	int side=0;
	int facing=0;
//	int IF1=0;
//	int IF2=0;



	public BBeanie()
	{
		addMouseListener(this);
		addKeyListener(this);

		font = new Font("TimesRoman",Font.ITALIC,30);

		Image heros =new ImageIcon(this.getClass().getResource("standR.gif")).getImage();


		Image herosL = new ImageIcon(this.getClass().getResource("standL.gif")).getImage();

		Image herou = new ImageIcon(this.getClass().getResource("standR.gif")).getImage();

		Image herod = new ImageIcon(this.getClass().getResource("standR.gif")).getImage();
		Image dude= new ImageIcon(this.getClass().getResource("dude.gif")).getImage();
		Image blockImage = new ImageIcon(this.getClass().getResource("mblock.png")).getImage();
		saved_i=new ImageIcon(this.getClass().getResource("mmback.jpg")).getImage();

		Image f1 = new ImageIcon(this.getClass().getResource("heart.png")).getImage();
		Image f2 = new ImageIcon(this.getClass().getResource("heart.png")).getImage();
		Image f3 = new ImageIcon(this.getClass().getResource("heart.png")).getImage();
		Image b1 = new ImageIcon(this.getClass().getResource("bfly1.gif")).getImage();
		Image b2 = new ImageIcon(this.getClass().getResource("bfly2.gif")).getImage();
		Image fly = new ImageIcon(this.getClass().getResource("flyer.gif")).getImage();
		Image burst = new ImageIcon(this.getClass().getResource("mmpow.png")).getImage();
		Image banman = new ImageIcon(this.getClass().getResource("badguy1.png")).getImage();
		Image fireimage =  new ImageIcon(this.getClass().getResource("regshot.png")).getImage();
		Image bombimage = new ImageIcon(this.getClass().getResource("bombimg.jpg")).getImage();
		Image chargeball =  new ImageIcon(this.getClass().getResource("chargeshot.png")).getImage();


		MediaTracker t = new MediaTracker(this);

				t.addImage(heros, 0);

				t.addImage(herou, 0);
				t.addImage(herod, 0);

				t.addImage(herosL, 0);

				t.addImage(blockImage, 0);
				t.addImage(saved_i, 0);
				t.addImage(f1, 0);
				t.addImage(f2, 0);
				t.addImage(f3, 0);
				t.addImage(b1, 0);
				t.addImage(b2, 0);
				t.addImage(banman, 0);
				t.addImage(burst, 0);
				t.addImage(fireimage, 0);
				t.addImage(bombimage, 0);
				t.addImage(chargeball,0);
				t.addImage(fly,0);

				try
				{
					t.waitForID(0);
				} catch (InterruptedException e)
					{}
		grid = new Grid(this, blockImage);
		// Store images with classes rather than a reference
		// in every object.

		HeroSprite.setStaticImages(heros,herou,herod,herosL);
		Flower.setStaticImages(f1,f2,f3);
		BFly.setStaticImages(b1,b2);
		Burst.setStaticImage(burst);
		Bullet.setStaticImage(fireimage);
		Bomb.setStaticImage(bombimage);
		//Bullet.setStaticImage(chargeball);
		Conveyor.setStaticImage(blockImage);
		BadGuy.setStaticImage(banman);
		flyman.setStaticImage(fly);
		flyman2.setStaticImage(fly);

		//BadGuy.setStaticImage(badguy1);
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
		for (int i = 9; i < grid.MWIDTH-200; i++)
			grid.setBlock(i, grid.MHEIGHT-1);

		// Now place specific blocks (depends on current map size)
		grid.setBlock(10,13);
		grid.setBlock(11,13); grid.setBlock(11,12);
		grid.setBlock(12,13); grid.setBlock(12,12); grid.setBlock(12,11);
		grid.setBlock(13,13);

		for (int x=0;x<3;x++)
		grid.setBlock(x,grid.MHEIGHT-1);

		for (int x=27;x<=37;x++)
		{
			grid.setBlock(x,12);
		}

		for (int x=27;x<=38;x++)
		{
			grid.setBlock(x,13);
		}

		for (int x=16;x<=26;x++)
				{
					grid.setBlock(x,11);
		}


		grid.setBlock(35,11);
		grid.setBlock(36,11);
		grid.setBlock(36,10);
		grid.setBlock(37,11);
		grid.setBlock(37,10);
		grid.setBlock(37,9);

		for (int x=40;x<=60;x++)
		{
			grid.setBlock(x,6);
		}


		grid.setBlock(61,7);
		grid.setBlock(62,8);
		grid.setBlock(63,9);
		grid.setBlock(64,10);

		for (int x=65;x<=100;x++)
		{
			grid.setBlock(x,10);
		}

		for (int x=68;x<76;x++)
		{
			grid.setBlock(x,7);
		}

		grid.setBlock(68,6);
		grid.setBlock(75,6);

		for (int x=88;x<96;x++)
		{
			grid.setBlock(x,7);
		}

		grid.setBlock(88,6);
		grid.setBlock(95,6);

		grid.setBlock(101,11);
		grid.setBlock(102,12);
		grid.setBlock(103,13);

		for (int x=104;x<120;x++)
		{
			grid.setBlock(x,13);
		}


		for (int x=0;x<14;x++)
		{
			grid.setBlock(0,x);
		}

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
		//bursts[7] = new Burst(this, 800,100);

		// And, the stars of our show...
		hero = new HeroSprite(this,grid,50,249);
		badguy2 = new BadGuy(this,550,249,520,620);
		badguy_list.add(badguy2);
		badguy2 = new BadGuy(this,550,250,300,450);
		badguy_list.add(badguy2);
		badguy2= new BadGuy(this, 550,800,600,300);
		badguy_list.add(badguy2);
		badguy2 = new BadGuy(this,359,191,349,493);
		badguy_list.add(badguy2);

		badguy2=new BadGuy(this,1400,171,1359,1497);
		badguy_list.add(badguy2);

		badguy2=new BadGuy(this,1800,171,1761,1899);
		badguy_list.add(badguy2);

		badguy2=new BadGuy(this,2155,231,2104,2176);
		badguy_list.add(badguy2);

		badguy2=new BadGuy(this,2300,231,2254,2350);
		badguy_list.add(badguy2);

		flyman = new Flyer(this,900,45,833,990);
		flyman2 = new Flyer(this,1050,45,983,1140);
		flyman3= new Flyer(this,1620,145,1581,1683);

		convey = new Conveyor(this,100,250,50,150);


	}

	public void run()
	{


		int i;

		while(true)
		{
			if (!killed)
				hero.updateSprite();

		// flyman.updateSprite();

			if (flyman.isActive())
					flyman.updateSprite();

			if (flyman2.isActive())
					flyman2.updateSprite();

			if (flyman3.isActive())
					flyman3.updateSprite();


			if (flyman.isActive() && flyman.getX()==900)
			{
				bombOBJ= new Bomb(this,grid,flyman.getX(),flyman.getY(),facing); //create bomb
				bomb_list.add(bombOBJ);
				bombOBJ.isActive();
			}

			if (flyman2.isActive()&& flyman2.getX()==1050)
			{
				bombOBJ= new Bomb(this,grid,flyman2.getX(),flyman2.getY(),facing); //create bomb
				bomb_list.add(bombOBJ);
				bombOBJ.isActive();
			}

			if (flyman3.isActive()&& flyman3.getX()==1620)
						{
							bombOBJ= new Bomb(this,grid,flyman3.getX(),flyman3.getY(),facing); //create bomb
							bomb_list.add(bombOBJ);
							bombOBJ.isActive();
						}


			for (int j=0;j<badguy_list.size();j++)
			{
					badguy2=badguy_list.get(j);
					if (badguy2.isActive())
			  			 badguy2.updateSprite();


			}



				for (int j=0;j<bomb_list.size();j++)
						{
								bombOBJ=bomb_list.get(j);
								if (bombOBJ != null)
								{
									if (bombOBJ.isActive())
									{
											bombOBJ.updateSprite();

											if (!bombOBJ.isActive())
												bombOBJ=null;

									}


								}


					}


			for (int j=0;j<bullet_list.size();j++)
			{
					fireball=bullet_list.get(j);
					if (fireball != null)
					{
						if (fireball.isActive())
						{
								fireball.updateSprite();
								if (!fireball.isActive())
									fireball=null;

						}
					}
			}






			convey.updateSprite();
			for (i = 0; i < scene.length; i++)
				scene[i].update();

			// Check for collisions (Note: we don't hit scenery)
			Rectangle cb = hero.collisionBox();

			//System.out.println("Before collision");

			for (int j=0;j<bullet_list.size();j++)
			{
				fireball=bullet_list.get(j);
				if ((fireball != null) && (fireball.isActive()))
				{
					fb = fireball.collisionBox();
					for ( int k=0;k<badguy_list.size();k++)
					{
						badguy2=badguy_list.get(k);
						if (badguy2.isActive() )
						{
						 if (fb.intersects(badguy2.collisionBox()))
						 {
						 		badguy2.hit();
						 		fireball.hit();
						 		fireball=null;
						 		// temp=1;
						 }


						}

					}

					if (flyman.isActive())
					{
						if (fb.intersects(flyman.collisionBox()))
						{
								flyman.hit();
								fireball.hit();
								fireball=null;
						}
					}

					if (flyman2.isActive())
					{
						if (fb.intersects(flyman2.collisionBox()))
							{
									flyman2.hit();
									fireball.hit();
									fireball=null;
							}
					}

					if (flyman3.isActive())
						{
								if (fb.intersects(flyman3.collisionBox()))
									{
										 flyman3.hit();
										fireball.hit();
										fireball=null;
								 }
					}
				}
			}

			for (int j=0;j<bomb_list.size();j++)
			{
				bombOBJ = bomb_list.get(j);

				if ((bombOBJ != null) && (bombOBJ.isActive()))
				{
//					bb = bombOBJ.collisionBox();

					if (cb.intersects(bombOBJ.collisionBox()))
						{
						setDead();
						System.out.println("Game Over");
						}


				}
			}

		//	if (charged==1)
		//		cc = chargeball.collisionBox();

			//System.out.println("After checking for collision between bullets and badguy");

			for (i = 0; i <= 6; i++)
				if (bursts[i].isActive() && cb.intersects(bursts[i].collisionBox()))
					bursts[i].hit();

			//System.out.println("After Burst");

			for (int j=0;j<badguy_list.size();j++)
			{
				badguy2=badguy_list.get(j);
				if (badguy2.isActive() && cb.intersects(badguy2.collisionBox()))
				{
					setDead();
					System.out.println("Game Over");

				}
			}

		//	System.out.println("After checking for hero collision wiht badguy");

		//	if (badguy2.isActive() && cb.intersects(badguy2.collisionBox()))
		//		setDead();


		//	if (badguy3.isActive() && cb.intersects(badguy3.collisionBox()))
		//		setDead();

			if (cb.intersects(bursts[6].collisionBox()))
			{
				hero.changeGrav();
			}

			if (cb.intersects(convey.collisionBox())&& hero.getDY()>0)
			{
				hero.setOn();
			}

		//	if (charged==1)
		//		if (cc.intersects(badguy2.collisionBox()))
		//		{
		//			badguy2.hit();
		//			//temp=1;
		//		}

			if(hero.isOn())
			{
				hero.setX(hero.getX()+convey.getDX());
				hero.setY(convey.getY()-29);


			}


//				flyman1.setX(flyman1.getX());
			//	flyman1.setY(flyman1.getY());



						//	flyman2.setX(flyman2.getX());
						//	flyman2.setY(flyman2.getY());


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
		//System.out.println(hero.getX()+" "+hero.getY());

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		int i;
		//System.out.println(hero.getX());
		if (intro)
		{
			// startup screen
			g2d.setColor(Color.white);
			g2d.fillRect(0,0,400,300);
			g2d.setColor(Color.black);
			g2d.drawRect(0,0,399,299);
			g2d.setFont(font);
			g2d.drawString("MegaMan X!",50,40);
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

	//	badguy1.drawSprite(g);

		for (int j=0;j<badguy_list.size();j++)
		{
			badguy2=badguy_list.get(j);
			if (badguy2.isActive())
				badguy2.drawSprite(g);
		}

	//	badguy3.drawSprite(g);

		convey.drawSprite(g);

	// flyman.updateSprite(g);
		if (flyman.isActive())
		{
			flyman.drawSprite(g);
			//System.out.println("DRAW SPRITE 1");

		}

		if (flyman2.isActive())
		{
			flyman2.drawSprite(g);
			//System.out.println("DRAW SPRITE 2");

		}

		if (flyman3.isActive())
		{
			flyman3.drawSprite(g);
			//System.out.println("DRAW SPRITE 3");
		}

		for (int j=0;j<bullet_list.size();j++)
		{
				fireball=bullet_list.get(j);
				if ( (fireball!=null) && (fireball.isActive()))
				{
					fireball.drawSprite(g);
				}
		}

		for (int j=0;j<bomb_list.size();j++)
				{
						bombOBJ=bomb_list.get(j);
						if ( (bombOBJ!=null) && (bombOBJ.isActive()))
						{
							bombOBJ.drawSprite(g);
						}
		}
		//else
		//	shot=0;

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

		for (int j=0;j<bullet_list.size();j++)
		{
			fireball=bullet_list.get(j);
			if (fireball != null)
			{
				if (fireball.isActive())
				{
						int bul_left=fireball.getX();
						System.out.println("INSIDE checkscroll " + bul_left);


				}
			}
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
								//fireball.dir = 1;
								break;

			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_K: hero.dir = 2;
								//fireball.dir = 2;
								break;
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_A: hero.jmp = 1;
								hero.setOff();
								break;

			case KeyEvent.VK_S:


										//shot=1;
										charged=0;
									//	System.out.println("I AM HERE WHEN PRESSING S KEY value of dir " + facing);
								//		if (fireball!=null)
								//		{
									//		if (!fireball.isActive())
										//	{
												fireball= new Bullet(this,grid,hero.getX(),hero.getY(),facing); //create bullet
												bullet_list.add(fireball);
											// shot=1;
									//		}
								//		}

								break;
		}
	}
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		if ((key == KeyEvent.VK_J)||(key == KeyEvent.VK_K)||
			(key == KeyEvent.VK_LEFT)||(key == KeyEvent.VK_RIGHT))
			{
			 facing = hero.dir;
			hero.dir = 0;

			}



	//	if ((key == KeyEvent.VK_S))
	//		charged=0;
	}

	public static void main(String args[])
	{
		BBeanie app=new BBeanie();


	}
}



