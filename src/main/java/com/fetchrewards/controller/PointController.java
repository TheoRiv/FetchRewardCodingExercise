package com.fetchrewards.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fetchrewards.bean.Payer;
import com.fetchrewards.bean.PayerPoints;
import com.fetchrewards.bean.Transaction;
import com.fetchrewards.service.AddTransaction;
import com.fetchrewards.service.SpendPoints;

@RestController
public class PointController {
	
	 DateTimeFormatter formatter =  DateTimeFormatter.ISO_DATE_TIME;
	 private static Logger LOGGER = LoggerFactory.getLogger(PointController.class);
	
	 //Call to return the list of payers and their point balance
	@RequestMapping("/getbalancepoints")
	public List<PayerPoints> getBalancePoints() {
		return getPayersPoints();
	}
	
	//Call to add a transaction to an existing payer. If the payer doesn't have enough points, or don't exist, return null
	@RequestMapping(value="/addtransaction", method = RequestMethod.POST)
	public Transaction addTransaction(@ModelAttribute("payerName") String payerName, @ModelAttribute("transactionPoints") int points) {
		AddTransaction addNewtransaction = new AddTransaction();
		Transaction transaction = null;
		try {
			transaction = addNewtransaction.addTransaction(payerName, points, LocalDateTime.now().format(formatter), getPayers());
			if(transaction == null)
				LOGGER.info("Check if the payer exist and/or if the balance is higher than the points removed");
		} catch (Exception e){
			LOGGER.info(e.getMessage());
		}
		return transaction;
	}
	
	//Call to spend points using the payers' balance. If the payers don't have enough points, return empty list.
	@RequestMapping(value ="/spendpoints", method = RequestMethod.POST)
	public  List<PayerPoints> spendPointsPerOldest(@ModelAttribute("spendPointsValue") int points) {
		List<PayerPoints> listPayerPoints = new ArrayList<>();
		try {
			List<Payer> listPayer = getPayers();
			SpendPoints spend = new SpendPoints();
			listPayerPoints = spend.spendPoints(points, listPayer);
			if(listPayerPoints.isEmpty())
				LOGGER.info("Enter a lower value, the payers don't have enough points");
		}	catch (Exception e){
			LOGGER.info(e.getMessage());
		}
		return listPayerPoints;
	}
	

	public List<PayerPoints> getPayersPoints() {
		return getPointsBalance(getPayers());
	}
	
	//Method to initialize a list of payer. We consider that the payers have to be manually added
	public List<Payer> getPayers() {
		List<Payer> listPayer = new ArrayList<Payer>();
		AddTransaction transaction = new AddTransaction();
		Payer payer1 = new Payer();
		payer1.setPayerName("DANNON");
		listPayer.add(payer1);
		Payer payer2 = new Payer();
		payer2.setPayerName("UNILEVER");
		listPayer.add(payer2);
		Payer payer3 = new Payer();
		payer3.setPayerName("MILLER COORS");
		listPayer.add(payer3);
		transaction.addTransaction("DANNON", 1000, LocalDateTime.parse("2020-11-02T14:00:00").format(formatter), listPayer);
		transaction.addTransaction("UNILEVER", 200, LocalDateTime.parse("2020-10-31T11:00:00").format(formatter), listPayer);
		transaction.addTransaction("DANNON", -200, LocalDateTime.parse("2020-10-31T15:00:00").format(formatter), listPayer);
		transaction.addTransaction("MILLER COORS", 10000, LocalDateTime.parse("2020-11-01T14:00:00").format(formatter), listPayer);
		transaction.addTransaction("DANNON", 300, LocalDateTime.parse("2020-10-31T10:00:00").format(formatter), listPayer);
//		SpendPoints spend = new SpendPoints();
//		spend.spendPoints(5000, listPayer);
		return listPayer;
	}
	
	
	//Method to return the list of payers and their point balance
	public List<PayerPoints> getPointsBalance(List<Payer> listPayer) {
		List<PayerPoints> listBalance = new ArrayList<>();
		for(Payer i : listPayer) {
			PayerPoints payerpoints = new PayerPoints(i.getPayerName(), i.getPoints());
			listBalance.add(payerpoints);
		}
		return listBalance;
	}

}
