package com.kh.lucky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @Builder @NoArgsConstructor
public class RequestChargeVO {
	  private String chargeType;
	  private int routeNo;
	  private int count;
	  private int paymentNo;
}
