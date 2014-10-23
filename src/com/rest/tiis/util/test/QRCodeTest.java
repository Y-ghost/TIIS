package com.rest.tiis.util.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class QRCodeTest {
	static int width = 90;
	static int height = 90;

	public QRCodeTest() {
	}

	public static void create_image(String sms_info) throws Exception {
		try {
			Qrcode testQrcode = new Qrcode();
			testQrcode.setQrcodeErrorCorrect('M');
			testQrcode.setQrcodeEncodeMode('B');
			testQrcode.setQrcodeVersion(7);
			String testString = sms_info;
			byte[] d = testString.getBytes("utf8");
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
			Graphics2D g = bi.createGraphics();
			g.setBackground(Color.WHITE);
			g.clearRect(0, 0, width, height);
			g.setColor(Color.BLACK);

			// 限制最大字节数为119
			if (d.length > 0 && d.length < 120) {
				boolean[][] s = testQrcode.calQrcode(d);
				for (int i = 0; i < s.length; i++) {
					for (int j = 0; j < s.length; j++) {
						if (s[j][i]) {
							g.fillRect(j * 2, i * 2, 2, 2);
						}
					}
				}
			}
			g.dispose();
			bi.flush();
			String ctxPath = "/Library/tomcat7/webapps/TIIS/images/";
			File f = new File(ctxPath + "1.png");
			if (!f.exists())
				f.createNewFile();
			ImageIO.write(bi, "png", f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		String string = "http://tiis.rainet.com.cn/Files/download?fileName=Raient-TIIS-newAPK-v1_4_1";
		QRCodeTest.create_image(string);
		long end = System.currentTimeMillis();
		long last = end - start;
		System.out.println("time consume:" + last);
	}
}