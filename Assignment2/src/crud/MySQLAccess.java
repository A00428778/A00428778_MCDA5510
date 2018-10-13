package crud;

import java.io.File;

/**
 * Source code sample from 
 * http://www.vogella.com/tutorials/MySQLJava/article.html
 * 
 **/

import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Date;
import crud.Transaction;

import java.util.logging.*;

public class MySQLAccess {

	private static Connection connect = null;
	private Statement statement = null;
	//	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private static final Logger logger = Logger.getLogger(MySQLAccess.class.getName());

	// construct method to run logging
	MySQLAccess() {
		try {
			String cc = System.getProperty("user.dir");
			cc = cc+File.separator+"logs"+File.separator+"logs";

			FileHandler fh = new FileHandler(cc, true);

			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Connection getConnect() throws Exception {

		try {
			if (connect == null) {
				// This will load the MySQL driver, each DB has its own driver
				// Class.forName("com.mysql.jdbc.Driver");
				Class.forName("com.mysql.cj.jdbc.Driver");
				// Setup the connection with the DB
				connect = DriverManager.getConnection("jdbc:mysql://localhost/transactions?" // DTP I spelled
						// transactions wrong
						// oops
						+ "user=root&password=1023" // Creds
						+ "&useSSL=false&allowPublicKeyRetrieval=true" // b/c localhost
						+ "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"); // timezone
			}
			return connect;
		} catch (Exception e) {
			throw e;
		}

	}

	public static void main(String[] args) {
		MySQLAccess dao = new MySQLAccess();
		try {
			dao.readDataBase();
			Transaction trxn = dao.getTransaction(1023);
			System.out.println(trxn.getNameOnCard());

			logger.info("create by creadia");

			dao.removeTransaction(1024);

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
			dao.updateTransaction(trns);

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
			dao.createTransaction(trns2);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.close();
		}
	}

	public void testUPdateTransaction() {
		Transaction trans = new Transaction();

	}

	public Transaction getTransaction(Integer trxnID) {
		Transaction trxn = new Transaction();
		Statement stmt = null;
		try {
			try {
				stmt = this.getConnect().createStatement();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ResultSet rs = stmt.executeQuery("SELECT * FROM transactions.transaction WHERE ID=" + trxnID);
			if (rs.next()) {
				trxn.setID(rs.getInt("ID"));
				trxn.setCardNumber(rs.getString("CardNumber"));
				trxn.setNameOnCard(rs.getString("NameOnCard"));
				trxn.setUnitPrice(rs.getDouble("UnitPrice"));
				trxn.setQuantity(rs.getInt("Quantity"));
				trxn.setTotalPrice(rs.getDouble("TotalPrice"));
				trxn.setExpDate(rs.getString("ExpDate"));
				trxn.setCreatedOn(rs.getString("CreatedOn"));
				trxn.setCreatedBy(rs.getString("CreatedBy"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return trxn;
	}

	public boolean checkWhetherIdExists(int id) {
		Statement stmt = null;
		try {
			stmt = this.getConnect().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM transactions.transaction WHERE ID=" + id);
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public boolean createTransaction(Transaction trxn) {
		String SQL = "INSERT INTO `transaction` (ID,NameOnCard,CardNumber,UnitPrice,Quantity,TotalPrice,ExpDate,CreatedOn,CreatedBy) "
				+ " VALUES(?,?,?,?,?,?,?,?,?) ";
		try {
			int id = trxn.getID();
			String mun = trxn.getNameOnCard();
			String cnum = trxn.getCardNumber();
			String exp = trxn.getExpDate();
			String createdby = trxn.getCreatedBy();
			if (checkWhetherIdExists(id)) {
				//  fails if ID exists in DB and prompts user to use update 
				System.out.println("you should not use create but to use update transaction instead,id already exists");
				throw new Exception("ID Exists");	
			}
			if(!validateSpecialChar(mun)){
				System.out.println("it includes special character");}
				if(!validateSpecialChar(cnum)){
					System.out.println("it includes special character");}
					if(!validateSpecialChar(exp)){
						System.out.println("it includes special character");}
						if(!validateSpecialChar(createdby)){
							System.out.println("it includes special character");}
							double uni = trxn.getUnitPrice();
							int quan = trxn.getQuantity();
							double total = trxn.getTotalPrice();
							String createdon = trxn.getCreatedOn();
							PreparedStatement pstmt = this.getConnect().prepareStatement(SQL);
							pstmt.setInt(1, id);
							pstmt.setString(2, mun);
							pstmt.setString(3, cnum);
							pstmt.setDouble(4, uni);
							pstmt.setInt(5, quan);
							pstmt.setDouble(6, total);
							pstmt.setString(7, exp);
							pstmt.setString(8,  createdon);
							pstmt.setString(9, createdby);
							pstmt.executeUpdate();
							logger.info("create a transaction");
							return true;
						}
					catch (Exception e) {
				e.printStackTrace();
				logger.info(e.getMessage());
				return false;
			} 

		}
		//		boolean res = false;
		//		return res



		public boolean updateTransaction(Transaction trxn) {
			//		String query = " insert into users (first_name, last_name, date_created, is_admin, num_points)"
			//		        + " values (?, ?, ?, ?, ?)";

			// TODO figure out why null content can't insert into table

			//		String SQL="INSERT INTO transaction(ID,NameOnCard,CardNumber,UnitPrice,Quantity,TotalPrice,ExpDate,CreatedOn,CreatedBy) "
			//				+" VALUES(5,\"dd\",\"d\",12,12,12,\"23/10/2018\",\"2018-09-30 00:00:00\",\"wjs\")";

			// USE replace will be better for handling duplicate ID
			String SQL = "REPLACE INTO `transaction` (ID,NameOnCard,CardNumber,UnitPrice,Quantity,TotalPrice,ExpDate,CreatedOn,CreatedBy) "
					+ " VALUES(?,?,?,?,?,?,?,?,?) ";
			try {
				int id = trxn.getID();

				// if id exists,continue;else,promote user to create transaction
				if (!checkWhetherIdExists(id)) {
					System.out.println(
							"you should not use update but to create a new transaction instead, because id not exists");
					throw new Exception("you should create a new transaction,id not exists");
				}

				String mun = trxn.getNameOnCard();
				String cnum = trxn.getCardNumber();
				double uni = trxn.getUnitPrice();
				int quan = trxn.getQuantity();
				double total = trxn.getTotalPrice();
				String exp = trxn.getExpDate();
				String createdon = trxn.getCreatedOn();
				String createdby = trxn.getCreatedBy();

				PreparedStatement pstmt = this.getConnect().prepareStatement(SQL);
				pstmt.setInt(1, id);
				pstmt.setString(2, mun);
				pstmt.setString(3, cnum);
				pstmt.setDouble(4, uni);
				pstmt.setInt(5, quan);
				pstmt.setDouble(6, total);
				pstmt.setString(7, exp);
				pstmt.setString(8, createdon);
				pstmt.setString(9, createdby);
				pstmt.executeUpdate();
				logger.info("update a transaction");
				return true;
			}

			catch (Exception e) {
				e.printStackTrace();
				logger.info(e.getMessage());
				return false;
			}

		}

		public boolean removeTransaction(int trxnID) {
			String SQL = String.format("delete from transaction where id = %d", trxnID);
			try {

				Statement statement = this.getConnect().createStatement();
				statement.executeUpdate(SQL);
				logger.info("remove a transaction");
				return true;
			}

			catch (Exception e) {
				e.printStackTrace();
				logger.info(e.getMessage());
				return false;

			}

			// Also you can check whether id exists and return result
			//		if(!checkWhetherIdExists(trxnID)) {
			//			return true;
			//		}else {
			//			return false;
			//		}

		}
		private static boolean validateSpecialChar(String var) {
			boolean vaild=true;

			if(var.contains(";")||var.contains(":")||var.contains("!")||var.contains("@")||
					var.contains("#")||var.contains("$")||var.contains("%")||var.contains("^")||
					var.contains("*")||var.contains("+")||var.contains("?")||var.contains("<")||
					var.contains(">")) { 
				vaild=false;
			}
			return vaild;
		}



		public void readDataBase() throws Exception {
			try {

				// Statements allow to issue SQL queries to the database
				statement = this.getConnect().createStatement();
				// Result set get the result of the SQL query
				String sqlStatement = "select * from transactions.transaction";
				resultSet = statement.executeQuery(sqlStatement);
				writeResultSet(resultSet);

				logger.info("read the database record " + sqlStatement);

				/**
				 * 
				 * // PreparedStatements can use variables and are more efficient
				 * preparedStatement = connect .prepareStatement("insert into feedback.comments
				 * values (default, ?, ?, ?, ? , ?, ?)"); // "myuser, webpage, datum, summary,
				 * COMMENTS from // feedback.comments"); // Parameters start with 1
				 * preparedStatement.setString(1, "Test"); preparedStatement.setString(2,
				 * "TestEmail"); preparedStatement.setString(3, "TestWebpage");
				 * preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
				 * preparedStatement.setString(5, "TestSummary"); preparedStatement.setString(6,
				 * "TestComment"); preparedStatement.executeUpdate();
				 * 
				 * preparedStatement = connect .prepareStatement("SELECT myuser, webpage, datum,
				 * summary, COMMENTS from feedback.comments"); resultSet =
				 * preparedStatement.executeQuery(); writeResultSet(resultSet);
				 * 
				 * // Remove again the insert comment preparedStatement =
				 * connect.prepareStatement("delete from feedback.comments where myuser= ? ; ");
				 * preparedStatement.setString(1, "Test"); preparedStatement.executeUpdate();
				 * 
				 * resultSet = statement.executeQuery("select * from feedback.comments");
				 * writeMetaData(resultSet);
				 **/
			} catch (Exception e) {
				throw e;
			} finally {
				// close();
			}

		}

		private void writeMetaData(ResultSet resultSet) throws SQLException {
			// Now get some metadata from the database
			// Result set get the result of the SQL query

			System.out.println("The columns in the table are: ");

			System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
			for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
				System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i));
			}
		}

		private void writeResultSet(ResultSet resultSet) throws SQLException {
			// ResultSet is initially before the first data set
			while (resultSet.next()) {
				// It is possible to get the columns via name
				// also possible to get the columns via the column number
				// which starts at 1
				// e.g. resultSet.getSTring(2);
				String ID = resultSet.getString("ID");
				String NameOnCard = resultSet.getString("NameOnCard");
				String CardNumber = resultSet.getString("CardNumber");
				String ExpDate = resultSet.getString("CardNumber");
				String UnitPrice = resultSet.getString("UnitPrice");
				String totalPrice = resultSet.getString("TotalPrice");
				Integer qty = resultSet.getInt("Quantity");
				String createdBy = resultSet.getString("CreatedBy");
				Date createdOn = resultSet.getDate("createdOn");
				System.out.println("ID: " + ID);
				System.out.println("NameOnCard: " + NameOnCard);
				System.out.println("CardNumber: " + CardNumber);
				System.out.println("ExpDate: " + ExpDate);
				System.out.println("UnitPrice: " + UnitPrice);
				System.out.println("Qty: " + qty);
				System.out.println("TotalPrice: " + totalPrice);
				System.out.println("CreatedOn: " + createdOn);
				System.out.println("CreatedBy: " + createdBy);
			}
		}


		// You need to close the resultSet
		private void close() {
			try {
				if (resultSet != null) {
					resultSet.close();
				}

				if (statement != null) {
					statement.close();
				}

				if (connect != null) {
					connect.close();
				}
			} catch (Exception e) {

			}
		}

	}
