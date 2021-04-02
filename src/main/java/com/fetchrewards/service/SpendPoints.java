package com.fetchrewards.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.fetchrewards.bean.Payer;
import com.fetchrewards.bean.PayerPoints;
import com.fetchrewards.bean.Transaction;

@Service
@Configuration
public class SpendPoints {

	public List<PayerPoints> spendPoints(int points, List<Payer> listPayer) {
		List<PayerPoints> listPayerPoints = new ArrayList<>();
		if (points<getTotalBalancePoints(listPayer)) {
			List<Transaction> tempList = new ArrayList<>();
			points = -1 * Math.abs(points);
			for(Payer p : listPayer) {
				for(Transaction t : p.getListTransaction()) {
					tempList.add(t);
				}
			}
			
			while (points!=0){
				String oldestDate = null;
				for(Transaction t : tempList) {
					if(t.getPoints()!=0) {
						oldestDate = t.getTimestamp();
						break;
					}
				}
				for(Transaction t : tempList) {
					if(LocalDateTime.parse(t.getTimestamp()).isBefore(LocalDateTime.parse(oldestDate)) && t.getPoints()!=0)
						oldestDate = t.getTimestamp();
				}
				for(int i=0; i<tempList.size(); i++) {
					if(tempList.get(i).getTimestamp().equals(oldestDate)) {
						if(tempList.get(i).getPoints()+points > 0) {
							tempList.get(i).setPoints(tempList.get(i).getPoints()+points);
							points = 0;
						}
						else {
							points = points + tempList.get(i).getPoints();
							tempList.get(i).setPoints(0);
						}
					}
				}
			}
			for(Payer p : listPayer) {
				int balance = 0;
				int originalValue = p.getPoints();
				for(Transaction t : tempList) {
					if(t.getPayer().equals(p.getPayerName())) {
						balance = balance + t.getPoints();
						p.setPoints(balance);
					}
				}
				PayerPoints payerPoints = new PayerPoints(p.getPayerName(), p.getPoints()-originalValue);
				listPayerPoints.add(payerPoints);
			}
		}
		return listPayerPoints;
	}
	
	public int getTotalBalancePoints(List<Payer> listPayer) {
		int balance =0;
		for(Payer i : listPayer) {
			balance = balance + i.getPoints();
		}
		return balance;
		
	}
}
