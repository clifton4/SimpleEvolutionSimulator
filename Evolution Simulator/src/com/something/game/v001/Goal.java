package com.something.game.v001;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Goal extends GameObject{

	private int width = 10, height = 10;
	private Color goalColor = Color.RED;
	private static ID id = ID.Goal;
	
	public Goal(int x, int y, Handler handler) {
		super(x, y, id, handler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics g) {
		g.setColor(goalColor);
		g.fillRect(x, y, width, height);
	}

	@Override
	public void tick() {
		
	}

	@Override
	public Rectangle getBounds() {
		
		return new Rectangle(x, y, width, height);
	}

}
