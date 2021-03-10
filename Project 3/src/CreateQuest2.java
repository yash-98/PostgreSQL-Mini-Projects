import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import pgpass.*;

public class CreateQuest2 {
	private Connection conDB;       // Connection to the database system.
    private String url;             // URL: Which database?
    private String user = "tudion"; // Database user account
    
    private Date date;
	private String realm;
	private String theme;
	private int amount;
	private String userName;
	private float seed = -10f;
    
    public CreateQuest2(String[] args) {
    	// parse arguments
    	parseArgs(args);
 
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
    	
    	//url = "jdbc:postgresql://db:5432/<dbname>?currentSchema=yrb";
        url = "jdbc:postgresql://db:5432/";
        
        Properties props = new Properties();
        try {
            String passwd = PgPass.get("db", "*", user, user);
//            if (passwd == null) { 
//            	throw new PgPassException();
//            }
            props.setProperty("user", user);
            props.setProperty("password", "214225189");
            // props.setProperty("ssl","true"); // NOT SUPPORTED on DB
        } catch(PgPassException e) {
            System.out.print("\nCould not obtain PASSWD from <.pgpass>.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Initialize the connection.
        try {
            // Connect with a fall-thru id & password
            //conDB = DriverManager.getConnection(url,"<username>","<password>");
            conDB = DriverManager.getConnection(url, props);
        } catch(SQLException e) {
            System.out.print("\nSQL: database connection error.\n");
            System.out.println(e.toString());
            System.exit(0);
        }    
        
        if (checkDate(this.date) && qcheckRealm(this.realm) && qcheckAmount(this.amount)) {
        	if (!(Float.compare(this.seed, -10f) == 0)) {
        		if (checkSeed(this.seed)) {
        			setSeed(this.seed);
        		}
        	} 
        	addQuest(this.theme, this.realm, this.date);
        	addLoot(this.theme, this.realm, this.date, this.amount);
        }
        
        // Close the connection.
        try {
            conDB.close();
        } catch(SQLException e) {
            System.out.print("\nFailed trying to close the connection.\n");
            e.printStackTrace();
            System.exit(0);
        }    
    }
    
    private void parseArgs(String[] args) {
    	if (args.length < 4) {
    		System.out.println("Not enough Arguemnts...");
    	}
    	String rawDate = args[0];
    	String rawRealm = args[1];
    	String rawTheme = args[2];
    	String rawAmount = args[3];
    	String rawUser = null;
    	String rawSeed = null;
    	if (args.length > 4) { rawUser = args[4]; }
    	if (args.length > 5) { rawSeed = args[5]; }
    	
    	this.date = getDate(rawDate);
    	this.realm = rawRealm;
    	this.theme = rawTheme;
    	this.amount = Integer.parseInt(rawAmount);
    	if (rawUser != null) { this.user = rawUser; }
    	if (rawSeed != null) { this.seed = Float.parseFloat(rawSeed); }
    }
    
    private Date getDate(String rawDate) {
    	//format for date 'yyyy-mm-dd'
    	boolean valid = true;
    	Date returnedDate = null;
    	String[] dateStrArr = rawDate.split("-");
    	int year = Integer.parseInt(dateStrArr[0]);
    	int month = Integer.parseInt(dateStrArr[1]);
    	int day = Integer.parseInt(dateStrArr[2]);
    	Calendar inputDate = Calendar.getInstance();
    	inputDate.set(year, month, day);
    	
    	if(inputDate.getTime().after(Calendar.getInstance().getTime())) {
    		returnedDate = inputDate.getTime();
    	}
    	return returnedDate;
    }
    
    private boolean checkDate(Date date) {
    	boolean isValid = true;
    	if (date == null) {
    		System.out.println("day is not in future");
    		isValid = false;
    	}
    	return isValid;
    }
    
    private boolean qcheckRealm(String realm) {
    	boolean isValid = true;
    	// SQL statement
        String            queryText = "";     // The SQL text.
        PreparedStatement querySt   = null;   // The query handle.
//        int         	  answers   = 0;   // A cursor.
        ResultSet answers = null;
        
        queryText =
            "SELECT EXISTS"
          + "(" 
          + "SELECT realm FROM quest "
          + "WHERE realm = ?         "
          + ")";
        
     // Prepare the query.
        querySt = prepareQuery(queryText);
        
     // Execute the query.
        try {
            querySt.setString(1, realm);
            answers = querySt.executeQuery();
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }
        // Any answer?
        try {
            if (answers.next()) {
            	//exists is the column name
            	if (!answers.getString("exists").equals("t")) {
            		System.out.println("realm does not exist");
            		isValid = false;
            	} 
            } else {
            	System.out.println(answers);
            }
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in cursor.");
            System.out.println(e.toString());
            System.exit(0);
        }
        
        closeCursor(answers);
        closeQuery(querySt);
        
    	return isValid;
    }
    
    private boolean qcheckAmount(int amount) {
    	boolean isValid = true;
    	// SQL statement
        String            queryText = "";     // The SQL text.
        PreparedStatement querySt   = null;   // The query handle.
        ResultSet answers = null;
        
        queryText =
            "SELECT SUM(sql) "
          + "FROM treasure   "; 
        
     // Prepare the query.
        querySt = prepareQuery(queryText);
        
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
            	//exists is the column name
            	if (!(answers.getInt("sum") >= amount)) {
            		System.out.println("amount exceeds what is possible");
            		isValid = false;
            	} 
            } else {
            	System.out.println(answers);
            }
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in cursor.");
            System.out.println(e.toString());
            System.exit(0);
        }
        
        closeCursor(answers);
        closeQuery(querySt);
        
    	return isValid;
    }
    
    private boolean checkSeed(float seed) {
    	boolean isValid = true;
    	if ((Float.compare(seed, 1f) > 0) || (Float.compare(seed, -1f) < 0)) {
    		System.out.println("seed value is improper");
    		isValid = false;
    	}
    	return isValid;
    }
    
    /**
     * Add new quest to the database
     * @param theme
     * @param realm
     * @param date
     * @param amount
     */
    private void addQuest(String theme, String realm, Date date) {
    	// SQL statement
        String            queryText = "";     // The SQL text.
        PreparedStatement querySt   = null;   // The query handle.
        int         	  answers   = 0;   // A cursor.

        queryText =
            "INSERT INTO quest(theme, realm, day) VALUES"
          + "(?,?,?)";

        // Prepare the query.
        querySt = prepareQuery(queryText);

        // Execute the query.
        try {
            querySt.setString(1, theme);
            querySt.setString(2, realm);
            
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            querySt.setDate(3, sqlDate);
            answers = querySt.executeUpdate();
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }
        
        // We're done with the handle.
        closeQuery(querySt);

        return;
    }
    
    private void addLoot(String theme, String realm, Date date, int amount) {
    	// SQL statement
        String            queryText = "";     // The SQL text.
        PreparedStatement querySt   = null;   // The query handle.
        int         	  answers   = 0;   // A cursor.
        int 			  loot_id   = 1;
//        ResultSet answers = null;
        
        String            treasureQueryText = "";     // The SQL text.
        PreparedStatement treasureQuerySt   = null;   // The query handle.
        ResultSet 		  treasureAnswers   = null;   // A cursor.
    
        int total = 0;
        
        treasureQueryText = 
        	"SELECT *    		" 
          + "FROM treasure 		" 
          + "ORDER BY random()  ";

        queryText =
            "INSERT INTO loot(loot_id, treasure, theme, realm, day) VALUES"
          + "(?,?,?,?,?)";

        // Prepare the queries.
        treasureQuerySt = prepareQuery(treasureQueryText);
        querySt = prepareQuery(queryText);

        // Execute the query.
        try {
        	treasureAnswers = treasureQuerySt.executeQuery();
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }
        try {
        	while (total < amount) {
        		treasureAnswers.next();
        		
        		querySt.setInt(1, loot_id);
                querySt.setString(2, treasureAnswers.getString("treasure"));
        		querySt.setString(3, theme);
                querySt.setString(4, realm);
          
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                querySt.setDate(5, sqlDate);
                answers = querySt.executeUpdate();
                
                loot_id ++;
                total = total + treasureAnswers.getInt("sql");
        	}
        } catch(SQLException e) {
        	System.out.println("SQL#2 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }
        
        // We're done with the handle.
        closeCursor(treasureAnswers);
        closeQuery(querySt);
        closeQuery(treasureQuerySt);

        return;
    }
    
    private void setSeed(float seed) {
    	// SQL statement
        String            queryText = "";     // The SQL text.
        PreparedStatement querySt   = null;   // The query handle.
        ResultSet         answers   = null;   // A cursor.
       
        queryText =
            "SELECT setseed"
          + "(?)";

        // Prepare the query.
        querySt = prepareQuery(queryText);

        // Execute the query.
        try {
            querySt.setFloat(1, seed);
            answers = querySt.executeQuery();
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }
        
        // We're done with the handle.
        closeCursor(answers);
        closeQuery(querySt);
        
        return;
    }
    
    private PreparedStatement prepareQuery(String queryText) {
    	PreparedStatement querySt = null;
    	try {
            querySt = conDB.prepareStatement(queryText);
        } catch(SQLException e) {
            System.out.println("SQL#1 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
        }
    	return querySt;
    }
    
    /**
     * Closes the cursor for queries (NOT updates (insert, update, delete))
     * @param answers 
     */
    private void closeCursor(ResultSet answers) {
    	// Close the cursor.
      try {
          answers.close();
      } catch(SQLException e) {
          System.out.print("SQL#1 failed closing cursor.\n");
          System.out.println(e.toString());
          System.exit(0);
      }
    }
    
    /**
     * Closes the query statement.
     * @param preparedQuery
     */
    private void closeQuery(PreparedStatement preparedQuery){
    	try {
            preparedQuery.close();
        } catch(SQLException e) {
            System.out.print("SQL#1 failed closing the handle.\n");
            System.out.println(e.toString());
            System.exit(0);
        }
    }
    
    public static void main (String[] args) {
    	CreateQuest2 cq = new CreateQuest2(args);
    }
}
