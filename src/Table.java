import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Table {
	List<Player> table;
	static HashMap<Integer, Player> map;
	Gson g;
	
	public Table(){
		g = new Gson();
		map = new HashMap<>();
	}
	
	/**
	 * 
	 * @param s takes string s which is a JSON string
	 * that will contain the extra player information
	 * This info will then update whatever currently exists in 
	 * the map
	 */
	public void refreshTable(String json){
		List<Player> tempList = g.fromJson(json,new TypeToken<List<Player>>(){}.getType());
		for(Player p: tempList){
			int key = Integer.parseInt(p.bibNumber);
			if(map.containsKey(key)){
				Player toMod = map.get(key);
				toMod.time = p.time;//update the time value
			}
			else{
				map.put(key, p);//adds new player to list no name only time and bibNum
			}
		}
	}
	
	/**
	 * 
	 * @param P Player that will be added to the map
	 * if a previous player exists with the same bib number they
	 * will be replaced by p
	 */
	public void addToTable(Player p){
		Integer key = Integer.parseInt(p.bibNumber);//adds player to map
		if(map.containsKey(key)){
			map.remove(key);
		}
		map.put(key, p);
		
	}
	
	/**
	 * goes through the map and sort the Players by their time
	 * and returns a string in with HTML table formatting;
	 * otherwise if map was empty it returns empty String
	 */
	public static String getListAsHTML(){
		
		String ret = "";
		List<Player> mainList = new ArrayList<>();
		for(Player p: map.values()){
			mainList.add(p);
		}
		if(mainList.isEmpty()){
			ret  = "";
		}
		else{
			List<Player> sorted = sort(mainList);
			for(int i=0; i<sorted.size(); i++){
				//ret = ret + sorted.get(i) + "\n";
			}
			for(Player p: sorted){
				ret +=("<tr><td>" + p.firstInitial + "</td>"
				+ "<td>"+ p.lastName + "</td>" 
				+ "<td>"+ p.bibNumber + "</td>" 
				+"<td>"+ p.time + "</td></tr>");
			}
		}
		return ret;
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<Player> sort(List<Player> mainList){
		List<Player>sorted= new ArrayList<>();
		for(int i =0; i<mainList.size(); i++){
			sorted.add(mainList.get(i));
		}
		sorted.sort(c);
		return sorted;
	}
	
	public static Comparator<Player> c = new Comparator<Player>(){
		public int compare(Player p1,Player p2){
			return p1.time.compareTo(p2.time);
			
		}
	};
	
	
}
