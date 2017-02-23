package Site;
import java.util.HashMap;

import javafx.scene.image.Image;

/**
 * Class location--current location information;
 * 
 * It has exits of this location;
 * And It also has four different direction views of this location.
 * In addition, it contains the logical location info in logical map.
 * @author zjh
 *
 */
public class Location {

	private String name; //The name of new location
	private String description; //The description of new location
	private HashMap<String,Location> exits; //To store exits of this location
	private HashMap<String,Image> pictures; //To store different direction view of this location
	private Image logicMap; //To store logical location of this location
	
	/**
	 * Create a location. Initially,it has no exits and surrounding views.
	 */
	public Location(){
		exits = new HashMap<String,Location>();
		pictures = new HashMap<String,Image>();
	}
	
	/**
	 * Define the exits of this location
	 * Set the neighbor locations of current location
	 * @param direction the direction of exits
	 * @param neighbor next location which exit loads to
	 */
	public void setExits(String direction,Location neighbor){
		exits.put(direction, neighbor);
	}
	
	/**
	 * Return the room that is reached if we go from this room in direction
	 * @param direction the exit's direction
	 * @return the location in given direction
	 */
	public Location getExits(String direction){
		return exits.get(direction);
	}
	
	/**
	 * Set surrounding views of current location
	 * If it fails to load a picture, throw an exception
	 * @param direction the direction which player faces
	 * @param path the store path of picture
	 */
	public void setPictures(String direction,String path){
		
		try{
			Image picture = new Image(path);
			pictures.put(direction, picture);
		}catch(Exception e){
			System.out.println("picture error");
		}
	}
	
	/**
	 * Return the view of current direction
	 * @param direction the direction which player faces
	 * @return the picture in given direction
	 */
	public Image getPictures(String direction){
		return pictures.get(direction);
	}
	
	/**
	 * Set a simple introduction of this location
	 * @param description the introduction of location
	 */
	public void setDescription(String description){
		this.description = description;
	}
	
	/**
	 * Return the description of this location
	 * @return the introduction of location
	 */
	public String getDescription(){
		return description + "\n";
	}
	
	/**
	 * Set the name of this location
	 * @param name the name of location
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Return the name of location
	 * @return the name of location
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Set the location info in logical map
	 * If it fails to load the logical picture, throw an exception
	 * @param path the store path of logical map
	 */
	public void setLogicMap(String path){
		try{
			this.logicMap = new Image(path);
		}catch(Exception e){
			System.out.println("picture error");
		}
	}
	
	/**
	 * Return the logical map of this location 
	 * @return the picture which marked this location 
	 */
	public Image getLogicMap(){
		return logicMap;
	}
	
	
}
