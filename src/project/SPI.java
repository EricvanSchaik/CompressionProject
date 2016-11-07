package project;

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
		List<byte[]> data = new ArrayList<byte[]>();
		data.add(new byte[4096]);
		System.out.println(Arrays.toString(data.get(0)));
	}

}
