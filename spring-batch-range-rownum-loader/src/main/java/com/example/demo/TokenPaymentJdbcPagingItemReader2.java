package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;

public class TokenPaymentJdbcPagingItemReader2 extends JdbcPagingItemReader<TokenPayment> implements StepExecutionListener{

	private static final Logger LOG = LoggerFactory.getLogger(TokenPaymentJdbcPagingItemReader2.class);

	private StepExecution stepExecution;


	public TokenPaymentJdbcPagingItemReader2(DataSource dataSource, int pageSize, String queryProviderSelect,
			String queryProviderFrom, String queryProviderWhere, String sortKey) {
		this.setDataSource(dataSource);
		this.setPageSize(pageSize);
		this.setQueryProvider(queryProvider(dataSource, queryProviderSelect, queryProviderFrom, queryProviderWhere, sortKey));
		this.setRowMapper(new CustomTokenPaymentRowMapper());
		//Need to be set to false for concurrent implementations
	    this.setSaveState(false);
	}

	public PagingQueryProvider queryProvider(DataSource dataSource, String queryProviderSelect,
			String queryProviderFrom, String queryProviderWhere, String sortKey) {
		
		 PagingQueryProvider queryProvider = null;
		 SqlPagingQueryProviderFactoryBean provider = new SqlPagingQueryProviderFactoryBean();
		 provider.setDataSource(dataSource);
		 provider.setSelectClause(queryProviderSelect);
		 provider.setFromClause(queryProviderFrom);
		 try {
		 queryProvider = provider.getObject();
		 } catch (Exception e) {
		 LOG.error("Exception occured while creating the PagingQueryProvider : ",
		 e);
		 }
	
		 return queryProvider;
	}

	@Override
	public synchronized void beforeStep(StepExecution stepExecution) {
		
		this.stepExecution = stepExecution;
//		this.setParameterValues(setQueryParameters());
	}
	
	private Map<String, Object> setQueryParameters(){
		
		Integer startRowNumber = stepExecution.getExecutionContext().getInt("startRowNumber");
		Integer endRowNumber = stepExecution.getExecutionContext().getInt("endRowNumber");
		
		Map<String, Object> queryParameters = new HashMap<>();
		queryParameters.put("startRowNumber", startRowNumber);
		queryParameters.put("endRowNumber", endRowNumber);
		
		return queryParameters;
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return null;
	}
}
