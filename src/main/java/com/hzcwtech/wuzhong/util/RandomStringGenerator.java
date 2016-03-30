package com.hzcwtech.wuzhong.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class RandomStringGenerator {

	/**
	 * 获取一定长度的随机字符串
	 * 
	 * @param length
	 *            指定字符串长度
	 * @return 一定长度的字符串
	 */
	public static String getRandomStringByLength(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	public static String getSuffix() {
		StringBuffer suffix = new StringBuffer("");
		String now = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());
		suffix.append(now);
		suffix.append("_");
		UUID uuid = UUID.randomUUID();
		suffix.append(uuid.toString());
		return suffix.toString();
	}
}