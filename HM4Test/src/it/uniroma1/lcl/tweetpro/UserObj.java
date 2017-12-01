package it.uniroma1.lcl.tweetpro;

import java.util.Arrays;
import java.util.List;


/**
 * This class is an implementation of the User interface, it contains all the methods to obtain the users infos.
 * 
 * @see User
 */
public class UserObj implements User {

	private List<String> userInfo;
	private long id;
	private String name;
	private String screenName;
	private int tweetsCount;
	private int favsCount;
	private int followersCount;
	private int friendsCount;
	private boolean verified;

	/**
	 * Class constructor
	 * 
	 * @param userInfo The string containins all the user's info
	 */
	public UserObj(String userInfo) throws StringIndexOutOfBoundsException {
		try {
			this.userInfo = Arrays.asList(userInfo.replace(",\"", "%sep%\"").split("%sep%"));
		this.name = this.userInfo.stream().filter(x -> x.contains("\"name\":")).findFirst()
				.map(x -> x.substring(x.indexOf(":\"") + 2, x.length() - 1)).get();
		this.screenName = this.userInfo.stream().filter(x -> x.contains("\"screen_name\":")).findFirst()
				.map(x -> x.substring(x.indexOf(":\"") + 2, x.length() - 1)).get();
		this.tweetsCount = this.userInfo.stream().filter(x -> x.contains("\"statuses_count\":")).findFirst()
				.map(x -> x.substring(x.indexOf(":") + 1, x.length())).map(Integer::parseInt).get();
		this.favsCount = this.userInfo.stream().filter(x -> x.contains("\"favourites_count\":")).findFirst()
				.map(x -> x.substring(x.indexOf(":") + 1, x.length())).map(Integer::parseInt).get();
		this.followersCount = this.userInfo.stream().filter(x -> x.contains("\"followers_count\":")).findFirst()
				.map(x -> x.substring(x.indexOf(":") + 1, x.length())).map(Integer::parseInt).get();
		this.friendsCount = this.userInfo.stream().filter(x -> x.contains("\"friends_count\":")).findFirst()
				.map(x -> x.substring(x.indexOf(":") + 1, x.length())).map(Integer::parseInt).get();
		this.id = this.userInfo.stream().filter(x -> x.contains("\"id_str\":")).findFirst()
                .map(x -> x.substring(x.indexOf(":\"") + 2, x.length() - 1)).map(Long::parseLong).get();
		this.verified = this.userInfo.stream().filter(x -> x.contains("\"verified\":")).findFirst()
				.map(x -> x.substring(x.indexOf("\":") + 2, x.length())).get().equals("true");
			}catch (StringIndexOutOfBoundsException s) {
				s.printStackTrace();
			}
		}

	public String getName() {
		return name;
	}

	public String getScreenName() {
		return screenName;
	}

	public int getTweetsCount() {
		return tweetsCount;
	}

	public int getFavsCount() {
		return favsCount;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public int getFriendsCount() {
		return friendsCount;
	}

	public long getId() {
		return id;
	}

	public boolean isVerified() {
		return verified;
	}

}
