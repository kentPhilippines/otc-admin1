package com.ruoyi.common.utils;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * MDC工具类
 * 
 * @author JQ棣
 */
public class MdcUtil {

	public final static String UNIQUE_KEY = "trace-id";

	/**
	 * 1.Random是线程安全的<br/>
	 * 2.高并发情况下，单实例的性能不如每个线程持有一个实例<br/>
	 * 3.经粗略测试，并发数少于200情况下性能是单实例优，按需来说项目目前并发量在200内<br/>
	 */
	private static Random random = new Random();
	
	public static void put() {
		// 可修改traceid生成算法
		String traceid = "" + System.currentTimeMillis() + (1000 + random.nextInt(9000));
		MDC.put(UNIQUE_KEY, traceid);
	}
	
	public static void put(String traceId) {
		if (StringUtils.isNotEmpty(traceId)) {
			MDC.put(UNIQUE_KEY, traceId);
			return;
		}
		put();
	}

	public static String get() {
		return MDC.get(UNIQUE_KEY);
	}
	
	public static void remove() {
		MDC.remove(UNIQUE_KEY);
	}
}