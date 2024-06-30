package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import entities.Sale;

public class Program{

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Entre o caminho do arquivo: ");
		String path = sc.next();
		
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			

			 List<Sale> list = new ArrayList<>();
			
			 String line = br.readLine();
	         while (line != null) {
	         String[] fields = line.split(",");
	                    
	         Integer month = Integer.parseInt(fields[0]);
	         Integer year = Integer.parseInt(fields[1]);
	         String seller = fields[2];
	         Integer itemsSold = Integer.parseInt(fields[3]);
	         Double totalAmount = Double.parseDouble(fields[4]);
	            
	         list.add(new Sale(month, year, seller, itemsSold, totalAmount));
	         line = br.readLine();
			}
	         
	         Set<String> uniqueVendors = list.stream()
	        	       .map(Sale::getSeller)
	        	       .collect(Collectors.toSet());
	         
	         Map<String, Double> vendorSales = uniqueVendors.stream()
	        	       .collect(Collectors.toMap(
	        	                vendor -> vendor,
	        	                vendor -> list.stream()
	        	                       .filter(sale -> sale.getSeller().equals(vendor))
	        	                       .mapToDouble(Sale::getTotal)
	        	                       .sum()
	        	        ));
	         
	         vendorSales.forEach((seller, total) -> 
	            System.out.println(seller + " - " + total));
	              
			
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}finally {
			sc.close();
		}

	}

}
