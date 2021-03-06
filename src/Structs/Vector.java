package Structs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vector {

	List<Double> values;
	
	public Vector(double[] values){
		
		this.values = new ArrayList<Double>(values.length);
		for (double val: values){
			this.values.add(val);
		}
		
	}
	
	public double get(int i){
		return values.get(i);
	}
	
	public void set(int i, double value){
		values.set(i, value);
	}
}
