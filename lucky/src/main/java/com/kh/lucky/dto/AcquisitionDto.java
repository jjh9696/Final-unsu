package com.kh.lucky.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AcquisitionDto {
	private int acquisitionNo;
	private String acquisitionItem;
	private String acquisitionLocation1;
	private String acquisitionLoacation2;
	private String acquisitionDate;
	private String acquisitionStorage;
	private String acquisitionStatus;
}
