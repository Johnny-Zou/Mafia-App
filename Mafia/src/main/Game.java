package main;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import players.Player;

public class Game extends Canvas implements Runnable{
//http://www.iconarchive.com/show/multipurpose-alphabet-icons-by-hydrattz.2.html
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2313996282976670619L;
	public static int width;
	public static int height;
	private boolean running;
	private Thread thread;
	private Handler handler;
	private int numPlayers;
	
	private boolean day = true;
	
	public Game() {
		
		handler = new Handler();
		new MouseInput(handler);
		
		//creates all of the players
		createPlayers();
		
		System.out.println();
		
		//prints all of the players
		for(Player player: handler.players){
			System.out.println(player);
		}
		height = 48 + numPlayers * 115;
		width = 115 + numPlayers * 100;
				
		//Start Window
		new Window(this, width, height, "Window...");
	}
	
	public void day(){
		
	}
	
	public void night(){
		
	}
	
	public void createPlayers(){
		Scanner input = new Scanner(System.in);
		ArrayList<Role> roles = new ArrayList<Role>();
		
		boolean validStart = false;
		do{
			
			System.out.print("How many Villagers: ");
			//int numVillagers = inputInt(2, -1);
			int numVillagers = 5;
			
			System.out.print("How many Werewolves: ");
			//int numWerewolves = inputInt(1, (int)(numVillagers / 2));
			int numWerewolves = 1;
			
			System.out.print("How many Guardian Angels: ");
			//int numGuardianAngels = inputInt(0, 1);
			int numGuardianAngels = 1;
			numPlayers = numVillagers + numWerewolves + numGuardianAngels;
			
			//if (numPlayers < 6){
			if(numPlayers < 0){
				System.out.println("There must be at least 6 people to play. Please try again.");
			}
			else{
				System.out.println("You've selected:");
				System.out.println();
				System.out.println(numVillagers + " Villagers ");
				System.out.println(numWerewolves + " Werewolves");
				System.out.println(numGuardianAngels + " Guardian Angels");
				System.out.println();
				System.out.println("A total of " + numPlayers + " players.");
				
				boolean valid = false;
				do{
					System.out.print("Would you like to confirm this selection?(Yes/No): ");
					//String str = input.next();
					String str = "Yes";
					valid = true;
					
					if (str.equals("Yes")){
						validStart = true;
						for (int x = 0; x < numVillagers; x++)
							roles.add(Role.Villager);
						for (int x = 0; x < numWerewolves; x++)
							roles.add(Role.Werewolf);
						for (int x = 0; x < numGuardianAngels; x++)
							roles.add(Role.GuardianAngel);
					}
					else if(str.equals("No")){
						System.out.println("Please reselect your players.");
					}
					else{
						valid = false;
						System.out.print("Invalid input. ");
					}
				}while(!valid);
			}
			
		} while (!validStart);
		
		for(int x = numPlayers; x > 0; x--){
			System.out.print("Player " + (numPlayers - x + 1) + ". Please enter your first and last name: ");
			
			String firstName = input.next();
			String lastName = input.next();
			
			Random rand = new Random();
			int num = rand.nextInt(roles.size());
			Role role = roles.get(num);
			roles.remove(num);
			handler.add(firstName, lastName, role);	
		}
		
		for(Player player: handler.players){
			ArrayList<Button> buttons = new ArrayList<>(numPlayers);
			
			for(int x = 0; x < numPlayers; x++){
				Button button = new Button(handler.players.get(x).toString());
				buttons.add(button);
			}
			player.setButtons(buttons);
		}
		
	}
	
	public static int inputInt(int min, int max){
		
		boolean maximum = true;
		if(max < min){
			maximum = false;
		}
		
		Scanner input = new Scanner(System.in);
		int num = 0;
		boolean valid = false;
		while(!valid){
			valid = true;
			try {
				num = input.nextInt();
			
				//min
				if(num < min){
					System.out.print("Please enter a number greater than " + min + ": ");
					valid = false;
				}
				
				//max
				if(maximum && num > max){
					System.out.print("Please enter a number less than " + max + ": ");
					valid = false;
				}
				
			} catch (InputMismatchException e) {
				valid = false;
				input.nextLine();
				System.out.print("Please enter an Integer:");
			}
			
		}
		return num;
	}

	// Starts the thread
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	@Override
	public void run() {
		this.hasFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				delta--;
			}
			if(running){
				render();
			}
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				//System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
	}

	private void render(){
		
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null){
			//3 is recommended.
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		//background
		g.setColor(Color.cyan);
		g.fillRect(0, 0, width, height);
		
		//players
		handler.render(g);
		
		
		//g.setColor(c);
		//g.drawRect(x, y, width, height);
		/*
		for (int y = 0; y < numPlayers; y++){
			Player player = handler.players.get(y);
			for (int x = 0; x < numPlayers; x++){
				Button button = player.getButtons().get(x);
				gbc.gridx = x;
				gbc.gridy = y;
				panel.add(button,gbc);
			}
		}*/
		
		g.dispose();
		bs.show();
	}
	
	private void tick() {
		handler.tick();
		
		if(day) day = handler.day();
		else 	day = handler.night();
		
	}
	
}
