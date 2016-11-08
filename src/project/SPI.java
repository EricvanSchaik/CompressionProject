package project;

import com.pi4j.wiringpi.Spi;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by eric on 31-10-16.
 */
public class SPI {

	public static void main(String[] args) {
//		BufferedImage image = null;
//		try {
//			image = ImageIO.read(new File("/home/eric/IdeaProjects/CompressionProject/src/project/144p.jpg"));
//		} catch (IOException e) {
//			System.out.println("Couldn't find the image");
//		}
//		for (int i = 0; i < 144; i++) {
//			for (int j = 0; j < 256; j++) {
//				System.out.print(Integer.toHexString(CompressImage.grayScale(new Color(image.getRGB(j,i)))) + " ");
//			}
//			System.out.println("\n");
//		}
//		System.out.println(Integer.toHexString(CompressImage.grayScale(new Color(image.getRGB(72,0)))));
//		System.out.println(Integer.parseInt("80",16));
//		System.out.println(Integer.toHexString(128));
		int i = 230;
		byte b = (byte)i;
		byte[] data = new byte[4096];
		System.out.println(Arrays.toString(data));
	}
}