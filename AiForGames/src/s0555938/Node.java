package s0555938;

import java.awt.Point;
import java.util.ArrayList;
import java.awt.Rectangle;

public class Node {

	private int serial;
	
	//cost to travel to this node
	//1 for walkable nodes, Integer.MAX_VALUE for not walkable ones
	private int cost;
	
	//the value of this node
	private int value;
	
	private Point bottomLeftCorner;
	
	ArrayList<Node> neighbours = new ArrayList<Node>();
	
	public Node(int serial, int cost, Point bottomLeftCorner){
		
		//System.out.println("Node created");
		this.serial = serial;
		this.cost = cost;
		this.bottomLeftCorner = bottomLeftCorner;
	}
	
	public int getSerial(){
		return serial;
	}
	
	public int getCost(){
		return cost;
	}
	
	public void setCost(int cost){
		this.cost = cost;
	}
	
	public int getValue(){
		return value;
	}
	
	public void setValue(int value){
		this.value = value;
	}
	
	public Point getPosition(){
		return bottomLeftCorner;
	}
	
	public void setNeighbours(ArrayList<Node> nodes){
		neighbours = new ArrayList<Node>();
		for (int i=0; i<nodes.size(); i++){
			neighbours.add(nodes.get(i));
		}
	}
	
	public ArrayList<Node> getNeighbours(){
		return neighbours;
	}
	
	public Node getNeighbourWithNextValue(Node goal){
		
		/////// TODO FALLS ES MEHR ALS 1 NEIGHBOUR GIBT MIT TIEFEREM VALUE, SOLL DER MIT DER KÜRZESTEN LUFTLINIE GEWÄHLT WERDEN \\\\\\\\\\
		/*
		Node next = neighbours.get(0);
		for(int i=1; i<neighbours.size(); i++){
			if (neighbours.get(i).getValue() == getValue()-1 /*&& neighbours.get(i).getValue(3 > -1){
				next = neighbours.get(i);
				
				break;
			}
		}
		return next;	
		*/
		
		ArrayList<Node> nextList = new ArrayList<Node>();
		Node next = neighbours.get(0);
		
		System.out.println("neighbourSize: " + neighbours.size());
		
		for(int i=0; i<neighbours.size(); i++){
			
			System.out.println(neighbours.get(i).getValue());
			
			if (neighbours.get(i).getValue() == getValue()-1){
				next = neighbours.get(i);
				nextList.add(next);
			}
			//if (neighbours.get(i).getValue() <= getValue()-1){
			//	next = neighbours.get(i);
			//	nextList.add(next);
			//}
		}
		
		System.out.println(nextList.size());
		//if(nextList.size()>0){
		next = nextList.get(0);
		
		for(int i=0; i<nextList.size(); i++){
			System.out.println(nextList.get(i).getValue());
		}
		
		//}
		//else {
		//next = neighbours.get(0);
		//}
		for(int i=0; i<nextList.size(); i++){
			/*
			if(Math.sqrt(Math.pow(goal.getPosition().x+5 - next.getPosition().x+5, 2) + Math.pow(goal.getPosition().y+5 - next.getPosition().y+5, 2))
				> Math.sqrt(Math.pow(goal.getPosition().x+5 - nextList.get(i).getPosition().x+5, 2) + Math.pow(goal.getPosition().x+5 - nextList.get(i).getPosition().x+5, 2))){
				next = nextList.get(i);
			}*/
			if(Math.sqrt(Math.pow(next.getPosition().x+5 - goal.getPosition().x+5, 2) + Math.pow(next.getPosition().y+5 - goal.getPosition().y+5, 2))
					> Math.sqrt(Math.pow(nextList.get(i).getPosition().x+5 - goal.getPosition().x+5, 2) + Math.pow(nextList.get(i).getPosition().x+5 - goal.getPosition().x+5, 2))){
					next = nextList.get(i);
				}
		}
		return next;
	}
	
	public boolean contains(Point point){
		Rectangle rect = new Rectangle(bottomLeftCorner.x, bottomLeftCorner.y, 10, 10);
		
		if (rect.contains(point)){
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean containsWithTolerance(Point point){
		Rectangle rect = new Rectangle(bottomLeftCorner.x-15, bottomLeftCorner.y-15, 40, 40);
		
		if (rect.contains(point)){
			return true;
		}
		else {
			return false;
		}
	}
}
