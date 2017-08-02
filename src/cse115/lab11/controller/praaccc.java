package cse115.lab11.controller;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class praaccc {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Integer> a = new ArrayList<>();
		a.add(1);
		a.add(2);
		a.add(3);

		ArrayList<Integer> b = new ArrayList<>(a.subList(0, 3));

		a.set(0, 8);

		System.out.println(a.toString());
		System.out.println(b.toString());
	}

}
