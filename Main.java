package main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Fish;
import model.Freshwater;
import model.Saltwater;
import model.Transaction;

public class Main {
	
	Scanner scan = new Scanner (System.in);
	Connect connect = Connect.getConnection();
	ArrayList<Transaction> transactionList = new ArrayList<>();
	ArrayList<Fish> fishList = new ArrayList<>();
	ArrayList<Freshwater> freshList = new ArrayList<>();
	ArrayList<Saltwater> saltList = new ArrayList<>();

	public Main() {
		int choice = 0;
		initialize();
		do {
			System.out.println("Exotic Fish Store");
			System.out.println("=================");
			System.out.println("1. Purchase Fish ");
			System.out.println("2. View Transaction");
			System.out.println("3. Cancel Transaction");
			System.out.println("4. Exit Menu");
			System.out.print("Input [1-4]: ");
			choice = scan.nextInt();
			scan.nextLine();
			
			switch(choice) {
			case 1:
				insert();
				break;
			case 2:
				view();
				System.out.println("Press enter to Continue..");
				scan.nextLine();
				break;
			case 3:
				cancel();
				break;
			case 4:
				System.out.println("Thank you for Using Our Application...");
				break;
			}
			
		}while (choice != 4);
	}
	
	private void cancel() {
		view();
		
		if(transactionList.isEmpty()) {
			System.out.println("Press Enter to Continue...");
			scan.nextLine();
			return;
		}
		
		int cancel;
		System.out.print("Input transaction ID to be cancelled: ");
		cancel = scan.nextInt();
		scan.nextLine();
		
//		transactionList.remove(cancel-1);
		
//		String q = String.format("DELETE FROM transaction "+"WHERE TransactionID = '%d'", cancel);
//		connect.executeUpdate(q);
		
		String q = "DELETE FROM transaction "+"WHERE TransactionID = ?";
		PreparedStatement ps = connect.prepareStatement(q);
		
		try {
			ps.setInt(1, cancel);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("Transaction Cancelled");
		System.out.println("Press enter to continue...");
		scan.nextLine();
	}
	
	private void view(){
		System.out.println("=============================================================");
		System.out.println("| TransactionID | UserEmail | Quantity | FishID | FishName |");
		System.out.println("=============================================================");
		
		if(transactionList.isEmpty()) {
			System.out.println("No Transaction Data Available");
			scan.nextLine();
		}
		
		ResultSet rs1 = connect.executeQuery("SELECT * FROM transaction t,"+"freshwaterfish f WHERE t.FishID = f.FishID");
		try {
			while(rs1.next()) {
				System.out.println("| " + rs1.getInt("TransactionID") + " | " +rs1.getString("UserEmail")+" | "+rs1.getInt("Quantity")+" | "+rs1.getString("t.FishID")+" | "+rs1.getString("f.FishName")+" |");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		ResultSet rs2 = connect.executeQuery("SELECT * FROM transaction t,"+ "saltwaterfish s WHERE t.FishID = s.FishID");
		try {
			while(rs2.next()) {
				System.out.println("| "+rs2.getInt("TransactionID")+" | "+rs2.getString("UserEmail")+" | " + rs2.getInt("Quantity")+" | " + rs2.getString("t.FishID") + " | " +rs2.getString("s.FishName") + " |");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("=============================================================");
		
	}
	
	private void insert() {
		String email;
		do {
			System.out.print("Input User Email [Ends with '.com'| contains '@']: ");
			email = scan.nextLine();
		}while(!email.contains("@") && !email.endsWith(".com"));
		
		int quantity;
		do {
			System.out.print("Input Quantity [Must be Greater than 0]: ");
			quantity = scan.nextInt();
			scan.nextLine();
		} while(quantity < 1);
		
		String type;
		do {
			System.out.print("Input Fish Type [Freshwater|Saltwater] (Case Sensitive): ");
			type = scan.nextLine();
			
		}while(!type.equals("Freshwater") && !type.equals("Saltwater"));
		
		int transid;
		transid = transactionList.size() + 1;
		
		int i = 0,j = 0;
		if(type.equals("Freshwater")) {
			
			System.out.println("===============================================================================");
			System.out.println("|                            Freshwater Fish                                  |");
			System.out.println("===============================================================================");
			System.out.println("| No. | FishID	| Fish Name		| Fish Size | Fish Speed	| Algae Tolerance |");
			System.out.println("===============================================================================");
			
			for (Freshwater fresh : freshList) {
					
				i++;
				System.out.println("| "+i+" | "+fresh.getId()+" | "+fresh.getName()+" | "+fresh.getSpeed()+ " | "+fresh.getSize()+" | "+fresh.getAlgae()+" |");
			}
			
			int purch = -1;
			System.out.print("Input fish index to purchase [1-"+freshList.size()+"]: ");
			purch = scan.nextInt();
			scan.nextLine();
			
			int price;
			price = freshList.get(purch - 1).calculatePrice() * quantity;
			
			System.out.println("Fish ID: " + freshList.get(purch - 1).getId());
			System.out.println("Fish Name: " + freshList.get(purch - 1).getName());
			System.out.println("Fish Size: " + freshList.get(purch - 1).getSize());
			System.out.println("Fish Speed: " + freshList.get(purch - 1).getSpeed());
			System.out.println("Fish Price: " + price + "$");
			System.out.println("Press enter to Continue...");
			scan.nextLine();
			
//			String q = String.format("INSERT INTO transaction VALUES('%d', '%s', '%d', '%s')", transid, email, quantity, freshList.get(purch - 1).getId());
//			connect.executeUpdate(q);
			
			String q1 = "INSERT INTO transaction VALUES(?,?,?,?)";
			PreparedStatement ps = connect.prepareStatement(q1);
			
			try {
				ps.setInt(1,  transid);
				ps.setString(2, email);
				ps.setInt(3,  quantity);
				ps.setString(4, freshList.get(purch - 1).getId());
				
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if(type.equals("Saltwater")) {
			System.out.println("===============================================================================");
			System.out.println("|                             Saltwater Fish                                  |");
			System.out.println("===============================================================================");
			System.out.println("| No. | FishID	| Fish Name		| Fish Size | Fish Speed	| Depth Tolerance |");
			System.out.println("===============================================================================");
			
			for (Saltwater salt : saltList) {
				
				j++;
				System.out.println("| "+j+" | "+salt.getId()+" | "+salt.getName()+" | "+salt.getSpeed()+" | "+salt.getSize()+" | "+salt.getDepth()+" |");
			}
			
			int purch = -1;
			System.out.print("Input fish index to purchase [1-"+saltList.size()+"]: ");
			purch = scan.nextInt();
			scan.nextLine();
			
			int price;
			price = saltList.get(purch - 1).calculatePrice() * quantity;
			
			System.out.println("Fish ID: " + saltList.get(purch - 1).getId());
			System.out.println("Fish Name: " + saltList.get(purch - 1).getName());
			System.out.println("Fish Size: " + saltList.get(purch - 1).getSize());
			System.out.println("Fish Speed: " + saltList.get(purch - 1).getSpeed());
			System.out.println("Fish Price: " + price + "$");
			System.out.println("Press enter to Continue...");
			scan.nextLine();
			
//			String q = String.format("INSERT INTO transaction VALUES('%d', '%s', '%d', '%s')", transid, email, quantity, saltList.get(purch - 1).getId());
//			connect.executeUpdate(q);
			
			String q2 = "INSERT INTO transaction VALUES(?,?,?,?)";
			PreparedStatement ps = connect.prepareStatement(q2);
			
			try {
				ps.setInt(1,  transid);
				ps.setString(2, email);
				ps.setInt(3,  quantity);
				ps.setString(4,  saltList.get(purch - 1).getId());
				
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}	
	}

	
	private void initialize() {
		String q1 = "select * from freshwaterfish";
		ResultSet rs1 = connect.executeQuery(q1);
		
		try {
			while(rs1.next() == true) {
				Freshwater fresh = new Freshwater(rs1.getString("FishID"), rs1.getString("FishName"), rs1.getInt("FishSpeed"), rs1.getDouble("FishSize"), rs1.getInt("FishAlgaeTolerance"));
				freshList.add(fresh);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String q2 = "select * from saltwaterfish";
		ResultSet rs2 = connect.executeQuery(q2);
		
		try {
			while(rs2.next() == true) {
				Saltwater salt = new Saltwater(rs2.getString("FishID"), rs2.getString("FishName"), rs2.getInt("FishSpeed"), rs2.getDouble("FishSize"), rs2.getString("FishDepthTolerance"));
				saltList.add(salt);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String q3 = "select * from transaction";
		ResultSet rs3 = connect.executeQuery(q3);
		
		try {
			while(rs3.next()) {
				Transaction trans = new Transaction(rs3.getInt("TransactionId"), rs3.getString("UserEmail"), rs3.getInt("Quantity"), rs3.getString("FishId"));
				transactionList.add(trans);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
	}

}