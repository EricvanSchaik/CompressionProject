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
public class ReceiveCompressed {

	public static boolean validData;

	public static void handleCompressed(String data) {
		validData = false;
		String[] split = data.split(",");
		System.out.println(split);
		int j = 0;
		File file = new File(PictureToBinary.path + "/" + "data");
		for (String value : split) {
			int i = Integer.parseInt(value);
			if (i != 0) {
				validData = true;
			}
			if (validData) {
				if (j==0 && i==0) {
					validData = false;
				}

			}
			j = i;
		}
	}
}
