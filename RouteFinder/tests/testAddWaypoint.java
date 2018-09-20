
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import application.Waypoint;

/**
 * The Class testAddWaypoint.
 */
public class testAddWaypoint {

	/**
	 * Setup.
	 */
	@Before
	public void setUp() {}
	
	/**
	 * Adds the Waypoint.
	 */
	@Test
	public void addWaypoint() {
		
		Waypoint waypoint = new Waypoint("Waterford", 50, 3, 9, null);
		
		// Test the name was set correctly
		assertEquals("Waterford", waypoint.getName());
		
		// Test the speed limit was set correctly
		assertEquals(50, waypoint.getSpeedLimit());
				
		// Test the distance was set correctly
		assertEquals(3, waypoint.getDistance());
				
		// Test the time was set correctly
		assertEquals(9, waypoint.getTime());
		
	}
	
	/**
	 * Tear down.
	 */
	@After
	public void tearDown() {}
	
}
