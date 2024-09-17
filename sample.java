import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
@SuppressWarnings("unused")

public class sample {

    // Database credentials
    final static String HOSTNAME = "popo0002-sql-server.database.windows.net";
    final static String DBNAME = "cs-dsa-4513-sql-db";
    final static String USERNAME = "popo0002";
    final static String PASSWORD = "*****";

    // Database connection string
    final static String URL = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
            HOSTNAME, DBNAME, USERNAME, PASSWORD);

    // User input prompt//
    final static String PROMPT = 
            "\nPlease select one of the options below: \n" +
            "1) Enter a new customer \n" + 
            "2) Enter a new department \n" +
            "3) Enter a new process id and its department together with its type and information relevant to the type \n" +
            "4) Enter a new assembly with its customer name, assembly details, assembly id, and date ordered and associate it with one or more processes \n" +
            "5) Create a new account and associate it with the process, assembly, or department to which it is applicable \n" + 
            "6) Enter a new job, given its job number, assembly id, process id, and date the job commenced \n" +
            "7) At the completion of a job, enter the date it completed and the information relevant to the type of job \n" +
            "8) Enter a new cost transaction and its sup cost and update all the costs (details) of the affected accounts \n" + 
            "9) Retrieve the total cost incurred on an assembly id \n" + 
            "10) Retrieve the total labor time within a department for jobs completed in the department on given date \n" +
            "11) Retrieve the processes through which a given assembly id has passed so far (in date-commenced order) and the department responsible for each process \n" +
            "12) Retrieve the customers (in name order) whose category is in a given range \n" +
            "13) Delete all cut jobs whose job number is in a given range \n" + 
            "14) Change the color of a given paint job \n" +
            "15) Import: enter new customers from a data file \n" +
            "16) Export: Retrieve the customers (in name order) whose category is in a given range and output them to a data file instead of screen \n" +
            "17) Quit \n"
            ;

    public static void main(String[] args) throws SQLException {
 
        final Scanner sc = new Scanner(System.in); // Scanner is used to collect the user input
        String option = ""; // Initialize user option selection as nothing
        while (!option.equals("17")) { // As user for options until option 3 is selected
            System.out.println(PROMPT); // Print the available options
            option = sc.next(); // Read in the user option selection
            String name;
            String address;
            int category;
            int department_number;
            String department_data;
            int process_id;
            String process_data;
            String paint_type;
            String painting_method;
            String cutting_type;
            String machine_type;
            String fit_type;
            String assembly_details;
            int assembly_id;
            String date_ordered; //form "yyyy-mm-dd"
            int account_number;
            String established_date; //form "yyyy-mm-dd"
            int job_number;
            String commence_date; //form "yyyy-mm-dd"
            String completion_date; //form "yyyy-mm-dd"
            String color;
            int volume;
            int labor_time;
            int time_used;
            String material_used;
            int min_category;
            int max_category;
            int min_job_number;
            int max_job_number;
            int assembly_costs;
            int process_costs;
            int department_costs;
            int transaction_number;
            int sup_cost;
            int process_account_number;
            int assembly_account_number;
            int department_account_number;
            String option2 = ""; // Initialize user option selection as nothing
            String PROMPT2 = "\nPlease select one of the options below: \n" +
            "1) Insert paint \n" + 
            "2) Insert cutting \n" + 
            "3) Insert fitting \n";
            String option3 = ""; // Initialize user option selection as nothing
            String PROMPT3 = "\nPlease select one of the options below: \n" +
            "1) Insert assembly account \n" + 
            "2) Insert process account \n" + 
            "3) Insert department account \n";



            
            switch (option) { // Switch between different options
                case "1":
                    //get the input from user
                    System.out.println("Please enter new customer name:");
                    sc.nextLine();
                    name = sc.nextLine();
                    //get the input from user
                    System.out.println("Please enter customer address:");
                    address = sc.nextLine();
                    //sc.nextLine();
                    //get the input from user
                    System.out.println("Please enter customer category(has to be between 1 and 10):");
                    category = sc.nextInt();
                // call the procedure
                String procedureCall1 = "{call InsertNewCustomer(?, ?, ?)}";
                try (final Connection connection = DriverManager.getConnection(URL);
                    CallableStatement cs = connection.prepareCall(procedureCall1)) {
                    cs.setString(1, name);
                    cs.setString(2, address);
                    cs.setInt(3, category);
                    cs.execute();
                    System.out.println("Data inserted successfully using stored procedure.");
                }catch (SQLException e) {
                    System.err.println("SQL Error: " + e.getMessage());
                    e.printStackTrace();
                } catch (java.util.InputMismatchException e) {
                    System.err.println("Invalid input. Please enter a valid integer for job number.");
                }
                break;

                
	            case "2":
                    //get the input from user
                    System.out.println("Please enter integer department number:");
                    department_number = sc.nextInt();
                    //get the input from user
                    System.out.println("Please enter department data:");
                    sc.nextLine();
                    department_data = sc.nextLine();
                    // call the procedure  
                    String procedureCall2 = "{call InsertDepartment(?, ?)}";
                    try (final Connection connection = DriverManager.getConnection(URL);
                        CallableStatement cs = connection.prepareCall(procedureCall2)) {
                        cs.setInt(1, department_number);
                        cs.setString(2, department_data);
                        cs.execute();
                        System.out.println("Data inserted successfully using stored procedure.");
                    }catch (SQLException e) {
                        System.err.println("SQL Error: " + e.getMessage());
                        e.printStackTrace();
                    } catch (java.util.InputMismatchException e) {
                        System.err.println("Invalid input. Please enter a valid integer for job number.");
                    }
                    break;


	            case "3":
	                System.out.println(PROMPT2); // Print the available options
	                option2 = sc.next(); // Read in the user option selection
	                switch (option2) {
	                case "1":
	                    //get the input from user
	                    System.out.println("Please enter integer process ID:");
	                    process_id = sc.nextInt();
	                    //get the input from user
	                    System.out.println("Please enter process data:");
	                    sc.nextLine();
	                    process_data = sc.nextLine();
	                    //get the input from user
	                    System.out.println("Please enter integer department number:");
	                    department_number = sc.nextInt();
	                    //get the input from user
	                    System.out.println("Please enter paint type:");
	                    sc.nextLine();
	                    paint_type = sc.nextLine();
	                    System.out.println("Please enter painting method:");
	                    //sc.nextLine();
	                    painting_method = sc.nextLine();
	                    String procedureCall3_1 = "{call InsertPaintProcess(?, ?, ?, ?, ?)}";
	                    try (final Connection connection = DriverManager.getConnection(URL);
	                        CallableStatement cs = connection.prepareCall(procedureCall3_1)) {
	                        cs.setInt(1, process_id);
	                        cs.setString(2, process_data);
	                        cs.setInt(3, department_number);
	                        cs.setString(4, paint_type);
	                        cs.setString(5, painting_method);
	                        cs.execute();
	                        System.out.println("Data inserted successfully using stored procedure.");
	                    }catch (SQLException e) {
	                        System.err.println("SQL Error: " + e.getMessage());
	                        e.printStackTrace();
	                    } catch (java.util.InputMismatchException e) {
	                        System.err.println("Invalid input. Please enter a valid integer for job number.");
	                    }
	                	break;

	                	
	                case "2":
	                    //get the input from user
	                    System.out.println("Please enter integer process ID:");
	                    process_id = sc.nextInt();
	                    //get the input from user
	                    System.out.println("Please enter process data:");
	                    sc.nextLine();
	                    process_data = sc.nextLine();
	                    //get the input from user
	                    System.out.println("Please enter integer department number:");
	                    department_number = sc.nextInt();
	                    //get the input from user
	                    System.out.println("Please enter cutting type:");
	                    sc.nextLine();
	                    cutting_type = sc.nextLine();
	                    System.out.println("Please enter machine type:");
	                    //sc.nextLine();
	                    machine_type = sc.nextLine();
	                    String procedureCall3_2 = "{call InsertCutProcess(?, ?, ?, ?, ?)}";
	                    try (final Connection connection = DriverManager.getConnection(URL);
	                        CallableStatement cs = connection.prepareCall(procedureCall3_2)) {
	                        cs.setInt(1, process_id);
	                        cs.setString(2, process_data);
	                        cs.setInt(3, department_number);
	                        cs.setString(4, cutting_type);
	                        cs.setString(5, machine_type);
	                        cs.execute();
	                        System.out.println("Data inserted successfully using stored procedure.");
	                    }catch (SQLException e) {
	                        System.err.println("SQL Error: " + e.getMessage());
	                        e.printStackTrace();
	                    } catch (java.util.InputMismatchException e) {
	                        System.err.println("Invalid input. Please enter a valid integer for job number.");
	                    }
	                	break;
	                	
	                	
	                case "3":
	                    //get the input from user
	                    System.out.println("Please enter integer process ID:");
	                    process_id = sc.nextInt();
	                    //get the input from user
	                    System.out.println("Please enter process data:");
	                    sc.nextLine();
	                    process_data = sc.nextLine();
	                    //get the input from user
	                    System.out.println("Please enter integer department number:");
	                    department_number = sc.nextInt();
	                    //get the input from user
	                    System.out.println("Please enter fit type:");
	                    sc.nextLine();
	                    fit_type = sc.nextLine();
	                    String procedureCall3_3 = "{call InsertFitProcess(?, ?, ?, ?)}";
	                    try (final Connection connection = DriverManager.getConnection(URL);
	                        CallableStatement cs = connection.prepareCall(procedureCall3_3)) {
	                        cs.setInt(1, process_id);
	                        cs.setString(2, process_data);
	                        cs.setInt(3, department_number);
	                        cs.setString(4, fit_type);
	                        cs.execute();
	                        System.out.println("Data inserted successfully using stored procedure.");
	                    }
	                	break;
	                }
	                break;
	                
	                
	            case "4": //add possibility to add multiple contains at once
                    System.out.println("Please enter name:");
                    sc.nextLine();
                    name = sc.nextLine();
                    System.out.println("Please enter assembly details:");
                    //sc.nextLine();
                    assembly_details = sc.nextLine();
                    //get the input from user
                    System.out.println("Please enter integer assembly ID:");
                    assembly_id = sc.nextInt();
                    //get the input from user
                    System.out.println("Please enter date ordered in yyyy-mm-dd format");
                    sc.nextLine();
                    date_ordered = sc.nextLine();
                    System.out.println("Please enter integer process ID:");
                    process_id = sc.nextInt();
                    String procedureCall3_3 = "{call InsertAssemblyWithProcesses(?, ?, ?, ?, ?)}";
                    try (final Connection connection = DriverManager.getConnection(URL);
                        CallableStatement cs = connection.prepareCall(procedureCall3_3)) {
                        cs.setString(1, name);
                        cs.setString(2, assembly_details);
                        cs.setInt(3, assembly_id);
                        cs.setString(4, date_ordered);
                        cs.setInt(5, process_id);
                        cs.execute();
                        System.out.println("Data inserted successfully using stored procedure.");
                    }catch (SQLException e) {
                        System.err.println("SQL Error: " + e.getMessage());
                        e.printStackTrace();
                    } catch (java.util.InputMismatchException e) {
                        System.err.println("Invalid input. Please enter a valid integer for job number.");
                    }
                	break;

                	
	            case "5":
	                System.out.println(PROMPT3); // Print the available options
	                option3 = sc.next(); // Read in the user option selection
	                switch (option3) {
	                case "1":
	                    System.out.println("Please enter integer account number:");
	                    account_number = sc.nextInt();
	                    //get the input from user
	                    System.out.println("Please enter integer assembly ID:");
	                    assembly_id = sc.nextInt();
	                    //get the input from user
	                    System.out.println("Please enter established date in yyyy-mm-dd format");
	                    sc.nextLine();
	                    established_date = sc.nextLine();
	                    //get the input from user
	                    System.out.println("Please enter integer assembly costs:");
	                    assembly_costs = sc.nextInt();
	                    String procedureCall5_1 = "{call CreateAssemblyAccount(?, ?, ?, ?)}";
	                    try (final Connection connection = DriverManager.getConnection(URL);
	                        CallableStatement cs = connection.prepareCall(procedureCall5_1)) {
	                        cs.setInt(1, account_number);
	                    	cs.setInt(2, assembly_id);
	                        cs.setString(3, established_date);
	                        cs.setInt(4, assembly_costs);
	                        cs.execute();
	                        System.out.println("Data inserted successfully using stored procedure.");
	                    }catch (SQLException e) {
	                        System.err.println("SQL Error: " + e.getMessage());
	                        e.printStackTrace();
	                    } catch (java.util.InputMismatchException e) {
	                        System.err.println("Invalid input. Please enter a valid integer for job number.");
	                    }
	                	break;

	                	
	                case "2":
	                    System.out.println("Please enter integer account number:");
	                    account_number = sc.nextInt();
	                    //get the input from user
	                    System.out.println("Please enter integer process ID:");
	                    process_id = sc.nextInt();
	                    //get the input from user
	                    System.out.println("Please enter established date in yyyy-mm-dd format:");
	                    sc.nextLine();
	                    established_date = sc.nextLine();
	                    //get the input from user
	                    System.out.println("Please enter integer process costs:");
	                    process_costs = sc.nextInt();
	                    String procedureCall5_2 = "{call CreateProcedureAccount(?, ?, ?, ?)}";
	                    try (final Connection connection = DriverManager.getConnection(URL);
	                        CallableStatement cs = connection.prepareCall(procedureCall5_2)) {
	                        cs.setInt(1, account_number);
	                    	cs.setInt(2, process_id);
	                        cs.setString(3, established_date);
	                        cs.setInt(4, process_costs);
	                        cs.execute();
	                        System.out.println("Data inserted successfully using stored procedure.");
	                    }catch (SQLException e) {
	                        System.err.println("SQL Error: " + e.getMessage());
	                        e.printStackTrace();
	                    } catch (java.util.InputMismatchException e) {
	                        System.err.println("Invalid input. Please enter a valid integer for job number.");
	                    }
	                	break;
	                	
	                	
	                case "3":
	                    System.out.println("Please enter integer account number:");
	                    account_number = sc.nextInt();
	                    //get the input from user
	                    System.out.println("Please enter integer department number:");
	                    department_number = sc.nextInt();
	                    //get the input from user
	                    System.out.println("Please enter established date in yyyy-mm-dd format:");
	                    sc.nextLine();
	                    established_date = sc.nextLine();
	                    //get the input from user
	                    System.out.println("Please enter integer department costs:");
	                    department_costs = sc.nextInt();
	                    String procedureCall5_3 = "{call CreateDepartmentAccount(?, ?, ?, ?)}";
	                    try (final Connection connection = DriverManager.getConnection(URL);
	                        CallableStatement cs = connection.prepareCall(procedureCall5_3)) {
	                        cs.setInt(1, account_number);
	                        cs.setInt(2, department_number);
	                        cs.setString(3, established_date);
	                        cs.setInt(4, department_costs);
	                        cs.execute();
	                        System.out.println("Data inserted successfully using stored procedure.");
	                    }catch (SQLException e) {
	                        System.err.println("SQL Error: " + e.getMessage());
	                        e.printStackTrace();
	                    } catch (java.util.InputMismatchException e) {
	                        System.err.println("Invalid input. Please enter a valid integer for job number.");
	                    }
	                	break;
	                }
	            	break;
	            	
	            	
	            case "6":
                    //get the input from user
                    System.out.println("Please enter integer job number:");
                    job_number = sc.nextInt();
                    //get the input from user
                    System.out.println("Please enter integer assembly ID:");
                    assembly_id = sc.nextInt();
                    //get the input from user
                    System.out.println("Please enter integer process ID:");
                    process_id  = sc.nextInt();
                    //get the input from user
                    System.out.println("Please enter commence date in the format yyyy-mm-dd \n");
                    sc.nextLine();
                    commence_date = sc.nextLine();
                    String procedureCall6 = "{call EnterNewJob(?, ?, ?, ?)}";
                    try (final Connection connection = DriverManager.getConnection(URL);
                        CallableStatement cs = connection.prepareCall(procedureCall6)) {
                        cs.setInt(1, job_number);
                        cs.setInt(2, assembly_id);
                        cs.setInt(3, process_id);
                        cs.setString(4, commence_date);
                        cs.execute();
                        System.out.println("Data inserted successfully using stored proced ure.");
                    }catch (SQLException e) {
                        System.err.println("SQL Error: " + e.getMessage());
                        e.printStackTrace();
                    } catch (java.util.InputMismatchException e) {
                        System.err.println("Invalid input. Please enter a valid integer for job number.");
                    }
	            	break;
	            	
	            	
	            case "7":
	                System.out.println(PROMPT2); // Print the available options
	                option2 = sc.next(); // Read in the user option selection
	                switch (option2) {
	                case "1":
	                    //get the input from user
	                    System.out.println("Please enter integer job number:");
	                    job_number = sc.nextInt();
	                    //get the input from user
	                    System.out.println("Please enter compeltion date in the format yyyy-mm-dd:");
	                    sc.nextLine();
	                    completion_date  = sc.nextLine();
	                    //get the input from user
	                    System.out.println("Please enter the color:");
	                    //sc.nextLine();
	                    color  = sc.nextLine();
	                    //get the input from user
	                    System.out.println("Please enter integer volume:");
	                    volume = sc.nextInt();
	                    System.out.println("Please enter integer labor time:");
	                    labor_time = sc.nextInt();
	                    String procedureCall7_1 = "{call CompletePaintJob(?, ?, ?, ?, ?)}";
	                    try (final Connection connection = DriverManager.getConnection(URL);
	                        CallableStatement cs = connection.prepareCall(procedureCall7_1)) {
	                        cs.setInt(1, job_number);
	                        cs.setString(2, completion_date);
	                        cs.setString(3, color);
	                        cs.setInt(4, volume);
	                        cs.setInt(5, labor_time);
	                        cs.execute();
	                        System.out.println("Data inserted successfully using stored procedure.");
	                    }catch (SQLException e) {
	                        System.err.println("SQL Error: " + e.getMessage());
	                        e.printStackTrace();
	                    } catch (java.util.InputMismatchException e) {
	                        System.err.println("Invalid input. Please enter a valid integer for job number.");
	                    }
	                	break;

	                	
	                case "2":
	                    //get the input from user
	                    System.out.println("Please enter integer job number:");
	                    job_number = sc.nextInt();
	                    //get the input from user
	                    System.out.println("Please enter compeltion date in the format yyyy-mm-dd:");
	                    sc.nextLine();
	                    completion_date  = sc.nextLine();
	                    //get the input from user
	                    System.out.println("Please enter the machine type:");
	                    //sc.nextLine();
	                    machine_type = sc.nextLine();
	                    //get the input from user
	                    System.out.println("Please enter integer time used:");
	                    time_used = sc.nextInt();
	                    System.out.println("Please enter the material used:");
	                    sc.nextLine();
	                    material_used = sc.nextLine();
	                    System.out.println("Please enter integer labor time:");
	                    labor_time = sc.nextInt();
	                    String procedureCall7_2 = "{call CompleteCutJob(?, ?, ?, ?, ?, ?)}";
	                    try (final Connection connection = DriverManager.getConnection(URL);
	                        CallableStatement cs = connection.prepareCall(procedureCall7_2)) {
	                        cs.setInt(1, job_number);
	                        cs.setString(2, completion_date);
	                        cs.setString(3, machine_type);
	                        cs.setInt(4, time_used);
	                        cs.setString(5, material_used);
	                        cs.setInt(6, labor_time);
	                        cs.execute();
	                        System.out.println("Data inserted successfully using stored procedure.");
	                    }catch (SQLException e) {
	                        System.err.println("SQL Error: " + e.getMessage());
	                        e.printStackTrace();
	                    } catch (java.util.InputMismatchException e) {
	                        System.err.println("Invalid input. Please enter a valid integer for job number.");
	                    }
	                	break;
	                	
	                	
	                case "3":
	                    //get the input from user
	                    System.out.println("Please enter integer job number:");
	                    job_number = sc.nextInt();
	                    //get the input from user
	                    System.out.println("Please enter compeltion date in the format yyyy-mm-dd:");
	                    sc.nextLine();
	                    completion_date  = sc.nextLine();
	                    System.out.println("Please enter labor time:");
	                    labor_time = sc.nextInt();
	                    String procedureCall7_3 = "{call CompleteFitJob(?, ?, ?)}";
	                    try (final Connection connection = DriverManager.getConnection(URL);
	                        CallableStatement cs = connection.prepareCall(procedureCall7_3)) {
	                        cs.setInt(1, job_number);
	                        cs.setString(2, completion_date);
	                        cs.setInt(3, labor_time);
	                        cs.execute();
	                        System.out.println("Data inserted successfully using stored procedure.");
	                    }catch (SQLException e) {
	                        System.err.println("SQL Error: " + e.getMessage());
	                        e.printStackTrace();
	                    } catch (java.util.InputMismatchException e) {
	                        System.err.println("Invalid input. Please enter a valid integer for job number.");
	                    }
	                	break;
	                }
	            	break;
	            	
	            	
	            case "8":
                    //get the input from user
                    System.out.println("Please enter integer transaction number:");
                    transaction_number = sc.nextInt();
                    System.out.println("Please enter integer sup cost:");
                    sup_cost = sc.nextInt();
                    System.out.println("Please enter integer department account number:");
                    department_account_number = sc.nextInt();
                    System.out.println("Please enter integer process account number:");
                    process_account_number = sc.nextInt();
                    System.out.println("Please enter integer assembly account number:");
                    assembly_account_number = sc.nextInt();
                    String procedureCall8 = "{call UpdateCosts(?, ?, ?, ?, ?)}";
                    try (final Connection connection = DriverManager.getConnection(URL);
                        CallableStatement cs = connection.prepareCall(procedureCall8)) {
                        cs.setInt(1, transaction_number);
                        cs.setInt(2, sup_cost);
                        cs.setInt(3, department_account_number);
                        cs.setInt(4, process_account_number);
                        cs.setInt(5, assembly_account_number);
                        cs.execute();
                        System.out.println("Data inserted successfully using stored procedure.");
                    }catch (SQLException e) {
                        System.err.println("SQL Error: " + e.getMessage());
                        e.printStackTrace();
                    } catch (java.util.InputMismatchException e) {
                        System.err.println("Invalid input. Please enter a valid integer for job number.");
                    }
	            	break;
	            	
	            	
	            case "9":
                    System.out.println("Please enter integer assembly ID:");
                    assembly_id = sc.nextInt();
                    String procedureCall9 = "{call GetTotalCostForAssembly(?)}";
	                System.out.println("Connecting to the database...");

	                // Get the database connection
	                try (final Connection connection = DriverManager.getConnection(URL)) {
	                    System.out.println("Dispatching the query...");
	                    
	                    try (final CallableStatement cs = connection.prepareCall(procedureCall9)) {
	                        cs.setInt(1, assembly_id); // Set the parameter value
	                        cs.execute();

	                        // Process the result
	                        try (final ResultSet resultSet = cs.executeQuery()) {
	                            System.out.println("Total cost of selected assembly ID:");
	                            System.out.print("total_cost = ");

	                            // Unpack the tuples returned by the database and print them out to the user
	                            while (resultSet.next()) {
	                                System.out.println(String.format("%s", resultSet.getString(1)));
	                            }
	                        }
	                    }
	                } catch (SQLException e) {
	                    System.err.println("SQL Error: " + e.getMessage());
	                    e.printStackTrace();
	                } catch (java.util.InputMismatchException e) {
	                    System.err.println("Invalid input. Please enter a valid integer for assembly ID.");
	                }
	                break;
	            	
	            	
	            case "10":
                    System.out.println("Please enter integer department number number:");
                    department_number = sc.nextInt();
                    //get the input from user
                    System.out.println("Please enter compeltion date in the format yyyy-mm-dd:");
                    sc.nextLine();
                    completion_date  = sc.nextLine();
                    String procedureCall10 = "{call GetDepartmentLaborTime(?, ?)}";
                    
	                try (final Connection connection = DriverManager.getConnection(URL)) {
	                    System.out.println("Dispatching the query...");
	                    
	                    try (final CallableStatement cs = connection.prepareCall(procedureCall10)) {
	                        cs.setInt(1, department_number);
	                        cs.setString(2, completion_date);
	                        cs.execute();

	                        // Process the result
	                        try (final ResultSet resultSet = cs.executeQuery()) {
	                            System.out.println("Total labor time on selected date is:");

	                            // Unpack the tuples returned by the database and print them out to the user
	                            while (resultSet.next()) {
	                                System.out.println(String.format("%s ",
	                                        resultSet.getString(1)));
	                            }
	                        }
	                    }
	                } catch (SQLException e) {
	                    System.err.println("SQL Error: " + e.getMessage());
	                    e.printStackTrace();
	                } catch (java.util.InputMismatchException e) {
	                    System.err.println("Invalid input. Please enter a valid integer for department number.");
	                }

	                break;

	            	            	
	            	
	            case "11":
                    System.out.println("Please enter integer assembly ID:");
                    assembly_id = sc.nextInt();
                    String procedureCall11 = "{call GetProcessesForAssembly(?)}";
	                System.out.println("Connecting to the database...");

	                // Get the database connection
	                try (final Connection connection = DriverManager.getConnection(URL)) {
	                    System.out.println("Dispatching the query...");
	                    
	                    try (
	                    	final CallableStatement cs = connection.prepareCall(procedureCall11);
	                    	final ResultSet resultSet = cs.executeQuery()) {

	                    	cs.setInt(1, assembly_id);
	                    	cs.execute();

	                    	// Process the result
	                    	System.out.println("Processes for selected assembly ID:");
	                    	System.out.println("P.process_id, P.process_data, S.department_number, D.department_data ");

	                    	// Unpack the tuples returned by the database and print them out to the user
	                    	while (resultSet.next()) {
	                    		System.out.println(String.format("%s | %s | %s | %s ",
	                    				resultSet.getString(1),
	                    				resultSet.getString(2),
	                    				resultSet.getString(3),
	                    				resultSet.getString(4)));
	                    	}
	                    }
	                } catch (SQLException e) {
	                	System.err.println("SQL Error: " + e.getMessage());
	                	e.printStackTrace();
	                } catch (java.util.InputMismatchException e) {
	                	System.err.println("Invalid input. Please enter a valid integer for assembly ID.");
	                }

	                break;
	            	
	            	
	            case "12":
                    System.out.println("Please enter category lower bound:");
                    min_category = sc.nextInt();
                    System.out.println("Please enter category upper bound:");
                    max_category = sc.nextInt();
                    String procedureCall12 = "{call GetCustomersByCategoryRange(?, ?)}";
	                System.out.println("Connecting to the database...");

	                try (final Connection connection = DriverManager.getConnection(URL)) {
	                    System.out.println("Dispatching the query...");

	                    try (final CallableStatement cs = connection.prepareCall(procedureCall12)) {
	                        cs.setInt(1, min_category);
	                        cs.setInt(2, max_category);

	                        // Process the result
	                        System.out.println("Customers within the given category range:");
	                        System.out.println("name, address, category");

	                        try (final ResultSet resultSet = cs.executeQuery()) {
	                            // Unpack the tuples returned by the database and print them out to the user
	                            while (resultSet.next()) {
	                                System.out.println(String.format("%s | %s | %s ",
	                                        resultSet.getString(1),
	                                        resultSet.getString(2),
	                                        resultSet.getString(3)));
	                            }
	                        }
	                    }
	                } catch (SQLException e) {
	                    System.err.println("SQL Error: " + e.getMessage());
	                    e.printStackTrace();
	                } catch (java.util.InputMismatchException e) {
	                    System.err.println("Invalid input. Please enter valid integers for category bounds.");
	                }

	                break;
	            	
	            	
	            case "13":
	                System.out.println("Please enter minimum job number:");
	                min_job_number = sc.nextInt();
	                System.out.println("Please enter maximum job number:");
	                max_job_number = sc.nextInt();
	                String procedureCall13 = "{call DeleteCutJobsInRange(?, ?)}";

	                System.out.println("Connecting to the database...");

	                // Get the database connection
	                try (final Connection connection = DriverManager.getConnection(URL)) {
	                    System.out.println("Dispatching the query...");

	                    try (
	                        final CallableStatement cs = connection.prepareCall(procedureCall13);
	                    ) {
	                        cs.setInt(1, min_job_number);
	                        cs.setInt(2, max_job_number);
	                        cs.execute();
	                        System.out.println("Data deleted successfully using stored procedure.");
	                    }
	                } catch (SQLException e) {
	                    System.err.println("SQL Error: " + e.getMessage());
	                    e.printStackTrace();
	                } catch (java.util.InputMismatchException e) {
	                    System.err.println("Invalid input. Please enter valid integers for job number range.");
	                }
	                break;
	            	
	            	
	            case "14":
                    System.out.println("Please enter integer job number:");
                    job_number = sc.nextInt();
                    //get the input from user
                    System.out.println("Please enter the color:");
                    sc.nextLine();
                    color  = sc.nextLine();
                    //get the input from user
                    String procedureCall14 = "{call ChangePaintJobColor(?, ?)}";
                    try (final Connection connection = DriverManager.getConnection(URL);
                        CallableStatement cs = connection.prepareCall(procedureCall14)) {
                        cs.setInt(1, job_number);
                        cs.setString(2, color);
                        cs.execute();
                        System.out.println("Data updated successfully updated using stored procedure.");
                    }
                    catch (SQLException e) {
                        System.err.println("SQL Error: " + e.getMessage());
                        e.printStackTrace();
                    } catch (java.util.InputMismatchException e) {
                        System.err.println("Invalid input. Please enter a valid integer for job number.");
                    }
                	break;
	            	
	            	
	            case "15":
	                try (final Connection connection = DriverManager.getConnection(URL)) {
	                    Scanner scanner = new Scanner(System.in);

	                    // Ask user for input file name
	                    System.out.println("Enter the input file name (with extension, e.g., input.csv):");
	                    sc.nextLine();
	                    String inputFileName = scanner.next();

	                    // Prepare SQL statement for inserting new customers
	                    String insertQueryFile = "INSERT INTO Customer (name, address, category) VALUES (?, ?, ?)";
	                    try (PreparedStatement preparedStatement = connection.prepareStatement(insertQueryFile)) {
	                        // Read data from CSV file
	                        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
	                            String line;
	                            while ((line = reader.readLine()) != null) {
	                                // Split CSV line into values
	                                String[] values = line.split(",");
	                                if (values.length == 3) {
	                                    // Set parameters for the insert statement
	                                    preparedStatement.setString(1, values[0].trim());
	                                    preparedStatement.setString(2, values[1].trim());
	                                    preparedStatement.setInt(3, Integer.parseInt(values[2].trim()));

	                                    // Execute the insert statement
	                                    preparedStatement.executeUpdate();
	                                }
	                            }
	                            System.out.println("Data imported from " + inputFileName);
	                        }
	                    }
	                    //scanner.close();

	                } catch (SQLException | IOException e) {
	                    e.printStackTrace();
	                }

	            	break;
	            	
	            	
	            case "16":
	            	
	                try (final Connection connection = DriverManager.getConnection(URL)) {

	                    // Get category range from the user
	                    Scanner scanner = new Scanner(System.in);
	                    System.out.println("Enter minimum category:");
	                    int minCategory = scanner.nextInt();
	                    System.out.println("Enter maximum category:");
	                    int maxCategory = scanner.nextInt();

	                    // Prepare SQL query to retrieve customers in the given category range
	                    String query = "SELECT name, address, category FROM Customer WHERE category BETWEEN ? AND ? ORDER BY name";
	                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	                        preparedStatement.setInt(1, minCategory);
	                        preparedStatement.setInt(2, maxCategory);

	                        // Execute the query
	                        try (ResultSet resultSet = preparedStatement.executeQuery()) {

	                            // Ask user for output file name
	                            System.out.println("Enter the output file name (with extension, e.g., output.csv):");
	                            String outputFileName = scanner.next();

	                            // Write data to CSV file
	                            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFileName))) {
	                                // Write CSV header
	                                writer.println("Name,Address,Category");

	                                // Write data rows
	                                while (resultSet.next()) {
	                                    name = resultSet.getString("name");
	                                    address = resultSet.getString("address");
	                                    category = resultSet.getInt("category");

	                                    writer.println(name + "," + address + "," + category);
	                                }

	                                System.out.println("Data exported to " + outputFileName);
	                            }
	                        }
	                        //scanner.close();
	                    }

	                } catch (SQLException | IOException e) {
	                    e.printStackTrace();
	                }					
	            	break;
	            	
	            	
	            case "17": // Do nothing, the while loop will terminate upon the next iteration
	            	System.out.println("Exiting! Good-buy!");
	            	break;
	            default: // Unrecognized option, re-prompt the user for the correct one
	            	System.out.println(String.format(
	            			"Unrecognized option: %s\n" + 
	                        "Please try again!", 
	                        option));
	            	break;
            }
        }
        sc.close(); // Close the scanner before exiting the application
    }
}
