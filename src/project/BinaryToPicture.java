package project;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * Created by eric on 25-10-16.
 */
public class BinaryToPicture {

	public static void main(String[] args) throws Exception {
		BufferedImage image = null;
		File file = new File("C:/Users/Dylan/Pictures/images/data");
		byte[] data = Files.readAllBytes(file.toPath());
		System.out.println("file is created");
		InputStream in = new ByteArrayInputStream(data);
		image = ImageIO.read(in);

		System.out.println("image is created");
		File outputfile = new File("C:/Users/Dylan/Pictures/images/BinarytoPicture.jpg");

		ImageIO.write(image, "jpg", outputfile);
	}
}
