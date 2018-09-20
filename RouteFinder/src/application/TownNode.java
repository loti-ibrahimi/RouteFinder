package application;

import java.util.ArrayList;
import java.util.List;

public class TownNode<Type> {

	public Type data;
	public int nodeValue = Integer.MAX_VALUE;

	public List<RoadLink> roadList = new ArrayList<>();

	public TownNode(Type data) {
		this.data = data;
	}

	public void connectToNodeDirected(TownNode<Type> destNode, int speed, int distance, int time) {
		roadList.add(new RoadLink(destNode, speed, distance, time));
	}

	public void connectToNodeUndirected(TownNode<?> townNode, int speed, int distance, int time) {
		roadList.add(new RoadLink(townNode, speed, distance, time));
		townNode.roadList.add(new RoadLink(this, speed, distance, time));
	}

}
