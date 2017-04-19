
public class Player {
	String bibNumber;
	String lastName;
	String firstInitial;
	String time;
	
	public Player(String bibNumber, String lastName, String firstInitial, String time){
		this.bibNumber = bibNumber;
		this.lastName = lastName;
		this.firstInitial = firstInitial;
		this.time = time;
	}
	
	public String toString(){
		String str="";
		str = str  + bibNumber + " " + lastName + " "  + firstInitial  + ". " + time; 
		return str;
	}
	
	public String getTime(){
		return time;
	}
}
