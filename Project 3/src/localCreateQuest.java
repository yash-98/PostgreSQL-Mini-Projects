import java.util.*;
import java.util.Date;
import java.net.*;
import java.text.*;
import java.lang.*;
import java.io.*;
import java.sql.*;
//import pgpass.*;

/*============================================================================
CLASS CreateQuest
============================================================================*/

public class localCreateQuest {
    private Connection conDB;		// Connection to the database system.
    private String url;				// URL: Which database?
    private String user = "postgres";			// Database user account
    private PreparedStatement querySt   = null;   // The query handle.
    
    private String day;
    private String realm;
    private String theme;
    private int amount;
    private float seed;
    private	String tempQuery;
    private ArrayList<String> treasures = new ArrayList<String>();
    
    

    // Constructor
    public localCreateQuest (String[] args) {
        // Set up the DB connection.
    	this.dbDriverCheck();

        // URL: Which database?
        url = "jdbc:postgresql://localhost:5432/project3";
        
        // Check supplied args
        this.checkArgs(args);

        // set up acct info
        this.testConnect();
        
        
        // Check arguments pre-condition
        this.preCondition();
        
    	tempQuery = 		"INSERT	INTO	Quest (theme, realm, day, succeeded) VALUES"
				  + " (?, ?, ?, NULL);";
    	
    	this.prepareQuest(tempQuery);
    	
    	this.runChangeQuery();
        
    	this.assignTreasure();
    	
        this.updateLoot();

    	
        try {
			conDB.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
    	if(args.length < 4) {
            System.out.println("\nUsage: java CreateQuest 'XX-XX-XXXX' realm 'theme name' maxAmount user(optional) seed(optional)");
            System.exit(0);
    	}
    	else{
    		try {
    			day = args[0];
    			System.out.println(day);
    			realm = args[1];
    			theme = args[2];
    			amount = Integer.parseInt(args[3]);
    			user = args[4];
    			seed = Float.parseFloat(args[5]);
    		}catch (NumberFormatException e) {
    			System.out.println("\nFloat Not in the format of 0.xxx while actual is " +args[1]);
    			System.out.println(e.toString());
    			System.exit(0);
    		}
    	}
    }
    
    private void testConnect() {
        Properties props = new Properties();
                
        props.setProperty("user",	this.user);
        props.setProperty("password", "");


        // Initialize the connection.
        try {
            conDB = DriverManager.getConnection(url, props);
        } catch(SQLException e) {
            System.out.print("\nSQL: database connection error.\n");
            System.out.println(e.toString());
            System.exit(0);
        } 
    }
    
    private void preCondition() {
    	
    	Date tempDay;
		try {
			tempDay = new SimpleDateFormat("yyyy-MM-dd").parse(day);
			System.out.println("Also date " +tempDay);
			
	    	if(tempDay.before(Calendar.getInstance().getTime())) {
	    		System.out.println("\nday is not in future");
	    		System.exit(0);
	    	}
		}catch (ParseException e) {
			System.out.println("\nDate Not in the format of 'YYYY-MM-DD'");
			System.out.println(e.toString());
			System.exit(0);
		}
    	
    	tempQuery =       "SELECT *       "
		                + "FROM realm "
		                + "WHERE realm = ?     ";
    	
    	prepareQuery(tempQuery);
    	
    	if(runQuery() == 0) {
    		System.out.println("\nrealm does not exist");
    		System.exit(0);
    	}
    	
    	tempQuery =       "SELECT sum(sql)       "
                		+ "FROM treasure ";

    	prepareQueryAmt(tempQuery);
    	
    	if(runQuery() < amount) {
    		System.out.println("\namount exceeds what is possible");
    		System.exit(0);
    	}
    	
    	if(seed > 1 || seed < -1) {
    		System.out.println("\nseed value is improper");
    		System.exit(0);
    	}
    	
    }
    
    private void prepareQuery(String query) {
    	
    	String	queryText = "";     // The SQL text.
    	
    	queryText = query;

            // Prepare the query.
            try {
                querySt = conDB.prepareStatement(queryText);
                querySt.setString(1, this.realm);
            } catch(SQLException e) {
                System.out.println("SQL#1 failed in prepare");
                System.out.println(e.toString());
                System.exit(0);
            }
    }
    
    private void setSeed(String query) {
    	
    	String	queryText = "";     // The SQL text.
    	
    	queryText = query;

            // Prepare the query.
            try {
                querySt = conDB.prepareStatement(queryText);
                querySt.setFloat(1, seed);
                System.out.println("The Query is " +querySt);
            } catch(SQLException e) {
                System.out.println("SQL#1 failed in prepare");
                System.out.println(e.toString());
                System.exit(0);
            }
    	
    }
    
    private void prepareQueryAmt(String query) {
    	String	queryText = "";     // The SQL text.
    	
    	queryText = query;

            // Prepare the query.
            try {
                querySt = conDB.prepareStatement(queryText);
                System.out.println(querySt);
            } catch(SQLException e) {
                System.out.println("SQL#1 failed in prepare");
                System.out.println(e.toString());
                System.exit(0);
            }
    }
    
    private void prepareQuest(String query) {
    	String	queryText = "";     // The SQL text.
    	Date date = null;
			
	    	queryText = query;
	    	try {
	    		System.out.println("Also another date " +day);
	    		
				date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	System.out.println("The Date in the object date is " +
			date);
	    	java.sql.Date dt = new java.sql.Date(date.getTime());
	    	System.out.println("The date shows as " +dt);

	            // Prepare the query.
	            try {
	                querySt = conDB.prepareStatement(queryText);
	                querySt.setString(1, this.theme);
	                querySt.setString(2, this.realm);
	                querySt.setDate(3, dt);
	                
	                System.out.println(querySt);
	                
	            } catch(SQLException e) {
	                System.out.println("SQL#1 failed in prepare");
	                System.out.println(e.toString());
	                System.exit(0);
	            }
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
            	System.out.println("Query Has Passed.");
            } else {
                result = 0;
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
    
    private void runChangeQuery() {

        // Execute the query.
        try {
            querySt.execute();
            System.out.println("The Table is updated");
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }
    	
    }
    
    private void assignTreasure() {
    	
    	int holder = 0;
    	String queryText;
        queryText = " select *, "
        			+"setseed(?)"
							+" from Treasure"
							+" order by random();";
        
        try {
            querySt = conDB.prepareStatement(queryText);
            querySt.setFloat(1, seed);
            System.out.println("The query is " +querySt);
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
        }
    	
    	ResultSet         answers   = null;   // A cursor.

        // Execute the query.
        try {
            answers = querySt.executeQuery();
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Any answer?
        while(holder < amount) {
	        try {
	            if (answers.next()) {
	            	System.out.println("The Loot is " +answers.getString(1) +" and te value is " +answers.getInt(2));
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
        
        System.out.println("The Array of treasures is " +treasures.toString());
        
    	System.out.println("The Total Amount is " +holder);
    	
    }
    
    private void updateLoot() {
    	
        String queryText; 
        
        queryText = " 		INSERT INTO Loot (loot_id, treasure, theme, realm, day, login) values"
							+" (?, ?, ?, ?, ?, NULL);";
        
    	Date date = null;
    	try {
    		System.out.println("Also another date " +day);
    		
			date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	System.out.println("The Date in the object date is " +
		date);
    	java.sql.Date dt = new java.sql.Date(date.getTime());
    	
    	for(int i = 0; i < treasures.size(); i++) {
    		
            queryText = " 		INSERT INTO Loot (loot_id, treasure, theme, realm, day, login) values"
    							+" (?, ?, ?, ?, ?, NULL);";
    		
            try {
                querySt = conDB.prepareStatement(queryText);
                querySt.setInt(1, (i + 1));
                querySt.setString(2, treasures.get(i));
                querySt.setString(3, theme);
                querySt.setString(4, realm);
                querySt.setDate(5, dt);
                System.out.println("The query is " +querySt);
            } catch(SQLException e) {
                System.out.println("SQL#1 failed in prepare");
                System.out.println(e.toString());
                System.exit(0);
            }
            
            this.runChangeQuery();
    		
    	}
    	
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
        localCreateQuest cq = new localCreateQuest(args);

    }
 }