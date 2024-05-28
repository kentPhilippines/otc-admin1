/* 
 * All content copyright Terracotta, Inc., unless otherwise indicated. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 * 
 */
package com.ruoyi.web.core.config;

import java.util.Map;
import java.util.Properties;

import org.quartz.Calendar;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 复制于源码org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration
 * 
 * {@link EnableAutoConfiguration Auto-configuration} for Quartz Scheduler.
 *
 * @author Vedran Pavic
 * @author Stephane Nicoll
 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ Scheduler.class, SchedulerFactoryBean.class, PlatformTransactionManager.class })
@EnableConfigurationProperties(QuartzProperties.class)
@AutoConfigureAfter({ DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
		LiquibaseAutoConfiguration.class, FlywayAutoConfiguration.class })
public class QuartzAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public SchedulerFactoryBean quartzScheduler(QuartzProperties properties,
			ObjectProvider<SchedulerFactoryBeanCustomizer> customizers, ObjectProvider<JobDetail> jobDetails,
			Map<String, Calendar> calendars, ObjectProvider<Trigger> triggers, ApplicationContext applicationContext,
			ThreadPoolTaskExecutor threadPoolTaskExecutor) {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		SpringBeanJobFactory jobFactory = new SpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		schedulerFactoryBean.setJobFactory(jobFactory);
		if (properties.getSchedulerName() != null) {
			schedulerFactoryBean.setSchedulerName(properties.getSchedulerName());
		}
		schedulerFactoryBean.setAutoStartup(properties.isAutoStartup());
		schedulerFactoryBean.setStartupDelay((int) properties.getStartupDelay().getSeconds());
		schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(properties.isWaitForJobsToCompleteOnShutdown());
		schedulerFactoryBean.setOverwriteExistingJobs(properties.isOverwriteExistingJobs());
		if (!properties.getProperties().isEmpty()) {
			schedulerFactoryBean.setQuartzProperties(asProperties(properties.getProperties()));
		}
		schedulerFactoryBean.setJobDetails(jobDetails.orderedStream().toArray(JobDetail[]::new));
		schedulerFactoryBean.setCalendars(calendars);
		schedulerFactoryBean.setTriggers(triggers.orderedStream().toArray(Trigger[]::new));
		schedulerFactoryBean.setTaskExecutor(threadPoolTaskExecutor); // 源码中增加了这行代码，使用自定义线程池执行任务
		customizers.orderedStream().forEach((customizer) -> customizer.customize(schedulerFactoryBean));
		return schedulerFactoryBean;
	}

	private Properties asProperties(Map<String, String> source) {
		Properties properties = new Properties();
		properties.putAll(source);
		return properties;
	}

}
