package it.uniroma1.lcl.tweetpro;

import java.util.List;

/**
 * This is an interface that needs to be implemented by all the TopHashtags strategy algorithms.
 * 
 * @see CountMin
 * @see Corpus#setTopHashtagsStrategy(TopHashtags)
 * @see Corpus#getTopHashtags(int)
 */
public interface TopHashtags{

    /**
     * This method is an implementation of a frequency calculation strategy.
     * It takes a List of hashtags and an int k and returns the k most used hashtags.
     * 
     * @param hastagsList List of all the hashtags in a TweetCorpus
     * @param k The number of most used hashtags that we want to know.
     * @return List<String> List of the k most used hashtags in the corpus.
     * @see Corpus
     * @see TweetCorpus
     * @see CountMin
     */
    List<String> calculate(List<String> hastagsList, int k);
}