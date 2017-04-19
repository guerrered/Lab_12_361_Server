
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import Chronotimer.Console;

public class Server {
	

    // a shared area where we get the POST data and then use it in the other handler
    static String sharedResponse = "";
    static boolean gotMessageFlag = false;

    public static void main(String[] args) throws Exception {
    	
    	UserInterface();
    	
    	
    	
    	
    	new Table();
        // set up a simple HTTP server on our local host
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // create a context to get the request to display the results
        server.createContext("/displayresults", new DisplayHandler());

        // create a context to get the request for the POST
        server.createContext("/sendresults",new PostHandler());
    
        //
        server.createContext("/styles.css", new StaticFileServer("styles.css"));
        
        server.setExecutor(null); // creates a default executor
        
        // get it going
        System.out.println("Starting Server...");
        server.start();
    }
    

    static class DisplayHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
        	String response = HTMLtoString();
        	t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    /*
     * class that replaces the DisplayHandler in favor of a "Static"FileServer
     * which will print out the desired files even though they may be changed
     * later on
     */
    static class StaticFileServer implements HttpHandler{
    	String fileName;
    	
    	public StaticFileServer(String fileName){
    		this.fileName = fileName;
    	}
    	
    	public void handle(HttpExchange exchange)throws IOException{
    		//String fileID = exchange.getRequestURI().getPath();
    		File file =  new File(fileName);
    		if(!file.exists()){
    			String response = "Error 404 File not found.";
    			exchange.sendResponseHeaders(404,  response.length());
    			OutputStream output = exchange.getResponseBody();
    			output.write(response.getBytes());
    			output.flush();
    			output.close();
    		}
    		else{
    			exchange.sendResponseHeaders(200,0);
    			OutputStream output = exchange.getResponseBody();
    			FileInputStream fs = new FileInputStream(file);
    			final byte[] buffer = new byte[0x10000];
    			int count = 0;
    			while((count = fs.read(buffer)) >= 0){
    				output.write(buffer, 0,  count);
    			}
    			output.flush();
    			output.close();
    			fs.close();
    		}
    	}
    }
    
    static class PostHandler implements HttpHandler {
        public void handle(HttpExchange transmission) throws IOException {

            //  shared data that is used with other handlers
            sharedResponse = "";
            // set up a stream to read the body of the request
            InputStream inputStr = transmission.getRequestBody();
            // set up a stream to write out the body of the response
            OutputStream outputStream = transmission.getResponseBody();
    
            // string to hold the result of reading in the request
            StringBuilder sb = new StringBuilder();
            String postResponse = "notWorking";
            // read the characters from the request byte by byte and build up the sharedResponse
            int nextChar = inputStr.read();
            while (nextChar > -1) {
                sb=sb.append((char)nextChar);
                nextChar=inputStr.read();
            }
            String x = sb.toString();
            String[] R = x.split(" ");
         

            // create our response String to use in other handler
            sharedResponse = sharedResponse+sb.toString();

            // respond to the POST with ROGER

            System.out.println("response: " + sharedResponse);

            //Desktop dt = Desktop.getDesktop();
            //dt.open(new File("raceresults.html"));

            // assume that stuff works all the time
            transmission.sendResponseHeaders(300, postResponse.length());

            // write it and return it
            outputStream.write(postResponse.getBytes());          
            //outputStream.close();
        }
    }
    
    
    /*
     * Possibly unneeded as this was fix for DisplayHandler which we altered
     */
    public static String HTMLtoString(){
    	String ret = "<html><head><title>Company Directory</title><link rel = \"stylesheet\" type= \"text/css\" href = \"styles.css\"></head><body><h1 title =\"Hidden(1) Bit\">Company Directory</h1><table style =\"width:100%>\"<tr><th>FirstName</th><th>LastName</th><th>Department</th><th>Title</th><th>Phone Number</th><th>Gender</th></tr>";
    	ret += Table.newTable();
    	ret+= "</table></body></html>";
    	return ret;
    }
    public void UserInterface()
    {
    	Scanner scan = new Scanner(System.in);
    	System.out.println("Enter 'C' for console input, 'F' for file input");
    	String choice  = scan.nextLine();
    	while(!choice.equalsIgnoreCase("C")&&!choice.equalsIgnoreCase("F"))
    	{
    		System.out.println("Enter 'C' for console input, 'F' for file input");
    		choice = scan.nextLine();
    	}	
    	if(choice.equalsIgnoreCase("f"))
    	{
    		System.out.println("Enter the name of the input file");
			String fileName = scan.nextLine();
			File nameChecker  = new File(fileName);
			while(!nameChecker.exists()){
				System.out.println("Enter the name of the input file");
				fileName = scan.nextLine();
				nameChecker = new File(fileName);
    	    }
			scan.close();
			//Do somehthing here to create table.
			//create new player and pass to table class
    	}	
    	else if(choice.equalsIgnoreCase("c"))
    	{
    		System.out.println("Reading from the input");
    		readFromConsole(scan);
    	}
			
			
    	
    }
    
    public void readFromConsole(Scanner scan)
    {
     String	BibNum, LastName, FirstInitial,Time, enter;
     System.out.println("Enter 1 to begin and enter 2 to exit");
     enter = scan.nextLine();
     while(!enter.equals(2))
     {
    	 System.out.println("Enter Bib number");
    	 BibNum = scan.nextLine();
    	 System.out.println("Enter LastName");
    	 LastName = scan.nextLine();
    	 System.out.println("Enter First intial");
    	 FirstInitial = scan.nextLine();
    	 new Player(BibNum,LastName, FirstInitial,null);
    	 
    	 
    	 
    	 
     }
     
    	
    }
    
    
    

}