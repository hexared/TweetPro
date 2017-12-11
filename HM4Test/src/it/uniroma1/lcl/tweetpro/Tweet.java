package it.uniroma1.lcl.tweetpro;

import java.net.URL;
import java.util.List;
import java.util.Optional;


public interface Tweet {

	
	long getID();

	
	User getUser();

	
	String getText();


	List<String> getHashtags();

	
	int getLikeCount();

	
	int getRTCount();


	boolean isRetweet();

	
	Tweet getOriginalTweet();


	Optional<URL> getMedia();

}
