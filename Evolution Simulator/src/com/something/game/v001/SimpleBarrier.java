package com.something.game.v001;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class SimpleBarrier extends GameObject {
	private int width, height;
	private static ID id = ID.SimpleBarrier;
	private Color barrierColor = Color.blue;
	
	
	public SimpleBarrier(int x, int y, int width, int height, Handler handler) {
		super(x, y, id, handler);

		this.width = width;
		this.height = height;
	}

	@Override
	public void tick() {}

	@Override
	public void render(Graphics g) {
		g.setColor(barrierColor);
		g.fillRect(x, y, width, height);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
