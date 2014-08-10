package net.netne.mina.utils;

import java.util.UUID;

/**
 * diceGame
 * @date 2014-8-10 下午9:53:55
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class GamblingKeyCreator {
	
	public static String create(){
		return "gb-"+UUID.randomUUID().toString();
	}

	
	public static void main(String[] args) {
		System.out.println(GamblingKeyCreator.create());
	}
}
