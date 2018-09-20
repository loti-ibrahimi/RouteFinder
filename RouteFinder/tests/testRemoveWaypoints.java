
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import application.Waypoint;

// TODO: Auto-generated Javadoc
/**
 * The Class testRemoveWaypoint.
 */
public class testRemoveWaypoints {

	/**
	 * Setup.
	 */
	@Before
	public void setUp() {}
	
	
	/**
	 * Removes the Waypoints.
	 */
	@Test
	public void getWaypoints() {
		
		HashMap<String, Waypoint> waypoints = new HashMap<String, Waypoint>();
		
		Waypoint waypoint_one 	= new Waypoint("Waterford", 50, 3, 9, null);
		Waypoint waypoint_two 	= new Waypoint("Dungarvan", 80, 30, 45, null);
		Waypoint waypoint_three 	= new Waypoint("Tramore", 80, 10, 13, null);
		
		waypoints.put("Waterford", waypoint_one);
		waypoints.put("Dungarvan", waypoint_two);
		waypoints.put("Tramore", waypoint_three);
		
		// Waypoint 1 - Waterford
		assertEquals(50, waypoints.get("Waterford").getSpeedLimit());
		assertEquals(3, waypoints.get("Waterford").getDistance());
		assertEquals(9, waypoints.get("Waterford").getTime());
		
		// Waypoint 2 - Dungarvan
		assertEquals(80, waypoints.get("Dungarvan").getSpeedLimit());
		assertEquals(30, waypoints.get("Dungarvan").getDistance());
		assertEquals(45, waypoints.get("Dungarvan").getTime());
		
		// Waypoint 3 - Tramore
		assertEquals(80, waypoints.get("Tramore").getSpeedLimit());
		assertEquals(10, waypoints.get("Tramore").getDistance());
		assertEquals(13, waypoints.get("Tramore").getTime());
		
		// Now Remove.
		waypoints.remove("Waterford");
		waypoints.remove("Dungarvan");
		waypoints.remove("Tramore");
		
		// Check they are removed.
		assertEquals(null, waypoints.get("Waterford"));
		assertEquals(null, waypoints.get("Dungarvan"));
		assertEquals(null, waypoints.get("Tramore"));
		
		
	
	}
	
	/**
	 * Tear down.
	 */
	@After
	public void tearDown() {}
	
}
