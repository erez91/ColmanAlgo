package algoTrading;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class myFiles 
{
		// Convert the csv with now commands to one with
		public static void createNewCsvWithCommands(int currancy)
		{
			// Create file if needed
		    try {
		    	// create file if it not exists
		    	File file = new File(Paramenters.commandCsvPath);
		    	if (!file.exists()) {
		    		file.createNewFile();
                    System.out.println("CSV file was created successfully !!!"); 
		    	}
		    	else
		    	{
		    		file.delete();
		    		System.out.println("Old CSV file was deleted successfully !!!");
		    	}
		    	
		    	FileWriter fileWriter = new FileWriter(file,true);
		    	String msg = "";
		    	fileWriter.append("End Date," + main.arrCurrencys.get(currancy).get(0).getType()+ ",COMMAND");
		    	// write all data to new file
		    	for (int index = 0; index < main.arrCurrencys.size();index++)
		    	{
		            fileWriter.append(Paramenters.NEW_LINE_SEPARATOR);
		    		Currency cur = main.arrCurrencys.get(currancy).get(index);
			    	msg = cur.getDate() + Paramenters.COMMA_DELIMITER + cur.getValue() + Paramenters.COMMA_DELIMITER + cur.getCommand(); 
			    	fileWriter.append(msg);
		    	}
		        fileWriter.flush();
                fileWriter.close();
    	    }
		    catch (Exception e) 
		    {
    	
   	            System.out.println("Error in CsvFileWriter !!!");
		    }
		}
		
		public static void writeToLog(String msg)
		{
			 try {
				 	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				   //get current date time with Date()
				   java.util.Date date = new java.util.Date();
				   String dateTime = dateFormat.format(date);
		            //Whatever the file path is.
		            File statText = new File("C:\\AlgoTrading\\usa-eur-Weka-Log" + dateTime + ".csv");
		            if (!statText.exists()) {
		            	statText.createNewFile();
		            	// write log of trading
		        		writeToLog("Day,Deposite,OperetionCost,CurrentAmount,lastPriceOperation,CurrentPrice,Command");
		            }           
				    // create writer
				    FileWriter fileWriter = null;
		            fileWriter = new FileWriter(statText,true);
		            fileWriter.append(msg);
		            fileWriter.append(Paramenters.NEW_LINE_SEPARATOR);
		            fileWriter.flush();
		            fileWriter.close();
		            
		        } catch (IOException e) {
		            System.err.println("Problem writing to the Log");
		        }
		}
		
		public static void getDataToArray()
		{
			main.arrCurrencys.add(new ArrayList<Currency>());
			
			int currancy =0;
			File folder = new File(Paramenters.folderOfAllRowDataPath);
			if (!folder.exists())
			{
				folder.mkdirs();
			}
			File[] listOfFiles = folder.listFiles();

			// Run for all files in the folder
			for (int indexFiles = 0; indexFiles < listOfFiles.length; indexFiles++)
			{
				// Read all data from the file 
				try(BufferedReader br = new BufferedReader(new FileReader(listOfFiles[indexFiles]))) 
				{
					// read from file
					String header = br.readLine(); 
					String line ="";
					line = br.readLine();
					
					// read all the data
					while (line != null)
				    {
						// add to data that will write to file 		
						// Create new curency value 
						Currency cur = new Currency();
						cur.setDate(line.split(Paramenters.COMMA_DELIMITER)[0]);
						cur.setValue(Double.parseDouble(line.split(Paramenters.COMMA_DELIMITER)[1]));
						main.arrCurrencys.get(currancy).add(cur);
					
						// read new data line
				    	line = br.readLine();
				    }
				}
				catch (Exception ex)
				{
					   System.out.println("There are problem to read the row data file");
				}
			}
			// reveres the array 
			for (int index = 0; index < main.arrCurrencys.get(currancy).size()/2; index++)
			{
				Currency cur = main.arrCurrencys.get(currancy).get(index);
				main.arrCurrencys.get(currancy).set(index, main.arrCurrencys.get(currancy).get(main.arrCurrencys.get(currancy).size()-index-1));
				main.arrCurrencys.get(currancy).set(main.arrCurrencys.get(currancy).size()-index-1, cur);
			}
			
			
	    	// Add command
	    	for (int index = 0; index < main.arrCurrencys.get(currancy).size() -1 ; index++)
	    	{	
	    		if (index != 0)
	    		{	
		    		double lastPos = main.arrCurrencys.get(currancy).get(index - 1).getValue();
		    		double currentPos = main.arrCurrencys.get(currancy).get(index).getValue();
		    		double futurePos= main.arrCurrencys.get(currancy).get(index + 1).getValue();
		    		double newCommand = myWeka.commandTodo(lastPos, currentPos, futurePos);
		    		
		    		main.arrCurrencys.get(currancy).get(index).setCommand(newCommand);
	    		}
	    		else
	    		{
	    			main.arrCurrencys.get(currancy).get(index).setCommand(Paramenters.waitAt);
	    		}
	    	}
		}

		public static void createAllFileToAnalyseToERO_USA()
		{	
			// get size of currences
			for (int index = 0; index < main.arrCurrencys.size(); index++)
			{
				for (int valuesCount = 10; valuesCount <= 1000; valuesCount+=50)
				{
					String path ="C:\\AlgoTrading\\Analyze\\eur_usa_Weka_Interval_" + main.arrCurrencys.get(index).get(0).getType() +"_"+ valuesCount+ ".arff" ;
					createFileToAnalyse(path,valuesCount,index);
				}
			}
		}
		
		public static void createFileToAnalyse(String path,int valuesCount, int currancy)
		{

			try{
				File statText = new File(path);
	            if (!statText.exists()) {

	            	statText.createNewFile();
	            }
	            else
	            {
	            	statText.delete();	            	
	            }
	            
	            // create writer
			    FileWriter fileWriter = null;
	            fileWriter = new FileWriter(statText,true);
            
	    		fileWriter.append("@RELATION intervals" + valuesCount);
	            fileWriter.append(Paramenters.NEW_LINE_SEPARATOR);
	            fileWriter.append(Paramenters.NEW_LINE_SEPARATOR);
	            // add atributes 
	            for (int index = 1 ; index <= valuesCount; index++)
	            {
	            	fileWriter.append("@ATTRIBUTE a" + index + " NUMERIC");
	            	fileWriter.append(Paramenters.NEW_LINE_SEPARATOR);
	            }
	        	
	            fileWriter.append("@ATTRIBUTE class {50,100,0}");
	            fileWriter.append(Paramenters.NEW_LINE_SEPARATOR);
	            fileWriter.append(Paramenters.NEW_LINE_SEPARATOR);
	            fileWriter.append("@DATA");
	            
	            // for all rows
	        	for (int index = 0;index < main.arrCurrencys.get(0).size() - valuesCount;index++)
				{
	        		fileWriter.append(Paramenters.NEW_LINE_SEPARATOR);
	        		
	        		// for every row
					for (int countRow = 0; countRow < valuesCount; countRow++)
					{
						
						fileWriter.append("" + main.arrCurrencys.get(currancy).get(index + countRow).getValue());
						fileWriter.append(Paramenters.COMMA_DELIMITER);
					}
					
					fileWriter.append("" + (int)main.arrCurrencys.get(currancy).get(index+valuesCount-1).getCommand());
				}
			
	            fileWriter.flush();
	            fileWriter.close();
	            
	        } catch (IOException e) {
	            System.err.println("Problem writing to the Log analyze");
	        }
		}
}