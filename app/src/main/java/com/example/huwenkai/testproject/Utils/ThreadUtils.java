package com.example.huwenkai.testproject.Utils;

import android.os.Handler;

public class ThreadUtils {

	public static void runInThread(Runnable r) {
		new Thread(r).start();
	}

	private static Handler handler = new Handler();

	public static void runUIThread(Runnable r) {
		handler.post(r);
	}
}
