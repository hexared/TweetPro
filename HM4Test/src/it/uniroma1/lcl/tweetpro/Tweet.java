package it.uniroma1.lcl.tweetpro;

import java.net.URL;
import java.util.List;
import java.util.Optional;

/**
 * Interface implemented by TweetObj, it contains methods used to get Tweets elements.
 * @see TweetObj
 */
public interface Tweet {

	/**
	 * This method returns the Tweet's id as long number
	 * 
	 * @return long Tweet's id.
	 */
	long getID();

	/**
	 * This methods returns the User that posted the Tweet
	 * 
	 * @return User object that posted the Tweet
	 */
	User getUser();

	/**
	 * This methods returns the text of the tweet
	 * 
	 * @return String with the Tweet's text
	 */
	String getText();

	/**
	 * This method returns a list of hashtags used in the Tweet (without '#')
	 * 
	 * @return List<String> hastags used in the Tweet
	 */
	List<String> getHashtags();

	/**
	 * This method returns the amount of likes the Tweet get.
	 * 
	 * @return int number of likes given to the Tweet
	 */
	int getLikeCount();

	/**
	 * This method returns the amount of timeeeeees this Tweet has benn retweeted
	 * 
	 * @return int times this Tweet has been retweeted
	 */
	int getRTCount();

	/**
	 * This method returns true if this Tweet is a retweet
	 * 
	 * @return boolean true if this Tweet is a retweet, false if it's not.
	 */
	boolean isRetweet();

	/**
	 * If this Tweet is a retweet, this method returns a Tweet object that rapresents the original Tweet.
	 * If not, this method will prompt an erro saying that this isn't a retweet.
	 * 
	 * @return Tweet original tweet if this is a retweet or an erro message
	 */
	Tweet getOriginalTweet();

	/**
	 * This method return the URL of the Tweet's media if present or will prompt an error message.
	 * 
	 * @return Optional<URL> URL of the Tweet's media if present.
	 */
	Optional<URL> getMedia();

}
