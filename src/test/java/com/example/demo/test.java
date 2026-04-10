package com.example.demo;

import java.util.ArrayList;
import java.util.Random;

public class test {

	public static void main(String[] args) {
		
		
		String account ="";
		for(int i=0; i < 11; i++) {
			Random rand = new Random();
			int a=  rand.nextInt(10);
			account += a;
		}
		System.out.println(account);
		
	}
}
