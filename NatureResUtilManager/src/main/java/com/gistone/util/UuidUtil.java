package com.gistone.util;

import java.util.UUID;

/**
 * 
 * @author lx
 *
 */
public class UuidUtil {

	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}
}

