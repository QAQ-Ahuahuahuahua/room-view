import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import Items.Item;
import Site.Direction;
import Site.Location;
/**
 * WorldController-- Initialise the application
 * 
 * This class main focuses on the controllers of views
 * including turn left,turn right, go forward,
 * drop,pick buttons reactions.
 * 
 * @author zjh
 *
 */
public class WorldController {

	//The imageView to show the location pictures
	@FXML
	private ImageView imageView;
	
	//The imageView to show items
	@FXML
	private ImageView basketView;
	
	//The imageView to show logic map
	@FXML
	private ImageView logicView;
	
	//The button to control player go forward
	@FXML
	private Button forward;
	
	//The button which player could drop items
	@FXML
	private Button drop;
	
	//The button that player can pick up items
	@FXML
	private Button pick;
	
	//The message box to show relevant information
	@FXML
	private TextArea siteMessage;
	
	//The ComboBox which player could select items to drop
	@FXML
	private ComboBox<String> comboBox;
	
	@FXML
	private AnchorPane Message;
	
	@FXML
	private AnchorPane ImagePane;
	
	@FXML
	private ScrollPane ItemPane;
	
	@FXML
	private HBox Item_Hbox;
	
	//all item imageViews
	private HashMap<String,ImageView> Item_Images;
	private Player player;
	
	/**
	 * Initialise the whole application
	 * including the initialisation of location,direction,items
	 * create a new player with initial relevant information
	 */
	public void Initialise() {

		HashMap<String,Location> Locations = createLocation();
		HashMap<String,Item> Items = createItems();
			
		//Set start location
		Location startLocation = Locations.get("hall1");
		//Set start direction
		Direction startDirection = Direction.North;
        //Create a new player
        player = new Player(startLocation,startDirection,Items);
        
        /**
         * Application interface initialisation
         */
        ImagePane.setId("ImagePane");
        ItemPane.setId("ItemPane");
        Item_Hbox.setId("Item_Hbox");
        imageView.setId("imageView");
		imageView.setImage(startLocation.getPictures(startDirection.getDirection()));
        logicView.setImage(startLocation.getLogicMap());
        siteMessage.appendText("Welcome to our flatO(∩_∩)O！\n");
        
        
        //function Pick and Drop initialisation
        DropAndPick(Items);
        ItemDrag();
	}
	
    /**
     * Initialise the location part
     * Load all the location data from json file
     * and then use gson library to deal the json data
     * @return the location hashmap which contains all the locations information
     */
    public HashMap<String,Location> createLocation(){
    	//Get the json data as JsonObject
    	JsonObject object = LoadJson();
		HashMap<String,Location> Locations = new HashMap<String,Location>();
		
		/**
		 * Get the locations and initialise them
		 */
    	JsonArray locations = object.getAsJsonArray("locations");
    	for (JsonElement location:locations){
    		Locations.put(location.getAsString(), new Location());
    	}
    	
    	/**
    	 * Get details of each location
    	 * Including its neirghbors, surrounding pictures,
    	 * Description and name
    	 * Analyze them and infuse the data with Location Class
    	 */
    	JsonArray details = object.getAsJsonArray("details");
    	for(int i=0;i<details.size();i++){
    		JsonObject site = details.get(i).getAsJsonObject();
    		String name = site.get("name").getAsString();
    		JsonArray exits = site.getAsJsonArray("exits");
    		JsonArray pictures = site.getAsJsonArray("pictures");
    		
    		/**
    		 * Try to set all the data of a location
    		 */
    		try{
        		Location temp = Locations.get(name);
    			for(JsonElement exit:exits){
    				String direction = exit.getAsJsonObject().get("direction").getAsString();
    				String neighbor = exit.getAsJsonObject().get("neighbor").getAsString();
    				temp.setExits(direction,Locations.get(neighbor));
    			}
    			for(JsonElement picture : pictures){
    				String direction = picture.getAsJsonObject().get("direction").getAsString();
    				String path = picture.getAsJsonObject().get("path").getAsString();
    				temp.setPictures(direction, path);
    			}
    			temp.setName(name);
    			temp.setDescription(site.get("description").getAsString());
    			temp.setLogicMap(site.get("logicmap").getAsString());

    		}catch(Exception e){
    			System.out.println("There is not a such location");
    		}
    	}
    	return Locations;
    }
   
    /**
     * Initialise the item part
     * Load all the item data from json file
     * and then use gson library to deal the json data
     * @return the item hashmap which contains all the items information
     */
    public HashMap<String,Item> createItems(){
    	//Get the json data as JsonObject
    	JsonObject object = LoadJson();
		HashMap<String,Item> Items = new HashMap<String,Item>();
		
		/**
		 * Get the item data from json
		 * Try to set all data information through item class
		 */
		JsonArray items = object.getAsJsonArray("items");
    	for(int i=0;i<items.size();i++){
    		JsonObject item = items.get(i).getAsJsonObject();
    		String name = item.get("name").getAsString();
    		String path = item.get("path").getAsString();
    		Item temp = new Item(name);
    		temp.setItem(path);
    		Items.put(name, temp);
    		
    	}
    	return Items;
		
    }
    
    /**
     * Function Drop and Pick up items
     * When player double clicks the item on scroll pane or imageView
     * It will be dropped or picked up 
     */
    public void DropAndPick(HashMap<String,Item> items){
    	Item_Images = new HashMap<String,ImageView>();
    	for(Item item:items.values()){
    		ImageView pic = new ImageView();
    		pic.setImage(item.getItem());
    		pic.setId(item.getName());
    		pic.setOnMouseClicked(new EventHandler<MouseEvent>() {
    		    @Override
    		    public void handle(MouseEvent mouseEvent) {
    		        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
    		            if(mouseEvent.getClickCount() == 2){

    		                if(ImagePane.getChildren().contains(pic)){
    		                	Item_Hbox.getChildren().add(pic);
    		                	player.Pick(pic.getId());
    		                	siteMessage.appendText("You pick a "+pic.getId()+"\n");
    		                	showItems();
    		                }
    		                else{
    		                	ImagePane.getChildren().add(pic);
    		                	player.Drop(pic.getId());    		                	
    		                	siteMessage.appendText("You drop a "+pic.getId()+"\n");
    		                	showItems();
    		                	pic.setOnMouseDragged(new EventHandler<MouseEvent>() {
    		                		 @Override public void handle(MouseEvent event) {
    		                			 // drag the item to new position
    		                			 event.consume();
    		                			 double xOffset = event.getSceneX();
    		                			 double yOffset = event.getSceneY();
    		                			 pic.setLayoutX(xOffset);
    		                			 pic.setLayoutY(yOffset);
    		                			 }
    		                			});
    		                }
    		                
    		            }
    		        }
    		    }
    		});
    		
    		/**
    		 * put item picture into HBox
    		 */
    		Item_Hbox.getChildren().add(pic);
    		Item_Images.put(item.getName(), pic);
    		
    	}
    	//Show item pictures in scrollPane
    	ItemPane.setContent(Item_Hbox);
    }
    
    public void ItemDrag(){

    }
    
    /**
     * If user presses left button
     * the direction he faces will change
     * for example,if the user is facing north now,
     * then pressing button,he will face the west direction
     * @param event press left button event
     */
    public void turnLeft(ActionEvent event) {
    	
    	Direction playerDirection = player.getCurrentDirection();
    	Direction nextDirection=playerDirection.turnLeft(playerDirection);   	
    	playerDirection=nextDirection;
    	player.setCurrentDirection(playerDirection);
    	showPicture();
    }


    /**
     * If user presses left button
     * the direction he faces will change
     * for example,if the user is facing north now,
     * then pressing button,he will face the west direction
     * @param event press right button event
     */
    public void turnRight(ActionEvent event){
    	
    	Direction playerDirection = player.getCurrentDirection();
    	Direction nextDirection=playerDirection.turnRight(playerDirection);   	
    	playerDirection=nextDirection;
    	player.setCurrentDirection(playerDirection);
    	showPicture();
    }
    
    /**
     * If user presses forward button
     * He will go to next location which is different,
     * according to the direction he is facing.
     * If the user is facing the east of current location,
     * he will still face the east of next location
     * which is the east of current location
     * 
     * @param event press go forward button event
     */
    public void goForward(ActionEvent event){
    	
    	String playerDirection = player.getCurrentDirection().getDirection();
    	Location playerLocation = player.getCurrentLocation();
    	Location nextLocation = playerLocation.getExits(playerDirection);
    	playerLocation = nextLocation;
    	player.setCurrentLocation(playerLocation);
    	siteMessage.appendText(playerLocation.getDescription());
    	showPicture();
    	siteMessage.setEditable(false);
    	
    }

    
    /**
     * Show the relevant item which player dropped
     * according to current location and direction
     */
    public void showItems(){
    	Location location = player.getCurrentLocation();
    	Direction direction = player.getCurrentDirection();
    	List<Item> item_list = player.getItems(location, direction);
    	List<Item> items_rest = player.getItems_rest();
    	if(item_list!=null){
    		for(Item item : item_list){
    			ImageView pic = Item_Images.get(item.getName());
    			pic.setImage(item.getItem());
    		}
    	}
    	if(items_rest!=null){
    		for(Item item :items_rest){
    			ImageView pic = Item_Images.get(item.getName());
    			pic.setImage(null);
    		}
    	}
    }
    
    /**
     * Show information to the application
     * according to the player operation
     */
    public void showPicture(){
    	String playerDirection = player.getCurrentDirection().getDirection();
    	Location playerLocation = player.getCurrentLocation();
    	Location nextLocation = playerLocation.getExits(playerDirection);
    	if(nextLocation==null){
    		forward.setDisable(true);
    	}
    	else
    		forward.setDisable(false);
    	imageView.setImage(playerLocation.getPictures(playerDirection));
    	logicView.setImage(playerLocation.getLogicMap());
    	siteMessage.appendText("You are looking at the "+player.getCurrentSiteInfo());
    	showItems();
    }
    
    /**
     * Load data from json file
     * by using Gson library
     * @return the data loaded from json file as jsonobject format
     */
    public JsonObject LoadJson() {
    	try{
    		JsonParser parser = new JsonParser();
        	JsonObject object = (JsonObject)parser.parse(new FileReader("src/data.json"));
        	return object;
    	} catch(JsonIOException e){
    		e.printStackTrace();
    	} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
}
