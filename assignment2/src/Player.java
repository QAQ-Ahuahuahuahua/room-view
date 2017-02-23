
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Items.Item;
import Site.Direction;
import Site.Location;

/**
 * Player Class--The information of a player
 * 
 * A player has his current location and direction,
 * he also has the different items
 * which he can drop or pick in different location
 * 
 * @author zjh
 *
 */
public class Player {
	//The name of player
	private String playerName;
	//The player current location
	private Location currentLocation;
	//The player current direction
	private Direction currentDirection;
	//The whole items the player has
	private HashMap<String,Item> items;
	//The items that player drops
	private HashMap<String,List<Item>> item_group;
	//The item list at last location or direction
	private List<Item> items_rest;
	
	/**
	 * Create a new player, he has the initial location and direction,
	 * and the whole items as well
	 * @param beginLocation the player's start location
	 * @param beginDirection the player's starting direction
	 * @param items the whole items the player has 
	 */
	public Player(Location beginLocation,Direction beginDirection,HashMap<String,Item> items){
		this.currentLocation = beginLocation;
		this.currentDirection = beginDirection;
		this.items = items;
		item_group = new HashMap<String,List<Item>>();
		items_rest = new ArrayList<Item>();
	}
	
	/**
	 * Set the name of player
	 * @param name the name string
	 */
	public void setPlayerName(String name){
		this.playerName = name;
	}
	
	/**
	 * Return the name of player
	 * @return the name string
	 */
	public String getPlayerName(){
		return playerName;
	}
	
	/**
	 * Set the player current location
	 * @param location the new location
	 */
	public void setCurrentLocation(Location location){
		this.currentLocation = location;
	}
	
	/**
	 * Return the player current location
	 * @return the player location information
	 */
	public Location getCurrentLocation(){
		return currentLocation;
	}
	
	/**
	 * Set the player current direction
	 * @param direction the new direction
	 */
	public void setCurrentDirection(Direction direction){
		this.currentDirection = direction;
	}
	
	/**
	 * Return the player current direction
	 * @return the player direction information
	 */
	public Direction getCurrentDirection(){
		return currentDirection;
	}
	
	/**
	 * Player can drop the select items in a special site
	 * Record the location and direction 
	 * where the item is placed by player
	 * @param ItemName the item name that player selects
	 */
	public void Drop(String ItemName){
		Item item = items.get(ItemName);
		String key = currentLocation.getName()+":"+currentDirection.getDirection();
		List<Item> temp = new ArrayList<Item>();
		if(item_group.get(key)==null){
			temp.add(item);
		}else{
			temp = item_group.get(key);
			temp.add(item);
		}
		item_group.put(key, temp);
	}
	
	/**
	 * Player could pick one item dropped,
	 * After pick up,set the item location and direction nulls.
	 * @return the name of item that picked up
	 */
	public void Pick(String ItemName){
		Item item = items.get(ItemName);
		String key = currentLocation.getName()+":"+currentDirection.getDirection();
		List<Item> temp = item_group.get(key);
		temp.remove(item);
		item_group.put(key, temp);
	}
	
	/**
	 * Return the item lists which player drops in a specific site
	 * according to the location and direction
	 * @param location the location information
	 * @param direction the direction information
	 * @return the items in given location and direction
	 */
	public List<Item> getItems(Location location,Direction direction){
		String key = location.getName()+ ":" + direction.getDirection();
		return item_group.get(key);
	}
	
	/**
	 * Return items which have been selected by player
	 * and are not in current location and direction
	 * @return the item list
	 */
	public List<Item> getItems_rest(){
		items_rest.clear();
		for(List<Item> temp:item_group.values()){
			if(!temp.equals(getItems(currentLocation,currentDirection))){
				items_rest.addAll(temp);
			}
		}
		return items_rest;
	}
	
	/**
	 * Return the player current site information
	 * including current location and direction
	 * @return the current information string
	 */
	public String getCurrentSiteInfo(){
		return currentDirection.getDirection()+ " of "+currentLocation.getName()+"\n";
	}
}
