package it.uniroma1.lcl.tweetpro;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Interface Used to Build Corpus Objects.
 *
 * @see Corpus
 * @see TweetCorpus#parseFile(File)
 */
public interface TweetCorpus extends Iterable<Tweet> {
	
	/** 
	 * This method takes a javascript File to list its lines as Tweets.
	 * 
	 * @param file Javascript file containing Standard format of Tweets.
	 * @return TweetCorpus The metod returns a new TweetCorpus object.
	 * @see Corpus(List<Tweets>)
	 */
	static TweetCorpus parseFile(File file) {
		List<Tweet> corpus = new ArrayList<>();
		try{
			corpus = Files.lines(Paths.get(file.getPath())).filter(str -> !str.equals(""))
					.map(TweetObj::new).collect(Collectors.toList());
		}catch(IOException e){
			e.printStackTrace();
		}
		return new Corpus(corpus);
	}
	
	/**
	 * This methods returns the number of Tweets in the TweetCorpus.
	 * 
	 * @return	The number of Tweets in the TweetCorpus (the total size of the corpus List).
	 * @see	Corpus
	 */
	int getTweetCount();
	
	/**
	 * This methods takes a user object and returns all the user tweets in the TweetCorpus.
	 * 
	 * @param user User object of which we want to know the Tweets 
	 * @return	The user Tweets in the TweetCorpus
	 * @see	Corpus
	 */
	List<Tweet> getTweets(User user);
	
	/**
	 * This methods takes an int k and returns the k most used hashtags in the corpus.
	 * 
	 * @param k	Total number of most used Tweets we want to know
	 * @return	A List<String> of most used hashtags
	 * @see	Corpus#topHashtagsStrategy
	 * @see	Corpus#setTopHashtagsStrategy(TopHashtags)
	 * @see TopHashtags
	 */
	List<String> getTopHashtags(int k);
	
	/**
	 * This method returns the unique Users count in the corpus.
	 * 
	 * @return	An int rapresenting the unique users count
	 * @see	Corpus#UniqueUsersCountStrategy
	 * @see	Corpus#setUniqueUsersCountStrategy
	 * @see UniqueUsersCount
	 */
	int getUniqueUsersCount();

}
