package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

public class RownumPartitioner implements Partitioner {
	private JdbcOperations jdbcTemplate;

	private String table;

	private String column;
	private String partitionerQuery = "SELECT COUNT(" + column + ") from " + table;
	private int numberOfRecordsPerPartition = 1000;

	/**
	 * The name of the SQL table the data are in.
	 *
	 * @param table
	 *            the name of the table
	 */
	public void setTable(String table) {
		this.table = table;
	}

	/**
	 * The name of the column to partition.
	 *
	 * @param column
	 *            the column name.
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * The data source for connecting to the database.
	 *
	 * @param dataSource
	 *            a {@link DataSource}
	 */
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {

		int count = jdbcTemplate.queryForObject(partitionerQuery, Integer.class);

		Map<String, ExecutionContext> result = new HashMap<String, ExecutionContext>();
		int number = 0;
		int startRowNumber = 1;
		int endRowNumber = startRowNumber + numberOfRecordsPerPartition - 1;

		while (startRowNumber <= count) {
			ExecutionContext value = new ExecutionContext();
			result.put("partition" + number, value);
			if (endRowNumber >= count) {
				endRowNumber = count;
			}
			value.putInt("startRowNumber", startRowNumber);
			value.putInt("endRowNumber", endRowNumber);
			startRowNumber += numberOfRecordsPerPartition;
			endRowNumber += numberOfRecordsPerPartition;
			number++;
		}

		return result;
	}

}