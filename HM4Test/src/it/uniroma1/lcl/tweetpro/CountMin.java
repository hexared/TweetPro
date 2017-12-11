package it.uniroma1.lcl.tweetpro;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents a TopHashtags strategy, it contains only an implementation of the CountMin_sketch algorithm.

     * <p>
     * This is a "basic" implementation of the CountMin_Sketch algorithm.
     * It's a probabilistic algorithm used for the resolution of the k-heavy hitter problem.
     * Uses a n*m sketch (as multiset) where m is an arbitrary length of the sketch and n is the number of the different hash function we have.
     * The multiset is nothing more than a frequency table for each hashtag;
     * the index of every hashtag will be: multiset[index of the hash function][hashed hashtag].
     * The execution time and the space used are very low.
     * </p>
     * <p> 
     * In this method takes a List of all the hashtags in all the Tweets 
     * in the TweetCorpus, an int k, and returns the k most frequently used hashtags.
     * </p>

 * @see TopHashtags
 * @see TweetCorpus#getTopHashtags(int)
 */

public class CountMin implements TopHashtags{


    private final int[] primesList = {2, 3, 5, 7, 11, 13}; // array of primes numbers used in the hash function
    private final int d = primesList.length;  // # of rows equal to the possible hash functions
    private int w; // # of columns
    private int k;

    /**
     * @param hashtagsList list of all the hashtags in a TweetCorpus
     * @param k The number of most used tweet we want to know
     * @return the k most used hashtags in a TweetCorpus
     * @see TopHashtags
     * @see Corpus#setTopHashtagsStrategy(TopHashtags)
     * @see TweetCorpus#getTopHashtags(int)
    */
    public List<String> calculate(List<String> hashtagsList, int k)
    {   
        this.k=k;
        this.w= (int) (2*hashtagsList.size())/k;
        if(hashtagsList.size()<=0){return null;}
        else if(hashtagsList.size() <= k){
            System.out.println("there is/are only "+hashtagsList.size()+" hashtags: \n");
            return hashtagsList;}
        return countMin(hashtagsList);
    }

    public List<String> countMin(List<String> hashtagsList){
        int[][] multiset = new int[d][w];
        HashMap<String, Integer> topK = new HashMap<>();
        for(String h : hashtagsList){
            addMultiset(multiset, h);
            int hMultisetMinVal = minVal(multiset, h);
            if(topK.keySet().contains(h) || topK.keySet().size() < k){
                topK.put(h, hMultisetMinVal);
            }else{
                int topKMinVal = topK.keySet().stream().map(ke -> topK.get(ke)).min(Integer::compareTo).get();
                String minKey = topK.keySet().stream().filter( ke -> topK.get(ke)<=topKMinVal).findFirst().get();
                if(hMultisetMinVal>topKMinVal){
                    topK.put(h, topKMinVal+1);
                    topK.remove(minKey);
                }
            }
        }
        return topK.keySet().stream().collect(Collectors.toList());
    }

    public void addMultiset(int[][] multiset, String s){
        for(int i = 0; i < d; i++){
            multiset[i][hash(s, i)]++;
        }
    }

    public int minVal(int[][] multiset, String s){
        int min = multiset[0][hash(s, 0)];
        for(int i=0; i<d; i++){
            if(multiset[i][hash(s, i)]<min){
                min = multiset[i][hash(s, i)];
            }
        }
        return min;
    }

    public int hash(String s, int j){
        return Math.abs((s.hashCode()*primesList[j]) % w);
    }

}