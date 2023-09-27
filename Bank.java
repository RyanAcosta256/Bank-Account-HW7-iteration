import java.util.ArrayList;
import java.util.Calendar;

public class Bank {
	private static double totalAmountInSavingsAccts = 0.0;
	private static double totalAmountInCheckingAccts = 0.0;
	private static double totalAmountInCDAccts = 0.0;
	private static double totalAmountInAllAccts = 0.0; 
	private ArrayList<Account> arrayAcct;
	//private int numAccts;

	public Bank() {
		arrayAcct = new ArrayList<>();
		totalAmountInSavingsAccts = 0.0;
		totalAmountInCheckingAccts = 0.0;
		totalAmountInCDAccts = 0.0;
		totalAmountInAllAccts = 0.0; 
	}

	//static methods
	public void addToTotalSavingsAcctAmnt(double amnt) {
		totalAmountInSavingsAccts+= amnt;
	}
	
	public void removeFromTotalSavingsAcctAmnt(double amnt) {
		totalAmountInSavingsAccts-= amnt;
	}
	
	public void addToTotalCheckingsAcctAmnt(double amnt) {
		totalAmountInSavingsAccts+= amnt;
	}
	
	public void removeFromTotalCheckingsAcctAmnt(double amnt) {
		totalAmountInSavingsAccts-= amnt;
	}
	
	public void addToTotalCDAcctAmnt(double amnt) {
		totalAmountInCDAccts+=amnt;
	}
	
	public void removeFromTotalCDAcctAmnt(double amnt) {
		totalAmountInCDAccts-=amnt;
	}
	
	public void addToTotalAmntInAllAccts(double amnt) {
		totalAmountInAllAccts+=amnt;
	}
	
	public void removeFromTotalAmntInAllAccts(double amnt) {
		totalAmountInAllAccts-=amnt;
	}
	
	public TransactionReceipt getBalance(TransactionTicket ticket) {
		//Calendar cal = Calendar.getInstance();
		 int index = findAcct(ticket.getAcctNumber());
		if (index == -1) {
			TransactionReceipt result = new TransactionReceipt(ticket, false,
					String.format("Error: Account number %d doesn't exist", ticket.getAcctNumber()), 
					0.0, 0.0, // pre and post
					null);
			return result;
		}
		if (arrayAcct.get(index).getAccountStatus().equals("Closed")){
			TransactionReceipt result = new TransactionReceipt(ticket, false,
					("Error: Account "+ ticket.getAcctNumber() + " is closed, no transactions are allowed at this time"), 
					0.0, 0.0, // pre and post
					null);
			return result;
		} 
			 return arrayAcct.get(index).getBalance(ticket);

	}

	public TransactionReceipt makeDeposit(TransactionTicket ticket) {
	    int index = findAcct(ticket.getAcctNumber());
		if (index == -1) {
			TransactionReceipt result = new TransactionReceipt(ticket, false,
					String.format("Error: Account number %d doesn't exist", ticket.getAcctNumber()), 
					0.0, 0.0, // pre and post
					null);
			return result;
		}
		if (arrayAcct.get(index).getAccountStatus().equals("Closed")){
			TransactionReceipt result = new TransactionReceipt(ticket, false,
					("Error: Account "+ ticket.getAcctNumber() + " is closed, no transactions are allowed at this time"), 
					0.0, 0.0, // pre and post
					null);
			return result;
		}		

			// int index = findAcct(ticket.getAcctNumber());
			return arrayAcct.get(index).makeDeposit(ticket);
		}

	public TransactionReceipt makeWithdrawal(TransactionTicket ticket) {
		 int index = findAcct(ticket.getAcctNumber());
		if (index == -1) {//invalid acct
			return new TransactionReceipt(ticket, false,
					String.format("Error: Account number %d doesn't exist", ticket.getAcctNumber()), 
					0.0, 0.0, // pre and post
					null);
			//return result;
		} 
		if (arrayAcct.get(index).getAccountStatus().equals("Closed")){//invalid b/c closed
			TransactionReceipt result = new TransactionReceipt(ticket, false,
					("Error: Account "+ ticket.getAcctNumber() + " is closed, no transactions are allowed at this time"), 
					0.0, 0.0, // pre and post
					null);
			return result;
		}
		return arrayAcct.get(index).makeWithdrawal(ticket);
	}

	public TransactionReceipt clearCheck(Check check) {
		int index = findAcct(check.getAcctNumber());
		if (index == -1) {//invalid acct
			TransactionReceipt result = new TransactionReceipt(null, false,
					String.format("Error: Account number %d doesn't exist", check.getAcctNumber()), 
					0.0, 0.0, // pre and post
					null);
			return result;
		}
		if (arrayAcct.get(index).getAccountStatus().equals("Closed")){ //invalid b/c closed
			TransactionReceipt result = new TransactionReceipt(null, false,
					("Error: Account "+ check.getAcctNumber() + " is closed, no transactions are allowed at this time"), 
					0.0, 0.0, // pre and post
					null);
			return result;
		}	
			return arrayAcct.get(index).clearCheck(check);

	}

	public TransactionReceipt closeAcct(TransactionTicket ticket) {
		int index = findAcct(ticket.getAcctNumber());
		//public Account(Depositor d, int num, String type, double bal, String matDate)
		
		if (index == -1) { // invalid 
			TransactionReceipt result = new TransactionReceipt(ticket, false,
					String.format("Error: Account number %d exist, please try using a different account number\n", ticket.getAcctNumber()), 
					0.0, 0.0, // pre and post
					null);
			return result;
		}else {
			return arrayAcct.get(index).closeAcct(ticket);
		}
	}
	
	public TransactionReceipt reOpenAcct(TransactionTicket ticket) {
		int index = findAcct(ticket.getAcctNumber());
		//public Account(Depositor d, int num, String type, double bal, String matDate)
		
		if (index == -1) { //invalid
			TransactionReceipt result = new TransactionReceipt(ticket, false,
					String.format("Error: Account number %d exist, please try using a different account number\n", ticket.getAcctNumber()), 
					0.0, 0.0, // pre and post
					null);
			return result;
		}else {
			return arrayAcct.get(index).reOpenAcct(ticket);
		}
	}

	public TransactionReceipt openNewAcct(TransactionTicket ticket, Depositor dep, String acctType) { //ticket gets acctNum, balance, date plus termOfCD(if needed), depositor is to just get name and SSN 
		int index = findAcct(ticket.getAcctNumber());

		if (index != -1) {//Guard class //if code runs into criteria, do x
			return new TransactionReceipt(ticket, false,
						String.format("Error: Account number %d exist, please try using a different account number\n", ticket.getAcctNumber()), 
						0.0, 0.0, // pre and post
						null);
		}
		if(acctType.equals("CD")) {
			if(ticket.constructorCount == 6) {
				Calendar maturityDateFromInfo = ticket.getCDDate();
				String strMatDate = setMaturityDateString(maturityDateFromInfo);
				arrayAcct.add(new CDAccount(dep, ticket.getAcctNumber(),acctType,ticket.getTransactionAmount(),strMatDate, "Open" ));
				TransactionReceipt result = new TransactionReceipt(ticket, 
						true,
						String.format("Account type: %s\nAccount amount: $%.2f\nMaturity Date: %s\n",getAcct(arrayAcct.size()-1).getAcctType(), getAcct(arrayAcct.size()-1).getAcctBalance(),strMatDate), 
						 0.0,getAcct(arrayAcct.size()-1).getAcctBalance(), // pre and post
						null);
				arrayAcct.get(arrayAcct.size()-1).addTransaction(result);
				return result;
			}
			else {
				Calendar maturityDateFromInfo = ticket.getDateOfTransaction();
				
				maturityDateFromInfo.add(Calendar.MONTH, ticket.getTermOfCD());
				String strMatDate = setMaturityDateString(maturityDateFromInfo);
				
				arrayAcct.add(new CDAccount(dep, ticket.getAcctNumber(),acctType,ticket.getTransactionAmount(),strMatDate, "Open" ));
				TransactionReceipt result = new TransactionReceipt(ticket, 
						true,
						String.format("Account type: %s\nAccount amount: $%.2f\nMaturity Date: %s\n",getAcct(arrayAcct.size()-1).getAcctType(), getAcct(arrayAcct.size()-1).getAcctBalance(),strMatDate), 
						 0.0,getAcct(arrayAcct.size()-1).getAcctBalance(), // pre and post
						null);
				arrayAcct.get(arrayAcct.size()-1).addTransaction(result);
				return result;
			}
			
		}
		else if(acctType.equals("Savings")){			
				arrayAcct.add(new SavingsAccount(dep, ticket.getAcctNumber(),acctType,ticket.getTransactionAmount(), "Open"  ));
					TransactionReceipt result = new TransactionReceipt(ticket, 
							true,
							String.format("Account type: %s\nAccount amount: $%.2f\n",getAcct(arrayAcct.size()-1).getAcctType(), getAcct(arrayAcct.size()-1).getAcctBalance()), 
							 0.0,getAcct(arrayAcct.size()-1).getAcctBalance(), // pre and post
							 null);
					arrayAcct.get(arrayAcct.size()-1).addTransaction(result);
					return result;
			}
			else {
				arrayAcct.add(new CheckingAccount(dep, ticket.getAcctNumber(),acctType,ticket.getTransactionAmount(), "Open"  ));
					
			
			TransactionReceipt result = new TransactionReceipt(ticket, 
					true,
					String.format("Account type: %s\nAccount amount: $%.2f\n",getAcct(arrayAcct.size()-1).getAcctType(), getAcct(arrayAcct.size()-1).getAcctBalance()), 
					 0.0,getAcct(arrayAcct.size()-1).getAcctBalance(), // pre and post
					 null);
			arrayAcct.get(arrayAcct.size()-1).addTransaction(result);
			return result;
		}

	}
	
//try using the TransactionTicket flags or improving condition length
	public TransactionReceipt deleteAcct(TransactionTicket ticket) {
		
		int index = findAcct(ticket.getAcctNumber());

		if (index == -1) // invalid account
		{
			TransactionReceipt result = new TransactionReceipt(ticket, false,
					String.format("Error: Account number %d doesn't exist", ticket.getAcctNumber()), 
					0.0, 0.0, // pre and post
					null);
			return result;

		}//subject to change, awaiting feedback
		else if (getAcct(index).getAccountStatus().equals("Closed")){ //valid account that is closed will bypass all the normal delete conditions
			arrayAcct.remove(index);	
			TransactionReceipt result = new TransactionReceipt(ticket, true,
						String.format("Account number: %d successfully deleted", ticket.getAcctNumber()), 
						0.0, 0.0, // pre and post
						null);
				return result;
			}
		
		else// valid account
		{
			if (arrayAcct.get(index).getAcctBalance() > 0) { // balanceArray[index] //Doesn't delete because acct still has
															// a
															// balance
				TransactionReceipt result = new TransactionReceipt(ticket, false, String.format(
						"Error: Your account, %d, can't be deleted at this time because you still have money in your account \n",
						ticket.getAcctNumber()), 
						arrayAcct.get(index).getAcctBalance(), arrayAcct.get(index).getAcctBalance(), // pre and post
						null);
				return result;
			} 
			else { // can delete an acct because no balance

				arrayAcct.remove(index);
				TransactionReceipt result = new TransactionReceipt(ticket, true,
						String.format("Account number: %d successfully deleted", ticket.getAcctNumber()), 
						0.0, 0.0,  //pre and post
						null);
				return result;
			}
		}
}
//to be used in the main method's read accts
	
	//To make 
	

	public void addAcct(Account newAcct) {
		arrayAcct.add(newAcct);
	}

		
	public int findAcct(int requestedAccount) {
		for (int index = 0; index < arrayAcct.size(); index++)
			if ((arrayAcct.get(index).getAcctNumber() == requestedAccount))//&& (arrayAcct.get(index).getAcctType().equalsIgnoreCase()))
				return index;
		return -1;
	}

// getters
	//we want to return the copy of an account 
	//The error of non-updated outputs (the balance, account status and maturity date are fixed if I don't use the commented out body portion
	public Account getAcct(int index) {
		Account instanceIndex = arrayAcct.get(index);
		if(instanceIndex instanceof CDAccount) {
			return new CDAccount((CDAccount)arrayAcct.get(index));		
		}
		else if(instanceIndex instanceof CheckingAccount) {
			return new CheckingAccount((CheckingAccount)arrayAcct.get(index));
		}
		else if(instanceIndex instanceof SavingsAccount) {
			return new SavingsAccount((SavingsAccount)arrayAcct.get(index));
		}
		else {
			return null; //this was added because without it, the method would have an error
		}
		
		
		//return new Account(arrayAcct.get(index));
		
	}

	public int getNumAccts() {
		return arrayAcct.size();
	}
	
	
	public String setMaturityDateString(Calendar cal) {
		String str;
		if(cal== null) {
			str = " ";
		}
		else {
			str = String.format("%02d/%02d/%4d",
					cal.get(Calendar.MONTH) + 1,
					cal.get(Calendar.DAY_OF_MONTH),
					cal.get(Calendar.YEAR)
								);
		}
		return str;
		
	}
	
	
}
