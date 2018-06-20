package s0555938;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import lenz.htw.ai4g.ai.AI;
import lenz.htw.ai4g.ai.DriverAction;
import lenz.htw.ai4g.ai.Info;

import s0555938.Dijkstra;
import s0555938.Node;

//import s0555938.DijkstraTest;

public class HuliandosAI extends AI {

	Point start;
	Point goal;
	Point lastGoal;
	
	Point checkPoint;
	
	float distance;
	float accel;
	float turnAngle;
	
	float difference;	
	float differenceRatio;
	float pitch;
	
	float angTol = (float) 0.36; //default 0.4
	
	boolean[][] walkable = new boolean[info.getTrack().getWidth()/10][info.getTrack().getHeight()/10];
	ArrayList<Node> shortestWayReverse = new ArrayList<Node>();
	
	Dijkstra dijkstra;
	
	public HuliandosAI(Info info) {
		super(info);
		
		enlistForTournament(555938, 555950);
		// TODO Auto-generated constructor stub
		
		defineMap();
		
		start = new Point((int)info.getX(), (int)info.getY());
		goal = info.getCurrentCheckpoint();
		lastGoal = start;
		
		dijkstra = new Dijkstra(walkable, info.getTrack().getWidth()/10, info.getTrack().getHeight()/10, start, goal);
	}


	@Override
	public String getName() {
		return "Huliandos !Baum";
	}

	@Override
	public DriverAction update(boolean arg0) {
		// Gas: 1 vorwärts, -1 rückwärts, Lenken: 1 Links, -1 Rechts
		accel = (float)0.75;
		turnAngle = 0;
		
		goal = info.getCurrentCheckpoint();

		//so it just creates the path after a new goal is chosen
		if (goal.x != lastGoal.x && goal.y != lastGoal.y){
			
						
			doDebugStuff();
			//new start is last Checkpoint
			start = new Point(lastGoal);
			
			//last goal is the new goal
			lastGoal = new Point(goal);
			
			//INITALISING DIJKSTRA AND START FIRST PATH CALCULATION
			shortestWayReverse = new ArrayList<Node>();
			shortestWayReverse = dijkstra.getShortestPath(goal, start);
			
		}
		
		//in case the car explodes
		else if(arg0 == true){
			
			doDebugStuff();
			
			shortestWayReverse = new ArrayList<Node>();
			shortestWayReverse = dijkstra.getShortestPath(goal, start);
			
		}
		
		if(shortestWayReverse.size() == 1 || shortestWayReverse.size() == 0){
			checkPoint = goal;
		}
		else{
			if(shortestWayReverse.get(shortestWayReverse.size()-1).containsWithTolerance(new Point((int)info.getX(), (int)info.getY())) && shortestWayReverse.size() >= 1){
				shortestWayReverse.remove(shortestWayReverse.size()-1);
				doDebugStuff();
			}
			checkPoint = new Point (shortestWayReverse.get(shortestWayReverse.size()-1).getPosition().x + 5, shortestWayReverse.get(shortestWayReverse.size()-1).getPosition().y + 5);
		}
		
		//pitch calculation
		pitch = (float) (Math.atan2(checkPoint.y - info.getY(), checkPoint.x - info.getX()));
		
		difference = pitch - info.getOrientation();
		
		if(difference > Math.PI) {
			difference = (float) (2*Math.PI - difference)*-1;
		}
		if(difference < -Math.PI) {
			difference = (float) (2*Math.PI + difference);
		}
		
		if(difference != 0 && difference > 0){
			differenceRatio = (float)(difference / Math.PI) * 2;
		}
		else if(difference != 0 && difference < 0){
			differenceRatio = -1 * (float)(difference / Math.PI) * 2;
		}
		else{
			differenceRatio = 0;
		}
		
		if(differenceRatio>1){
			differenceRatio = 1;
		}
		
		
		if(difference >= angTol || difference <= -angTol){
			turnAngle = (float) (difference /* * Math.sqrt(pitch / Math.pow(info.getOrientation(), 2))*/);
			//turnAngle = difference;
		}
		else  {			
			turnAngle = 0 - info.getAngularVelocity();					
		}
		
		//turnAngle *= differenceRatio;
		/*
		if(pitch > info.getOrientation()){
			turnAngle = difference * differenceRatio;
		}
		else if(pitch < info.getOrientation()){
			turnAngle = difference * differenceRatio;
		}
		else{
			turnAngle = 0;
		}
		*/
		
		
		if (turnAngle >= info.getMaxAngularAcceleration()) {turnAngle = info.getMaxAngularAcceleration();}
		if (turnAngle <= -info.getMaxAngularAcceleration()) {turnAngle = -info.getMaxAngularAcceleration();}
	
		//turnAngle *= differenceRatio;
		
		//acceleration calculation
		float wantSpeed = info.getMaxVelocity() * (1-differenceRatio);
		float currentVelocity = (float) Math.sqrt(Math.pow(info.getVelocity().x, 2) + Math.pow(info.getVelocity().y, 2));
		
		if(currentVelocity <= wantSpeed){
			//accel = info.getMaxAcceleration();
			accel++;
		}
		else/* if(currentVelocity > wantSpeed)*/{
			//accel = -info.getMaxAcceleration();
			accel--;
		}
		/*else{
			accel = 0;
		}*/
		
		//System.out.println(accel);
		
		return new DriverAction(accel, turnAngle);
	}

	
	public String getTextureResourceName() {
		//path zur texture
		return ("/s0555938/carHuliandos.png");
	}
	
	
	//all das Debug Zeug hier aufrufen
	public void doDebugStuff() {
		/*
		// Markierung durch AI Klasse
		GL11.glBegin(GL11.GL_QUADS);
		for (int x=0; x < info.getTrack().getWidth(); x+=10) {
			for (int y=0; y < info.getTrack().getHeight(); y+=10) {
				if(walkable[x/10][y/10] == true) {
					//System.out.println("exited");
					GL11.glColor3f(0, 0, 1);
					GL11.glVertex2f(x+3, y+3);
					GL11.glVertex2f(x+7, y+3);
					GL11.glVertex2f(x+7, y+7);
					GL11.glVertex2f(x+3, y+7);
				}
				else {
					GL11.glColor3f(1, 0, 0);
					GL11.glVertex2f(x+3, y+3);
					GL11.glVertex2f(x+7, y+3);
					GL11.glVertex2f(x+7, y+7);
					GL11.glVertex2f(x+3, y+7);
				}
			}
		}
		GL11.glEnd();
		*/
		
		//drawing the way to our goal
		GL11.glBegin(GL11.GL_QUADS);
		Node node;
		for (int way=0; way < shortestWayReverse.size(); way++){
			node = shortestWayReverse.get(way);
			GL11.glColor3f(0, 1, 0);
			GL11.glVertex2f(node.getPosition().x, node.getPosition().y);
			GL11.glVertex2f(node.getPosition().x + 10, node.getPosition().y);
			GL11.glVertex2f(node.getPosition().x + 10, node.getPosition().y + 10);
			GL11.glVertex2f(node.getPosition().x, node.getPosition().y + 10);
		}
		GL11.glEnd();
		//Test-Markierung durch Dijkstra
		GL11.glBegin(GL11.GL_QUADS);
		for (int x=0; x < info.getTrack().getWidth(); x+=10) {
			for (int y=0; y < info.getTrack().getHeight(); y+=10) {			
				if (dijkstra.nodes.get(x*walkable.length/10 + y/10).getCost() == 0) {
					GL11.glColor3f(0, 1, 0);
				}
				else if (dijkstra.nodes.get(x*walkable.length/10 + y/10).getCost() == 1) {
					GL11.glColor3f(0, 0, 1);
				}
				else if (dijkstra.nodes.get(x*walkable.length/10 + y/10).getCost() == -1) {
					GL11.glColor3f(1, 0, 0);
				}	
				GL11.glVertex2f(x+3, y+3);
				GL11.glVertex2f(x+7, y+3);
				GL11.glVertex2f(x+7, y+7);
				GL11.glVertex2f(x+3, y+7);
			}
		}
		GL11.glEnd();
		
		/*
		//weiterer Test von Dijkstra
		GL11.glBegin(GL11.GL_QUADS);
		for (int x=0; x < info.getTrack().getWidth(); x+=10) {
			for (int y=0; y < info.getTrack().getHeight(); y+=10) {			
				float colorModifier;
				
				colorModifier = dijkstra.nodes.get(x*walkable.length/10 + y/10).getValue();
				if(colorModifier == 0){
					GL11.glColor3f(0, 1, 0);
				}
				else if(colorModifier < 0){
					GL11.glColor3f(0, 0, 1);
				}
				else{
					GL11.glColor3f(0, 0, colorModifier);
				}
				
				GL11.glVertex2f(x+3, y+3);
				GL11.glVertex2f(x+7, y+3);
				GL11.glVertex2f(x+7, y+7);
				GL11.glVertex2f(x+3, y+7);
			}
		}
		GL11.glEnd();
		*/
	}
	
	private void defineMap() {
		for (int x=0; x < info.getTrack().getWidth(); x+=10) {
			for (int y=0; y < info.getTrack().getHeight(); y+=10) {
				
				//10x10 Rectangle
				//Rectangle rect = new Rectangle(x, y, 10, 10);
				//20x20 Rectangle
				//Rectangle rect = new Rectangle(x-5, y-5, 20, 20);
				//30x30 Rectangle
				Rectangle rect = new Rectangle(x-10, y-10, 30, 30);
				//20x20 Rectangle
				//Rectangle rect = new Rectangle(x-15, y-15, 40, 40);
				
				
				for(int i=0; i<info.getTrack().getObstacles().length; i++) {
					if(info.getTrack().getObstacles()[i].intersects(rect)) {
						walkable[x/10][y/10] = false;
						break;
					}
					else {
						walkable[x/10][y/10] = true;
					}
				}
				
			}
		}
	}

}
