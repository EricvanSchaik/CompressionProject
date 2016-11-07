package project;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by eric on 31-10-16.
 */
public class SPI {

	public static void main(String[] args) {
		try {
			FileWriter writer = new FileWriter("test");
			writer.write(0);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
