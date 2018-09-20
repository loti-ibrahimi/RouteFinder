/*
 * Date: 29/04/2018
 * Authors: Lee O'Mahoney & Loti Ibrahimi (W20015453)
 * Module: Data Structure & Algorithms 2
 * Course: Internet of Things (Year 2)
 * 
 * 												Application: RouteFinder
 * 											-------------------------------
 * Description:
 * ===========
 * This is a small app that basically takes in two locations - starting point & a destination, then giving the user the following options:
 * - Give the shortest route between the two (distance wise).
 * - Show all possible routes between the two.
 * - Give the quickest route between the two (time wise).   
 * 
 * A database of other routes may be brought in therefore having a greater possibility of user location inputs!
 * 
 * The shortest/quickest routes were established through the use of Dijksta's algorithm.
 * 
 * 
 * Notes:
 * ===========
 * - The possibility of avoiding certain waypoints/towns/roads etc. on a route has not been implemented yet as of now.
 * 
 */

package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


// =================================================== RUN APP ==================================================================//
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {

			Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));

			primaryStage.setTitle("Data Structures 2 Route Finder");

			primaryStage.setMinWidth(900);
			primaryStage.setMinHeight(600);
			primaryStage.setMaxWidth(900);
			primaryStage.setMaxHeight(600);

			Scene scene = new Scene(root, 900, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}

//=================================================================================================================================//
