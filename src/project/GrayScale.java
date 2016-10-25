package project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by eric on 25-10-16.
 */
public class GrayScale {

	BufferedImage image;
	static File output;
	int width;
	int height;

	public GrayScale() {

		try {
			image = ImageIO.read(new File("C:/Users/Dylan/Pictures/images/sea-wallpaper-29.jpg"));
			width = image.getWidth();
			height = image.getHeight();

			for(int i=0; i<height; i++){

				for(int j=0; j<width; j++){

					Color c = new Color(image.getRGB(j, i));
					int red = (int)(c.getRed() * 0.299);
					int green = (int)(c.getGreen() * 0.587);
					int blue = (int)(c.getBlue() *0.114);
					Color newColor = new Color(red+green+blue,
							red+green+blue,red+green+blue);
					image.setRGB(j,i,newColor.getRGB());
				}
			}
			output = new File("C:/Users/Dylan/Pictures/images/grayscale.jpg");
			ImageIO.write(image, "jpg", output);

		} catch (Exception e) {}
	}
	public File getImage() {
		return output;
	}
}
