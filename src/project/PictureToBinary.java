package project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import com.pi4j.wiringpi.Spi;


/**
 * Created by eric on 25-10-16.
 */
public class PictureToBinary extends Thread {

	public static String path = "/home/pi/Pictures/Webcam";

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
		byte[] data = new byte[4096];
		List<byte[]> compr = new ArrayList<byte[]>();
		Spi.wiringPiSPISetup(0,32000000);
		for (int i = 0; i < (image.getHeight()) / 8; i++) {
			for (int j = 0; j < (image.getWidth()) / 8; j++) {
				for (int k = 0; k < 8; k++) {
					for (int l = 0; l < 8; l++) {
						Color c = new Color(image.getRGB(j*8 + l, i*8 + k));
						data[d] = (byte)grayScale(c);
						d++;
						if (d>4095) {
							Spi.wiringPiSPIDataRW(0,data);
							compr.add(data);
							data = new byte[4096];
							d=0;
						}
					}
				}
			}
		}
		System.out.println(Arrays.toString(compr.get(0)));
	}

	public static int grayScale(Color c) {
		return (int)((c.getRed() * 0.299)+(c.getGreen() * 0.587)+(c.getBlue() *0.114));
	}

}
