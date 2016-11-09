package project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import com.pi4j.wiringpi.Spi;


/**
 * Created by eric on 25-10-16.
 */
public class CompressImage {

	public static String path = "/home/pi/Pictures/Webcam";
	List<Byte> comprFile = new ArrayList<>();
	public static int packetSize = 4096;



	public static void main(String[] args) {
		new CompressImage().sendPicture("144p.jpg");
	}

	public void sendPicture(String pic) {
		BufferedImage image = null;
		String img = pic;
		byte[] data = new byte[packetSize];
		try {
			image = ImageIO.read(new File(path.concat("/" + img)));
		} catch (IOException e) {
			System.out.println("Couldn't find the image");
		}
		int d = 0;
		List<byte[]> compr = new ArrayList<>();
		Spi.wiringPiSPISetup(0,1000000);
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
						if (d>(packetSize-1)) {
							Spi.wiringPiSPIDataRW(0,data);
							System.out.println(Arrays.toString(data));
							if (arrayContainsD(data)) {
								compr.add(data);
							}
							data = new byte[packetSize];
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
			data = new byte[packetSize];
			Spi.wiringPiSPIDataRW(0,data);
			System.out.println(Arrays.toString(data));
			if (arrayContainsD(data)) {
				compr.add(data);
			}
		}
		while (arrayContainsD(data)) {
			data = new byte[packetSize];
			Spi.wiringPiSPIDataRW(0,data);
			System.out.print(Arrays.toString(data));
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
		BufferedImage newimg = null;
		File newfile = null;
		try {
			Files.write(Paths.get(path + "/compressed" + pic.replace(".jpg","")), listToArray(comprFile));
			newfile = new File(path + "/" + pic);
			newimg = ImageIO.read(newfile);
			int f = 0;
			for (int i = 0; i < (image.getHeight()) / 8; i++) {
				for (int j = 0; j < (image.getWidth()) / 8; j++) {
					for (int k = 0; k < 8; k++) {
						for (int l = 0; l < 8; l++) {
							Color color = new Color((int)comprFile.get(f)&0xFF,(int)comprFile.get(f)&0xFF,(int)comprFile.get(f)&0xFF);
							newimg.setRGB(j*8+l, i*8+k,color.getRGB());
							f++;
						}
					}
				}
			}
			ImageIO.write(newimg,"jpg", new File(path + "/resulting" + pic));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int grayScale(Color c) {
		return (int)((c.getRed() * 0.299)+(c.getGreen() * 0.587)+(c.getBlue() *0.114));
	}

	public static boolean arrayContainsD(byte[] ar) {
		for (byte a : ar) {
			if (a != (byte)0) {
				return true;
			}
		}
		return false;
	}

	public byte[] listToArray(List<Byte> ls) {
		byte[] data = new byte[ls.size()];
		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) ls.get(i);
		}
		return data;
	}

}
