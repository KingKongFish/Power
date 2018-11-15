package org.xiaoyu.utils.token;

import org.xiaoyu.utils.io.http.HttpClientKit;
import org.apache.commons.httpclient.Header;

import java.util.HashMap;
import java.util.Map;

public class OAuthTokenUtil {

  //token用户验证接口
  public static String auth(String consumerKey,String consumerSecret,String accessToken,String tokenSecret,String requestUrl) throws Exception {
    OAuthToken token = new OAuthToken();
    token.setOauthToken(accessToken);
    token.setOauthTokenSecret(tokenSecret);

    OAuth auth = new OAuth(consumerKey, consumerSecret);
    Map<String, Object> params = new HashMap<>();
    String headerString = auth.generateAuthorizationHeader("GET", requestUrl, params, token);
    Header header = new Header("Authorization", headerString);
    return HttpClientKit.get(requestUrl, header);
  }
}
