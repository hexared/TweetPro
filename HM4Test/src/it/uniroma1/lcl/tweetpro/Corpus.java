package it.uniroma1.lcl.tweetpro;

import java.io.File;
import java.util.Iterator;
import java.util.NoSuchElementException;
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
	* @param corpus Is a List of Tweets in a TweetCorpus.
	* @see TweetCorpus#parseFile(File) parseFile(Files)
	*/
	public Corpus(List<Tweet> corpus) {
		this.corpus = corpus;
		setTopHashtagsStrategy(new CountMin());
		setUniqueUsersCountStrategy(new LogLog());
	}

	public Iterator<Tweet> iterator() {
		return new Iterator<Tweet>() {
			private int k = 0;

			public boolean hasNext() {
				return this.k < corpus.size();
			}

			public Tweet next(){
				try{
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					}
				}catch(NoSuchElementException e){
					e.printStackTrace();
				}
				return corpus.get(k++);
			}

			public void remove() {
				corpus.remove(k);
			}
		};
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
	 * @see	Corpus#topHashtagsStrategy topHashtagsStrategy
	 * @see TopHashtags TopHashtags
	 * @see	Corpus#setTopHashtagsStrategy(int) setTopHashtagsStrategy(int)
	 * @see CountMin CountMin
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
	 * @see	Corpus#UniqueUsersCountStrategy UniqueUsersCountStrategy
	 * @see	Corpus#setUniqueUsersCountStrategy(UniqueUsersCount) setUniqueUsersCountStrategy(UniqueUsersCount)
	 * @see UniqueUsersCount UniqueUsersCount
	 * @see LogLog LogLog
	 */
	public void setUniqueUsersCountStrategy(UniqueUsersCount strategy){
		uniqueUsersCountStrategy = strategy;
	}

	/**
	 * This is an implementation of the method in the TweetCorpus interface.
	 * @see TweetCorpus#getUniqueUsersCount() getUniqueUsersCount
	 */
	// rimetti il return int
	public int getUniqueUsersCount(){
        return uniqueUsersCountStrategy.calculate(corpus.stream()
                                        .map(t -> t.getUser().getId())
                                        .collect(Collectors.toList()));}

	public static void main(String[] args){
		Tweet t = TweetCorpus.parseFile(new File(args[0])).iterator().next();
		// User u = t.getUser();
		// System.out.println(t.getID());
		// System.out.println(t.getLikeCount());
		// System.out.println(t.getRTCount());
		// System.out.println(t.getText());
		// System.out.println(t.isRetweet());
		// System.out.println(t.getMedia());
		// System.out.println("Hashtags {");
		// t.getHashtags().forEach(System.out::println);
		// System.out.println("}");
		// System.out.println(u.getName());
		// System.out.println(u.getScreenName());
		// System.out.println(u.getTweetsCount());
		// System.out.println(u.getFavsCount());
		// System.out.println(u.getFollowersCount());
		// System.out.println(u.getFriendsCount());
		// System.out.println(u.getId());
		// System.out.println(u.isVerified());
		// Tweet t2 = t.getOriginalTweet();
		// System.out.println("\n\n" + t2.getID());
		// System.out.println(t2.getLikeCount());
		// System.out.println(t2.getRTCount());
		// System.out.println(t2.getText());
		// System.out.println(t2.isRetweet());
		// Tweet t3 = t2.getOriginalTweet();
		// User u2 = t2.getUser();
		// System.out.println(u2.getName());
		// System.out.println(u2.getScreenName());
		// System.out.println(u2.getTweetsCount());
		// System.out.println(u2.getFavsCount());
		// System.out.println(u2.getFollowersCount());
		// System.out.println(u2.getFriendsCount());
		// System.out.println(u2.getId());
		// System.out.println(u2.isVerified());
		TweetCorpus c = new Corpus(corpus);
		// System.out.println();
		c.getTopHashtags(10).forEach(System.out::println);
		// c.getUniqueUsersCount();
		// c.forEach(o -> System.out.println(o.getUser().getId()));
	 }
}
