package players;

import java.util.Scanner;

import main.Handler;
import main.Role;

public class Werewolf extends Player {

	public Werewolf(String firstName, String lastName) {
		super(firstName, lastName, Role.Werewolf);
	}

	public void day() {
		// no actions
	}
	
	public void night() {
		// kill someone
		boolean selected = false;
		do {
			Scanner input = new Scanner(System.in);
			String firstName = input.next();
			String lastName = input.next();

			Player player = identify(firstName, lastName);

			if (player != null && player.isAlive()) {
				selected = true;
				player.setTargeted(true);
				setNight(true);
			}

		} while (!selected);
	}
}
