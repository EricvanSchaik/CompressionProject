package project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by eric on 25-10-16.
 */
public class PictureToBinary extends Thread {

	public static String path = "/home/eric/IdeaProjects/CompressionProject/src/project";
	public static ReentrantLock lock = new ReentrantLock();

	public static void main(String[] args) {
		new PictureToBinary().sendPicture("144p.jpg");
	}

	public void sendPicture(String pic) {
		BufferedImage image = null;
		String img = pic;
		try {
			System.out.println("Starting to grayscale " + pic);
			File file = grayScale(path, img);
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.out.println("Couldn't find the image");
		}

		String progr = "python";
		String path = "/home/pi/Pictures/Webcam/sendByte.py";
		StringBuilder stringBuilder = new StringBuilder();
		int d = 0;
		for (int i = 0; i < (image.getHeight()) / 8; i++) {
			for (int j = 0; j < (image.getWidth()) / 8; j++) {
				for (int k = 0; k < 8; k++) {
					for (int l = 0; l < 8; l++) {
						Color c = new Color(image.getRGB(j*8 + l, i*8 + k));
						stringBuilder.append("0x" + Integer.toHexString(c.getBlue()) + ",");
						d++;
						if (d>25000) {
							stringBuilder.setLength(stringBuilder.length()-1);
							System.out.println("250000 bytes worden verstuurd");
							ProcessBuilder pb = new ProcessBuilder(progr, path, stringBuilder.toString()).inheritIO();
							Process p;
							try {
								lock.lock();
								p = pb.start();
								p.waitFor();
								lock.unlock();
							} catch (IOException|InterruptedException e) {
								e.printStackTrace();
							}
							System.out.println("Zijn verzonden");
							d=0;
							stringBuilder = new StringBuilder();
						}
					}
				}
			}
		}
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
			output = new File(path.concat("/" + img + "-gray.jpg"));
			ImageIO.write(image, "jpg", output);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}

}
