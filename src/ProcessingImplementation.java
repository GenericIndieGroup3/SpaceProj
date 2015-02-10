import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;


public class ProcessingImplementation extends PApplet {
	//Yes, yes, we are using an openGL implementation for the final product
	//and processing is horrible. However, it'll take me a while to learn
	//how to use lwjgl, and I want to instantly see if my code works
	
	public void setup(){
		size(1500, 1200);
		boolean check = Vector2.runUnitTests();
		if(check)
			background(255, 0, 0);
		else
			background(0, 255, 0);
		
		size(1500, 1200);
		background(0);
		
		ships.add(new PhysicsObject(new Vector2(200, 500), new Vector2(0, 5), 20, 10));
		ships.add(new PhysicsObject(new Vector2(500, 500), new Vector2(0, 0), 100, 100));
		ships.add(new PhysicsObject(new Vector2(1000, 500), new Vector2(0, -3), 20, 10));
		ships.add(new PhysicsObject(new Vector2(500, 1000), new Vector2(-4, 0), 20, 10));
		
	}
	
	public List<PhysicsObject> ships = new ArrayList<PhysicsObject>();
	
	public PhysicsObject ship(){
		return ships.get(0);
	}

	public void keyPressed(){
		/*
		if(key == 'w'){
			ship(). += 5f;
		}
		if(key == 's'){
			if(ship().gravitationMass > 5f)
				ship().gravitationMass -= 5f;
		}
		if(key == 'j'){
			ship().addForce(new Vect2(-3, 0));
		}
		if(key == 'l'){
			ship().addForce(new Vect2(3, 0));
		}
		if(key == 'i'){
			ship().addForce(new Vect2(0, -3));
		}
		if(key == 'k'){
			ship().addForce(new Vect2(0, 3));
		}
		*/
	}

	public void draw() {
		background(0);
		stroke(255);
		for(PhysicsObject ship : ships){
			for(PhysicsObject planet: ships){
				//This won't work
				if (planet != ship){
					Vector2 distance = (planet.getPosition().subtract(ship.getPosition()));
					double radius = distance.magnitude();
					//ship.position = ship.position.subtract(ships.get(0).position).add(new Vector2(400, 400));
					Vector2 direction = distance.normalize();
					double grav = 300 * planet.getGravitationalMass() * ship.getGravitationalMass() / (radius * radius * ship.getInertialMass());
					Vector2 finalGrav = (direction.multiply(grav));
					ship.addForce(finalGrav);
				}
			}
			ship.update();
		}
		for(PhysicsObject ship : ships){
			fill(255, 255, 255);
			ellipse((float)ship.getPosition().x, (float)ship.getPosition().y, (float)ship.getGravitationalMass(), (float)ship.getGravitationalMass());
		}
	}
}
