package com.youbout.batchperformance.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.youbout.batchperformance.TransactionVO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@ToString
public class CSVColumns {
	
	private String region;
	private String country;
	private String itemType;
	private String salesChannel;
	private String orderPriority;
	private String orderDate;
	private String orderId;
	private String shipDate;
	private String unitsSold;
	private String unitPrice;
	private String unitCost;
	private String totalRevenue;
	private String totalCost;
	private String totalProfit;
    

}
