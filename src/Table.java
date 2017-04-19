import java.util.HashMap;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Table {
	List<Player> table;
	HashMap<Integer, Player> map;
	Gson g;
	
	public Table(){
		g = new Gson();
		map = new HashMap<>();
	}
	
	/**
	 * 
	 * @param s takes string s which is a JSON string
	 * that will contain the players
	 */
	public void refreshTable(String s){
		
		map.clear();
		
	}
	
	/**
	 * 
	 * @param p if p's bib number is new it is added to the map
	 */
	public void addToTable(Player p){
		
	}
	
	/**
	 * makes the list
	 */
	public void getListAsHTML(){
		
	}
	
	
	
}
