package Items;

import javafx.scene.image.Image;

/**
 * Class item--the information of items
 * 
 * Here, item has its name and picture showed in application.
 * In addition, the item has its location and direction
 * to record the placed site by player
 * 
 * @author zjh
 *
 */
public class Item {

	// The name of item
	private String name;
	// The picture of item showed in application
	private Image item;
	
	/**
	 * Create a new item. Initially, it requires the name of this item.
	 * @param name the name of item
	 */
	public Item(String name){
		this.name = name;
	}
	
	/**
	 * Set the of name of item
	 * @param name the name string
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Return the name of this item
	 * @return the name string
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Set the item pic according to its store path
	 * if it fails to load the picture, throw an exception.
	 * @param path the picture path string
	 */
	public void setItem(String path){
		try{
			this.item = new Image(path);
		}catch(Exception e){
			System.out.println("item picture error");
		}
	}
	
	/**
	 * Return the picture of this item
	 * @return the Image of item
	 */
	public Image getItem(){
		return item;
	}
	
	
}
