package com.sam.graduation.design.gdemailserver.utils.cut;

public class ImageBean {
	private String name;
	private int x;
	private int y;
	private int w;
	private int h;
	
	public ImageBean(String name, int x, int y, int w, int h) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public String getName() {
		return name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	@Override
	public String toString() {
		return name+" "+x+" "+y+" "+w+" "+h;
	}
}
