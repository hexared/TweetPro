package it.uniroma1.lcl.tweetpro;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.NoSuchElementException;
import java.net.MalformedURLException;
import java.net.URL;

public class TweetObj implements Tweet {

    private String tweet;
    private String userInfo;
    private String origTweetInfo;
    private Long id;
    private int likeCount;
    private int rtCount;

    public TweetObj(String tweet) {
        
        this.tweet = tweet.replace(",\"", "%sep%\"");
        this.userInfo = this.tweet.substring(this.tweet.indexOf("\"user\":"), this.tweet.indexOf("%sep%\"geo\":"));

        this.origTweetInfo = tweet.contains("\"retweeted_status\":")
                ? tweet.substring(tweet.indexOf("\"retweeted_status\":"), tweet.indexOf("},\"retweet_count\":") + 1) : "";

        this.id = Arrays.asList(this.tweet.split("%sep%")).stream().filter(x -> x.contains("\"id_str\":")).findFirst()
                .map(x -> x.substring(x.indexOf(":\"") + 2, x.length() - 1)).map(Long::parseLong).get();

        this.likeCount = Arrays.asList(this.tweet.split("%sep%")).stream().filter(x -> x.contains("\"favorite_count\":"))
                .findFirst().map(x -> x.substring(x.indexOf(":") + 1, x.length())).map(Integer::parseInt).get();
                
        this.rtCount = Arrays.asList(this.tweet.split("%sep%")).stream().filter(x -> x.contains("\"retweet_count\":"))
                .findFirst().map(x -> x.substring(x.indexOf(":") + 1, x.length())).map(Integer::parseInt).get();
    }

    public long getID() {
        return id;
    }

    public User getUser() {
        return new UserObj(userInfo);
    }

    public String getText() {
        return Arrays.asList(tweet.split("%sep%")).stream().filter(x -> x.contains("\"text\":")).findFirst()
                .map(x -> x.substring(x.indexOf(":\"") + 2, x.length() - 1)).get();
    }

    public List<String> getHashtags() {
        List<String> hashtags = Arrays.asList(tweet.substring(tweet.lastIndexOf("\"hashtags\":[") + 12, tweet.lastIndexOf("]%sep%\"symbols\""))
                                        .split("\\},\\{"));
        return hashtags.stream().filter(h -> !h.equals(""))
                .map(h -> h.substring(h.indexOf("\"text\":") + 8, h.indexOf("%sep%\"indices\"") - 1))
                .collect(Collectors.toList());
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getRTCount() {
        return rtCount;
    }

    public boolean isRetweet() {
        return !origTweetInfo.equals("");
    }

    public Tweet getOriginalTweet() {
        if (this.isRetweet()) {
            return new TweetObj(origTweetInfo);
        }
        System.out.println("This is not a retweet.");
        return null;
    }

    public Optional<URL> getMedia() {
        try {
            String media = Arrays.asList(tweet.split("%sep%")).stream().filter(x -> x.contains("\"media_url\":"))
                    .findFirst().map(x -> x.substring(x.indexOf(":") + 2, x.length() - 1).replace("\\", "")).get();

            return Optional.of(new URL(media));

        } catch (NoSuchElementException e) {
            System.out.println("This Tweet has no media.");
            return null;
        } catch (MalformedURLException e) {
            System.out.println("Bad URL.");
            return null;
        }
    }
}