package in.co.praveenkumar.tumtumtracker.helper;

import java.util.Calendar;

public class TimeFormat {
	/**
	 * Evaluates relative time for given time stamp. The outputs could looks
	 * like,<br/>
	 * <b> For past </b><br/>
	 * 2 years ago <br/>
	 * 3 months ago <br/>
	 * 5 minutes ago <br/>
	 * A moment ago <br/>
	 * <br/>
	 * <b> For furture </b><br/>
	 * In 2 years<br/>
	 * In 3 months<br/>
	 * In 5 minutes<br/>
	 * In a moment<br/>
	 * <br/>
	 * 
	 * <b>Note:</b> Plural and singular of the numbers are also considered. Like
	 * 1 minute ago and 2 minutes ago<br/>
	 * 
	 * @param time
	 *            in seconds
	 * @return neat relative time string
	 */
	public static String getNiceRelativeTime(int time) {
		long ltime = ((long) time) * 1000;
		Calendar c = Calendar.getInstance();
		int nowSec = c.get(Calendar.SECOND);
		int nowMin = c.get(Calendar.MINUTE);
		int nowHour = c.get(Calendar.HOUR_OF_DAY);
		int nowDay = c.get(Calendar.DATE);
		int nowMonth = c.get(Calendar.MONTH);
		int nowYear = c.get(Calendar.YEAR);

		c.setTimeInMillis(ltime);
		int givenSec = c.get(Calendar.SECOND);
		int givenMin = c.get(Calendar.MINUTE);
		int givenHour = c.get(Calendar.HOUR_OF_DAY);
		int givenDay = c.get(Calendar.DATE);
		int givenMonth = c.get(Calendar.MONTH);
		int givenYear = c.get(Calendar.YEAR);

		// Past
		if (time * 1000 < System.currentTimeMillis()) {
			if (nowYear - givenYear > 1)
				return (nowYear - givenYear) + " years ago";
			else if (nowYear - givenYear == 1)
				return "1 year ago";

			else if (nowMonth - givenMonth > 1)
				return (nowMonth - givenMonth) + " months ago";
			else if (nowMonth - givenMonth == 1)
				return "1 month ago";

			else if (nowDay - givenDay > 1)
				return (nowDay - givenDay) + " days ago";
			else if (nowDay - givenDay == 1)
				return "Yesterday";

			else if (nowHour - givenHour > 1)
				return (nowHour - givenHour) + " hours ago";
			else if (nowHour - givenHour == 1)
				return "1 hour ago";

			else if (nowMin - givenMin > 1)
				return (nowMin - givenMin) + " minutes ago";
			else if (nowMin - givenMin == 1)
				return "1 minute ago";

			else if (nowSec - givenSec > 10)
				return (nowSec - givenSec) + " seconds ago";

			else
				return "A moment ago";
		}

		// Future
		else {
			if (givenYear - nowYear > 1)
				return "In " + (givenYear - nowYear) + " years";
			else if (givenYear - nowYear == 1)
				return "In 1 year";

			else if (givenMonth - nowMonth > 1)
				return "In " + (givenMonth - nowMonth) + " months";
			else if (givenMonth - nowMonth == 1)
				return "In 1 month";

			else if (givenDay - nowDay > 1)
				return "In " + (givenDay - nowDay) + " days";
			else if (givenDay - nowDay == 1)
				return "Tomorrow";

			else if (givenHour - nowHour > 1)
				return "In " + (givenHour - nowHour) + " hours";
			else if (givenHour - nowHour == 1)
				return "In 1 hour";

			else if (givenMin - nowMin > 1)
				return "In " + (givenMin - nowMin) + " minutes";
			else if (givenMin - nowMin == 1)
				return "In 1 minute";

			else if (givenSec - nowSec > 10)
				return "In " + (givenSec - nowSec) + " seconds";

			else
				return "In a moment";
		}

	}

}
