package s0555938;

import java.util.ArrayList;
//import java.util.Stack;
import java.awt.Point;

import s0555938.Node;

public class Dijkstra {
	ArrayList<Node> shortestWayReverse = new ArrayList<Node>();
	ArrayList<Node> nodes = new ArrayList<Node>();
	ArrayList<Integer> allNodes = new ArrayList<Integer>();
	
	Node startNode;
	
	int costs[][];

	Point start;
	Point goal;

	private ArrayList<Node> currentNeighbourList;
	
	public Dijkstra(boolean[][] walkable, int width, int height, Point Start, Point Goal){

		costs = new int[width][height];
		
		start = Start;		
		goal = Goal;
		

		for(int x=0; x < width; x++){
			for(int y=0; y < height; y++){
				if (walkable[x][y] == false){
					costs[x][y] = -1;
				}
				else {
					costs[x][y] = 1;
				}
			}
		}
		initNodes();
	}
	
	private void initNodes(){
		int serial = 0;
		
		//creating nodes with the essential parameters
		for (int x=0; x<costs.length; x++){
			for (int y=0; y<costs[x].length; y++){
				Point point = new Point(x*10, y*10);
				
				Node node = new Node(serial, costs[x][y], point);
				
				if(node.contains(start)){
					node.setCost(0);
					node.setValue(0);
					
					startNode = node;
				}
				
				nodes.add(node);
				
				serial++;
			}
		}
		advancedNeighbourhood();
		
		refreshCosts();
	}
	
	private void basicNeighbourhood(){
		//setting the neighbours
				for (int x=0; x<costs.length; x++){
					for (int y=0; y<costs[x].length; y++){
						ArrayList<Node> newNeighbours = new ArrayList<Node>();
						Node node;
						//left and right neighbours
						//x at the very left
						if (x == 0){
							node = nodes.get((x+1) * costs.length + y);
									
							newNeighbours.add(node);
						}
						//x at the very right
						else if(x == costs.length - 1){
							node = nodes.get((x-1) * costs.length + y);
							
							newNeighbours.add(node);
						}
						else {
							node = nodes.get((x+1) * costs.length + y);
							
							newNeighbours.add(node);
							
							node = nodes.get((x-1) * costs.length + y);
							
							newNeighbours.add(node);
						}
						
						//top and bottom neighbours
						//y at the very bottom
						if (y == 0){
							node = nodes.get(x*costs.length + y+1);
							
							newNeighbours.add(node);
						}
						//y at the very top
						else if(y == costs[x].length - 1){
							node = nodes.get(x*costs.length + y-1);
							
							newNeighbours.add(node);
						}
						else {
							node = nodes.get(x*costs.length + y+1);
							
							newNeighbours.add(node);
							
							node = nodes.get(x*costs.length + y-1);
							
							newNeighbours.add(node);
						}
						
						nodes.get(x*costs.length + y).setNeighbours(newNeighbours);
					}
				}
	}
	
	private void advancedNeighbourhood(){
		//setting the neighbours
				for (int x=0; x<costs.length; x++){
					for (int y=0; y<costs[x].length; y++){
						ArrayList<Node> newNeighbours = new ArrayList<Node>();
						Node node;
						//ADVANCED DIAGONAL NEIGHBOURHOOD SYSTEM
						//bottom left
						if(x==0 && y == 0){
							//top neighbour
							node = nodes.get(x*costs.length + y+1);					
							newNeighbours.add(node);	
							
							//top right neighbour
							node = nodes.get((x+1) * costs.length + y+1);					
							newNeighbours.add(node);
							
							//right neighbour
							node = nodes.get((x+1) * costs.length + y);					
							newNeighbours.add(node);
						}
						//top left
						else if(x== 0 && y == costs[x].length -1){
							//right neighbour
							node = nodes.get((x+1) * costs.length + y);					
							newNeighbours.add(node);
							
							//bottom right neighbour
							node = nodes.get((x+1) * costs.length + y-1);					
							newNeighbours.add(node);
							
							//bottom neighbour
							node = nodes.get(x*costs.length + y-1);					
							newNeighbours.add(node);
						}
						//top right
						else if(x==costs.length - 1 && y == costs[x].length -1){
							//bottom neighbour
							node = nodes.get(x*costs.length + y-1);					
							newNeighbours.add(node);
							
							//bottom left neighbour
							node = nodes.get((x-1) * costs.length + y-1);					
							newNeighbours.add(node);
							
							//left neighbour
							node = nodes.get((x-1) * costs.length + y);					
							newNeighbours.add(node);
						}
						//bottom right
						else if(x==costs.length - 1 && y == 0){
							//left neighbour
							node = nodes.get((x-1) * costs.length + y);					
							newNeighbours.add(node);
							
							//top left neighbour
							node = nodes.get((x-1) * costs.length + y+1);					
							newNeighbours.add(node);
							
							//top neighbour
							node = nodes.get(x*costs.length + y+1);					
							newNeighbours.add(node);		
						}
						//left
						else if(x==0 && y != 0 && y!= costs[x].length -1){
							//top neighbour
							node = nodes.get(x*costs.length + y+1);					
							newNeighbours.add(node);	
							
							//top right neighbour
							node = nodes.get((x+1) * costs.length + y+1);					
							newNeighbours.add(node);
							
							//right neighbour
							node = nodes.get((x+1) * costs.length + y);					
							newNeighbours.add(node);
							
							//bottom right neighbour
							node = nodes.get((x+1) * costs.length + y-1);					
							newNeighbours.add(node);
							
							//bottom neighbour
							node = nodes.get(x*costs.length + y-1);					
							newNeighbours.add(node);
						}
						//right
						else if(x==costs.length - 1 && y != 0 && y!= costs[x].length -1){
							//bottom neighbour
							node = nodes.get(x*costs.length + y-1);					
							newNeighbours.add(node);
							
							//bottom left neighbour
							node = nodes.get((x-1) * costs.length + y-1);					
							newNeighbours.add(node);
							
							//left neighbour
							node = nodes.get((x-1) * costs.length + y);					
							newNeighbours.add(node);
							
							//top left neighbour
							node = nodes.get((x-1) * costs.length + y+1);					
							newNeighbours.add(node);
							
							//top neighbour
							node = nodes.get(x*costs.length + y+1);					
							newNeighbours.add(node);		
						}
						//top
						else if(x!=costs.length - 1 && x != 0 && y== costs[x].length -1){
							//right neighbour
							node = nodes.get((x+1) * costs.length + y);					
							newNeighbours.add(node);
							
							//bottom right neighbour
							node = nodes.get((x+1) * costs.length + y-1);					
							newNeighbours.add(node);
							
							//bottom neighbour
							node = nodes.get(x*costs.length + y-1);					
							newNeighbours.add(node);
							
							//bottom left neighbour
							node = nodes.get((x-1) * costs.length + y-1);					
							newNeighbours.add(node);
							
							//left neighbour
							node = nodes.get((x-1) * costs.length + y);					
							newNeighbours.add(node);
						}
						//bottom
						else if(x!=costs.length - 1 && x != 0 && y== 0){
							//left neighbour
							node = nodes.get((x-1) * costs.length + y);					
							newNeighbours.add(node);
							
							//top left neighbour
							node = nodes.get((x-1) * costs.length + y+1);					
							newNeighbours.add(node);
							
							//top neighbour
							node = nodes.get(x*costs.length + y+1);					
							newNeighbours.add(node);	
							
							//top right neighbour
							node = nodes.get((x+1) * costs.length + y+1);					
							newNeighbours.add(node);
							
							//right neighbour
							node = nodes.get((x+1) * costs.length + y);					
							newNeighbours.add(node);
						}
						else{		
							//top right neighbour
							node = nodes.get((x+1) * costs.length + y+1);					
							newNeighbours.add(node);
							
							//right neighbour
							node = nodes.get((x+1) * costs.length + y);					
							newNeighbours.add(node);
							
							//bottom right neighbour
							node = nodes.get((x+1) * costs.length + y-1);					
							newNeighbours.add(node);
							
							//bottom neighbour
							node = nodes.get(x*costs.length + y-1);					
							newNeighbours.add(node);
							
							//bottom left neighbour
							node = nodes.get((x-1) * costs.length + y-1);					
							newNeighbours.add(node);
							
							//left neighbour
							node = nodes.get((x-1) * costs.length + y);					
							newNeighbours.add(node);
							
							//top left neighbour
							node = nodes.get((x-1) * costs.length + y+1);					
							newNeighbours.add(node);
							
							//top neighbour
							node = nodes.get(x*costs.length + y+1);					
							newNeighbours.add(node);			
						}
						
						nodes.get(x*costs.length + y).setNeighbours(newNeighbours);
					}
				}
	}
	
	private void refreshCosts(){
		for (int x=0; x<costs.length; x++){
			for (int y=0; y<costs[x].length; y++){
				
				if(nodes.get(x*costs.length + y).contains(start)){
					nodes.get(x*costs.length + y).setCost(0);
					
					startNode = nodes.get(x*costs.length + y);
				}
				else if(costs[x][y] == -1){
					nodes.get(x*costs.length + y).setCost(-1);
				}
				else{
					nodes.get(x*costs.length + y).setCost(1);
				}
			}
		}
	}
	
	private void setNodeValues(Node currentNode){
		//currentNode.setValue(value);
		
		//System.out.println(allNodes.size());
		
		//System.out.println("entered nodeValues");
		int currentValue = currentNode.getValue();
		ArrayList<Node> nodesNeighbours = new ArrayList<Node>();	
				
		nodesNeighbours = currentNode.getNeighbours();
		
		allNodes.add(currentNode.getSerial());
		
		//System.out.println(currentNode.getValue());
		
		while(nodes.size() != allNodes.size()){
			for (int i = 0; i<nodesNeighbours.size(); i++){
				currentValue += nodesNeighbours.get(i).getCost();
				
				
				if(nodesNeighbours.get(i).getCost() == -1){
					nodesNeighbours.get(i).setValue(-1);
				}
				else if (nodesNeighbours.get(i).getValue() < currentValue || currentNode.getCost() == -1  || currentNode.getValue() == 10000){
					//do nothing
				}
				else /*if (currentNode.getCost() != -1)*/{
					nodesNeighbours.get(i).setValue(currentValue);
					
					//System.out.println(nodes.get(i).getValue());
				}
				
				currentValue = currentNode.getValue();
				//System.out.println(allNodes.size());
			}
			for (int i = 0; i<nodesNeighbours.size(); i++){
				//System.out.println(nodes.get(i).getValue());
				if(!allNodes.contains(nodesNeighbours.get(i).getSerial()) /*&& (nodes.get(i).getValue() >= 0)*/){
					setNodeValues(nodesNeighbours.get(i));
				}
			}
		}
	}
	
	private void setBetterNodeValues(Node currentNode){
		
		int currentValue = currentNode.getValue();
		ArrayList<Node> allNeighbourList = new ArrayList<Node>();
		currentNeighbourList = new ArrayList<Node>();
		ArrayList<Node> nodesNeighbours = new ArrayList<Node>();
				
		allNeighbourList.add(currentNode);
		currentNeighbourList = currentNode.getNeighbours();
		
		//FIRST VALUE CIRCLE
		for (int i = 0; i<currentNeighbourList.size(); i++){
			currentValue = currentNode.getValue() + currentNeighbourList.get(i).getCost();
		
			if(currentNeighbourList.get(i).getCost() == -1){
				currentNeighbourList.get(i).setValue(-1);
			}
			else if (currentNeighbourList.get(i).getValue() < currentValue || currentNode.getCost() == -1 || currentNode.getValue() == 10000){
				//do nothing
			}
			else {
				currentNeighbourList.get(i).setValue(currentValue);
			}
			currentValue = currentNode.getValue();
		}
		
		
		while(allNeighbourList.size()!=nodes.size()){
			//Value Circle
			for (int i = 0; i<currentNeighbourList.size(); i++){
				currentNode = currentNeighbourList.get(i);
				currentValue = currentNode.getValue();
				for (int j=0; j<currentNode.getNeighbours().size(); j++){
				
					currentValue += currentNode.getNeighbours().get(j).getCost();
			
			
					if(currentNode.getNeighbours().get(j).getCost() == -1){
						currentNode.getNeighbours().get(j).setValue(-1);
					}
					else if (currentNode.getNeighbours().get(j).getValue() < currentValue || currentNode.getCost() == -1 || currentNode.getValue() == 10000){
						//do nothing
					}
					else {
						currentNode.getNeighbours().get(j).setValue(currentValue);
					}
					currentValue = currentNode.getValue();			
				}
				allNeighbourList.add(currentNode);
			}
			//saving size atm
			int beforeLoopSize = currentNeighbourList.size();
			nodesNeighbours = new ArrayList<Node>();
			
			//setting new Neighbourhood
			for(int i=0; i<beforeLoopSize; i++){
				ArrayList<Node> currentNeighbours = currentNeighbourList.get(i).getNeighbours();
				for(int j=0; j<currentNeighbours.size(); j++){
					if(!allNeighbourList.contains(currentNeighbours.get(j)) && !nodesNeighbours.contains(currentNeighbours.get(j))){
						nodesNeighbours.add(currentNeighbours.get(j));
					}
				}			
			}
			
			currentNeighbourList = new ArrayList<Node>();
			currentNeighbourList.addAll( nodesNeighbours);
		}
	}
	
	private void initNodeValues(){
		for (int i=0; i<nodes.size(); i++){
			if(nodes.get(i).getCost() == -1){
				nodes.get(i).setValue(-1);
			}
			else {
				nodes.get(i).setValue(10000);
			}
		}
		startNode.setValue(0);
	}
	
	public ArrayList<Node> getShortestPath(Point goal, Point start) {
		//shortestWayReverse neu initialisieren
		shortestWayReverse = new ArrayList<Node>();
		
		this.start = start;
		
		for (int i = 0; i<nodes.size(); i++) {
			Node nodeCounter = nodes.get(i);
			if(nodeCounter.contains(start)) {
				startNode = nodeCounter;
				break;				
			}
		}
		
		//System.out.println(startNode.getPosition());
		this.goal = goal;
		
		refreshCosts();
		
		allNodes = new ArrayList<Integer>();
		
		initNodeValues();
		//setNodeValues(startNode);
		setBetterNodeValues(startNode);
		
		Node goalNode = null;
		for (int i = 0; i<nodes.size(); i++) {
			Node nodeCounter = nodes.get(i);
			if(nodeCounter.contains(goal)) {
				goalNode = nodeCounter;
				break;				
			}
		}
		
		if(goalNode == null) {
			throw new NullPointerException("goalNode is still ==null");
		}
		
		while(goalNode.getValue() != 0) {
			shortestWayReverse.add(goalNode);
			
			goalNode = goalNode.getNeighbourWithNextValue(startNode);
		}
		
		//adding the last node, our start
		shortestWayReverse.add(goalNode);
		return shortestWayReverse;
	}
}

