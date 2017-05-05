package players;

import java.util.Scanner;

import main.Role;

public class GuardianAngel extends Player {

	public GuardianAngel(String firstName, String lastName) {
		super(firstName, lastName, Role.GuardianAngel);
	}

	public void day() {
		// no actions
	}
	
	public void night() {
		// saves someone
		boolean selected = false;
		do {
			Scanner input = new Scanner(System.in);
			String firstName = input.next();
			String lastName = input.next();

			Player player = identify(firstName, lastName);

			if (player != null && player.isAlive()) {
				selected = true;
				player.setProtected(true);
				setNight(true);
			}

		} while (!selected);
	}
}
