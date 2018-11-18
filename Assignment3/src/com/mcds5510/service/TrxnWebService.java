package com.mcds5510.service;
import com.mcds5510.entity.Transaction;

import java.rmi.RemoteException;

import com.mcds5510.dao.MySQLAccess;
public class TrxnWebService {
	public static MySQLAccess dao = new MySQLAccess();
	
	public Transaction getTransaction(int trxnID) {
		return dao.getTransaction(trxnID) ;
	}
	public boolean createTransaction(Transaction trxn) {
		return dao.createTransaction(trxn);
	}
	public boolean updateTransaction(Transaction trxn) {
		return dao.updateTransaction(trxn);
	}
	public boolean removeTransaction(int trxnID) {
		return dao.removeTransaction(trxnID);
	}
	public void readDataBase() throws Exception {
		dao.readDataBase();
	}

}
