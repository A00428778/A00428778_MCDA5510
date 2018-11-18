package com.mcds5510;

import java.rmi.RemoteException;

import com.mcds5510.entity.Transaction;
import com.mcds5510.service.TrxnWebServiceProxy;

public class TestClient {

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		TrxnWebServiceProxy proxy = new TrxnWebServiceProxy();
        //proxy.setEndpoint("http://localhost:8080/Assignment3/services/TrxnWebService");//defined in wsdl
	proxy.setEndpoint("http://dev.cs.smu.ca:8555/Assignment3/services/TrxnWebService");

        try {

			proxy.readDataBase();
			Transaction trxn = proxy.getTransaction(1023);
			System.out.println(trxn.getNameOnCard());

			proxy.removeTransaction(1024);

			Transaction trns = new Transaction();
			trns.setID(1023);
			trns.setNameOnCard("wjs111");
			trns.setCardNumber("88888899");
			trns.setUnitPrice(8.8);
			trns.setQuantity(100);
			trns.setTotalPrice(10000.00);
			trns.setExpDate("2019/01/01");
			//trns.setCreatedOn(null);
			trns.setCreatedBy("wjs");
			proxy.updateTransaction(trns);

			Transaction trns2 = new Transaction();
			trns2.setID(103888);
			trns2.setNameOnCard("wjs111");
			trns2.setCardNumber("88888899");
			trns2.setUnitPrice(8.8);
			trns2.setQuantity(100);
			trns2.setTotalPrice(10000.00);
			trns2.setExpDate("2019/01/01");
			trns2.setCreatedOn(null);
			trns2.setCreatedBy("wjs");
			proxy.createTransaction(trns2);
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
