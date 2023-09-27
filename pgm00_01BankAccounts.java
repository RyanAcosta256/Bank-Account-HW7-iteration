import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.StringTokenizer; 

public class pgm00_01BankAccounts {

	public static void main(String[] args) throws IOException {
		// constant definitions
		//final int MAX_NUM = 50;  redundant due to arraylist being dynamic
		
		Bank bankAccounts = new Bank(); // the array is found in the ban
		
		// variable declarations
		// initializes the array class
		

		//int numAccts; // number of accounts
		char choice; // menu item selected
		boolean notDone = true; // loop control flag

		// File("/bc/cisc3115/pgms/chapter_00/prj00_01BankAccounts/myTestCases.txt");
		File testFile = new File("myTestCases.txt");

		// create Scanner object
		Scanner kybd = new Scanner(testFile);
		// Scanner kybd = new Scanner(System.in);

		
		// PrintWriter("/bc/cisc3115/pgms/chapter_00/prj00_01BankAccounts/pgmOutput.txt");
		PrintWriter outFile = new PrintWriter("pgmOutput.txt");
		// PrintWriter outFile = new PrintWriter(System.out);

		
		 readAccts(bankAccounts);
		//printAccts(bankAccounts, numAccts, outFile);
		printAccts(bankAccounts, outFile);

		/* second part */
		/* prompts for a transaction and then */
		/* call functions to process the requested transaction */
		do {
			menu();
			choice = kybd.next().charAt(0);
			switch (choice) {
			case 'q':
			case 'Q':
				notDone = false;
				printAccts(bankAccounts, outFile);
				break;
				
			case 'b':
			case 'B':
				balance(bankAccounts, outFile, kybd);
				break;
					
			case 'i':
			case 'I':
				accountInfo(bankAccounts, outFile, kybd);
				break;
			//new options	
			case 'h':
			case 'H':
				acctInfoHistory(bankAccounts, outFile, kybd);
				break;
				
			case 's':
			case 'S':
				closeAcct(bankAccounts, outFile, kybd);
				break;	
				
			case 'r':
			case 'R':
				reOpenAcct(bankAccounts, outFile, kybd);
				break;		
		
			case 'd':
			case 'D':
				deposit(bankAccounts, outFile, kybd);
				break;
				
			case 'c':
			case 'C':
				clearCheck(bankAccounts, outFile, kybd);
				break;
				
			case 'w':
			case 'W':
				withdrawal(bankAccounts, outFile, kybd);
				break;
				
			case 'n':
			case 'N':
				newAcct(bankAccounts, outFile, kybd);
				break;
				
			case 'x':
			case 'X':
				deleteAcct(bankAccounts, outFile, kybd);
				break;
				
			default:
				outFile.println("Error: " + choice + " is an invalid selection -  try again");
				outFile.println();
				outFile.flush();
				break;
			}
		
		} while (notDone);

		// close the output file
		outFile.close();

		// close the test cases input file
		kybd.close();

		System.out.println();
		System.out.println("The program is terminating");
	}

	
	
	
	/*
	 * Method readAccts() Input: acctNumArray - reference to array of account
	 * numbers balanceArray - reference to array of account balances
	 * 
	 * Process: Reads the initial database of accounts and balances
	 * 
	 * Output: Fills in the initial account and balance arrays and returns the
	 * number of active accounts
	 */
	public static void readAccts(Bank bankAccounts) throws IOException {
		// File("/bc/cisc3115/pgms/chapter_00/prj00_01BankAccounts/initAccounts.txt");
		File dbFile = new File("initAccounts.txt");

		// create Scanner object
		Scanner sc = new Scanner(dbFile);
		while (sc.hasNext() ) {
			String line;
			// the class that ties them all together
			
			// read next line of data
			line = sc.nextLine();
			// StringTokenizer myLine = new StringTokenizer(line);
			String[] tkns = line.split(" ");

			// extract the data from the line read
			Name name = new Name(tkns[0], // myLine.nextToken(),
					tkns[1]); // myLine.nextToken());

			Depositor depositor = new Depositor(name, Integer.parseInt(tkns[2])); //SSN
			//Bam Nat 111222333 121234 CD 200.55 11/13/2022 Open
			// 0   1    2         3     4   5        6        7
			int acctNum = Integer.parseInt(tkns[3]);
			String acctType = tkns[4];
			//polymorphism is in play now
			Account acct = null;//by making this null, I can assume that this will make it possible to fill in the array of accounts 
			//assumption correct 
			Calendar date = Calendar.getInstance();
			TransactionTicket ticket = null;
			switch(acctType) {
			case "CD":
				//public TransactionTicket(int num, Calendar date, String type, double amnt, int term) {
				Calendar maturityDate = Calendar.getInstance();
				maturityDate.clear();
				String[] dateArray = tkns[6].split("/");
				maturityDate.set(Integer.parseInt(dateArray[2]), Integer.parseInt(dateArray[0]) - 1,
						Integer.parseInt(dateArray[1]));
				ticket = new TransactionTicket(acctNum, date, "new Account", Double.parseDouble(tkns[5]), 0, maturityDate);
				//bnk.openNewAcct(ticket, depositor, acctType);
				bankAccounts.openNewAcct(ticket, depositor, acctType);//.toString());//.getTransactionFailureReason());
				break;
			//Hector Bret 222333444 567890 Checking 1234.56  Open
			// 0      1    2          3      4        5       6
			case "Savings":
			
				ticket = new TransactionTicket(acctNum, date, "new Account", Double.parseDouble(tkns[5]), 0);
				bankAccounts.openNewAcct(ticket, depositor, acctType);
				break;
				
			case "Checking":
			
				//begins the process of calling the openNewAcct method
				ticket = new TransactionTicket(acctNum, date, "new Account",Double.parseDouble(tkns[5]), 0);
				bankAccounts.openNewAcct(ticket, depositor, acctType);
				break;
			}
			
				//bankAccounts.addAcct(acct);

			}
			
			sc.close();
			/*the null acct will allow for the subclasses to made 
			 * because the null account is being filled with the subclass object
			 * 
			 * 
			 */
		}
		
	
	//toString() implemented 
	
	public static void closeAcct(Bank bank, PrintWriter outFile, Scanner kybd) {
		int requestedAccount;
		 
		System.out.println();
		System.out.print("Enter the account number: ");
		// prompt for the account number
		requestedAccount = kybd.nextInt(); // read-in the account number


		int acctNumInUse = bank.findAcct(requestedAccount);
		Calendar cal = Calendar.getInstance();
		
		
		if (acctNumInUse == -1) // invalid account
		{
			outFile.println("Transaction Requested: Close Account");
			outFile.println("Error: Account number " + requestedAccount + " does not exist");
			//outFile.println(account.getAcct(acctNumInUse).getBalance(ticket));
		} 
		else // valid account
		{
		//format option acctNum ty
		//	public TransactionTicket(int num, Calendar date, String type, double amnt, int term) {
			
			TransactionTicket ticket = new TransactionTicket(requestedAccount, 
					cal, 
					"Close Account", 
					bank.getAcct(acctNumInUse).getAcctBalance(), 
					0);
			outFile.println(bank.closeAcct(ticket).toString());                       
			
			outFile.println();

		}
		outFile.println();

		outFile.flush(); // flush the output buffer
	}
	
	
	//toString() implemented 
	public static void reOpenAcct(Bank bank, PrintWriter outFile, Scanner kybd) {
		int requestedAccount;
		
		System.out.println();
		System.out.print("Enter the account number: ");
		// prompt for the account number
		requestedAccount = kybd.nextInt(); // read-in the account number


		int acctNumInUse = bank.findAcct(requestedAccount);
		Calendar cal = Calendar.getInstance();
		
		
		if (acctNumInUse == -1) // invalid account
		{
			outFile.println("Transaction Requested: Open Account");
			outFile.println("Error: Account number " + requestedAccount + " does not exist");
		} 
		else // valid account
		{
		//	public TransactionTicket(int num, Calendar date, String type, double amnt, int term) {
			
			TransactionTicket ticket = new TransactionTicket(requestedAccount, 
					cal, 
					"Open Account", 
					bank.getAcct(acctNumInUse).getAcctBalance(), 
					0);
			outFile.println(bank.reOpenAcct(ticket).toString());
			outFile.println();

		}
		outFile.println();

		outFile.flush(); // flush the output buffer
	}
	
	
	


	
	
	


	/*
	 * Method printAccts: Input: acctNumArray - array of account numbers
	 * balanceArray - array of account balances numAccts - number of active accounts
	 * outFile - reference to the output file
	 * 
	 * Process: Prints the database of accounts and balances
	 * 
	 * Output: Prints the database of accounts and balances
	 */
	//toString() implemented 
	public static void printAccts(Bank bankAccounts, PrintWriter outFile) {
	
		//Depositor dep;
		//Account myAccounts;

		outFile.println();
		outFile.println("\t\tDatabase of Bank Accounts");
		outFile.println();//"Account Status"
		outFile.printf("%-27s %-27s %-24s %-24s %-14s %14s %20s\n", "Name", "SSN", "Account Number", "Account Type","Account Status", 
				"Balance", "Maturity Date");
		
		for (int index = 0; index < bankAccounts.getNumAccts(); index++) {
		
			Account acct = bankAccounts.getAcct(index);
			//System.out.println("1" + acct.getAcctType() + (acct instanceof CDAccount));
			//System.out.println("2" + acct.getAcctType() + (acct instanceof CheckingAccount));
			//System.out.println("3" + acct.getAcctType() + (acct instanceof SavingsAccount));
			
			
			if (acct instanceof CDAccount) {
				String status = acct.getAccountStatus();
				double bal = acct.getAcctBalance();
				CDAccount cd = (CDAccount)acct;
				//System.out.println(acct.getClass().getSimpleName());
						outFile.printf("%s %-20s $%8.2f %16s\n",acct.toString(),
										status,
										bal,
										cd.getMaturityDateString()); //cast was needed to get this to work
			}else if(acct instanceof SavingsAccount) {
				outFile.printf("%s %-20s $%8.2f %16s\n",acct.toString(),
						acct.getAccountStatus(),
						acct.getAcctBalance(),
						" "); 
			}
			else if(acct instanceof CheckingAccount) {
				outFile.printf("%s %-20s $%8.2f %16s\n",acct.toString(),
						acct.getAccountStatus(),
						acct.getAcctBalance(),
						" "); 
			}
			/*error: for some weird reason only these accounts don't print their status whereas all other accts of their type do so w/o problem
			 *  Hector Brety 222333444 567890 Checking 1234.56 Open
				Eagor Vankman 999888777 987654 Savings 2.33 Open
				Marry Sue 444666888 333333 Checking 999.99 Open 
				//PROBLEM SOLVED: there was an extra space just before Open that was being read 
			 */
			outFile.println();
		
		}
		outFile.println();

		// flush the output file
		outFile.flush();
	}

	/*
	 * Method menu() Input: none
	 * 
	 * Process: Prints the menu of transaction choices
	 * 
	 * Output: Prints the menu of transaction choices
	 */
	
	
	public static void menu() {
		System.out.println();
		System.out.println("Select one of the following transactions:");
		System.out.println("\t****************************");
		System.out.println("\t    List of Choices         ");
		System.out.println("\t****************************");
		System.out.println("\t     W -- Withdrawal");
		System.out.println("\t     D -- Deposit");
		System.out.println("\t     C -- Clear Check");
		System.out.println("\t     N -- New Account");
		System.out.println("\t     B -- Balance Inquiry");
		System.out.println("\t     I -- Account Information");
		System.out.println("\t     H -- Account History");
		System.out.println("\t     S -- Close Account");
		System.out.println("\t     R -- Reopen Account");
		System.out.println("\t     X -- Delete Account");
		System.out.println("\t     Q -- Quit");
		System.out.println();
		System.out.print("\tEnter your selection: ");
	}



	/*
	 * Method accountInfo: Input: BankAccount - the array for various account
	 * numAccts - number of active accounts outFile - reference to output file kybd-
	 * reference to the "test cases" input file
	 * 
	 * Process: Prompts for the requested SSN runs a for loop to check for any
	 * accounts using the same SSN If the account exists, the instances with the
	 * same SSN are printed Otherwise, an error message is printed
	 * 
	 * Output: If the accounts exists, the SSN instances are printed too Otherwise,
	 * an error message is printed
	 */

	//toString() implemented
	public static void accountInfo(Bank account, PrintWriter outFile, Scanner kybd) {
		int requestedSSN;
		outFile.println("Transaction Type: Account Information Request ");
		//System.out.print("Enter your Social Security number: ");
		requestedSSN = kybd.nextInt();
	
		boolean found = false;
		for (int i = 0; i < account.getNumAccts(); i++) {
			if(account.getAcct(i).getDepositor().getSSN() == requestedSSN) {
				outFile.println("Name: " + account.getAcct(i).getDepositor().getName().toString());//.getName().toString());
				outFile.println("Account Number: " + account.getAcct(i).getAcctNumber());
				outFile.println("Account Type: " + account.getAcct(i).getAcctType());
				outFile.printf("Account Balance: $%.2f ", account.getAcct(i).getAcctBalance());
				outFile.println();
				found = true;
				
			
			}
			
		}
		outFile.println(found == true ? ""
				: "Error, there are no recorded accounts with the following Social Security Number " + requestedSSN
						+ "\n");

		outFile.println();

		outFile.flush();
	}

	
	//arrayOftransHistory isn't working    toString() is implemented
	public static void acctInfoHistory(Bank account, PrintWriter outFile, Scanner kybd) {
		
		int requestedSSN;
		outFile.println("Transaction Type: Transaction History ");
		System.out.print("Enter your Social Security number: ");
		requestedSSN = kybd.nextInt();
	
		boolean found = false;
		for (int i = 0; i < account.getNumAccts(); i++) {
			if(account.getAcct(i).getDepositor().getSSN() == requestedSSN) {
				
				outFile.println("Name: " + account.getAcct(i).getDepositor().getName().toString());
				outFile.println("Account Number: " + account.getAcct(i).getAcctNumber());
				outFile.println("Account Type: " + account.getAcct(i).getAcctType());
				outFile.printf("Account Balance: $%.2f ", account.getAcct(i).getAcctBalance());
				
				outFile.println();
				found = true;
				//outFile.println("Transaction History");
				Calendar cal = Calendar.getInstance();
				//public TransactionTicket(int num, Calendar date, String type, double amnt, int term) {
			
				//int accountUsed = account.getAcct(i).getAcctNumber();
				TransactionTicket ticket = new TransactionTicket(account.getAcct(i).getAcctNumber(), cal,"Transaction History", 0.0, 0  );
				account.getAcct(i).getTransactionHistory(ticket);
				
				outFile.println("Transaction History");
				outFile.println();
				//Everything up to here works, history isn't printed here
				
				
				// The contents of the for loop don't execute at all
				//this prints out the transaction history  
				//add double to add check amount
				//The problem lies in the acctHistory array for
				//System.out.println("Trans His size" + account.getAcct(i).getTransactionHistory(ticket).size()); //the array isn't getting filled
				for(int j = 0; j<account.getAcct(i).getTransactionHistory(ticket).size();j++){//gets the account and then opens the transactionHistoryMethod in order to get the array size
					
					//System.out.println("HELLO DO I WORK?"); //No it doesn't
					outFile.println("Transaction Type: "+ account.getAcct(i).getTransactionHistory(ticket).get(j).getTransactionTicket().getTransactionType());
					Calendar transDate = account.getAcct(i).getTransactionHistory(ticket).get(j).getTransactionTicket().getDateOfTransaction();
					
					//converts Calendar object into a String 				
					//System.out.println("Transaction Type: "+ account.getAcct(i).getTransactionHistory(ticket).get(j).getTransactionTicket().getTransactionType());
					String strTransDate = String.format("%02d/%02d/%4d",
							 								transDate.get(Calendar.MONTH) + 1,
							 								transDate.get(Calendar.DAY_OF_MONTH),
							 								transDate.get(Calendar.YEAR));
					//System.out.println("Transaction Date: " + strTransDate);
					//System.out.printf("Transaction Amount: $%.2f\n", account.getAcct(i).getTransactionHistory(ticket).get(j).getTransactionTicket().getTransactionAmount());
					//System.out.println(account.getAcct(i).getTransactionHistory(ticket).get(j).toString());
					 //end of conversion
					
					outFile.println("Transaction Date: " + strTransDate);
					outFile.printf("Transaction Amount: $%.2f\n", account.getAcct(i).getTransactionHistory(ticket).get(j).getTransactionTicket().getTransactionAmount());
					outFile.println();
					outFile.println("Receipt");
					outFile.println(account.getAcct(i).getTransactionHistory(ticket).get(j).toString());
			
				}
				
				outFile.println();	
			}
		}
		outFile.println(found == true ? ""
				: "Error, there are no recorded accounts with the following Social Security Number " + requestedSSN
						+ "\n");
		outFile.println();
		outFile.flush();
	}
	

	/*
	 * Method balance: Input: acctNumArray - array of account numbers balanceArray -
	 * array of account balances numAccts - number of active accounts outFile -
	 * reference to output file kybd - reference to the "test cases" input file
	 * 
	 * Process: Prompts for the requested account Calls findAcct() to see if the
	 * account exists If the account exists, the balance is printed Otherwise, an
	 * error message is printed
	 * 
	 * Output: If the account exists, the balance is printed Otherwise, an error
	 * message is printed
	 */
	
	//toString() used  
	public static void balance(Bank account, PrintWriter outFile, Scanner kybd) {
		int requestedAccount;
		TransactionTicket ticket;
		System.out.println();
		System.out.print("Enter the account number: ");
		
		// prompt for the account number
		requestedAccount = kybd.nextInt(); // read-in the account number
		
		// call findAcct to search if requestedAccount exists index =
		int acctNumInUse = account.findAcct(requestedAccount);
		Calendar cal = Calendar.getInstance();
		//problem it works but I fear the error condition doesn't work and the accountType may be redundant
		if (acctNumInUse == -1) // invalid account
		{
			outFile.println("Transaction Requested: Balance Inquiry");
			outFile.println("Error: Account number " + requestedAccount + " does not exist");
			//outFile.println(account.getAcct(acctNumInUse).getBalance(ticket));
		} 
		else // valid account
		{
			ticket = new TransactionTicket(requestedAccount, 
					cal, 
					"Balance Inquiry", 
					account.getAcct(acctNumInUse).getAcctBalance(), 
					0);
			outFile.println(account.getBalance(ticket).toString());//Error lied in toString() no having a case for Balance Inquiry //Resolved 

		}
		outFile.println();

		outFile.flush(); // flush the output buffer
	}

	/*
	 * Method deposit: Input: acctNumArray - array of account numbers balanceArray
	 * -array of account balances numAccts - number of active accounts outFile -
	 * reference to the output file kybd - reference to the "test cases" input file
	 * 
	 * Process: Prompts for the requested account Calls findacct() to see if the
	 * account exists If the account exists, prompts for the amount to deposit If
	 * the amount is valid, it makes the deposit and prints the new balance
	 * Otherwise, an error message is printed
	 * 
	 * Output: For a valid deposit, the deposit transaction is printed Otherwise, an
	 * error message is printed
	 */
	
	
	
	//toString() implemented 
	public static void deposit(Bank bnk, PrintWriter outFile, Scanner kybd) {
		int requestedAccount;
		int index;
		double amountToDeposit;
		Calendar date = Calendar.getInstance();

		System.out.println();
		System.out.print("Enter the account number: "); // prompt for the account number
		requestedAccount = kybd.nextInt(); // read-in the account number

		// call findAcct to search if requestedAccount exists
		index = bnk.findAcct(requestedAccount);
	
		if (index == -1) // invalid account
		{
			
			TransactionTicket ticket = new TransactionTicket(requestedAccount, date, "deposit", 0.0, 0);
			outFile.println(bnk.makeDeposit(ticket).getTransactionFailureReason());

		} else // valid account
		{
			/*
			 * 	outFile.println("Transaction Type: "+ account.getAcct(i).getTransactionHistory(ticket).get(j).getTransactionTicket().getTransactionType());
					Calendar transDate = account.getAcct(i).getTransactionHistory(ticket).get(j).getTransactionTicket().getDateOfTransaction();
			 */
			System.out.print("Enter amount to deposit: "); // prompt for amount to deposit
			amountToDeposit = kybd.nextDouble(); // read-in the amount to deposit
			
			//Accounts that are CDs 
		    if(bnk.getAcct(index) instanceof CDAccount) {                                      //	if (bnk.getAcct(index).getAcctType().equals("CD")) { 	       
				if((((CDAccount) bnk.getAcct(index)).getMaturityDate().equals(date)) || (((CDAccount) bnk.getAcct(index)).getMaturityDate().compareTo(date)) < 0 ){  //If matDate doesn't equal to date(today's date)
					
					//System.out.println("Choose a new maturity date for the CD (in months): ");
					int choice = kybd.nextInt();
					boolean validDate;
					do {
						switch(choice) {
						case 6:
							validDate = true;
							break;
						case 12:
							validDate = true;
							break;
						case 18:
							validDate = true;
							break;
						case 24:
							validDate = true;
							break;
						default:
							validDate = false;
							outFile.println("Error: " + choice + " months is an invalid maturity date. Try again.");
							choice = Integer.parseInt(kybd.next());
						}
					}	while (!validDate);
	
					TransactionTicket ticket  = new TransactionTicket(requestedAccount, date, "Deposit", amountToDeposit, choice);
					bnk.addToTotalCDAcctAmnt(amountToDeposit); 
					bnk.addToTotalAmntInAllAccts(amountToDeposit);  
					
					//Account CD = bnk.getAcct(index);
					outFile.println(bnk.makeDeposit(ticket));
					//outFile.println("New Maturity Date: " + (bnk.getMaturityDateString());
			
				}
				else {
					outFile.println("Transaction Requested: Deposit");
					outFile.println("Account Number: "+ bnk.getAcct(index).getAcctNumber());
					outFile.println("Error: CD hasn't matured yet, come back when it's matured"); 
					
				}
			} //can use a superClass var to ref a subclass ibject
			else {  //Accounts that aren't CDs
				if(bnk.getAcct(index).getAcctType().equals("Savings")) {
					TransactionTicket ticket = new TransactionTicket(requestedAccount, date, "Deposit", amountToDeposit, 0);
					//public Account(Depositor d, int num, String type, double bal, String status)
					  //Account acct = bnk.getAcct(index);
					  outFile.println(bnk.makeDeposit(ticket).toString());

				}
				else {//Checkings   
					TransactionTicket ticket = new TransactionTicket(requestedAccount, date, "Deposit", amountToDeposit, 0);
					outFile.println(bnk.makeDeposit(ticket).toString());
				}
				 
			switch(bnk.getAcct(index).getAcctType()) {
			case "Checking":
				bnk.addToTotalCheckingsAcctAmnt(amountToDeposit);
				bnk.addToTotalAmntInAllAccts(amountToDeposit);
				break;
			case "Savings":
				bnk.addToTotalSavingsAcctAmnt(amountToDeposit);
				bnk.addToTotalAmntInAllAccts(amountToDeposit);
				break;
			}
			
			
			}
		}
		outFile.println();

		outFile.flush(); // flush the output buffer
	}


	
	public static void clearCheck(Bank bnk, PrintWriter outFile, Scanner kybd) {
		int requestedAccount;
		int index;
		double checkAmnt;
		//Calendar date = Calendar.getInstance();

		System.out.println();
		System.out.print("Enter the account number: "); // prompt for the account number
		requestedAccount = kybd.nextInt(); // read-in the account number

		// call findAcct to search if requestedAccount exists
		index = bnk.findAcct(requestedAccount);
		outFile.println("Transaction Requested: Clear check");
		outFile.println("Account number: " + requestedAccount); 
		
		if (index == -1) // invalid account
		{//this is essentially filled so the failure toString can be printed from the check method instead of in this method of main
			outFile.print("Enter check amount: "); // prompt for amount to deposit
			checkAmnt = kybd.nextDouble(); // read-in the amount to deposit
			outFile.printf("$%.2f\n",checkAmnt);
			outFile.print("Enter the date of the Check: ");
			String date = kybd.next();	
			outFile.println(date);
			
			//gets this info so it can print out the ticket infor
			Check check = new Check(requestedAccount, checkAmnt, date);
			bnk.removeFromTotalCheckingsAcctAmnt(checkAmnt);
			bnk.removeFromTotalAmntInAllAccts(checkAmnt);
			
			//Account Checking =  bnk.getAcct(index);
			outFile.println(bnk.clearCheck(check).toString());


		}
		else // valid account
		{
			if(bnk.getAcct(index) instanceof CheckingAccount) {
				outFile.print("Enter check amount: "); // prompt for amount to deposit
				checkAmnt = kybd.nextDouble(); // read-in the amount to deposit
				outFile.printf("$%.2f\n",checkAmnt);
				
				outFile.print("Enter the date of the Check: ");
				String date = kybd.next();	
				outFile.println(date);
				Check check = new Check(requestedAccount, checkAmnt, date);
				
				outFile.println(bnk.clearCheck(check).toString());
				bnk.removeFromTotalCheckingsAcctAmnt(checkAmnt);
				bnk.removeFromTotalAmntInAllAccts(checkAmnt);
			}
			else {
				outFile.println("Error: wrong Account type");
			}
		}
		
		
		outFile.println();

		outFile.flush(); // flush
	}
	
	
	
	
	
	
	
	
	//toString() implemented
	public static void withdrawal(Bank bnk, PrintWriter outFile, Scanner kybd) {
		int requestedAccount;
		int index;
		double amountToDeposit;
		Calendar date = Calendar.getInstance();

		System.out.println();
		System.out.print("Enter the account number: "); // prompt for the account number
		requestedAccount = kybd.nextInt(); // read-in the account number

		// call findAcct to search if requestedAccount exists
		index = bnk.findAcct(requestedAccount);

		if (index == -1) // invalid account
		{
			// public TransactionTicket(int num, Calendar date, String type, double amnt, int term) {
			TransactionTicket ticket = new TransactionTicket(requestedAccount, date, "Withdrawal", 0.0, 0);//0.0 is a placeholder for now, will be updated later in the code
			outFile.println(bnk.makeWithdrawal(ticket).getTransactionFailureReason());

		} else // valid account
		{
			//outFile.println("Account number: " + requestedAccount); 
			System.out.print("Enter amount to withdrawal: "); // prompt for amount to deposit
			amountToDeposit = kybd.nextDouble(); // read-in the amount to deposit
			
			//Accounts that are CDs 
			if (bnk.getAcct(index).getAcctType().equals("CD")) {	       
				if((((CDAccount) bnk.getAcct(index)).getMaturityDate().equals(date))||(((CDAccount) bnk.getAcct(index)).getMaturityDate().compareTo(date)) < 0 )               {  //If matDate doesn't equal to date(today's date)
					
					//System.out.println("Choose a new maturity date for the CD (in months): ");
					int choice = kybd.nextInt();
					boolean validDate;
					do {
						switch(choice) {
						case 6:
							validDate = true;
							break;
						case 12:
							validDate = true;
							break;
						case 18:
							validDate = true;
							break;
						case 24:
							validDate = true;
							break;
						default:
							validDate = false;
							outFile.println("Error: " + choice + " months is an invalid maturity date. Try again.");
							choice = Integer.parseInt(kybd.next());
						}
					}	while (!validDate);
					TransactionTicket ticket  = new TransactionTicket(requestedAccount, date, "withdrawal", amountToDeposit, choice);
					
					bnk.removeFromTotalCDAcctAmnt(amountToDeposit);
					bnk.removeFromTotalAmntInAllAccts(amountToDeposit);
				
					outFile.print(bnk.makeWithdrawal(ticket).toString());//.getTransactionFailureReason());
					//outFile.println("New Maturity Date: " + ((CDAccount) bnk.ge).getMaturityDateString());
				}
				else {
					outFile.println("Transaction Requested: Deposit");
					outFile.println("Account Number: "+ bnk.getAcct(index).getAcctNumber());
					outFile.println("Error: CD hasn't matured yet, come back when it's matured");  
					
				}
			} 
			else {//Accounts that aren't CDs
			 //Accounts that aren't CDs
				if(bnk.getAcct(index).getAcctType().equals("Savings")) {
					TransactionTicket ticket = new TransactionTicket(requestedAccount, date, "withdrawal", amountToDeposit, 0);
					
					 // Account Svngs = bnk.getAcct(index);
					  outFile.println(bnk.makeWithdrawal(ticket).toString());
		
					//outFile.println(bnk.makeWithdrawal(ticket).toString());
				}
				else {//Checkings
					TransactionTicket ticket = new TransactionTicket(requestedAccount, date, "withdrawal", amountToDeposit, 0);
					outFile.println(bnk.makeWithdrawal(ticket).toString());
				}

				 
			switch(bnk.getAcct(index).getAcctType()) {
			case "Checking":
				bnk.addToTotalCheckingsAcctAmnt(amountToDeposit);
				bnk.addToTotalAmntInAllAccts(amountToDeposit);
				break;
			case "Savings":
				bnk.addToTotalSavingsAcctAmnt(amountToDeposit);
				bnk.addToTotalAmntInAllAccts(amountToDeposit);
				break;
			}
				
			}
		}
		outFile.println();

		outFile.flush(); // flush the output buffer
	}
	


	/*
	 * Method newAcct: Input: acctNumArray - array of account numbers balanceArray
	 * -array of account balances numAccts - number of active accounts outFile -
	 * reference to the output file kybd - reference to the "test cases" input file
	 * 
	 * Process: prompts the user for their account number findAcct() is called to
	 * see if the account exist if the account exists, an error message is produced
	 * if the account doesn't exist, a new account with a balance of 0 is made
	 * 
	 * Output: It adds the new requested account to the Array while updating the
	 * numAccts
	 */

	/*
	 * Problem: new accounts don't enter into the object array elements and the
	 * deleteAcct() method works but leaves behind zero's rather than updating the
	 * array element with the last element in the array Location of problem: may lie
	 * within the classes in that they don't have arrays
	 */
	
	//toString() implemented 
	
	public static void newAcct(Bank bnk, PrintWriter outFile, Scanner kybd) {
		int requestedAccount;
		Double deposit;
		

		outFile.println();
		outFile.println("Transaction Requested: New Account Creation");
		
		//Beginning of info requesting first and last name, SSN, and acctType and number
		outFile.print("Enter the account number: ");
		requestedAccount = kybd.nextInt();
		outFile.println(requestedAccount);
		String fname = kybd.next();
		String lname = kybd.next();
		int SSN = kybd.nextInt();
		String acctType = kybd.next();
		//end of info requesting first and last name, SSN, and acctType and number
		
		if(acctType.equals("CD")) {
			
			//outFile.print("Please enter how many months you would like to mature the CD for based on the following limiits; 6, 12, 18, 24");
			int CDTerm = kybd.nextInt();
			
			outFile.print("Please enter in the amount you wish to be deposited: ");
			deposit = kybd.nextDouble();
			outFile.printf("$%.2f\n", deposit);
			//setters for Account components 
			Name name = new Name(fname, lname); 
			Depositor depositor = new Depositor(name, SSN); 
		
			//begins the process of calling the openNewAcct method
			Calendar date = Calendar.getInstance();		
			TransactionTicket ticket = new TransactionTicket(requestedAccount, date, "new Account", deposit, CDTerm);
			//bnk.openNewAcct(ticket, depositor, acctType);
			outFile.println(bnk.openNewAcct(ticket, depositor, acctType));//.toString());//.getTransactionFailureReason());
			bnk.addToTotalCDAcctAmnt(deposit);
			bnk.addToTotalAmntInAllAccts(deposit);
		}
		else {
			
			Name name = new Name(fname, lname); 
			Depositor depositor = new Depositor(name, SSN);
			
			//begins the process of calling the openNewAcct method
			Calendar date = Calendar.getInstance();
			TransactionTicket ticket = new TransactionTicket(requestedAccount, date, "new Account", 0.0, 0);
			outFile.println(bnk.openNewAcct(ticket, depositor, acctType).toString());
		}
		outFile.flush();
	}

	
	/*
	 * Method deleteAcct: Input: acctNumArray - array of account numbers
	 * balanceArray - array of account balances numAccts - number of active accounts
	 * o utFile - reference to the output file kybd - reference to the "test cases"
	 * input file
	 * 
	 * Process: prompts the user for their account number findAcct() is called to
	 * see if the account exist if the account exists and is below $0 in it's
	 * balance, an error is produced if the account exists and it's balance is above
	 * zero/at zero the account is then deleted
	 * 
	 * Output: It deletes the requested account and updates the array with a new
	 * numAccts
	 */
	
    //toString() implemented 
	public static void deleteAcct(Bank account, PrintWriter outFile, Scanner kybd)throws IOException {
		int requestedAccount;
		
	//	outFile.println("Transaction Requested: Account Deletion");
		System.out.print("Enter the account number: "); // prompt for the account number
		requestedAccount = kybd.nextInt();
		
		//calls delete Account Method in Bank class
		//public TransactionTicket(int num, Calendar date, String type, double amnt, int term) {
		TransactionTicket ticket = new TransactionTicket(requestedAccount, null, "Account Deletion", 0.0, 0);
		outFile.println(account.deleteAcct(ticket).toString());//.getTransactionFailureReason());
		outFile.println();
		outFile.flush();
	}

	// Don't forget to add a comment saying what the method does
	// public static void acccountInfor(BankAccount[] account, int numAccts, )
	
	/* Method pause() */
	public static void pause(Scanner keyboard) {
		String tempstr;
		System.out.println();
		System.out.print("press ENTER to continue");
		tempstr = keyboard.nextLine(); // flush previous ENTER
		tempstr = keyboard.nextLine(); // wait for ENTER
	}

}
