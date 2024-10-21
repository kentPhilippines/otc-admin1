package com.ruoyi.common.config.thread.threadpool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruoyi.common.utils.MdcUtil;

/**
 * 自定义Schedule线程池
 * 
 * @author JQ棣
 */
public class CustomScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {
	private static final Logger log = LoggerFactory.getLogger(CustomScheduledThreadPoolExecutor.class);
	
	public CustomScheduledThreadPoolExecutor(int corePoolSize, RejectedExecutionHandler handler) {
		super(corePoolSize, handler);
	}

	public CustomScheduledThreadPoolExecutor(int corePoolSize, ThreadFactory threadFactory,
			RejectedExecutionHandler handler) {
		super(corePoolSize, threadFactory, handler);
	}

	public CustomScheduledThreadPoolExecutor(int corePoolSize, ThreadFactory threadFactory) {
		super(corePoolSize, threadFactory);
	}

	public CustomScheduledThreadPoolExecutor(int corePoolSize) {
		super(corePoolSize);
	}

	/**
	 * 保存任务开始执行的时间，当任务结束时，用任务结束时间减去开始时间计算任务执行时间
	 */
	private ThreadLocal<Long> timetl = new ThreadLocal<>();

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		timetl.set(System.currentTimeMillis());
		super.beforeExecute(t, r);
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		Long start = timetl.get();
		timetl.remove();
		long diff = System.currentTimeMillis() - start;
		// 统计任务耗时、初始线程数、正在执行的任务数量、 已完成任务数量、任务总数、队列里缓存的任务数量、池中存在的最大线程数
		log.info("duration:{} ms,poolSize:{},active:{},completedTaskCount:{},taskCount:{},queue:{},largestPoolSize:{}",
				diff, this.getPoolSize(), this.getActiveCount(), this.getCompletedTaskCount(), this.getTaskCount(),
				this.getQueue().size(), this.getLargestPoolSize());
		MdcUtil.remove();
	}

	public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
		return super.schedule(new WrappedRunnable(command), delay, unit);
	}

	@Override
	public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
		return super.scheduleAtFixedRate(new WrappedRunnable(command), initialDelay, period, unit);
	}

	@Override
	public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
		return super.scheduleWithFixedDelay(new WrappedRunnable(command), initialDelay, delay, unit);
	}

	private static class WrappedRunnable implements Runnable {
		private final Runnable target;
		private final String traceId;

		public WrappedRunnable(Runnable target) {
			this.target = target;
			this.traceId = MdcUtil.get();
		}

		@Override
		public void run() {
			MdcUtil.put(traceId);
			target.run();
		}
	}
}
