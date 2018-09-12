package com.growatt.shinephone.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class dataAlgorithm {
	public static byte[] readBytes(InputStream in, int start, int len) {
		if (start >= len) {
			return null;
		}

		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		byte[] data = new byte[len];
		byte[] temp = new byte[100];
		int readLen = 0;
		int destPos = start;
		try {
			if ((readLen = in.read(temp)) > -1) {
				System.arraycopy(temp, 0, data, destPos, readLen);
				destPos += readLen;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return bo.toByteArray();
		}

		if (destPos < len) {
			destPos = len;
		}
		bo.write(data, 0, destPos);

		try {
			bo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bo.toByteArray();
	}
}
