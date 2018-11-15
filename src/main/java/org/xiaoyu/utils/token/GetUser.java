package org.xiaoyu.utils.token;

public class GetUser {

    public static void main(String[] args) throws Exception{
        String site_url = "http://219.143.70.61/";
        String access_token = "0ec18c965f5ca637c4ab867f8eca92d8";
        String token_secret = "6828046356ca672c532797878f2d6bfc";
        String consumer_key = "dT0xJmFwcF9pZD03MjY5MzQ4JmFjY2Vzcz1ydw==";
        String consumer_secret = "6f4e885d30bc03333c89f8ccc1b4e5d2";
        String request_url = site_url + "apiv4/account/verify_credentials.json";
        String request_body = request_url + "";
        OAuthTokenUtil userAuthUtil = new OAuthTokenUtil();
        userAuthUtil.auth(consumer_key,consumer_secret,access_token,token_secret,request_url);
//        OAuthToken token = new OAuthToken();
//        token.setOauth_token(access_token);
//        token.setOauth_token_secret(token_secret);
//
//        OAuth auth = new OAuth(consumer_key,consumer_secret);
//        Map<String,Object> params = new HashMap<>();
//        String header = auth.generateAuthorizationHeader("GET","http://219.143.70.61/apiv4/account/verify_credentials.json",params,token);
//        System.out.println(header);
//
//        JSONObject jsonObject = null;
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpGet httpGet = new HttpGet(request_body);
//
////      header = "OAuth realm=\"api.example.com\",oauth_consumer_key=\"dT0xJmFwcF9pZD03MjY5MzQ4JmFjY2Vzcz1ydw%3D%3D\",oauth_nonce=\"\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"\",oauth_token=\"e8be53578fecc7b0b066c537973f5fd5\",oauth_version=\"1.0\",oauth_signature=\"syebR0YovhobS9eVezy%2ByMNdKd4%3D\"";
//        Header header1 = new BasicHeader("Authorization",header);
//        httpGet.setHeader(header1);
//
//        CloseableHttpResponse response = httpClient.execute(httpGet);
//
//        try{
//            HttpEntity entity = response.getEntity();
//            if(entity != null){
//                String result = EntityUtils.toString(entity, "utf-8");
//                System.out.println("result"+result);
//            }
//        }finally{
//            response.close();
//            httpClient.close();
//        }
    }
}
