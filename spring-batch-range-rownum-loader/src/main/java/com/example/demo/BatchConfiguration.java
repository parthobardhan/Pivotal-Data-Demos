package com.example.demo;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;

	@Value("${reader.page.size}")
	private int readerPageSize;

	@Value("${batch.grid.size}")
	private int gridSize;

	@Value("${commit.interval}")
	private int commitInterval;
	
	@Value("${partitioner.table}")
	private String partitionerTable;

	@Value("${partitioner.column}")
	private String partitionerColumn;
	
	@Value("${core.pool.size}")
	private int corePoolSize;

	@Value("${max.pool.size}")
	private int maxPoolSize;

	@Value("${queue.capacity}")
	private int queueCapacity;

	@Value("${query.provider.select}")
	private String queryProviderSelect;

	@Value("${query.provider.from}")
	private String queryProviderFrom;

	@Value("${query.provider.where}")
	private String queryProviderWhere;

	@Value("${query.provider.sort.key}")
	private String queryProviderSortKey;

	@Bean
	public Job dataLoadJob() throws Exception {
		return this.jobBuilderFactory.get("dataLoadJob1").start(dataLoadMasterStep()).build();
	}

	@Bean
	public Step dataLoadMasterStep() throws Exception {
		return stepBuilderFactory.get("dataLoadMasterStep").partitioner("dataLoadSlaveStep", partitioner())
				.partitionHandler(masterSlaveHandler()).build();
	}

	@Bean
	public Step dataLoadSlaveStep() throws Exception {
		return stepBuilderFactory.get("dataLoadSlaveStep").<TokenPayment, TokenPayment>chunk(commitInterval)
				.reader(tokenPaymentReader()).writer(tokenPaymentItemWriter()).build();
	}

	@Bean
	@StepScope
	public TokenPaymentJdbcPagingItemReader2 tokenPaymentReader() throws Exception {
		return new TokenPaymentJdbcPagingItemReader2(dataSource, readerPageSize, queryProviderSelect, queryProviderFrom,
				queryProviderWhere, queryProviderSortKey);
	}

	@Bean
	@StepScope
	public TokenPaymentItemWriter tokenPaymentItemWriter() {
		return new TokenPaymentItemWriter();
	}

	@Bean
	public PartitionHandler masterSlaveHandler() throws Exception {

		TaskExecutorPartitionHandler handler = new TaskExecutorPartitionHandler();
		handler.setGridSize(gridSize);
		handler.setTaskExecutor(threadPoolTaskExecutor());
		handler.setStep(dataLoadSlaveStep());

		try {
			handler.afterPropertiesSet();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return handler;
	}

	@Bean
	public TaskExecutor threadPoolTaskExecutor() {

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix("task_executor_thread");
		executor.initialize();

		return executor;
	}

	@Bean
	public RownumPartitioner partitioner() {
		RownumPartitioner rowPartitioner = new RownumPartitioner();
		rowPartitioner.setDataSource(dataSource);
		rowPartitioner.setTable(partitionerTable);
		rowPartitioner.setColumn(partitionerColumn);
		return rowPartitioner;
	}

}
