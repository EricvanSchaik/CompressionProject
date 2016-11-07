package project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import com.pi4j.wiringpi.Spi;


/**
 * Created by eric on 25-10-16.
 */
public class PictureToBinary extends Thread {

	public static String path = "/home/pi/Pictures/Webcam";
	private byte[] data = new byte[4096];
	private boolean recv;
	List<Byte> comprFile = new ArrayList<>();


	public static void main(String[] args) {
		new PictureToBinary().sendPicture("144p.jpg");
	}

	public void sendPicture(String pic) {
		BufferedImage image = null;
		String img = pic;
		try {
			image = ImageIO.read(new File(path.concat("/" + img)));
		} catch (IOException e) {
			System.out.println("Couldn't find the image");
		}
		int d = 0;
		List<byte[]> compr = new ArrayList<>();
		Spi.wiringPiSPISetup(0,32000000);
		for (int i = 0; i < (image.getHeight()) / 8; i++) {
			for (int j = 0; j < (image.getWidth()) / 8; j++) {
				for (int k = 0; k < 8; k++) {
					for (int l = 0; l < 8; l++) {
						Color c = new Color(image.getRGB(j*8 + l, i*8 + k));
						int g = grayScale(c);
						if (i == 0 && j == 0 && k == 0 && l == 0 && g==0) {
							g = 1;
						}
						data[d] = (byte)g;
						d++;
						if (d>4095) {
							Spi.wiringPiSPIDataRW(0,data);
							if (arrayContainsD(data)) {
								compr.add(data);
							}
							data = new byte[4096];
							d=0;
						}
					}
				}
			}
		}
		Spi.wiringPiSPIDataRW(0,data);
		if (arrayContainsD(data)) {
			compr.add(data);
		}
		while (compr.isEmpty()) {
			data = new byte[4096];
			Spi.wiringPiSPIDataRW(0,data);
			if (arrayContainsD(data)) {
				compr.add(data);
			}
		}
		while (arrayContainsD(data)) {
			data = new byte[4096];
			Spi.wiringPiSPIDataRW(0,data);
			if (arrayContainsD(data)) {
				compr.add(data);
			}
		}
		for (byte[] ar : compr) {
			for (byte b : ar) {
				comprFile.add(b);
			}
		}
		while (comprFile.get(0) == 0) {
			comprFile.remove(0);
		}
		while (comprFile.get(comprFile.size()-1) == 0) {
			comprFile.remove(comprFile.size()-1);
		}
	}

	public static int grayScale(Color c) {
		return (int)((c.getRed() * 0.299)+(c.getGreen() * 0.587)+(c.getBlue() *0.114));
	}

	public boolean arrayContainsD(byte[] ar) {
		for (byte a : ar) {
			if (a != (byte)0) {
				return true;
			}
		}
		return false;
	}

}
