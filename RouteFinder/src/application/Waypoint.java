package application;

public class Waypoint {

	String name;
	int speedLimit = 0;
	int distance = 0;
	int time = 0;
	TownNode<String> townNode = null;
	
	
	// Constructor
	public Waypoint(String name, int speedLimit, int distance, int time, TownNode<String> townNode) {
		this.name = name;
		this.speedLimit = speedLimit;
		this.distance = distance;
		this.time = time;
		this.townNode = townNode;
	}
	
	public Waypoint(String name, TownNode<String> townNode) {
		this.name = name;
		this.townNode = townNode;
	}
	
	
	// ----------- Getters ---------//
	public String getName() {
		return this.name;
	}
	
	public int getSpeedLimit() {
		return this.speedLimit;
	}
	
	public int getDistance() {
		return this.distance;
	}
	
	public int getTime() {
		return this.time;
	}
	
	public TownNode<?> getTownNode() {
		return this.townNode;
	}
	
}
