package algoTrading;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import weka.classifiers.functions.LinearRegression;
import weka.core.Instance;
import weka.core.Instances;

public class myWeka {

	public static void train(int currancy)
	{
		// Trading date
		String tradingDate = "";
				
		// Get current value 
		double currentPrice = -1;
		
		// get the command
		double predictCommand  = -1;
		
		// While i have money keep playing
		for (int indexDay = Paramenters.startDay; indexDay < Paramenters.durationDay && Paramenters.deposite > 100 && Paramenters.deposite <1000000; indexDay++)
		{
			// Get predict what to do (BUY ,SELL, WAIT)
			Currency cur = main.arrCurrencys.get(currancy).get(indexDay);
			
			// Set current price for that day 
			Paramenters.currentPrice = cur.getValue(); 
					
			// Trading date
		    tradingDate = cur.getDate(); 
			
			// Get current value 
			currentPrice = cur.getValue();
			
			// get the command
			predictCommand  = cur.getCommand();
			
			// Check what to do
			// If to buy
			if (Paramenters.predictCommand < (Paramenters.waitAt/2))
			{
				// Check how much strong this predict
				//strongPredict = buyPredict - waitPredict;
				
				if (Paramenters.currentAmount < 100){
					// Check if can buy (if have available money)
					if (Paramenters.buyAmount * Paramenters.currentPrice < Paramenters.deposite)
					{
						// buy some amount
						Paramenters.currentAmount += (int)(Paramenters.buyAmount * Paramenters.strongPredict);
						
						Paramenters.operationCost = Paramenters.currentAmount * Paramenters.currentPrice + Paramenters.comisions; 
						// change my deposit
						Paramenters.deposite -= Paramenters.operationCost; 
						// change my last price i bought
						Paramenters.lastPriceOperation = Paramenters.currentPrice;
						// write to log that i bought
						myFiles.writeToLog(tradingDate + "," + Paramenters.deposite + "," + (-Paramenters.operationCost) + ","+ Paramenters.currentAmount + "," + Paramenters.lastPriceOperation + "," + Paramenters.currentPrice + "," + "Buy");
					}
				}
			}
			else {
				// Check if to sell
				if (Paramenters.predictCommand > (Paramenters.waitAt + (Paramenters.waitAt/2)))
				{
					// Check how much strong this predict
					//strongPredict = sellPredict * waitPredict;
					
					// Check if there is what to sell
					if (Paramenters.currentAmount > 0)
					{
						Paramenters.operationCost = Paramenters.buyAmount * Paramenters.currentPrice + Paramenters.comisions;
						// set the current deposite after the sell
						Paramenters.deposite += Paramenters.operationCost;
						Paramenters.currentAmount -= Paramenters.buyAmount;
						Paramenters.lastPriceOperation = Paramenters.currentPrice;	
						myFiles.writeToLog(tradingDate + "," + Paramenters.deposite + ","+ Paramenters.operationCost + "," + Paramenters.currentAmount + "," + Paramenters.lastPriceOperation + "," + Paramenters.currentPrice + "," + "Sell");
					}
				}
				else{
					// Check if to wait
					if (Paramenters.predictCommand > (Paramenters.waitAt/2) && Paramenters.predictCommand < (Paramenters.waitAt + (Paramenters.waitAt/2)))
					{
						// Check how much strong this predict
					    //StrongPredict = buyPredict * sellPredict;
						// write to log that i wait
						Paramenters.operationCost = 0;
						myFiles.writeToLog(tradingDate + "," + Paramenters.deposite + ","+ Paramenters.operationCost + "," + Paramenters.currentAmount + "," + Paramenters.lastPriceOperation + "," + Paramenters.currentPrice + "," + "Wait");
					}
				}
			}		
		}
		System.out.println("You have " + Paramenters.deposite + " out of range");
	}
			
	public static double getPrediction(int lastday)
	{
		try{
			//load data
			Instances data = new Instances(new BufferedReader(new
			FileReader("dataset/house.arff")));
			data.setClassIndex(data.numAttributes() - 1);
			//build model
			LinearRegression model = new LinearRegression();
			model.buildClassifier(data); //the last instance with missing class is not used
			//System.out.println(model);
			//classify the last instance
			Instance myHouse = data.lastInstance();
			double price = model.classifyInstance(myHouse);
			System.out.println("My house ("+myHouse+"): "+price);
			return price;
		}
		catch (Exception ex)
		{
			return Paramenters.waitAt;
		}
	}
	
	public static int commandTodo(double lastPos, double currentPos, double futurePos)
	{
		int command = -1;
		
		// Check when the graph go up
		if (currentPos > lastPos)
		{
			// check if future is go up to
			if(futurePos > currentPos)
			{
				// wait
				command = Paramenters.waitAt;
			}
			// The future pos go down (sell)
			else
			{
				// sell
				command = Paramenters.sellAt;
			}
		}
		// Check what happen when the graph go down
		else
		{
			// if the graph go down
			if (futurePos < currentPos)
			{
				// wait
				command = Paramenters.waitAt;
			}
			// the graph go up (buy)
			else
			{
				// buy
				command = Paramenters.buyAt;
			}
		}
		return command;
	}
}