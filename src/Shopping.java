import java.sql.*;
import java.util.*;

class ConnectionTest
{
	static Connection con;
	ConnectionTest() throws Exception
	{
		String url = "jdbc:mysql://localhost:3306/shopping";
		String username = "root";
		String password = "";
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(url,username,password);	
	}
	public static void makeDesign() 
	{
		System.out.println("--------------------------------------");
	}
}
public class Shopping 
{

	public static void main(String[] args) throws Exception 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("-------welcome to Your Shopping-------");
		ConnectionTest.makeDesign();
		System.out.println("::Please Select::");
		ConnectionTest.makeDesign();
		
		System.out.print("1.Admin Login\t\t");
		System.out.println("2.User Login");
		System.out.print("3.User SignUp\t\t");
		System.out.println("4.Seller Login");
		System.out.println("5.Seller SignUp");
		ConnectionTest.makeDesign();
		
		System.out.print("Choice : ");
		int x = sc.nextInt();
		
		switch(x)
		{
		case 1:Login.adminLogin();break;
		case 2:Login.login();break;
		case 3:Login.signUp();break;
		case 4:Login.sellerLogin();break;
		case 5:Login.sellerSignup();break;
		default:System.out.println("Invalid choice");System.exit(0);
		}
		
	}
}
class Login
{
	static String see_user_profile = ""; 
	static int see_user_id = 0;
	static int user_city = 0;
	static void adminLogin() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		new ConnectionTest();
		
		ConnectionTest.makeDesign();
		System.out.print("Enter Admin Id : ");
		String username = sc.next();
		System.out.print("Enter Admin Password : ");
		String password = sc.next();
		
		String sql = "SELECT * FROM admin_master WHERE admin_username = '"+username+"' and admin_pass = '"+password+"'";
		Statement st = ConnectionTest.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		if(rs.next())
		{
			ConnectionTest.makeDesign();
			System.out.println("Welcome  "+username);
			ConnectionTest.makeDesign();
			while(true)
			{
				Admin.adminMenu();
				ConnectionTest.makeDesign();
			}
		}
		else
			System.out.println("Sorry...Invalid Identity");
	}
	static void login() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		new ConnectionTest();
		
		ConnectionTest.makeDesign();
		System.out.print("Enter email : ");
		String email = sc.next();
		System.out.print("Enter password : ");
		String password = sc.next();
		
		String sql = "SELECT * FROM users WHERE user_email = '"+email+"' and user_pass = '"+password+"'";
		Statement st = ConnectionTest.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		if(rs.next())
		{
			ConnectionTest.makeDesign();
			System.out.println("Welcome  "+rs.getString(2));
			Login.see_user_profile = rs.getString(4);
			Login.see_user_id = rs.getInt(1);
			Login.user_city = rs.getInt(5);
			ConnectionTest.makeDesign();
			while(true)
			{
				User.userMenu();
				ConnectionTest.makeDesign();
			}
		}
		else
		{
			ConnectionTest.makeDesign();
			System.out.println("Invalid Details...Try Again...");
			ConnectionTest.makeDesign();
		}
		
	}
	static void signUp() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		new ConnectionTest();
		
		ConnectionTest.makeDesign();
		System.out.print("Enter Your Name : ");
		String name = sc.nextLine();
		System.out.print("Enter Your Email : ");
		String email = sc.next();
		
		String sql = "SELECT * FROM city";
		Statement st = ConnectionTest.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		ConnectionTest.makeDesign();
		System.out.println("Available cities : ");
		ConnectionTest.makeDesign();
		ArrayList<Integer> city_array = new ArrayList<Integer>();
		while(rs.next())
		{
			city_array.add(rs.getInt(1));
			System.out.println(rs.getInt(1)+" : "+rs.getString(2));
		}
		ConnectionTest.makeDesign();
		
		System.out.print("Enter city Id : ");
		int city = sc.nextInt();
		while(!city_array.contains(city))
		{
			System.out.print("Please Enter a valid city id : ");
			city = sc.nextInt();
		}
		
		System.out.print("Enter Your password : ");
		String password = sc.next();
		System.out.print("Re-Enter Your password : ");
		String repass = sc.next();
		
		while(!repass.equals(password))
		{
			System.out.println("Passwords did not match...Try again");
			System.out.print("Re-Enter Your password : ");
			repass = sc.next();
		}
		
		sql = "INSERT INTO users (user_name,user_pass,user_email,user_city) values('"+name+"','"+password+"','"+email+"','"+city+"')";
		st = ConnectionTest.con.createStatement();
		int result = st.executeUpdate(sql);
		
		if(result > 0)
		{
			ConnectionTest.makeDesign();
			System.out.println("Successfully signed up.");
			System.out.println("Want to Login Now?(Y/N)");
			System.out.print("Choice : ");
			if(sc.next().equalsIgnoreCase("Y"))
				login();
			else
			{
				ConnectionTest.makeDesign();
				System.out.println("Thank you");
				ConnectionTest.makeDesign();
			}
		}
	}
	static void sellerLogin() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		new ConnectionTest();
		
		System.out.print("Enter Seller Id  : ");
		String selleremail = sc.next();
		System.out.print("Enter Seller Password : ");
		String pass = sc.next();
		
		String sql = "SELECT * FROM seller_master WHERE seller_email = '"+selleremail+"' and seller_password = '"+pass+"'";
		Statement st = ConnectionTest.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		if(rs.next())
		{
			if(rs.getString(2).equals(selleremail) && rs.getString(3).equals(pass))
			{
				ConnectionTest.makeDesign();
				while(true)
				{
					Seller.sellerMenu();
					ConnectionTest.makeDesign();
				}
			}
			else
			{
				System.out.println("Authentication error...Try again.");
			}
		}
		
	}
	static void sellerSignup() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		new ConnectionTest();
		
		System.out.print("Enter SellerId : ");
		String selleremail = sc.next();
		
		String sql = "SELECT * FROM city";
		Statement st = ConnectionTest.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		ConnectionTest.makeDesign();
		System.out.println("Available cities : ");
		ConnectionTest.makeDesign();
		ArrayList<Integer> city_array = new ArrayList<Integer>();
		while(rs.next())
		{
			city_array.add(rs.getInt(1));
			System.out.println(rs.getInt(1)+" : "+rs.getString(2));
		}
		ConnectionTest.makeDesign();
		
		System.out.print("Enter city Id : ");
		int city = sc.nextInt();
		while(!city_array.contains(city))
		{
			System.out.print("Please Enter a valid city id : ");
			city = sc.nextInt();
		}
		
		System.out.print("Enter Seller Password : ");
		String pass = sc.next();
		System.out.print("Re-enter password : ");
		String repass = sc.next();
		while(!pass.equals(repass))
		{
			System.out.print("Passwords did not match...\nEnter again : ");
			repass = sc.next();
		}
		
		sql = "INSERT INTO seller_master(seller_email,seller_password,seller_city) values('"+selleremail+"','"+pass+"','"+city+"')";
		st = ConnectionTest.con.createStatement();
		int result = st.executeUpdate(sql);
		
		if(result > 0)
		{
			ConnectionTest.makeDesign();
			System.out.println("***Successfully Signed Up***");
			System.out.print("Want to Login now (Y/N)? : ");
			if(sc.next().equalsIgnoreCase("y"))
				Login.sellerLogin();
			else
			{
				ConnectionTest.makeDesign();
				System.out.println("Thank You");
				ConnectionTest.makeDesign();
			}
		}
	}
}
class Seller
{
	static void sellerMenu() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Choose One.");
		ConnectionTest.makeDesign();
		System.out.println("1.See Pricing");
		System.out.println("2.View Profile");
		System.out.println("3.Edit Profile");
		System.out.println("4.See Earning");
		System.out.println("5.Add Products");
		System.out.println("6.Remove Products");
		
		ConnectionTest.makeDesign();
		System.out.print("Choice : ");
		int choice = sc.nextInt();
		
		switch(choice)
		{
		case 1:Seller.seePricing();break;
		case 2:Seller.seeProfile();break;
		case 3:Seller.editProfile();break;
		case 4:Seller.totalEarning();break;
		case 5:Seller.addProducts();break;
		case 6:Seller.removeProducts();break;
		case 0:
			ConnectionTest.makeDesign();
			System.out.println("Thank you");
			ConnectionTest.makeDesign();
			System.exit(0);
		default:System.out.println("Invalid Choice.Try Again.");
		}
	}
	static void seePricing() throws Exception
	{
		Admin.viewPricing();
	}
	static void totalEarning() throws Exception
	{
		
	}
	static void addProducts() throws Exception
	{
		new ConnectionTest();
		Scanner sc = new Scanner(System.in);

		System.out.println();
	}
	static void removeProducts() throws Exception
	{
		
	}
	static void seeProfile() throws Exception
	{
		
	}
	static void editProfile() throws Exception
	{
		
	}
}
class User
{
	static void userMenu() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Choose One.");
		ConnectionTest.makeDesign();
		System.out.print("1.See All Products\t\t   ");
		System.out.println("2.See Products Category Wise");
		System.out.print("3.View/Search Specific Product\t   ");
		System.out.println("4.Buy Product");
		System.out.print("5.See History\t\t\t   ");
		System.out.println("6.See Profile");
		System.out.print("7.Edit Profile\t\t\t   ");
		System.out.println("8.See Offered Products");
		System.out.println("0.Logout");
		
		ConnectionTest.makeDesign();
		System.out.print("Choice : ");
		int choice = sc.nextInt();
		
		switch(choice)
		{
		case 1:User.seeAllProducts();break;
		case 2:User.seeProductsCategoryWise();break;
		case 3:User.viewProduct();break;
		case 4:User.buyProduct();break;
		case 5:User.seeHistory();break;
		case 6:User.seeProfile();break;
		case 7:User.updateProfile();break;
		case 8:User.seeOfferedProducts();break;
		case 0:
			ConnectionTest.makeDesign();
			System.out.println("Thank you");
			ConnectionTest.makeDesign();
			System.exit(0);
		default:System.out.println("Invalid Choice.Try Again.");
		}		
	}
	
	
	static void seeOfferedProducts() throws Exception
	{
		new ConnectionTest();
		System.out.println("**Offered Products**");
		ConnectionTest.makeDesign();
		
		String sql = "SELECT * FROM products INNER JOIN category ON products.product_category = category.category_id " + 
					 "WHERE show_price < actual_price and product_status = 'active' ORDER BY product_id DESC";
		Statement st = ConnectionTest.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		System.out.println("**All Offered Products**");
		ConnectionTest.makeDesign();
		System.out.println("#ID\tName\t\tAcualPrice\tShow Price\tCategory\tQuantity");
		ConnectionTest.makeDesign();
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+"   "+rs.getString(2)+"\t\t"+rs.getInt(3)+"\t\t"+rs.getInt(4)+"\t\t"+rs.getString(9)+"\t\t"+rs.getInt(5));
		}
	}


	static void seeProfile() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		new ConnectionTest();
		String sql = "SELECT * FROM users INNER JOIN city on city.city_id = users.user_city WHERE user_email = '"+Login.see_user_profile+"'";
		Statement st = ConnectionTest.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		System.out.println("**Your Profile**");
		ConnectionTest.makeDesign();
		rs.next();
		
		System.out.println("Name : "+rs.getString(2));
		System.out.println("Email : "+rs.getString(4));
		System.out.println("City : "+rs.getString(7));
		ConnectionTest.makeDesign();
		
		System.out.print("Want to edit profile ? (Y/N) \nChoice : ");
		if(sc.next().equalsIgnoreCase("y"))
			User.updateProfile();
		
	}
	
	
	static void updateProfile() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		new ConnectionTest();
		System.out.print("Enter Name : ");
		String name = sc.nextLine();
		System.out.print("Enter Email : ");
		String email = sc.next();		
		
		String sql = "SELECT * FROM city";
		Statement st = ConnectionTest.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		ConnectionTest.makeDesign();
		System.out.println("Available cities : ");
		ConnectionTest.makeDesign();
		ArrayList<Integer> city_array = new ArrayList<Integer>();
		while(rs.next())
		{
			city_array.add(rs.getInt(1));
			System.out.println(rs.getInt(1)+" : "+rs.getString(2));
		}
		ConnectionTest.makeDesign();
		System.out.print("Enter city Id : ");
		int city = sc.nextInt();
		while(!city_array.contains(city))
		{
			System.out.print("Please Enter a valid city id : ");
			city = sc.nextInt();
		}
		
		
		sql = "UPDATE users SET user_email = '"+email+"' , user_name = '"+name+"',user_city = '"+city+"' WHERE user_email = '"+Login.see_user_profile+"'";
		st = ConnectionTest.con.createStatement();
		int x = st.executeUpdate(sql);
		if(x > 0)
		{
			ConnectionTest.makeDesign();
			System.out.println("**Profile Updated**");
			System.out.println("You Have to Restart Application");
			ConnectionTest.makeDesign();
			System.out.println("Please Login Again : ");
			Login.login();
		}
		else
		{
			ConnectionTest.makeDesign();
			System.out.println("Error...Please try again.");
			ConnectionTest.makeDesign();
		}
	}
	
	
	static void seeAllProducts() throws Exception
	{
		Admin.viewAllProducts();
	}
	
	
	static void seeProductsCategoryWise() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		new ConnectionTest();

		Admin.viewCategories();
		
		System.out.print("Enter Category Id : ");
		int x = sc.nextInt();
		
		String sql = "Select * from products WHERE product_category = "+x+" and product_status = 'active' ORDER BY product_id DESC";
		Statement st = ConnectionTest.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		ConnectionTest.makeDesign();
		System.out.println("**All Products**");
		ConnectionTest.makeDesign();
		System.out.println("#ID\tName\t\tAcualPrice\tShow Price\tQuantity");
		ConnectionTest.makeDesign();
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+"   "+rs.getString(2)+"\t\t"+rs.getInt(3)+"\t\t"+rs.getInt(4)+"\t\t"+rs.getInt(5));
		}
	}
	
	
	static void viewProduct() throws Exception
	{
		Admin.viewProduct();
	}
	
	
	static void buyProduct() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		new ConnectionTest();
		
		System.out.println("***Choose One***");
		System.out.println("1.See all products.");
		System.out.println("2.See products category wise");
		ConnectionTest.makeDesign();
		System.out.print("Choice : ");
		int x = sc.nextInt();
		if(x == 1)
			Admin.viewAllProducts();
		else
			User.seeProductsCategoryWise();
		ConnectionTest.makeDesign();
		System.out.print("Enter product id to buy : ");
		int id = sc.nextInt();
		
		String sql = "SELECT * FROM products WHERE product_id = "+id;
		Statement st = ConnectionTest.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		rs.next();
		if(rs.getInt(5) > 0)
		{
			int new_qty = rs.getInt(5)-1;
			sql = "UPDATE products SET product_qty = '"+new_qty+"' WHERE product_id = "+id;
			int temp = st.executeUpdate(sql);
			sql = "INSERT INTO business(bus_product_id,bus_user_id,user_city) VALUES('"+id+"','"+Login.see_user_id+"','"+Login.user_city+"')";
			st = ConnectionTest.con.createStatement();
			int result = st.executeUpdate(sql);
			if(result > 0 && temp > 0)
			{
				ConnectionTest.makeDesign();
				System.out.println("***Order placed.Thank You***");
			}
			else
			{
				System.out.println("Sorry...Error Occured...Please try again.");
			}
		}
		else
		{
			ConnectionTest.makeDesign();
			System.out.println("Sorry Currently Not available...");
		}
	}
	
	
	static void seeHistory() throws Exception
	{
		new ConnectionTest();
		String sql = "SELECT * FROM business INNER JOIN products on products.product_id = business.bus_product_id WHERE bus_user_id = "+Login.see_user_id+" ORDER BY business_id DESC";
		Statement st = ConnectionTest.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		System.out.println("::Your Shopping History::");
		ConnectionTest.makeDesign();
		System.out.println("#id\tProduct Name\t\tDate");
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+"   \t"+rs.getString(6)+"\t"+rs.getObject(4));
		}
	}
	
	
}
class Admin
{
	static void adminMenu() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Choose One.");
		ConnectionTest.makeDesign();
		System.out.print("1.Add New Product\t        ");
		System.out.println("2.View All Products");
		System.out.print("3.View Specific Product\t        ");
		System.out.println("4.Delete a Product");
		System.out.print("5.Add Category\t\t        ");
		System.out.println("6.Delete Category");
		System.out.print("7.View All Categories\t        ");
		System.out.println("8.Add Existing Product");
		System.out.print("9.See Statistics\t        ");
		System.out.println("10.Add Offers to Product");
		System.out.print("11.Add Offers to Category\t");
		System.out.println("12.View deleted products");
		System.out.print("13.View deleted Categories\t");
		System.out.println("14.View Products Category Wise");
		System.out.print("15.Add Cities\t\t\t");
		System.out.println("16.View Pricing");
		System.out.print("17.Edit Pricing\t");
		System.out.println("\n0.Logout");
		
		ConnectionTest.makeDesign();
		System.out.print("Choice : ");
		int choice = sc.nextInt();
		
		switch(choice)
		{
		case 1:Admin.addProduct();break;
		case 2:Admin.viewAllProducts();break;
		case 3:Admin.viewProduct();break;
		case 4:Admin.deleteProduct();break;
		case 5:Admin.addCategories();break;
		case 6:Admin.deleteCategories();break;
		case 7:Admin.viewCategories();break;
		case 8:Admin.addExisting();break;
		case 9:Admin.seeTransactions();break;
		case 10:Admin.addOfferToProduct();break;
		case 11:Admin.addOfferToCategory();break;
		case 12:Admin.viewDeletedProducts();break;
		case 13:Admin.viewDeletedCategories();break;
		case 14:User.seeProductsCategoryWise();break;
		case 16:Admin.viewPricing();break;
		case 17:Admin.editPricing();break;
		case 15:Admin.addCity();break;
		case 0:
			ConnectionTest.makeDesign();
			System.out.println("Thank you");
			ConnectionTest.makeDesign();
			System.exit(0);
		default:System.out.println("Invalid Choice.Try Again.");
		}
	}
	
	
	private static void editPricing() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		new ConnectionTest();
		
		System.out.println("Available Prices : ");
		String sql = "SELECT * FROM rate_card ORDER BY rate_id DESC";
		ConnectionTest.makeDesign();
		System.out.println("Id\t\tPrice Range\t\tPrice(Rupees)");
		ConnectionTest.makeDesign();
		Statement st = ConnectionTest.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+"\t\t"+rs.getString(2)+"\t\t"+rs.getInt(3));
		}
		ConnectionTest.makeDesign();
		
		System.out.print("Enter price id : ");
		int id = sc.nextInt();
		System.out.print("Enter Price : ");
		int rate = sc.nextInt();
		
		sql = "Update rate_card SET "
				+ "rate = "+rate+" WHERE rate_id = "+id;
		st = ConnectionTest.con.createStatement();
		int res = st.executeUpdate(sql);
		if(res > 0)
		{
			ConnectionTest.makeDesign();
			System.out.println("Rate Updated : ");
		}
		else
		{
			ConnectionTest.makeDesign();
			System.out.println("Error...Try again");
		}
	}


	static void viewPricing() throws Exception 
	{
		String sql = "SELECT * FROM rate_card ORDER BY rate_id DESC";
		new ConnectionTest();
		ConnectionTest.makeDesign();
		System.out.println("Price Range\t\tPrice(Rupees)");
		ConnectionTest.makeDesign();
		Statement st = ConnectionTest.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		while(rs.next())
		{
			System.out.println(rs.getString(2)+"\t\t"+rs.getInt(3));
		}
	}


	static void addCity() throws Exception 
	{
		Scanner sc = new Scanner(System.in);
		new ConnectionTest();
		System.out.print("Enter City Name : ");
		String city = sc.nextLine();
		String sql = "INSERT INTO city (city_name) VALUES('"+city+"')";
		Statement st = ConnectionTest.con.createStatement();
		int result = st.executeUpdate(sql);
		if(result > 0)
		{
			ConnectionTest.makeDesign();
			System.out.println("**City Added**");
		}
		else
		{
			ConnectionTest.makeDesign();
			System.out.println("Database Error");
		}
	}


	static void addProduct() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		new ConnectionTest();
		System.out.print("Enter Product name : ");
		String name = sc.nextLine();
		System.out.print("Enter product's actual price : ");
		int price = sc.nextInt();
		System.out.print("Enter product's show price : ");
		int show_price = sc.nextInt();
		System.out.print("Enter product's quantity : ");
		int qty = sc.nextInt();
		Admin.viewCategories();
		System.out.print("Enter category id : ");
		int category = sc.nextInt();
		
		String sql = "SELECT * FROM category WHERE cat_status = 'active'";
		Statement st = ConnectionTest.con.createStatement();
		ResultSet rs = st.executeQuery(sql);

		HashSet<Integer> hs = new HashSet<Integer>();
		while(rs.next())
		{
			hs.add(rs.getInt(1));
		}
		while(!hs.contains(category))
		{
			System.out.print("Invalid Category Id.\nPlease Try again : ");
			category = sc.nextInt();
		}
		
		sql = "INSERT INTO products(product_name,actual_price,show_price,product_qty,product_category) VALUES('"+name+"','"+price+"','"+show_price+"','"+qty+"','"+category+"')";
		int result = st.executeUpdate(sql);
		
		if(result > 0)
		{
			ConnectionTest.makeDesign();
			System.out.println("**Product Inserted**");
		}
		else
		{
			ConnectionTest.makeDesign();
			System.out.println("Some Error Occured...Try Again");
		}
	}
	
	
	static void viewAllProducts() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		new ConnectionTest();
		
		String sql = "SELECT * FROM products INNER JOIN category ON products.product_category = category.category_id and product_status = 'active' ORDER BY product_id DESC";
		Statement st = ConnectionTest.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		ConnectionTest.makeDesign();
		System.out.println("**All Active Products**");
		ConnectionTest.makeDesign();
		System.out.println("#ID\tName\t\tAcualPrice\tShow Price\tCategory\tQuantity");
		ConnectionTest.makeDesign();
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+"   "+rs.getString(2)+"\t\t"+rs.getInt(3)+"\t\t"+rs.getInt(4)+"\t\t"+rs.getString(9)+"\t\t"+rs.getInt(5));
		}
	}
	
	
	static void viewDeletedProducts() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		new ConnectionTest();
		
		String sql = "SELECT * FROM products INNER JOIN category ON products.product_category = category.category_id and product_status = 'deleted' ORDER BY product_id DESC";
		Statement st = ConnectionTest.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		ConnectionTest.makeDesign();
		System.out.println("**All Deleted Products**");
		ConnectionTest.makeDesign();
		System.out.println("#ID\tName\t\tAcualPrice\tShow Price\tCategory\tQuantity");
		ConnectionTest.makeDesign();
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+"   "+rs.getString(2)+"\t\t"+rs.getInt(3)+"\t\t"+rs.getInt(4)+"\t\t"+rs.getString(9)+"\t\t"+rs.getInt(5));
		}
	}
	
	
	static void viewProduct() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		new ConnectionTest();
		
		System.out.println("Select One");
		System.out.println("1.Product Name\t2.Product Id");
		int x = sc.nextInt();
		sc.nextLine(); //skipping \n
		if(x == 1)
		{
			System.out.print("Enter Name : ");
			String str = sc.nextLine();
			
			String sql = "SELECT * FROM products WHERE UPPER(product_name) = '"+str.toUpperCase()+"'";
			Statement st = ConnectionTest.con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next())
			{
				System.out.println("#ID\tName\t\tAcualPrice\tShow Price\tCategory\tQuantity\tOn Offer");
				ConnectionTest.makeDesign();
				System.out.println(rs.getInt(1)+"   "+rs.getString(2)+"\t"+rs.getInt(3)+"\t\t"+rs.getInt(4)+"\t\t"+rs.getInt(6)+"\t\t"+rs.getInt(5)+"\t\t"+rs.getString(7));	
				while(rs.next())
					System.out.println(rs.getInt(1)+"   "+rs.getString(2)+"\t"+rs.getInt(3)+"\t\t"+rs.getInt(4)+"\t\t"+rs.getInt(6)+"\t\t"+rs.getInt(5)+"\t\t"+rs.getString(7));			
			}
			else
			{
				System.out.println("Sorry...No Result Found.");
			}
		}
		else
		{
			System.out.print("Enter Product Id : ");
			int id = sc.nextInt();
			
			String sql = "SELECT * FROM products WHERE product_id = "+id;
			Statement st = ConnectionTest.con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next())
			{
				System.out.println("#ID\tName\t\tAcualPrice\tShow Price\tCategory\tQuantity\tOn Offer");
				ConnectionTest.makeDesign();
				System.out.println(rs.getInt(1)+"   "+rs.getString(2)+"\t"+rs.getInt(3)+"\t\t"+rs.getInt(4)+"\t\t"+rs.getInt(6)+"\t\t"+rs.getInt(5)+"\t\t"+rs.getString(7));	
			}
			else
			{
				System.out.println("Sorry...No Result Found.");
			}
		}
	}
	
	
	static void deleteProduct() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		new ConnectionTest();
		System.out.println("***Choose One***");
		System.out.println("1.See all products.");
		System.out.println("2.See products category wise");
		ConnectionTest.makeDesign();
		System.out.print("Choice : ");
		int x = sc.nextInt();
		if(x == 1)
			Admin.viewAllProducts();
		else
			User.seeProductsCategoryWise();
		ConnectionTest.makeDesign();
		System.out.print("Enter product id to delete : ");
		int id = sc.nextInt();
		
		String sql = "UPDATE products set product_status = 'deleted' WHERE product_id = "+id;
		Statement st = ConnectionTest.con.createStatement();
		int result = st.executeUpdate(sql);
		if(result > 0)
		{
			ConnectionTest.makeDesign();
			System.out.println("***Product Removed***");
		}
		else
		{
			ConnectionTest.makeDesign();
			System.out.println("Sorry...Some error occured.");			
		}
	}
	
	
	static void addCategories() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		new ConnectionTest();
		System.out.print("Enter Category Name : ");
		String category = sc.next();
		
		String sql = "INSERT INTO category(category) VALUES('"+category+"')";
		Statement st = ConnectionTest.con.createStatement();
		int result = st.executeUpdate(sql);
		
		if(result > 0)
		{
			ConnectionTest.makeDesign();
			System.out.println("**Category Inserted**");
		}
		else
		{
			ConnectionTest.makeDesign();
			System.out.println("Some Error Occured...Try Again");
		}
	}
	
	
	static void viewCategories() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		new ConnectionTest();
		
		String sql = "SELECT * FROM category WHERE cat_status = 'active'";
		Statement st = ConnectionTest.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		System.out.println("Available Categories : ");
		ConnectionTest.makeDesign();
		while(rs.next())
		{
			System.out.print(rs.getInt(1)+" : "+rs.getString(2)+"\t");
		}
		System.out.println();
	}
	
	
	static void viewDeletedCategories() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		new ConnectionTest();
		
		String sql = "SELECT * FROM category WHERE cat_status = 'deleted'";
		Statement st = ConnectionTest.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		System.out.println("Available Categories : ");
		ConnectionTest.makeDesign();
		while(rs.next())
		{
			System.out.print(rs.getInt(1)+" : "+rs.getString(2)+"\t");
		}
		System.out.println();		
	}
	
	
	static void deleteCategories() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		new ConnectionTest();
		Admin.viewCategories();
		System.out.print("Enter category id to delete : ");
		int del_cat = sc.nextInt();
		
		String sql = "UPDATE category set cat_status = 'deleted' WHERE category_id = "+del_cat;
		Statement st = ConnectionTest.con.createStatement();
		int cat_res = st.executeUpdate(sql);
		sql = "UPDATE products set product_status = 'deleted' WHERE product_category = "+del_cat;
		int pro_res = st.executeUpdate(sql);
		
		if(cat_res > 0)
		{
			ConnectionTest.makeDesign();
			System.out.println("***Category Deleted***");
		}
		else
		{
			System.out.println("Some Error Occured.");
		}
	}
	static void addExisting() throws Exception
	{
		new ConnectionTest();
		Scanner sc = new Scanner(System.in);
		System.out.println("***Choose One***");
		System.out.println("1.See all products.");
		System.out.println("2.See products category wise");
		ConnectionTest.makeDesign();
		System.out.print("Choice : ");
		int x = sc.nextInt();
		if(x == 1)
			Admin.viewAllProducts();
		else
			User.seeProductsCategoryWise();
		ConnectionTest.makeDesign();
		System.out.print("Enter product id to Add units : ");
		int id = sc.nextInt();
		System.out.print("Enter no of units : ");
		int units = sc.nextInt();
		
		String sql = "SELECT * FROM products WHERE product_id = "+id;
		Statement st = ConnectionTest.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		rs.next();
		
		int new_unit = rs.getInt(5)+units;
		sql = "UPDATE products SET product_qty = "+new_unit+" WHERE product_id = "+id;
		int res = st.executeUpdate(sql);
		if(res > 0)
		{
			ConnectionTest.makeDesign();
			System.out.println("***Quantity Updated***");
		}
		else
		{
			System.out.println("Sorry...Some error occured.");
		}
	}
	static void seeTransactions() throws Exception
	{
		new ConnectionTest();
		String sql = "SELECT * FROM business natural join city natural join products\r\n" + 
				     "where city.city_id = business.user_city and products.product_id = business.bus_product_id ORDER BY business_id DESC";
		Statement st = ConnectionTest.con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		System.out.println("::All Shopping History::");
		ConnectionTest.makeDesign();
		System.out.println("#id\tProduct Name\t\tDate\t\t\tCity");
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+"     \t"+rs.getString(9)+"\t    "+rs.getObject(4)+"\t    "+rs.getObject(7));
		}		
	}
	private static void addOfferToCategory() throws Exception 
	{
		Scanner sc = new Scanner(System.in);
		new ConnectionTest();
		Admin.viewCategories();
		System.out.println("Chhose One (Id): ");
		int category = sc.nextInt();

		String sql = "SELECT * FROM category WHERE cat_status = 'active'";
		Statement st = ConnectionTest.con.createStatement();
		ResultSet rs = st.executeQuery(sql);

		HashSet<Integer> hs = new HashSet<Integer>();
		while(rs.next())
		{
			hs.add(rs.getInt(1));
		}
		while(!hs.contains(category))
		{
			System.out.print("Invalid Category Id.\nPlease Try again : ");
			category = sc.nextInt();
		}
		
		System.out.println("Enter % off : ");
		double d = 1-(sc.nextDouble()/100);
		
		sql = "UPDATE products SET show_price = actual_price*"+d+" WHERE category = "+category;
		int x = st.executeUpdate(sql);
		
		if(x>0)
		{
			ConnectionTest.makeDesign();
			System.out.println("***Offer Applied***");
		}
		else
		{
			ConnectionTest.makeDesign();
			System.out.println("Sorry...Error occured.");
		}
		
	}
	private static void addOfferToProduct() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		new ConnectionTest();
		System.out.println("***Choose One***");
		System.out.println("1.See all products.");
		System.out.println("2.See products category wise");
		ConnectionTest.makeDesign();
		System.out.print("Choice : ");
		int x = sc.nextInt();
		if(x == 1)
			Admin.viewAllProducts();
		else
			User.seeProductsCategoryWise();
		ConnectionTest.makeDesign();
		System.out.print("Enter product id to Add units : ");
		int id = sc.nextInt();
		System.out.print("Enter % off : ");
		double off = sc.nextDouble();
		double d = 1.0-(off/100);
		
		String sql = "UPDATE products SET show_price = actual_price*"+d+" WHERE product_id = "+id;
		Statement st = ConnectionTest.con.createStatement();
		int result = st.executeUpdate(sql);

		if(result > 0)
		{
			ConnectionTest.makeDesign();
			System.out.println("***Offer Applied***");
		}
		else
		{
			ConnectionTest.makeDesign();
			System.out.println("Sorry...Some error occured.");
		}
	}
}
