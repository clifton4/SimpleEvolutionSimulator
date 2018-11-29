package com.something.game.v001;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class DotGame extends Canvas implements Runnable{

	private static final long serialVersionUID = 6574357710865271670L;
	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
	public static final int STARTX = WIDTH/2 - 15, STARTY = HEIGHT-50;
	private static Color bgColor = Color.white;
	
	private boolean running = false;
	
	private Handler handler;
	private Selector selector;
	
	private Thread thread;
	
	public DotGame() {
		
		handler = new Handler(); 
		Population myPop = new Population(WIDTH/2 - 15, HEIGHT-50, handler);
		selector = new Selector(handler, myPop);
		new Window(WIDTH, HEIGHT, "Dot Simulator", this);
		
		
		//create barriers along the window edges
		selector.generate();
	}
	
	public static void main(String[] args) {
		new DotGame();
	}
	
	public void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void tick() {
		selector.tick();
		handler.tick();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		//set bg color
		g.setColor(bgColor);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		g.dispose();
		bs.show();
	}
	
	/**
	 * Game loop here
	 * calls the render and tick methods tick times per second
	 */
	@Override
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			//tick();
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta --;
			}
			if(running) 
				render();
			frames ++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	
}
