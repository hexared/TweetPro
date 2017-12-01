package it.uniroma1.lcl.tweetpro;

/**
 * Interface implemented by UserObj, it contains methods used to get User's Info.
 * 
 * @see UserObj
 */
public interface User {
	
	String getName();
	
	String getScreenName();
	
	int getTweetsCount();
	
	int getFavsCount();
	
	int getFollowersCount();
	
	int getFriendsCount();
	
	long getId();
	
	boolean isVerified();

}
