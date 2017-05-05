package players;

import java.awt.Button;
import java.awt.Graphics;
import java.util.ArrayList;

import main.Role;

public abstract class Player {

	private final String firstName;
	private final String lastName;
	private final Role role;
	
	private static ArrayList<Player> players = new ArrayList<>();
	
	private boolean alive = true;
	private boolean protect = false;
	private boolean target = false;
	private boolean day = false;
	private boolean night = false;
	private ArrayList<Button> buttons = new ArrayList<>();
	
	
	public Player(String firstName, String lastName, Role role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		players.add(this);
	}

	public abstract void day();
	public abstract void night();

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String toString() {
		//String fullName = firstName + " " + lastName + ", the " + role;
		String fullName = firstName + " " + lastName;
		return fullName;
	}

	public boolean isAlive() {
		return alive;
	}

	public void kill() {
		this.alive = false;
		setTargeted(false);
	}
	
	public void revive() {
		this.alive = true;
		setProtected(false);
	}
	
	protected void setTargeted(boolean target) {
		this.target = target;
	}

	public boolean getTargeted() {
		return target;
	}
	
	protected void setProtected(boolean protect) {
		this.protect = protect;
	}
	
	
	public boolean getProtected(){
		return protect;
	}

	protected Player identify(String firstName, String lastName) {
		Player selected = null;
		for (Player player : players) {
			if (player.getFirstName().equals(firstName) && player.getLastName().equals(lastName)) {
				selected = player;
			}
		}
		if (selected == null) {
			System.out.println("That person doesn't exist.");
		}
		return selected;
	}

	public static Player create(String firstName, String lastName, Role role) {

		Player player;
		switch (role) {
		case Villager:
			player = new Villager(firstName, lastName);
			break;
		case Werewolf:
			player = new Werewolf(firstName, lastName);
			break;
		case GuardianAngel:
			player = new GuardianAngel(firstName, lastName);
			break;
		default:
			player = null;
		}
		return player;
	}

	public String render(Graphics g){
		
		String str = "";
		str += role.toString().charAt(0);
		
		if(alive)	str += "A";
		else		str += "D";
		if(target)	str += "T";
		if(protect)	str += "P";

		return str;
	}
	
	public void tick(){
		
	}

	public boolean isNight() {
		return night;
	}

	public void setNight(boolean night) {
		this.night = night;
	}

	public boolean isDay() {
		return day;
	}

	public void setDay(boolean day) {
		this.day = day;
	}

	public ArrayList<Button> getButtons() {
		return buttons;
	}

	public void setButtons(ArrayList<Button> buttons) {
		this.buttons = buttons;
	}
	
}
