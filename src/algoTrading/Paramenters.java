package algoTrading;

import java.util.Random;

public class Paramenters 
{
	// set comisions
	public static double comisions = 1.0;
	// set the free deposit
	public static double deposite = 10000;
	// set the deposit that have been bought
	public static double depositeBought = 0;
	// the amount to bought
	public static int buyAmount = 1000;
	// the last price operation
	public static double lastPriceOperation = 0;
	// the current amount of this coins that i have
	public static int currentAmount = 0;
	// current price to bought
	public static double currentPrice = 0;
	// currency name
	public static String currency = "Dollar";
	// trading currency
	public static String trading = "eur/usa";
	// predictions from weka what to do
	// 0 - buy, 50 - wait, 100 - sell
	public static double predictCommand =-1;
	// the p+rediction stronge
	public static double strongPredict = 1;

	// the path of file where to write the log
	public static String logCsvPath = "C:\\AlgoTrading\\usa-eur-Weka-Log.csv";
	
	// the path of file where to write the log
	public static String folderOfAllRowDataPath = "C:\\AlgoTrading\\Currencies";
		
	// the path of file after the command date
	public static String commandCsvPath = "C:\\AlgoTrading\\usa-eur-Weka.csv";

	// the path of file before the command data
	public static String rowCsvDataPath = "C:\\AlgoTrading\\usa-eur.csv"; 
	
	// what to do with the value from weka
	public static int buyAt = 0;
	// what to do with the value from weka
	public static int sellAt = 100;
	// what to do with the value from weka
	public static int waitAt = 50;
	// start day trading
	public static int startDay = 0;
	// start day trading
	public static int durationDay = 1800;
	// The cost of operation
	public static double operationCost = 0;
	
	// file path
	public static String filePath = "eurDollar.svc";
	// Get predict till this date 
	public static Random rnd = new Random();
	public static String COMMA_DELIMITER = ",";
	public static String NEW_LINE_SEPARATOR = "\n";

	
	public void getFromFile()
	{
		// Read this all parameters from some file
	}
}
