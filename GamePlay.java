package demogame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements ActionListener, KeyListener{
	private boolean play=false;
	private int scrore=0; 
	private int totalBricks=21;
	private Timer timer; //javax.swing class
	private int delay=8;
	private int ballposX=120; // ball for x position
	private int ballposY=350; //ball for Y position
	private int ballXdir=-1; //ball in x direction the increment or decrement
	private int ballYdir=-2; //ball in y direction the increment or decrement
	private int playerX=350;
	private MapGenerator map;
	private int score;
	private int totalBrick;
	
	
	public GamePlay() {
		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		
		timer = new Timer(delay,this);
		timer.start();
		
		map =new MapGenerator(3,7);
	}
	public void paint(Graphics g)  //java.awt.Graphics (inbuild menthod) g is object g graphic class
	{
		//black canvas
		g.setColor(Color.BLACK);
		g.fillRect(1, 1, 692, 592);
		
		//top border
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, 692, 3);
		
		//left border
		
		g.fillRect(0, 3, 3, 592);
		
		//right border
		
		g.fillRect(684, 3, 3, 592);
		
		//paddle
		
		g.setColor(Color.RED);
		g.fillRect(playerX, 550, 100, 8);
		
		// bricks
		
		map.draw((Graphics2D)g);
		
		
		//ball
		
		g.setColor(Color.pink);
		g.fillOval(ballposX,ballposY,20,20);
		
		//score
		
		g.setColor(Color.GREEN);
		g.drawString("Score :"+score, 550, 30);
		
		//gameover msg
		
		if(ballposY>=570) {
			play=false;
			ballXdir=0;
			ballYdir=0;
			
			g.setColor(Color.PINK);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("GameOver, Score: "+score, 220,300);
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press Enter To Restart!!", 230,350);
		}
		
		//Player win msg
		
		if(totalBrick==0) {
			
			play=false;
			ballXdir=0;
			ballYdir=0;
			
			g.setColor(Color.PINK);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Press Enter For Play!!", 250,302);
			
			
		}
	}
	//paddle movement
	private void moveLeft() {
		play=true;
		playerX-=20;
		
	}
	private void moveRight() {
		play=true; 
		playerX+=20;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			if(playerX<=0)
				                                //padle move in left side frame
				playerX=0;
			else
				moveLeft();
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			if(playerX>=600)
				playerX=600;
			else
				moveRight();                     //padle move in right side
		}
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			if(!play) {
				score=0;
				totalBrick=21;
				ballposX=120;
				ballposY=350;
				ballXdir=-1;
				ballYdir=-2;
				playerX=320;
				
				map=new MapGenerator(3,7);
			}
		}
		repaint();
	}
	
		
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(play) {
			
			if(ballposX<=0) {
				ballXdir=-ballXdir;               //ball hits left wall
			}
			
			if(ballposX>=670) {
				ballXdir=-ballXdir;               //ball hits right wall
			}
			
			if(ballposY<=0) {
				ballYdir=-ballYdir;               //ball hits upper wall
			}
			
			Rectangle ballRect=new Rectangle(ballposX,ballposY,20,20);
			Rectangle paddleRect=new Rectangle(playerX,550,100,8);
			
			if(ballRect.intersects(paddleRect)) {
				ballYdir=-ballYdir;               //ball hits paddle and return
			}
			
			
			//removing bricks
			
			
			A:for(int i=0;i<map.map.length;i++) {
				for(int j=0;j<map.map[0].length;j++) {
					if(map.map[i][j]>0) {
						int width=map.brickWidth;
						int height=map.brickHeight;
						int brickXpos=80+j*width;
						int brickYpos=70+i*height;
						
						Rectangle brickRect=new Rectangle(brickXpos,brickYpos,width,height);
						
						if(ballRect.intersects(brickRect)) {
							
							map.setBrick(0, i, j);
							totalBricks--;					//bricks vanish after hitted by ball
							score+=5;
							
							//checking ball hit on brick by left side or right side
							if(ballposX+19<=brickXpos || ballposX+1>brickXpos+width) {
								ballXdir=-ballXdir;
							}
							else {
								ballYdir=-ballYdir;
							}
							
							break A;             //lebel A
						}
					}
				}
			}
			
			
			
			
			
			
			
			
			
			ballposX+=ballXdir;
			ballposY+=ballYdir;
			
			
		}
		repaint();
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
