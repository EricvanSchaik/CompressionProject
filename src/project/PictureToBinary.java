package project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;


/**
 * Created by eric on 25-10-16.
 */
public class PictureToBinary {

	public static String path = "/home/eric/IdeaProjects/CompressionProject/src/project";

	public static void main(String[] args) throws Exception {
		BufferedImage image = null;
		String img = (args.length == 1 ? args[1] : "rsz_bird.jpg");
		try {
			File file = grayScale(path, img);
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.out.println("Couldn't find the image");
		}

		String[] cmd = new String[3];
		cmd[0] = "python";
		cmd[1] = "/home/pi/Pictures/Webcam/sendByte.py";
		cmd[2] = "";
		for (int i = 0; i < (image.getHeight()) / 8; i++) {
			for (int j = 0; j < (image.getWidth()) / 8; j++) {
				for (int k = 0; k < 8; k++) {
					for (int l = 0; l < 8; l++) {
						Color c = new Color(image.getRGB(j*8 + l, i*8 + k));
						cmd[2] = cmd[2].concat("0x" + Integer.toHexString(c.getBlue()) + ",");
					}
				}
			}
		}
		System.out.println("cmd[2]: " + cmd[2]);
		Process p = Runtime.getRuntime().exec(cmd);
	}

	public static File grayScale(String path, String img) {
		File output = null;
		try {
			BufferedImage image = ImageIO.read(new File(path.concat("/" + img)));
			int width = image.getWidth();
			int height = image.getHeight();
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
			output = new File(path.concat("/grayscale.jpg"));
			ImageIO.write(image, "jpg", output);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}

}
