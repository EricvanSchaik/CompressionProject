package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

/**
 * Created by eric on 28-10-16.
 */
public class Webcam {

	public static void main(String[] args) {
		Process p;
		String time;
		String[] cmd = new String[2];
		System.out.println("starting");
		cmd[0] = "./webcam.sh";
		for (int i = 0; i < 2; i++) {
			System.out.println(i);
			time = LocalDateTime.now().toString();
			cmd[1] = time;
			try {
				p = Runtime.getRuntime().exec(cmd);
				System.out.println("process executed");
				BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					System.out.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}