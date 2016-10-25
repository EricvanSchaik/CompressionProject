package project;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by eric on 25-10-16.
 */
public class PictureToBinary {

	public static void main(String[] args) throws Exception {
		GrayScale grayscale = new GrayScale();

		BufferedImage image = null;
		try {
			File file = grayscale.getImage();
			image = ImageIO.read(file);
		} catch (IOException e){
		}

		// write it to byte array in-memory
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", b);
		byte[] ByteArray = b.toByteArray();
		Files.write((new File("C:/Users/Dylan/Pictures/images/data").toPath()), ByteArray);
		System.out.println("content is written");
	}

}
