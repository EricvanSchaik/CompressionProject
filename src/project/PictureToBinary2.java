package project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by eric on 25-10-16.
 */
public class PictureToBinary2 {

	public static String path = "/home/pi/Pictures/Webcam";

	public static void main(String[] args) throws Exception {
		BufferedImage image = null;
		String img = (args.length == 1 ? args[1] : "bird.jpg");
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
		System.out.println(image.getHeight());
		System.out.println(image.getWidth());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < (image.getHeight()) / 8; i++) {
			for (int j = 0; j < (image.getWidth()) / 8; j++) {
				for (int k = 0; k < 8; k++) {
					for (int l = 0; l < 8; l++) {
						Color c = new Color(image.getRGB(j*8 + l, i*8 + k));
						//cmd[2] = cmd[2].concat("0x" + Integer.toHexString(c.getBlue()) + ",");
						sb.append("0x" + Integer.toHexString(c.getBlue()) + ",");
					}
				//cmd[2] = cmd[2].replaceFirst(".$","");
                       		sb.setLength(sb.length()-1);
				System.out.println(sb.toString());
				ProcessBuilder pb = new ProcessBuilder(cmd[0], cmd[1], sb.toString());
				pb.redirectErrorStream(true);
                        	//Process p = pb.start();
				//p.destroy();
				//BufferedReader reader =
				//	new BufferedReader(new InputStreamReader(p.getInputStream()));
				//	while ((reader.readLine()) != null) {}
				//p.waitFor();
				sb = new StringBuilder();
				//p.destroy();
				//System.out.println(p.exitValue());
                        	//System.out.println("cmd[2]: " + cmd[2]);
                        	//Process p = Runtime.getRuntime().exec(cmd); //THIS METHOD TAKES A SUPER LONG TIME TO EXCECUTE/
				//p.waitFor();
				//System.out.println(p.exitValue());
                        	//cmd[2] = "";
                       		//p.waitFor();
                       		//System.out.println("done process: " + i);
				}
			//cmd[2] = cmd[2].replaceFirst(".$","");
			//ProcessBuilder pb = new ProcessBuilder(cmd[0], cmd[1], cmd[2]);
			//Process p = pb.start();
                        //System.out.println("cmd[2]: " + cmd[2]);
                        //Process p = Runtime.getRuntime().exec(cmd); //THIS METHOD TAKES A SUPER LONG TIME TO EXCECUTE
                        //cmd[2] = "";
                        //p.waitFor();
                        //System.out.println("done process: " + i);
			}
		System.out.println(i);
		}
		//System.out.println("done");
                cmd[2] = cmd[2].replaceFirst(".$","");
                //ProcessBuilder pb = new ProcessBuilder(cmd[0], cmd[1], cmd[2]);
                //Process p = pb.start();
                //System.out.println("cmd[2]: " + cmd[2]);
                //Process p = Runtime.getRuntime().exec(cmd); //THIS METHOD TAKES A SUPER LONG TIME TO EXCECUTE
                //cmd[2] = "";
                //p.waitFor();

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
			System.out.println("grayscale done!");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}

}
