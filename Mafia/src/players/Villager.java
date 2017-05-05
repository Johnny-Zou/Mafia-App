package players;

import main.Role;

public class Villager extends Player {

	public Villager(String firstName, String lastName) {
		super(firstName, lastName, Role.Villager);
	}

	public void day() {
		// no actions
	}
	
	public void night() {
		// no actions
	}
}
