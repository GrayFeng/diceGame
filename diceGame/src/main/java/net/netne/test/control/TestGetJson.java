package net.netne.test.control;

import com.alibaba.fastjson.JSON;

import net.netne.mina.pojo.param.CreateGamblingParams;

/**
 * diceGame
 * @date 2014-8-10 下午10:10:54
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class TestGetJson {
	
	public static void main(String[] args) {
		CreateGamblingParams createGamblingParams = new CreateGamblingParams();
		createGamblingParams.setActionCode("create");
		createGamblingParams.setUid("abc");
		createGamblingParams.setGamerNum(5);
		System.out.println(JSON.toJSONString(createGamblingParams));
	}

}
