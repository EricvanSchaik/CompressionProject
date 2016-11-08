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
		try {
			File newimg = new File(CompressImage.path + "/newimg.jpg");
			BufferedImage img = ImageIO.read(newimg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}