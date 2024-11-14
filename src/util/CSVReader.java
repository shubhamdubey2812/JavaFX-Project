package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Customer;

public class CSVReader {
	public static List<Customer> readCustomerFromCsv(File file) {
		List<Customer> customers=new ArrayList<>();
		try(BufferedReader br=new BufferedReader(new FileReader(file))){
			String line;
			while((line=br.readLine())!=null) {
				StringBuilder sb=new StringBuilder();
				ArrayList<String> data=new ArrayList<>();
				boolean isQuotes=false;
				for(int i=0; i<line.length(); i++) {
					char c=line.charAt(i);
					if(c=='"') {
						isQuotes=!isQuotes;
					}
					else if(c==',' && !isQuotes) {
						data.add(sb.toString());
						sb.setLength(0);
					}
					else {
						sb.append(c);
					}
					
				}
				data.add(sb.toString());
				if(data.size()==12)
					customers.add(new Customer(data.get(0), data.get(1), data.get(2), data.get(3), data.get(4),data.get(5),
							data.get(6),data.get(7),data.get(8),data.get(9),data.get(10), data.get(11)));
			}
			return customers;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
				
	}

}
