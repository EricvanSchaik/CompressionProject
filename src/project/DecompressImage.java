package project;


import com.pi4j.wiringpi.Spi;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

import static project.CompressImage.arrayContainsD;
import static project.CompressImage.packetSize;
import static project.CompressImage.path;

/**
 * Created by eric on 25-10-16.
 */
public class DecompressImage {

	public static void main(String[] args) {
		List<byte[]> decompr = new ArrayList<>();
		List<Byte> decomprFile = new ArrayList<>();
		byte[] data = new byte[0];
		for (int m = 1; m <= 11; m++) {
			try {
				data = Files.readAllBytes(Paths.get(path + "/compressed00" + new DecimalFormat("00").format(m)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			Spi.wiringPiSPISetup(0,1000000);
			for (int i = 0; i < data.length; i += packetSize) {
				byte[] dataPacket = new byte[packetSize];
				System.arraycopy(data, i, dataPacket, 0, (data.length-i < packetSize-1 ? data.length : i+ packetSize-1));
				Spi.wiringPiSPIDataRW(0,dataPacket);
				if (arrayContainsD(dataPacket)) {
					decompr.add(dataPacket);
				}
			}
			while (decompr.isEmpty()) {
				data = new byte[packetSize];
				Spi.wiringPiSPIDataRW(0,data);
				if (arrayContainsD(data)) {
					decompr.add(data);
				}
			}
			while (arrayContainsD(data)) {
				data = new byte[packetSize];
				Spi.wiringPiSPIDataRW(0,data);
				if (arrayContainsD(data)) {
					decompr.add(data);
				}
			}
			for (byte[] ar : decompr) {
				for (byte b : ar) {
					decomprFile.add(b);
				}
			}
			try {
				BufferedImage newimg;
				File template = new File(path + "/144p.jpg");
				newimg = ImageIO.read(template);
				int f = 0;
				for (int i = 0; i < (newimg.getHeight()) / 8; i++) {
					for (int j = 0; j < (newimg.getWidth()) / 8; j++) {
						for (int k = 0; k < 8; k++) {
							for (int l = 0; l < 8; l++) {
								Color color = new Color((int)decomprFile.get(f)&0xFF,(int)decomprFile.get(f)&0xFF,(int)decomprFile.get(f)&0xFF);
								newimg.setRGB(j*8+l, i*8+k,color.getRGB());
								f++;
							}
						}
					}
				}
				ImageIO.write(newimg,"jpg", new File(path + "/resulting" + new DecimalFormat("00").format(m)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
