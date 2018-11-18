/**
 * TrxnWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mcds5510.service;

public interface TrxnWebService extends java.rmi.Remote {
    public boolean removeTransaction(int trxnID) throws java.rmi.RemoteException;
    public boolean updateTransaction(com.mcds5510.entity.Transaction trxn) throws java.rmi.RemoteException;
    public boolean createTransaction(com.mcds5510.entity.Transaction trxn) throws java.rmi.RemoteException;
    public void readDataBase() throws java.rmi.RemoteException;
    public com.mcds5510.entity.Transaction getTransaction(int trxnID) throws java.rmi.RemoteException;
}
