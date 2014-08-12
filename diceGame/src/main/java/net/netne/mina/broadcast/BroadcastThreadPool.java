package net.netne.mina.broadcast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * diceGame
 * @date 2014-8-12 下午10:23:25
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class BroadcastThreadPool {
	
	private static ExecutorService pool = Executors.newFixedThreadPool(15);
	
	public static void execute(IBroadcastThread thread){
		pool.execute(thread);
	}

}
