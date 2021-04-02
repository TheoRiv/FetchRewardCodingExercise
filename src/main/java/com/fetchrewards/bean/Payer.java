package com.fetchrewards.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Payer {

	private String payerName;
	private int points;
	List<Transaction> listTransaction = new ArrayList<>();

}
