package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import players.Player;

public class Handler {
	
	ArrayList<Player> players = new ArrayList<Player>();
	
	public void tick(){
		for (Player player : players){
			player.tick();
		}
	}
	
	public boolean day(){
		boolean day = false;
		for (Player player : players){
			//day move
			player.day();

			//everyone has to make a move for night
			player.setNight(false);
			
			//someone hasn't made a move for day
			if (player.isDay() == false){
				day = true;
			}
		}
		
		//everyone moved
		if(!day){
			
		}
		return day;
	}
	
	public boolean night(){
		boolean night = false;
		for (Player player : players){
			
			//night move
			player.night();
			
			//everyone has to make a move for day
			player.setDay(false);
				
			//someone hasn't made a move for night
			if (player.isNight() == false){
				night = true;
			}
		}
		
		//everyone moved during night
		if(!night){
			for (Player player : players){
				if (player.getTargeted()){
					player.kill();
				}
				if (player.getProtected()){
					player.revive();
				}
			}
		}
		
		return !night;
	}
	
	public void render(Graphics g){
		
		for (Player player : players){		
			g.setColor(Color.RED);
			g.setFont(new Font("Verdana", Font.BOLD, 12));
			g.drawString(player.toString(), 128, 115*players.indexOf(player) + 64);
			
			//images
			String str = player.render(g);
			try{
				Image img = ImageIO.read(getClass().getResourceAsStream("/images/"+ str +".png"));
				g.drawImage(img, 0, 115*players.indexOf(player), null);
			}catch(IOException e){
				e.printStackTrace();
				System.out.println("something went wrong");
			}
		}
	}

	public void add(String firstName, String lastName, Role role) {
		Player player = Player.create(firstName, lastName, role);
		players.add(player);
	}

	
	
	
}
