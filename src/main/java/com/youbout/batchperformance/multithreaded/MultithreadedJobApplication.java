package com.youbout.batchperformance.multithreaded;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.youbout.batchperformance.domain.CSVColumns;
import com.youbout.batchperformance.util.DateUtil;

/**
 * @author Michael Minella
 */
@EnableBatchProcessing
@SpringBootApplication
public class MultithreadedJobApplication {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	@StepScope
	public FlatFileItemReader<CSVColumns> fileTransactionReader(
			@Value("#{jobParameters['inputFlatFile']}") String resource) {
	
			return new FlatFileItemReaderBuilder<CSVColumns>().saveState(false).resource(new PathResource(resource))
					.delimited()
					.names(new String[] { "Region", "Country", "Item Type", "Sales Channel", "Order Priority",
							"Order Date", "Order ID", "Ship Date", "Units Sold", "Unit Price", "Unit Cost",
							"Total Revenue", "Total Cost", "Total Profit" })
					.fieldSetMapper(fieldSet -> {
						CSVColumns csvColumns = new CSVColumns();
						csvColumns.setRegion(fieldSet.readString("Region"));
						csvColumns.setCountry(fieldSet.readString("Country"));
						csvColumns.setItemType(fieldSet.readString("Item Type"));
						csvColumns.setSalesChannel(fieldSet.readString("Sales Channel"));
						csvColumns.setOrderPriority(fieldSet.readString("Order Priority"));
						csvColumns.setOrderDate(fieldSet.readString("Order Date"));
						csvColumns.setShipDate(fieldSet.readString("Ship Date"));
						csvColumns.setUnitPrice(fieldSet.readString("Unit Price"));
						csvColumns.setUnitCost(fieldSet.readString("Unit Cost"));
						csvColumns.setTotalRevenue(fieldSet.readString("Total Revenue"));
						csvColumns.setTotalCost(fieldSet.readString("Total Cost"));
						csvColumns.setTotalProfit(fieldSet.readString("Total Profit"));
						csvColumns.setOrderId(fieldSet.readString("Order ID"));
						csvColumns.setUnitsSold(fieldSet.readString("Units Sold"));
						return csvColumns;
					}).build();
		
	}

	@Bean
	@StepScope
	public JdbcBatchItemWriter<CSVColumns> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<CSVColumns>().dataSource(dataSource).beanMapped()
				.sql("INSERT INTO csvdata\r\n"
						+ "(region, country, item_type, sales_channel, order_priority, order_date, orderid, ship_date, units_sold, unit_price, unit_cost, total_revenue, total_cost, total_profit)\r\n"
						+ "VALUES(:region, :country, :itemType, :salesChannel, :orderPriority, :orderDate, :orderId, :shipDate, :unitsSold, :unitPrice, :unitCost, :totalRevenue, :totalCost, :totalProfit)")
				.build();
	}

	@Bean
	public Job multithreadedJob() {
		return this.jobBuilderFactory.get("multithreadedCSVJob3").start(step1()).build();
	}

	@Bean
	public Step step1() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(4);
		taskExecutor.setMaxPoolSize(4);
		taskExecutor.afterPropertiesSet();

		return this.stepBuilderFactory.get("step1").<CSVColumns, CSVColumns>chunk(100)
				.reader(fileTransactionReader(null)).writer(writer(null)).taskExecutor(taskExecutor).build();
	}

	public static void main(String[] args) throws Exception {
		String[] newArgs = new String[] {
				"inputFlatFile = D:/Projects/Carrating/Workspace/SplitCSVFile/resource/output/CSVPart_1.csv" };

		SpringApplication.run(MultithreadedJobApplication.class, newArgs);

	}
}
