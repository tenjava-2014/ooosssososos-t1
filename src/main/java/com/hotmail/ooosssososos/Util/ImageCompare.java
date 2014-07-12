package com.hotmail.ooosssososos.Util;


import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

 public class ImageCompare {

	protected BufferedImage img1 = null;
	protected BufferedImage img2 = null;
	protected BufferedImage imgc = null;
	protected int comparex = 0;
	protected int comparey = 0;
	protected int factorA = 0;
	protected int factorD = 10;
	protected boolean match = false;
	protected int debugMode = 0;

	public ImageCompare(BufferedImage img1, BufferedImage img2) {
		this.img1 = img1;
		this.img2 = img2;
		autoSetParameters();
	}

	protected void autoSetParameters() {
		comparex = 10;
		comparey = 10;
		factorA = 10;
		factorD = 10;
	}

	public void setParameters(int x, int y, int factorA, int factorD) {
		this.comparex = x;
		this.comparey = y;
		this.factorA = factorA;
		this.factorD = factorD;
	}


     public double diff = 500;

	public void compare() {
		imgc = imageToBufferedImage(img2);
		Graphics2D gc = imgc.createGraphics();
		gc.setColor(Color.RED);
		img1 = imageToBufferedImage(GrayFilter.createDisabledImage(img1));
		img2 = imageToBufferedImage(GrayFilter.createDisabledImage(img2));
		int blocksx = (int)(img1.getWidth() / comparex);
		int blocksy = (int)(img1.getHeight() / comparey);
		this.match = true;
        int tmp = 0;
		for (int y = 0; y < comparey; y++) {
			if (debugMode > 0) System.out.print("|");
			for (int x = 0; x < comparex; x++) {
				int b1 = getAverageBrightness(img1.getSubimage(x*blocksx, y*blocksy, blocksx - 1, blocksy - 1));
				int b2 = getAverageBrightness(img2.getSubimage(x*blocksx, y*blocksy, blocksx - 1, blocksy - 1));
				int diff = Math.abs(b1 - b2);
				if (diff > factorA) {
                    tmp += diff-factorA;

				}

				if (debugMode == 1) System.out.print((diff > factorA ? "X" : " "));
				if (debugMode == 2) System.out.print(diff + (x < comparex - 1 ? "," : ""));
			}
			if (debugMode > 0) System.out.println("|");
		}
        System.out.println("(tmp/(comparex*comparey): " + (tmp/(comparex*comparey*1.0)));
        diff = (tmp/(comparex*comparey*1.0D));
        if((tmp/(comparex*comparey*1.0)) > factorA)this.match = false;
	}

	public BufferedImage getChangeIndicator() {
		return imgc;
	}
	
	// returns a value specifying some kind of average brightness in the image.
	protected int getAverageBrightness(BufferedImage img) {
		Raster r = img.getData();
		int total = 0;
		for (int y = 0; y < r.getHeight(); y++) {
			for (int x = 0; x < r.getWidth(); x++) {
				total += r.getSample(r.getMinX() + x, r.getMinY() + y, 0);
			}
		}
		return (int)(total / ((r.getWidth()/factorD)*(r.getHeight()/factorD)));
	}
	

	// returns true if image pair is considered a match
	public boolean match() {
		return this.match;
	}

	protected static BufferedImage imageToBufferedImage(Image img) {
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bi.createGraphics();
		g2.drawImage(img, null, null);
		return bi;
	}


	
}
