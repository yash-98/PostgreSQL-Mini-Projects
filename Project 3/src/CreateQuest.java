import java.util.*;
import java.util.Date;
import java.net.*;
import java.text.*;
import java.lang.*;
import java.io.*;
import java.sql.*;
import pgpass.*;

/*============================================================================
CLASS CreateQuest
============================================================================*/

public class CreateQuest {
    private Connection conDB;		// Connection to the database system.
    private String url;				// URL: Which database?
    private String user = "yash9698";			// Database user account
    private PreparedStatement querySt   = null;   // The query handle.
    
    private String day;
    private String realm;
    private String theme;
    private int amount;
    private float seed;
    private boolean changeSeed = false;
    
    private Date tempDate;
    private	String tempQuery;
    private ArrayList<String> treasures = new ArrayList<String>();
    
    

    // Constructor
    public CreateQuest (String[] args) {
        
    	// Set up the DB connection.
    	this.dbDriverCheck();

        // URL: Which database?
        url = "jdbc:postgresql://db:5432/";
        
        // Check supplied args
        this.checkArgs(args);

        // set up act info
        this.testConnect();
         
        this.setAutoCommit(false);
        
        // Check arguments pre-condition
        this.preCondition();
        
        // Change seed only if needed
        if(changeSeed) {
        	this.setSeed();
        }
    	
    	// Add the Quest to the "Quest" table.
    	this.addQuest();
        
    	this.assignTreasure();
    	
        this.updateLoot();

    	
        try {
			conDB.close();
		} catch (SQLException e) {
			System.out.println("The Database Connection Failed to be closed.");
		}
    }
    
    private void dbDriverCheck() {
        try {
            // Register the driver with DriverManager.
            Class.forName("org.postgresql.Driver").newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    private void checkArgs(String[] args) {
    	if(args.length < 4 || args.length > 6) {
            System.out.println("\nUsage: java CreateQuest 'XX-XX-XXXX' realm 'theme name' maxAmount user(optional) seed(optional)");
            System.exit(0);
    	}
    	else{
			day = args[0];			
			realm = args[1];
			theme = args[2];
			
			//Add User only if the arg length is 4
			if(args.length > 4) {
				user = args[4];
			}
			
    		try {
        		//Check Date Format
    			tempDate = new SimpleDateFormat("yyyy-MM-dd").parse(day);
    			
    			// Check amount is integer or not.
    			amount = Integer.parseInt(args[3]);
    			
    			//Add Seed only if the argument for seed is provided.
    			if(args.length == 6) {
    				
    				//Check if the seed supplied is a float or not.
        			seed = Float.parseFloat(args[5]);
        			changeSeed = true;

    			}
    		}catch (NumberFormatException e) {
    			System.out.printf("\nAmount [%s] entered is not an Integer OR Seed[%s] entered is not a float", args[3], args[5]);
    			System.exit(0);
    		}catch (ParseException e) {
				System.out.printf("\nDate [%s] Not in the format of 'YYYY-MM-DD'", day);
				System.exit(0);
			}
    	}
    }
    
    private void testConnect() {
    	
        Properties props = new Properties();
        try {
            String passwd = PgPass.get("db", "*", user, user);
            props.setProperty("user",    user);
            props.setProperty("password", passwd);
        } catch(PgPassException e) {
            System.out.print("\nCould not obtain PASSWD from <.pgpass>.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Initialize the connection.
        try {
            conDB = DriverManager.getConnection(url, props);
        } catch(SQLException e) {
            System.out.print("\nSQL: database connection error.\n");
            System.out.println(e.toString());
            System.exit(0);
        } 
    }
    
    private void setAutoCommit(boolean state) {
    	
        // Let's have autocommit turned off.  No particular reason here.
        try {
            conDB.setAutoCommit(state);
        } catch(SQLException e) {
            System.out.print("\nFailed trying to turn autocommit off.\n");
            e.printStackTrace();
            System.exit(0);
        } 
    	
    }
    
    private void preCondition() {
    	
			
    	if(tempDate.before(Calendar.getInstance().getTime())) {
    		System.out.printf("\nDay [%s] is not in future");
    		System.exit(0);
    	}
    	
    	// Check Realm
    	this.checkRealm();
    	
    	// Check Treasure Max Amount.
    	this.checkTreasureAmount();
    	
    	if((seed >= 1 || seed <= -1) && changeSeed) {
    		System.out.printf("\nSeed value [%f] is not between (-1) - (1)", seed);
    		System.exit(0);
    	}
    	
    }
    
    private void checkRealm() {
    	tempQuery =	"SELECT *	"
                +	"FROM realm	"
                +	"WHERE realm = ?";

        // Prepare the query.
    	try {
            querySt = conDB.prepareStatement(tempQuery);
            querySt.setString(1, this.realm);
            
            System.out.println("The Query to check Realm has been prepared succesfully.");
        } catch(SQLException e) {
            System.out.println("SQL#1 failed to prepare query for checking Realm.");
            System.out.println(e.toString());
            System.exit(0);
        }
		
		if(runQuery() == 0) {
			System.out.printf("\nRealm %s does not exist", realm);
			System.exit(0);
		}
		
		this.closeQueryHandle();
    }
    
    private void checkTreasureAmount() {
    	tempQuery =	"SELECT sum(sql)       "
        		+	"FROM treasure";

        // Prepare the query.
        try {
            querySt = conDB.prepareStatement(tempQuery);
            
            System.out.println("The Query to get max treasure amount prepared succesfully.");
        } catch(SQLException e) {
            System.out.println("SQL#2 failed to prepare query to find max treasure amount.");
            System.out.println(e.toString());
            System.exit(0);
        }
		
		if(runQuery() < amount) {
			System.out.printf("\nAmount [%d] exceeds the maximum amount possible.",  amount);
			System.exit(0);
		}
		
		this.closeQueryHandle();
    }
    
    private void setSeed() {
    	
    	String	queryText = "";     // The SQL text.
    	
    	queryText = "SELECT SETSEED(?)";

        // Prepare the query.
        try {
            querySt = conDB.prepareStatement(queryText);
            querySt.setString(1, Float.toString(seed));
            
            System.out.println("The Query to set seed has been prepared succesfully.");
 
            } catch(SQLException e) {
                System.out.println("SQL#3 failed to prepare query for Seeding");
            System.out.println(e.toString());
            System.exit(0);
        }
        
        this.runQuery();
        
		this.closeQueryHandle();
    }
    
    private void addQuest() {
    	tempQuery = "INSERT	INTO	Quest (theme, realm, day, succeeded) VALUES"
    			+	" (?, ?, ?, NULL)";
    	
    	java.sql.Date dt = new java.sql.Date(tempDate.getTime());

        // Prepare the query.
        try {
            querySt = conDB.prepareStatement(tempQuery);
            querySt.setString(1, this.theme);
            querySt.setString(2, this.realm);
            querySt.setDate(3, dt);
            
            System.out.println("The query to add quest to the 'quest' table has been prepared succesful.");
        } catch(SQLException e) {
            System.out.println("SQL#4 failed to prepare for adding quest.");
            System.out.println(e.toString());
            System.exit(0);
        }
        
        this.runChangeQuery(true);
        
		this.closeQueryHandle();
    }
    
    private int runQuery() {
    	
        ResultSet         answers   = null;   // A cursor.
        int				  result = 0;

        // Execute the query.
        try {
            answers = querySt.executeQuery();
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Any answer?
        try {
            if (answers.next()) {
            	try {
                result = Integer.parseInt(answers.getString(1));
            	}catch (NumberFormatException e) {
            		result = 1;
            	}
            }
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in cursor.");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Close the cursor.
        try {
            answers.close();
        } catch(SQLException e) {
            System.out.print("SQL#1 failed closing cursor.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        return result;
    }
    
    private void runChangeQuery(boolean showUpdate) {

        // Execute the query.
        try {
            querySt.execute();
            conDB.commit();
            
            if(showUpdate) {
	            System.out.println("The Database has been updated");
            }
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }
    	
    }
    
    private void assignTreasure() {
    	
    	int holder = 0;
    	tempQuery = "SELECT *	"
				+	"FROM Treasure	"
				+	"ORDER BY random();";
        
        try {
            querySt = conDB.prepareStatement(tempQuery);
            
            System.out.println("The Query to get seeded(if provided) random treasure has been prepared succesfully.");
        } catch(SQLException e) {
            System.out.println("SQL#5 failed to prepare query for getting random treasure.");
            System.out.println(e.toString());
            System.exit(0);
        }
    	
    	ResultSet	answers = null;   // A cursor.

        // Execute the query.
        try {
            answers = querySt.executeQuery();
            
            System.out.println("List of Random Treasures have been succesfuly obtained.");
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Any answer?
        while(holder < amount) {
	        try {
	            if (answers.next()) {
	            	
	            	treasures.add(answers.getString(1));
	            	holder += answers.getInt(2);
	            }
	        } catch(SQLException e) {
	            System.out.println("SQL#1 failed in cursor.");
	            System.out.println(e.toString());
	            System.exit(0);
	        }
        }

        // Close the cursor.
        try {
            answers.close();
        } catch(SQLException e) {
            System.out.print("SQL#1 failed closing cursor.\n");
            System.out.println(e.toString());
            System.exit(0);
        }
        
		this.closeQueryHandle();
        
        System.out.printf("\nRandom Treasures with total value of greater than equal to %d have been selected.", amount);
    	
    }
    
    private void updateLoot() {
    	
    	java.sql.Date dt = new java.sql.Date(tempDate.getTime());
    	
    	for(int i = 0; i < treasures.size(); i++) {
    		
            tempQuery = " 		INSERT INTO Loot (loot_id, treasure, theme, realm, day, login) values"
    							+" (?, ?, ?, ?, ?, NULL);";
    		
            try {
                querySt = conDB.prepareStatement(tempQuery);
                querySt.setInt(1, (i + 1));
                querySt.setString(2, treasures.get(i));
                querySt.setString(3, theme);
                querySt.setString(4, realm);
                querySt.setDate(5, dt);

            } catch(SQLException e) {
                System.out.println("SQL#1 failed to prepare query to insert loot into Loot Table.");
                System.out.println(e.toString());
                System.exit(0);
            }
            
            this.runChangeQuery(false);  
            this.closeQueryHandle();
    	}

        System.out.printf("\nThe Loot Table has been updated with the new randomized loots added to the quest %s \n", theme);
    }
    
    private void closeQueryHandle() {
    	
        // We're done with the handle.
        try {
            querySt.close();
        } catch(SQLException e) {
            System.out.print("SQL#1 failed closing the handle.\n");
            System.out.println(e.toString());
            System.exit(0);
        }
    }
    	

    public static void main(String[] args) {
        CreateQuest cq = new CreateQuest(args);

    }
}