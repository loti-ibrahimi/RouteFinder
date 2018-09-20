package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;

public class MainController {

	static String starting_point = null;
	static String destination = null;

	static ArrayList routes = new ArrayList();
	static HashMap<String, Waypoint> waypoints = new HashMap<String, Waypoint>();

	
// ============ LABELS ===============//
	@FXML
	private Label info_message;
	@FXML
	private Label error_message;
	@FXML
	private Label txtbox_start_label;
	@FXML
	private Label txtbox_destination_label;

	@FXML
	private Label dist_label;
	@FXML
	private Label time_label;

	
// =========== TEXT BOXES =============//
	@FXML
	private TextField txtbox_start;
	@FXML
	private TextField txtbox_destination;

	
// =========== BUTTONS ================//
	@FXML
	private Button btn_shortest_route;
	@FXML
	private Button btn_all_routes;
	@FXML
	private Button btn_quickest_route;
	@FXML
	private Button new_search_button;

	
// ========== TREE VIEW ==============//
	@FXML
	TreeView route_tree;

	
// ========== Images =================//
	@FXML
	ImageView clock_image;
	@FXML
	ImageView distance_image;
	@FXML
	ImageView tape_image;

	// Database check
	boolean database_loaded = false;
	
	
// ================================================ SHORTEST ROUTE (Distance) ======================================================================//
	// Handles shortest route button click.
	public void calculateShortestRoute(ActionEvent e) {
		
		// Hide Previous Error Messages.
		error_message.setVisible(false);
		
		if (!database_loaded) {
			loadDatabase();
			database_loaded = true;
		}

		// First check both text boxes have been filled. (Temp check if Tra/Wat)
		if (!txtbox_start.getText().equals("") && !txtbox_destination.getText().equals("")) {

			starting_point = txtbox_start.getText();
			destination = txtbox_destination.getText();

			starting_point = starting_point.substring(0, 1).toUpperCase() + starting_point.substring(1).toLowerCase();
			destination = destination.substring(0, 1).toUpperCase() + destination.substring(1).toLowerCase();

			// Main Code
			for (int i = 0; i < routes.size(); i++) {

				String[] attr = ((String) routes.get(i)).split(",");

				// First will be start point, so after will be name of waypoint, speed limit to
				// next waypoint, distance to next waypoint, travel time to next waypoint
				String start_waypoint_name = attr[1];

				if (start_waypoint_name.equals(starting_point)) {

					int start_waypoint_speed_limit = Integer.parseInt(attr[2]);
					int start_waypoint_distance = Integer.parseInt(attr[3]);
					int start_waypoint_time = Integer.parseInt(attr[4]);

					if (!waypoints.containsKey(starting_point)) {
						waypoints.put(start_waypoint_name, new Waypoint(start_waypoint_name, start_waypoint_speed_limit,
							start_waypoint_distance, start_waypoint_time, new TownNode<String>(start_waypoint_name)));
					}
					

					// Waypoint generator
					int gen = 0;
					int waypoint_id = 0;

					String waypoint_one_name = null;
					String waypoint_two_name = null;
					String waypoint_three_name = null;
					String waypoint_four_name = null;

					//-------------------------------- Get attributes from here on... ---------------------------------------------//
					for (int j = gen + 5; j < attr.length; j = j + 5) {

						if (attr[j].equals("WAYPOINT")) {

							waypoint_id++;
							
							//
							if (waypoint_id == 1) {

								waypoint_one_name = attr[j + 1];

								int waypoint_one_speed_limit = Integer.parseInt(attr[j + 2]);
								int waypoint_one_distance = Integer.parseInt(attr[j + 3]);
								int waypoint_one_time = Integer.parseInt(attr[j + 4]);

								
									waypoints.put(waypoint_one_name,
											new Waypoint(waypoint_one_name, waypoint_one_speed_limit,
													waypoint_one_distance, waypoint_one_time,
													new TownNode<String>(waypoint_one_name)));
								

								waypoints.get(start_waypoint_name).getTownNode().connectToNodeUndirected(
										waypoints.get(waypoint_one_name).getTownNode(),
										waypoints.get(start_waypoint_name).getSpeedLimit(),
										waypoints.get(start_waypoint_name).getDistance(),
										waypoints.get(start_waypoint_name).getTime());
							
							//-
							} else if (waypoint_id == 2) {

								waypoint_two_name = attr[j + 1];
								int waypoint_two_speed_limit = Integer.parseInt(attr[j + 2]);
								int waypoint_two_distance = Integer.parseInt(attr[j + 3]);
								int waypoint_two_time = Integer.parseInt(attr[j + 4]);

								
									waypoints.put(waypoint_two_name,
											new Waypoint(waypoint_two_name, waypoint_two_speed_limit,
													waypoint_two_distance, waypoint_two_time,
													new TownNode<String>(waypoint_two_name)));
								

								waypoints.get(waypoint_one_name).getTownNode().connectToNodeUndirected(
										waypoints.get(waypoint_two_name).getTownNode(),
										waypoints.get(waypoint_one_name).getSpeedLimit(),
										waypoints.get(waypoint_one_name).getDistance(),
										waypoints.get(waypoint_one_name).getTime());
							
							//-
							} else if (waypoint_id == 3) {

								waypoint_three_name = attr[j + 1];
								int waypoint_three_speed_limit = Integer.parseInt(attr[j + 2]);
								int waypoint_three_distance = Integer.parseInt(attr[j + 3]);
								int waypoint_three_time = Integer.parseInt(attr[j + 4]);

								
									waypoints.put(waypoint_three_name,
											new Waypoint(waypoint_three_name, waypoint_three_speed_limit,
													waypoint_three_distance, waypoint_three_time,
													new TownNode<String>(waypoint_three_name)));
								

								waypoints.get(waypoint_two_name).getTownNode().connectToNodeUndirected(
										waypoints.get(waypoint_three_name).getTownNode(),
										waypoints.get(waypoint_two_name).getSpeedLimit(),
										waypoints.get(waypoint_two_name).getDistance(),
										waypoints.get(waypoint_two_name).getTime());
							
							//-
							} else if (waypoint_id == 4) {

								waypoint_four_name = attr[j + 1];
								int waypoint_four_speed_limit = Integer.parseInt(attr[j + 2]);
								int waypoint_four_distance = Integer.parseInt(attr[j + 3]);
								int waypoint_four_time = Integer.parseInt(attr[j + 4]);

								
									waypoints.put(waypoint_four_name,
											new Waypoint(waypoint_four_name, waypoint_four_speed_limit,
													waypoint_four_distance, waypoint_four_time,
													new TownNode<String>(waypoint_four_name)));
								

								waypoints.get(waypoint_three_name).getTownNode().connectToNodeUndirected(
										waypoints.get(waypoint_four_name).getTownNode(),
										waypoints.get(waypoint_three_name).getSpeedLimit(),
										waypoints.get(waypoint_three_name).getDistance(),
										waypoints.get(waypoint_three_name).getTime());
							}
						
						//-
						} else if (attr[j].equals("DEST")) {

							gen = 1;

							String dest_waypoint_name = attr[j + 1];

							if (!waypoints.containsKey(dest_waypoint_name)) {
								waypoints.put(dest_waypoint_name,
										new Waypoint(dest_waypoint_name, new TownNode<String>(dest_waypoint_name)));
							}
							
							switch (waypoint_id) {

							case 0:
							default:
								waypoints.get(start_waypoint_name).getTownNode().connectToNodeUndirected(
										waypoints.get(dest_waypoint_name).getTownNode(),
										waypoints.get(start_waypoint_name).getSpeedLimit(),
										waypoints.get(start_waypoint_name).getDistance(),
										waypoints.get(start_waypoint_name).getTime());
								break;
							case 1:
								waypoints.get(waypoint_one_name).getTownNode().connectToNodeUndirected(
										waypoints.get(dest_waypoint_name).getTownNode(),
										waypoints.get(waypoint_one_name).getSpeedLimit(),
										waypoints.get(waypoint_one_name).getDistance(),
										waypoints.get(waypoint_one_name).getTime());
								break;
							case 2:
								waypoints.get(waypoint_two_name).getTownNode().connectToNodeUndirected(
										waypoints.get(dest_waypoint_name).getTownNode(),
										waypoints.get(waypoint_two_name).getSpeedLimit(),
										waypoints.get(waypoint_two_name).getDistance(),
										waypoints.get(waypoint_two_name).getTime());
								break;
							case 3:
								waypoints.get(waypoint_three_name).getTownNode().connectToNodeUndirected(
										waypoints.get(dest_waypoint_name).getTownNode(),
										waypoints.get(waypoint_three_name).getSpeedLimit(),
										waypoints.get(waypoint_three_name).getDistance(),
										waypoints.get(waypoint_three_name).getTime());
								break;
							case 4:
								waypoints.get(waypoint_four_name).getTownNode().connectToNodeUndirected(
										waypoints.get(dest_waypoint_name).getTownNode(),
										waypoints.get(waypoint_four_name).getSpeedLimit(),
										waypoints.get(waypoint_four_name).getDistance(),
										waypoints.get(waypoint_four_name).getTime());
								break;

							}
							
							waypoint_id = 0;						
							
						} else {
							break;
						}

					}
				}

			}

			//------------------------------------ TREE VIEW -------------------------------------------------------//
			
			// If route doesn't exist, let them know!
			if (!waypoints.containsKey(starting_point) || !waypoints.containsKey(destination)) {
			
				error_message.setText("No such route exists, please try again!");
				error_message.setVisible(true);
				
				TreeItem<String> root = new TreeItem<>("No Route Found.");
				route_tree.setRoot(root);
				
				dist_label.setText(" ");
				tape_image.setVisible(false);
			
			} else {
			
				ShortestRoute sr = searchShortestRouteDijkstra(waypoints.get(starting_point).getTownNode(), destination);
	
				TreeItem<String> root = new TreeItem<>("Shortest Route");
	
				for (TownNode<?> n : sr.roadList) {
					TreeItem<String> itemChild = new TreeItem<>(n.data.toString());
					itemChild.setExpanded(false);
					// root is the parent of itemChild
					root.getChildren().add(itemChild);
				}
	
				route_tree.setRoot(root);
	
				tape_image.setVisible(true);
				dist_label.setText("Distance: " + sr.distance + "km");
				
			}
			
			//---------------------------------- Hide all components --------------------------------------------//	
			// JAVAFX - UI elements set to 'false' are hidden, 'true' visible.
			
			info_message.setText("Woo Hoo! We have calculated the shortest route from " + starting_point + " to "
					+ destination + " below.");
			txtbox_start_label.setVisible(false);
			txtbox_destination_label.setVisible(false);

			txtbox_start.setVisible(false);
			txtbox_destination.setVisible(false);

			btn_shortest_route.setVisible(false);
			btn_all_routes.setVisible(false);
			btn_quickest_route.setVisible(false);

			route_tree.setVisible(true);
			dist_label.setVisible(true);
			new_search_button.setVisible(true);
			
			//---------------------------------------------------------------------------------------------------//

		} else {
			// end if empty statement
			error_message.setText("Whoops! Please fill in all required fields.");
			error_message.setVisible(true);
		}
	}

	
	
//===================================================== QUICKEST ROUTE (Time) ===================================================================//

	// Handles QUICKEST route button click.
	public void calculateQuickestRoute(ActionEvent e) {

		// Hide Previous Error Messages.
		error_message.setVisible(false);
		
		if (!database_loaded) {
			loadDatabase();
			database_loaded = true;
		}

		// First check both text boxes have been filled. (Temp check if Tra/Wat)
		if (!txtbox_start.getText().equals("") && !txtbox_destination.getText().equals("")) {

			starting_point = txtbox_start.getText();
			destination = txtbox_destination.getText();

			starting_point = starting_point.substring(0, 1).toUpperCase() + starting_point.substring(1).toLowerCase();
			destination = destination.substring(0, 1).toUpperCase() + destination.substring(1).toLowerCase();

			// Main Code
			for (int i = 0; i < routes.size(); i++) {

				String[] attr = ((String) routes.get(i)).split(",");

				// First will be start point, so after will be name of waypoint, speed limit to
				// next waypoint, distance to next waypoint, travel time to next waypoint
				String start_waypoint_name = attr[1];

				if (start_waypoint_name.equals(starting_point)) {

					int start_waypoint_speed_limit = Integer.parseInt(attr[2]);
					int start_waypoint_distance = Integer.parseInt(attr[3]);
					int start_waypoint_time = Integer.parseInt(attr[4]);

					if (!waypoints.containsKey(start_waypoint_name)) {
						waypoints.put(start_waypoint_name, new Waypoint(start_waypoint_name, start_waypoint_speed_limit,
								start_waypoint_distance, start_waypoint_time, new TownNode<>(start_waypoint_name)));
					}

					// Waypoint generator
					int gen = 0;
					int waypoint_id = 0;

					String waypoint_one_name = null;
					String waypoint_two_name = null;
					String waypoint_three_name = null;
					String waypoint_four_name = null;

					//-------------------------------- Get DATABASE FILE attributes from here on... ----------------------------------------------//
					for (int j = gen + 5; j < attr.length; j = j + 5) {

						if (attr[j].equals("WAYPOINT")) {

							waypoint_id++;

							if (waypoint_id == 1) {

								waypoint_one_name = attr[j + 1];

								int waypoint_one_speed_limit = Integer.parseInt(attr[j + 2]);
								int waypoint_one_distance = Integer.parseInt(attr[j + 3]);
								int waypoint_one_time = Integer.parseInt(attr[j + 4]);

								
									waypoints.put(waypoint_one_name,
											new Waypoint(waypoint_one_name, waypoint_one_speed_limit,
													waypoint_one_distance, waypoint_one_time,
													new TownNode<>(waypoint_one_name)));
								

								
								waypoints.get(start_waypoint_name).getTownNode().connectToNodeUndirected(
										waypoints.get(waypoint_one_name).getTownNode(),
										waypoints.get(start_waypoint_name).getSpeedLimit(),
										waypoints.get(start_waypoint_name).getDistance(),
										waypoints.get(start_waypoint_name).getTime());

							} else if (waypoint_id == 2) {

								waypoint_two_name = attr[j + 1];
								int waypoint_two_speed_limit = Integer.parseInt(attr[j + 2]);
								int waypoint_two_distance = Integer.parseInt(attr[j + 3]);
								int waypoint_two_time = Integer.parseInt(attr[j + 4]);

							
									waypoints.put(waypoint_two_name,
											new Waypoint(waypoint_two_name, waypoint_two_speed_limit,
													waypoint_two_distance, waypoint_two_time,
													new TownNode<>(waypoint_two_name)));
								

								waypoints.get(waypoint_one_name).getTownNode().connectToNodeUndirected(
										waypoints.get(waypoint_two_name).getTownNode(),
										waypoints.get(waypoint_one_name).getSpeedLimit(),
										waypoints.get(waypoint_one_name).getDistance(),
										waypoints.get(waypoint_one_name).getTime());

							} else if (waypoint_id == 3) {

								waypoint_three_name = attr[j + 1];
								int waypoint_three_speed_limit = Integer.parseInt(attr[j + 2]);
								int waypoint_three_distance = Integer.parseInt(attr[j + 3]);
								int waypoint_three_time = Integer.parseInt(attr[j + 4]);

								
									waypoints.put(waypoint_three_name,
											new Waypoint(waypoint_three_name, waypoint_three_speed_limit,
													waypoint_three_distance, waypoint_three_time,
													new TownNode<>(waypoint_three_name)));
								

								waypoints.get(waypoint_two_name).getTownNode().connectToNodeUndirected(
										waypoints.get(waypoint_three_name).getTownNode(),
										waypoints.get(waypoint_two_name).getSpeedLimit(),
										waypoints.get(waypoint_two_name).getDistance(),
										waypoints.get(waypoint_two_name).getTime());

							} else if (waypoint_id == 4) {

								waypoint_four_name = attr[j + 1];
								int waypoint_four_speed_limit = Integer.parseInt(attr[j + 2]);
								int waypoint_four_distance = Integer.parseInt(attr[j + 3]);
								int waypoint_four_time = Integer.parseInt(attr[j + 4]);

								
									waypoints.put(waypoint_four_name,
											new Waypoint(waypoint_four_name, waypoint_four_speed_limit,
													waypoint_four_distance, waypoint_four_time,
													new TownNode<>(waypoint_four_name)));
								

								waypoints.get(waypoint_three_name).getTownNode().connectToNodeUndirected(
										waypoints.get(waypoint_four_name).getTownNode(),
										waypoints.get(waypoint_three_name).getSpeedLimit(),
										waypoints.get(waypoint_three_name).getDistance(),
										waypoints.get(waypoint_three_name).getTime());

							}

						} else if (attr[j].equals("DEST")) {

							gen = 1;

							String dest_waypoint_name = attr[j + 1];

							if (!waypoints.containsKey(dest_waypoint_name)) {
								waypoints.put(dest_waypoint_name,
										new Waypoint(dest_waypoint_name, new TownNode<>(dest_waypoint_name)));
							}

							switch (waypoint_id) {

							case 0:
							default:
								waypoints.get(start_waypoint_name).getTownNode().connectToNodeUndirected(
										waypoints.get(dest_waypoint_name).getTownNode(),
										waypoints.get(start_waypoint_name).getSpeedLimit(),
										waypoints.get(start_waypoint_name).getDistance(),
										waypoints.get(start_waypoint_name).getTime());
								break;
							case 1:
								waypoints.get(waypoint_one_name).getTownNode().connectToNodeUndirected(
										waypoints.get(dest_waypoint_name).getTownNode(),
										waypoints.get(waypoint_one_name).getSpeedLimit(),
										waypoints.get(waypoint_one_name).getDistance(),
										waypoints.get(waypoint_one_name).getTime());
								break;
							case 2:
								waypoints.get(waypoint_two_name).getTownNode().connectToNodeUndirected(
										waypoints.get(dest_waypoint_name).getTownNode(),
										waypoints.get(waypoint_two_name).getSpeedLimit(),
										waypoints.get(waypoint_two_name).getDistance(),
										waypoints.get(waypoint_two_name).getTime());
								break;
							case 3:
								waypoints.get(waypoint_three_name).getTownNode().connectToNodeUndirected(
										waypoints.get(dest_waypoint_name).getTownNode(),
										waypoints.get(waypoint_three_name).getSpeedLimit(),
										waypoints.get(waypoint_three_name).getDistance(),
										waypoints.get(waypoint_three_name).getTime());
								break;
							case 4:
								waypoints.get(waypoint_four_name).getTownNode().connectToNodeUndirected(
										waypoints.get(dest_waypoint_name).getTownNode(),
										waypoints.get(waypoint_four_name).getSpeedLimit(),
										waypoints.get(waypoint_four_name).getDistance(),
										waypoints.get(waypoint_four_name).getTime());
								break;

							}

							waypoint_id = 0;

						} else {
							break;
						}

					}
				}

			}

			//-------------------------------------- TREE VIEW ---------------------------------------------------//
			
			// If route doesn't exist, let them know!
			if (!waypoints.containsKey(starting_point) || !waypoints.containsKey(destination)) {
				
				error_message.setText("No such route exists, please try again!");
				error_message.setVisible(true);
				
				TreeItem<String> root = new TreeItem<>("No Route Found.");
				route_tree.setRoot(root);
				
				time_label.setText(" ");
				clock_image.setVisible(false);
			
			} else {
			
				QuickestRoute qr = searchQuickestRouteDijkstra(waypoints.get(starting_point).getTownNode(), destination);
	
				TreeItem<String> root = new TreeItem<>("Quickest Route");
	
				for (TownNode<?> n : qr.roadList) {
					TreeItem<String> itemChild = new TreeItem<>(n.data.toString());
					itemChild.setExpanded(false);
					// root is the parent of itemChild
					root.getChildren().add(itemChild);
				}
	
				route_tree.setRoot(root);
	
				clock_image.setVisible(true);
				time_label.setText("Total Travel Time: " + qr.time + " mins");
				
			}

			//--------------------------------------- Hide all components ----------------------------------------//
			// JAVAFX - UI elements set to 'false' are hidden, 'true' visible.
			info_message.setText("Woo Hoo! We have calculated the quickest route from " + starting_point + " to "
					+ destination + " below.");
			txtbox_start_label.setVisible(false);
			txtbox_destination_label.setVisible(false);

			txtbox_start.setVisible(false);
			txtbox_destination.setVisible(false);

			btn_shortest_route.setVisible(false);
			btn_all_routes.setVisible(false);
			btn_quickest_route.setVisible(false);

			route_tree.setVisible(true);
			time_label.setVisible(true);
			new_search_button.setVisible(true);
			
			//----------------------------------------------------------------------------------------------------//
			
		} else {
			// Display error message if entry fields are left blank and a button is pressed.
			error_message.setText("Whoops! Please fill in all required fields.");
			error_message.setVisible(true);
		}

	}

	
	//--------------------- All possible routes between selected Starting point & Destination ----------------------//
	public void showAllRoutes(ActionEvent e) {
		
		if (!database_loaded) {
			loadDatabase();
			database_loaded = true;
		}
		
		// Just to be sure..
		route_tree.setRoot(null);

		if (!txtbox_start.getText().equals("") && !txtbox_destination.getText().equals("")) {

			starting_point = txtbox_start.getText();
			destination = txtbox_destination.getText();

			starting_point = starting_point.substring(0, 1).toUpperCase() + starting_point.substring(1).toLowerCase();
			destination = destination.substring(0, 1).toUpperCase() + destination.substring(1).toLowerCase();

			int routeId = 1;
			
			boolean validRoutes = true;

			TreeItem<String> root = new TreeItem<>("All Routes");

			for (int i = 0; i < routes.size(); i++) {

				TreeItem<String> sub_root = new TreeItem<>("Route " + routeId);
				sub_root.setExpanded(false);
				// root is the parent of itemChild
				root.getChildren().add(sub_root);

				routeId++;

				String[] attr = ((String) routes.get(i)).split(",");

				// First will be start point, so after will be name of waypoint, speed limit to
				// next waypoint, distance to next waypoint, travel time to next waypoint
				
				if (!attr[1].equals(starting_point)) {
					validRoutes = false;
				} else {
					TreeItem<String> itemChild = new TreeItem<>(attr[1]);
					itemChild.setExpanded(false);
					// root is the parent of itemChild
					sub_root.getChildren().add(itemChild);

					// Waypoint generator
					int gen = 0;
					int waypoint_id = 0;

					
					//----------------------------- Get DATABASE FILE attributes from here on... ---------------------------------------//
					for (int j = gen + 5; j < attr.length; j = j + 5) {

						if (attr[j].equals("WAYPOINT")) {

							waypoint_id++;

							if (waypoint_id == 1) {

								TreeItem<String> itemChild1 = new TreeItem<>(attr[j + 1]);
								itemChild1.setExpanded(false);
								// root is the parent of itemChild
								sub_root.getChildren().add(itemChild1);

							} else if (waypoint_id == 2) {

								TreeItem<String> itemChild2 = new TreeItem<>(attr[j + 1]);
								itemChild2.setExpanded(false);
								// root is the parent of itemChild
								sub_root.getChildren().add(itemChild2);

							} else if (waypoint_id == 3) {

								TreeItem<String> itemChild3 = new TreeItem<>(attr[j + 1]);
								itemChild3.setExpanded(false);
								// root is the parent of itemChild
								sub_root.getChildren().add(itemChild3);

							} else if (waypoint_id == 4) {

								TreeItem<String> itemChild4 = new TreeItem<>(attr[j + 1]);
								itemChild4.setExpanded(false);
								// root is the parent of itemChild
								sub_root.getChildren().add(itemChild4);

							}

						} else if (attr[j].equals("DEST")) {

							gen = 1;

							if (!attr[j + 1].equals(destination)) {
								validRoutes = false;
							} else {
								TreeItem<String> itemChild5 = new TreeItem<>(attr[j + 1]);
								itemChild5.setExpanded(false);
								// root is the parent of itemChild
								sub_root.getChildren().add(itemChild5);
							}

						}

					}
				}
					
				
			}
			
			if (!validRoutes) {
				root = new TreeItem<>("No Routes Found.");
			}
			route_tree.setRoot(root);
			

			//------------------------------------ Hide all components ---------------------------------------------//
			error_message.setVisible(false);
			info_message.setText(
					"Woo Hoo! Below are all the routes possible from " + starting_point + " to " + destination + ".");
			txtbox_start_label.setVisible(false);
			txtbox_destination_label.setVisible(false);

			txtbox_start.setVisible(false);
			txtbox_destination.setVisible(false);

			btn_shortest_route.setVisible(false);
			btn_all_routes.setVisible(false);
			btn_quickest_route.setVisible(false);

			route_tree.setVisible(true);
			new_search_button.setVisible(true);
			
			//------------------------------------------------------------------------------------------------------//

		} else {
			error_message.setText("Whoops! Please fill in all required fields.");
			error_message.setVisible(true);
		}

	}
	
	// --------- Do a new search! --------//
	public void newSearch(ActionEvent e) {

		txtbox_start.setText("");
		txtbox_destination.setText("");

		info_message.setText("Please fill in the fields below and choose the appropriate button");
		txtbox_start_label.setVisible(true);
		txtbox_destination_label.setVisible(true);

		txtbox_start.setVisible(true);
		txtbox_destination.setVisible(true);

		btn_shortest_route.setVisible(true);
		btn_all_routes.setVisible(true);
		btn_quickest_route.setVisible(true);

		clock_image.setVisible(false);
		tape_image.setVisible(false);

		route_tree.setVisible(false);
		dist_label.setVisible(false);
		time_label.setVisible(false);
		new_search_button.setVisible(false);
		
		// Reset the tree views.
		route_tree.setRoot(null);
		
		// Hide any errors.
		error_message.setText(" ");
		error_message.setVisible(false);
		
	}


// DIJKSTRA'S ALGORITHM //
//============================================= ALGORITHM: SHORTEST ROUTE (Distance) =================================================================//
	
	// new search for shortest route
	public static <T> ShortestRoute searchShortestRouteDijkstra(TownNode<?> townNode, T lookingFor) {
		
		ShortestRoute sr = new ShortestRoute();
		List<TownNode<?>> encountered = new ArrayList<>(), unencountered = new ArrayList<>();
		townNode.nodeValue = 0;
		unencountered.add(townNode);
		TownNode<?> currentNode;
		
		do {
			currentNode = unencountered.remove(0);
			encountered.add(currentNode);

			if (currentNode.data.equals(lookingFor)) {
				sr.roadList.add(currentNode);
				sr.distance = currentNode.nodeValue;
				

				while (currentNode != townNode) {
					boolean foundPrevTownNode = false;
					for (TownNode<?> n : encountered) {
						for (RoadLink e : n.roadList) {
							if (e.townNode == currentNode && currentNode.nodeValue - e.distance == n.nodeValue) {
								sr.roadList.add(0, n);
								currentNode = n;
								foundPrevTownNode = true;
								break;
							}
							if (foundPrevTownNode)
								break;
						}
					}

				}

				for (TownNode<?> n : encountered)
					n.nodeValue = Integer.MAX_VALUE;
				for (TownNode<?> n : unencountered)
					n.nodeValue = Integer.MAX_VALUE;

				return sr;
			}

			for (RoadLink e : currentNode.roadList) {
				if (!encountered.contains(e.townNode)) {
					e.townNode.nodeValue = Integer.min(e.townNode.nodeValue, currentNode.nodeValue + e.distance);
					unencountered.add(e.townNode);
				}
			}
			
			Collections.sort(unencountered, (n1, n2) -> n1.nodeValue - n2.nodeValue);

		} while (!unencountered.isEmpty());
		return null;

	}

	@SuppressWarnings("unchecked")
	public static void loadDatabase() {

		File file = new File("routes.csv");

		try {

			Scanner inputStream = new Scanner(file);

			while (inputStream.hasNext()) {

				String data = inputStream.next().replace("_", " ");

				routes.add(data);

			}

			inputStream.close();

		} catch (FileNotFoundException e) {
			System.out.println(
					"Sorry, that file was not found, please make sure it is in the project root and try again.");

		}

	}

// DIJKSTRA'S ALGORITHM //
//=========================================== ALGORITHM: QUICKEST ROUTE (Time) ==============================================================//
	// new search for quickest route
	public static <T> QuickestRoute searchQuickestRouteDijkstra(TownNode<?> townNode, T lookingFor) {

		QuickestRoute qr = new QuickestRoute();
		List<TownNode<?>> encountered = new ArrayList<>(), unencountered = new ArrayList<>();
		townNode.nodeValue = 0;
		unencountered.add(townNode);
		TownNode<?> currentNode;

		do {
			currentNode = unencountered.remove(0);
			encountered.add(currentNode);

			if (currentNode.data.equals(lookingFor)) {
				qr.roadList.add(currentNode);
				qr.time = currentNode.nodeValue;

				while (currentNode != townNode) {
					boolean foundPrevTownNode = false;
					for (TownNode<?> n : encountered) {
						for (RoadLink e : n.roadList) {
							if (e.townNode == currentNode && currentNode.nodeValue - e.time == n.nodeValue) {
								qr.roadList.add(0, n);
								currentNode = n;
								foundPrevTownNode = true;
								break;
							}
							if (foundPrevTownNode)
								break;
						}
					}

				}

				for (TownNode<?> n : encountered)
					n.nodeValue = Integer.MAX_VALUE;
				for (TownNode<?> n : unencountered)
					n.nodeValue = Integer.MAX_VALUE;

				return qr;
			}

			for (RoadLink e : currentNode.roadList) {
				if (!encountered.contains(e.townNode)) {
					e.townNode.nodeValue = Integer.min(e.townNode.nodeValue, currentNode.nodeValue + e.time);
					unencountered.add(e.townNode);
				}
			}
			Collections.sort(unencountered, (n1, n2) -> n1.nodeValue - n2.nodeValue);

		} while (!unencountered.isEmpty());
		return null;

	}

	// Sadly not used, however can be implemented in future revisions.
	public static void traverse(TownNode<?> from, List<TownNode<?>> encountered) {
		System.out.println(from.data);
		if (encountered == null)
			encountered = new ArrayList<>();
		encountered.add(from);
		for (RoadLink roadLink : from.roadList)
			if (!encountered.contains(roadLink.townNode))
				traverse(roadLink.townNode, encountered);
	}

}
