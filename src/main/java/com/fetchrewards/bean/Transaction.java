package com.fetchrewards.bean;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class Transaction {
	
	private String payer;
	private int points;
	private String timestamp;

}
