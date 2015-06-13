package in.co.praveenkumar.tumtumtracker.helper;

public class TimeFormat {
	static final int SECOND = 1;
	static final int MINUTE = 60 * SECOND;
	static final int HOUR = 60 * MINUTE;
	static final int DAY = 24 * HOUR;
	static final int WEEK = 7 * DAY;
	static final int MONTH = 4 * WEEK;
	static final int YEAR = 12 * MONTH;

	/**
	 * Evaluates relative time for given time stamp. The outputs could looks
	 * like,<br/>
	 * 2 years ago <br/>
	 * 3 months ago <br/>
	 * 5 minutes ago <br/>
	 * A moment ago <br/>
	 * <br/>
	 * 
	 * <b>Note:</b> Plural and singular of the numbers are also considered. Like
	 * 1 minute ago and 2 minutes ago<br/>
	 * 
	 * @param time
	 *            in seconds
	 * @return neat relative time string
	 */
	public static String RelativeTime(int time) {
		int diff = (int) (System.currentTimeMillis() / 1000) - time;

		if (diff < 10 * SECOND)
			return "A moment ago";

		if (diff < 20 * SECOND)
			return "Few seconds ago";

		if (diff < 45 * SECOND)
			return "30 seconds ago";

		if (diff < 90 * SECOND)
			return "A minute ago";

		if (diff < 60 * MINUTE)
			return (int) Math.ceil(diff / MINUTE) + " minutes ago";

		if (diff < 90 * MINUTE)
			return "An hour ago";

		if (diff < 23 * HOUR)
			return (int) Math.ceil(diff / HOUR) + " hours ago";

		if (diff < 30 * HOUR)
			return "Yesterday";

		if (diff < 28 * DAY)
			return (int) Math.ceil(diff / DAY) + " days ago";

		if (diff < 40 * DAY)
			return "A month ago";

		if (diff < 12 * MONTH)
			return (int) Math.ceil(diff / MONTH) + " months ago";

		if (diff < 16 * MONTH)
			return "An year ago";

		return (int) Math.ceil(diff / YEAR) + " years ago";

	}

}
