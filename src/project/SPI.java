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
		Byte b1 = new Byte((byte)3);
		Byte b2 = new Byte((byte)3);
		List<Byte> l = new ArrayList<>();
		l.add(b1);
		System.out.println(l.contains(b2));
	}

}
