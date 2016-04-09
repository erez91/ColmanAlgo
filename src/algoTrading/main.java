package algoTrading;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.FileReader;
import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.functions.LinearRegression;

public class main {

	public static ArrayList<ArrayList<Currency>> arrCurrencys = new ArrayList<ArrayList<Currency>>();
	
	
	
	public static void main(String[] args) 
	{
		
		myFiles.getDataToArray();
		// create file with commands
		//myFiles.createNewCsvWithCommands(0);
		
		myFiles.createAllFileToAnalyseToERO_USA();
		
		// train();
		//myWeka.train();
		
		
		
		// Done
		System.out.print("You Done");
		
		
	}
}