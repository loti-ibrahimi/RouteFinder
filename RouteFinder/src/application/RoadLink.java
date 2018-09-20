package application;

public class RoadLink {

	public TownNode<?> townNode;
	public int speed;
	public int distance;
	public int time;

	public RoadLink(TownNode<?> townNode, int speed, int d, int time) {
		this.townNode = townNode;
		this.speed = speed;
		this.distance = d;
		this.time = time;
	}

}
