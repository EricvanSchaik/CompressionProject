package project;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

/**
 * Created by eric on 28-10-16.
 */
public class Webcam {

	public static void main(String[] args) {
		try {
			System.out.println("starting to record");
			ProcessBuilder processBuilder = new ProcessBuilder("./webcam.sh", LocalDateTime.now().toString()).inheritIO();
			Process p = processBuilder.start();
			p.waitFor();
			System.out.println("done recording, now starting to send");
			for (int i = 1; i <= 11; i++) {
				System.out.println("sending: " + i);
				new PictureToBinary().sendPicture(new DecimalFormat("0000").format(i));
			}
		} catch (IOException|InterruptedException e) {
			e.printStackTrace();
		}
	}
}