package toy.mywordle.twitter;

import com.twitter.clientlib.TwitterCredentialsOAuth2;
import com.twitter.clientlib.api.TwitterApi;

public class TwitTest {

    public static void main(String[] args) {
         TwitterCredentialsOAuth2 credentials = new TwitterCredentialsOAuth2("id",
             "Secret",
             "TWITTER_OAUTH2_ACCESS_TOKEN",
             "TWITTER_OAUTH2_REFRESH_TOKEN");
        TwitterApi api = new TwitterApi(credentials);
    }

    private static void getTweets(String id, String bearerToken) {

    }
}
