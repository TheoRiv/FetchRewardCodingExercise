package com.fetchrewards.service;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.fetchrewards.bean.Payer;
import com.fetchrewards.bean.Transaction;



@Service
@Configuration
public class AddTransaction {
	
	public Transaction addTransaction(String payerName, int points, String timeStamp, List<Payer> listPayer) {
		Payer payer = getPayer(payerName, listPayer);
		int balance = payer.getPoints() + points;
		if(balance >= 0) {
			Transaction transaction = new Transaction(payerName, points, timeStamp);
			payer.setPoints(balance);
			payer.getListTransaction().add(transaction);
			return transaction;
		}
		else {
			return null;
		}
	}
	
	public Payer getPayer(String payerName, List<Payer> listPayer) {
		Payer payer = null;
		for(Payer i : listPayer) {
			if(i.getPayerName().equals(payerName)) {
				payer = i;
			}
		}
		return payer;
	}
}
