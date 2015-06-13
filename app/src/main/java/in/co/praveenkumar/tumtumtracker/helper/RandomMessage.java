package in.co.praveenkumar.tumtumtracker.helper;

import java.util.Random;

public class RandomMessage {
	static final String[] Messages = { "Fueling tumtums..",
			"Resolving driver strikes..", "Getting externel support..",
			"Checking engines..", "Filling gas..", "Assigning routes..",
			"Hiring externel services..", "Powering engines..",
			"Mapping routes" };

	public static String getLoadText() {
		return Messages[new Random().nextInt(Messages.length)];
	}
}
