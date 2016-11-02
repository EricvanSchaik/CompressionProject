package project;

import java.io.IOException;

/**
 * Created by eric on 31-10-16.
 */
public class SPI {

	public static void main(String[] args) {
		String[] cmd = {"python", "/home/Pictures/Webcam/sendByte.py", "0xFF"};
		try {
			Process p = Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
