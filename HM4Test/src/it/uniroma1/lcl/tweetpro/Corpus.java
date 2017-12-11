package it.uniroma1.lcl.tweetpro;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is an implementation of TweetCorpus, 
 * it takes a List<Tweet> as input to be instantiated.
 * 
 * @param  corpus	Is a List of Tweets in a TweetCorpus
 * @param  topHashtagStrategy 	is an object that rapresent one of the TopHashtags algorithm 
 * 								implementation to find the most used hashtags in a corpus.
 * @param  UniqueUsersCountStrategy 	is an object that rapresent one of the UniqueUsersCount algorithm 
 * 										implementation to find the unique users id.
 * @see TweetCorpus TweetCorpus
 */
public class Corpus implements TweetCorpus {

	// TODO (1) rimuovi sto static per l'amor di dio.
	private static List<Tweet> corpus;
	private TopHashtags topHashtagsStrategy;
	private UniqueUsersCount uniqueUsersCountStrategy;

	/**
	* Class constructor.
	*
	* @param corpus Is the iterable list of tweets in the TweetCorpus.
	* @see TweetCorpus#parseFile(File) parseFile(Files)
	*/
	public Corpus(List<Tweet> corpus) {
		this.corpus = corpus;
		setTopHashtagsStrategy(new CountMin());
		setUniqueUsersCountStrategy(new LogLog());
	}

	public Iterator<Tweet> iterator() {
		return corpus.iterator();
	}

	/**
	 * This is an implementation of the method in the TweetCorpus interface.
	 * @see	TweetCorpus#getTweetCount() getTweetCount()
	 */
	public int getTweetCount() {
		return corpus.size();
	}

	/**
	 * This is an implementation of the method in the TweetCorpus interface.
	 * @see	TweetCorpus#getTweets(User) getTweets(User)
	 */
	public List<Tweet> getTweets(User user) {
		return corpus.stream().filter(x-> x.getUser().getId() == user.getId()).collect(Collectors.toList());
	}

	/**
	 * This methods takes a TopHashTags object and set it as a strategy to calculate the most used hashtags.
	 * 
	 * @param strategy TopHashtags object that implements an algorithm to calculate the frequencies of a certain word in a list.
	 */
	public void setTopHashtagsStrategy(TopHashtags strategy){
		topHashtagsStrategy = strategy;
	}

	/**
	 * This is an implementation of the method in the TweetCorpus interface.
	 * @see TweetCorpus#getTopHashtags(int) getTopHashtags(int)
	 */
    public List<String> getTopHashtags(int k){
		return topHashtagsStrategy.calculate(corpus.stream()
												.flatMap(t -> t.getHashtags().stream()
												.map(String::toLowerCase))
												.collect(Collectors.toList())
												, k);}

	/**
	 * This methods takes a UniqueUsersCount object and set it as a strategy to calculate the unique users id.
	 * 
	 * @param strategy 
	 */
	public void setUniqueUsersCountStrategy(UniqueUsersCount strategy){
		uniqueUsersCountStrategy = strategy;
	}

	/**
	 * This is an implementation of the method in the TweetCorpus interface.
	 */
	public int getUniqueUsersCount(){
        return uniqueUsersCountStrategy.calculate(corpus.stream()
                                        .map(t -> t.getUser().getId())
										.collect(Collectors.toList()));}
	public static void main(String[] args){
		
	}
}
