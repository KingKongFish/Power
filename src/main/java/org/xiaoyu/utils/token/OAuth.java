package org.xiaoyu.utils.token;



import org.xiaoyu.utils.data.StringKit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;


public class OAuth implements java.io.Serializable {

    private static final String TAG = OAuth.class.getName();

    private static final String HMAC_SHA1 = "HmacSHA1";
    /**
     * 签名的方法有：HMAC-SHA1、RSA-SHA1与PLAINTEXT等三种。
     */
    private static final String OAUTH_SIGNATURE_METHOD = "HMAC-SHA1";
    private static Random RAND = new Random();
    /**
     * 使用者的ID，OAUTH服务的直接使用者是开发者开发出来的应用。所以该参数值的获取一般是要去OAUTH服务提供商处注册一个应用，
     * 再获取该应用的OAUTH_consumer_key。
     */
    private String consumerKey = "";
    /**
     * OAUTH_consumer_key对应的密钥。
     */
    private String consumerSecret;

    public OAuth(String consumerKey, String consumerSecret) {
        setConsumerKey(consumerKey);
        setConsumerSecret(consumerSecret);
    }

    /**
     * The getRequest parameters are collected, sorted and concatenated into a
     * normalized string:<br>
     * 鈥�Parameters in the OAuth HTTP Authorization header excluding the realm
     * parameter.<br>
     * 鈥�Parameters in the HTTP POST getRequest body (with a content-type of
     * application/x-www-form-urlencoded).<br>
     * 鈥�HTTP GET parameters added to the URLs in the query part (as defined by
     * [RFC3986] section 3).<br>
     * <br>
     * The oauth_signature parameter MUST be excluded.<br>
     * The parameters are normalized into a single string as follows:<br>
     * 1. Parameters are sorted by name, using lexicographical byte value
     * ordering. If two or more parameters share the same name, they are sorted
     * by their value. For example:<br>
     * 2. a=1, c=hi%20there, f=25, f=50, f=a, z=p, z=t<br>
     * 3. <br>
     * 4. Parameters are concatenated in their sorted order into a single
     * string. For each parameter, the name is separated from the corresponding
     * value by an 鈥�鈥�character (ASCII code 61), even if the value is empty.
     * Each name-value pair is separated by an 鈥�鈥�character (ASCII code 38).
     * For example:<br>
     * 5. a=1&c=hi%20there&f=25&f=50&f=a&z=p&z=t<br>
     * 6. <br>
     *
     * @param params parameters to be normalized and concatenated
     * @return nomarized and concatenated parameters
     * @see <a href="http://oauth.net/core/1.0#rfc.section.9.1.1">OAuth Core -
     * 9.1.1. Normalize getRequest Parameters</a>
     */
    public static String normalizeRequestParameters(Map<String,Object> params) {
    	
        return encodeParameters(params);
    }

    /**
     * @param postParams parameters to be enocded and concatenated
     * @return eoncoded string
     * @see <a href="http://wiki.oauth.net/TestCases">OAuth / TestCases</a>
     * @see <a
     * href="http://groups.google.com/group/oauth/browse_thread/thread/a8398d0521f4ae3d/9d79b698ab217df2?hl=en&lnk=gst&q=space+encoding#9d79b698ab217df2">Space
     * encoding - OAuth | Google Groups</a>
     */
    public static String encodeParameters(Map<String,Object> postParams) {
        return encodeParameters(postParams, "&", false);
    }

    public static String encodeParameters(Map<String,Object> postParams,
            String splitter, boolean quot) {
    	StringBuffer buf = new StringBuffer();
    	ArrayList<String> paramKeys=new ArrayList(postParams.keySet());
    	Collections.sort(paramKeys);

    	for (String key:paramKeys) {
    		if (buf.length() != 0) {
    			if (quot) {
    				buf.append("\"");
    			}
    			buf.append(splitter);
			}
			buf.append(encode(key)).append("=");
			if (quot) {
				buf.append("\"");
			}
			if (null != postParams.get(key)) {
				buf.append(encode(postParams.get(key).toString()));
				}
			}
			if (buf.length() != 0) {
				if (quot) {
				buf.append("\"");
				}
		}
	return buf.toString();
}


    /**
     * @param value string to be encoded
     * @return encoded string
     * @see <a href="http://wiki.oauth.net/TestCases">OAuth / TestCases</a>
     * @see <a
     * href="http://groups.google.com/group/oauth/browse_thread/thread/a8398d0521f4ae3d/9d79b698ab217df2?hl=en&lnk=gst&q=space+encoding#9d79b698ab217df2">Space
     * encoding - OAuth | Google Groups</a>
     * @see <a href="http://tools.ietf.org/html/rfc3986#section-2.1">RFC 3986 -
     * Uniform Resource Identifier (URI): Generic Syntax - 2.1.
     * Percent-Encoding</a>
     */
    public static String encode(String value) {
        String encoded = "";
        try {
            encoded = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException ignore) {
            // ignore
//            Log.e(TAG, ignore.getMessage(), ignore);
        }
        StringBuffer buf = new StringBuffer(encoded.length());
        char focus;
        for (int i = 0; i < encoded.length(); i++) {
            focus = encoded.charAt(i);
            if (focus == '*') {
                buf.append("%2A");
            } else if (focus == '+') {
                buf.append("%20");
            } else if (focus == '%' && (i + 1) < encoded.length()
                    && encoded.charAt(i + 1) == '7'
                    && encoded.charAt(i + 2) == 'E') {
                buf.append('~');
                i += 2;
            } else {
                buf.append(focus);
            }
        }
        return buf.toString();
    }

    /**
     * The Signature BaseEntity String includes the getRequest absolute URL, tying the
     * signature to a specific endpoint. The URL used in the Signature BaseEntity
     * String MUST include the scheme, authority, and path, and MUST exclude the
     * query and fragment as defined by [RFC3986] section 3.<br>
     * If the absolute getRequest URL is not available to the Service Provider (it
     * is always available to the Consumer), it can be constructed by combining
     * the scheme being used, the HTTP Host header, and the relative HTTP
     * getRequest URL. If the Host header is not available, the Service Provider
     * SHOULD use the host name communicated to the Consumer in the
     * documentation or other means.<br>
     * The Service Provider SHOULD document the form of URL used in the
     * Signature BaseEntity String to avoid ambiguity due to URL normalization. Unless
     * specified, URL scheme and authority MUST be lowercase and include the
     * port number; http default port 80 and https default port 443 MUST be
     * excluded.<br>
     * <br>
     * For example, the getRequest:<br>
     * HTTP://Example.com:80/resource?id=123<br>
     * Is included in the Signature BaseEntity String as:<br>
     * http://example.com/resource
     *
     * @param url the url to be normalized
     * @return the Signature BaseEntity String
     * @see <a href="http://oauth.net/core/1.0#rfc.section.9.1.2">OAuth Core -
     * 9.1.2. Construct getRequest URL</a>
     */

    public static String constructRequestURL(String url) {
        int index = url.indexOf("?");
        if (-1 != index) {
            url = url.substring(0, index);
        }
        int slashIndex = url.indexOf("/", 8);
        //如果slashIndex等于-1，既url=https://www.baidu.com的情况
        String baseURL = (slashIndex>0?url.substring(0, slashIndex):url).toLowerCase(
                Locale.getDefault());
        int colonIndex = baseURL.indexOf(":", 8);
        if (-1 != colonIndex) {
            // url contains port number
            if (baseURL.startsWith("http://") && baseURL.endsWith(":80")) {
                // http default port 80 MUST be excluded
                baseURL = baseURL.substring(0, colonIndex);
            } else if (baseURL.startsWith("https://")
                    && baseURL.endsWith(":443")) {
                // http default port 443 MUST be excluded
                baseURL = baseURL.substring(0, colonIndex);
            }
        }
        url = baseURL + (slashIndex>0?url.substring(slashIndex):url);
        return url;
    }

    /**
     * @param method
     * @param url
     * @param params
     * @param nonce     随机生成的字符串，用于防止请求的重复，防止外界的非法攻击。
     * @param timestamp 发起请求的时间戳，其值是距1970 00:00:00
     *                  GMT的秒数，必须是大于0的整数。本次请求的时间戳必须大于或者等于上次的时间戳。
     * @param authToken    OAUTH进行到最后一步得到的一个“令牌”，通过此“令牌”请求，就可以去拥有资源的网站抓取任意有权限可以被抓取的资源。
     * @return
     */
    public String generateAuthorizationHeader(String method, String url,
                                              Map<String,Object> params, String nonce, String timestamp,
                                              OAuthToken authToken) {
        if (null == params) {
            params = new HashMap<String,Object>();
        }
        Map<String,Object> oauthHeaderParams = new HashMap<String,Object>();
        oauthHeaderParams.put("oauth_consumer_key", consumerKey);
        oauthHeaderParams.put("oauth_signature_method",OAUTH_SIGNATURE_METHOD);
        oauthHeaderParams.put("oauth_timestamp", timestamp);
        oauthHeaderParams.put("oauth_nonce", nonce);
        //OAUTH的版本号。
        oauthHeaderParams.put("oauth_version", "1.0");
        if (null != authToken && authToken.getOauthtoken() != null) {
            oauthHeaderParams.put("oauth_token", authToken.getOauthtoken());
        }
        Map<String,Object> signatureBaseParams = new HashMap<String,Object>();
        signatureBaseParams.putAll(oauthHeaderParams);
        String mToken = authToken.getOauthtoken();
        String mTokenSecret = authToken.getOauthTokenSecret();
        if (StringKit.isBlank(mToken) && StringKit.isBlank(mTokenSecret) && params.size()>0) {
            // 只有获取token接口才会执行
            if(!method.toUpperCase().equals("POST")) {
                signatureBaseParams.putAll(params);
            }
        }
        parseGetParameters(constructRequestURL(url), signatureBaseParams);
        StringBuffer base = new StringBuffer(method).append("&")
                .append(encode(constructRequestURL(url))).append("&");
        // Log.v(TAG, "base:" + base.toString());
        base.append(encode(normalizeRequestParameters(signatureBaseParams)));
        String oauthBaseString = base.toString();
        // Log.v(TAG, "oauthBaseString:" + oauthBaseString);
        String signature = generateSignature(oauthBaseString, authToken);
        // Log.v(TAG, "signature:" + signature);
        oauthHeaderParams.put("oauth_signature", signature);
        if (StringKit.isBlank(mToken) && StringKit.isBlank(mTokenSecret) && params.size()>0) {
            // 只有获取token接口才会执行
            if(!method.toUpperCase().equals("POST")) {
                oauthHeaderParams.putAll(params);
            }
        }
        // Log.v(TAG, "size:" + oauthHeaderParams.size());
        // Log.v(TAG, "oauthHeaderParams:" + oauthHeaderParams);
        return "OAuth " + encodeParameters(oauthHeaderParams, ",", true);

    }

    private void parseGetParameters(String url, Map<String,Object> signatureBaseParams) {
        int queryStart = url.indexOf("?");
        if (-1 != queryStart) {
            String[] queryStrs = url.substring(queryStart + 1).split("&");
            try {
                for (String query : queryStrs) {
                    String[] split = query.split("=");
                    if (split.length == 2) {
                        signatureBaseParams.put(URLDecoder.decode(split[0], "UTF-8"), URLDecoder.decode(split[1], "UTF-8"));
                    } else {
                        signatureBaseParams.put(URLDecoder.decode(split[0], "UTF-8"), "");
                    }
                }
            } catch (UnsupportedEncodingException ignore) {
            }

        }

    }

    /**
     * @return
     * @see <a href="http://oauth.net/core/1.0#rfc.section.5.4.1">OAuth Core -
     * 5.4.1. Authorization Header</a>
     */
    public String generateAuthorizationHeader(String method, String url,
                                              Map<String,Object> params, OAuthToken token) {
        long timestamp = System.currentTimeMillis() / 1000;
        long nonce = timestamp + RAND.nextInt();
        return generateAuthorizationHeader(method, url, params,
                String.valueOf(nonce), String.valueOf(timestamp), token);
    }

    /**
     * Computes RFC 2104-compliant HMAC signature.
     *
     * @param data the data to be signed
     * @return signature
     * @see <a href="http://oauth.net/core/1.0/#rfc.section.9.2.1">OAuth Core -
     * 9.2.1. Generating Signature</a>
     */
    public String generateSignature(String data, OAuthToken token) {
        byte[] byteHMAC = new byte[0];
        try {
            Mac mac = Mac.getInstance(HMAC_SHA1);
            SecretKeySpec spec;
            if (null == token) {
                String oauthSignature = encode(consumerSecret) + "&";
                spec = new SecretKeySpec(oauthSignature.getBytes(), HMAC_SHA1);
            } else {
                if (null == token.getSecretKeySpec()) {
                    String oauthSignature = encode(consumerSecret) + "&"
                            + encode(token.getOauthTokenSecret());
                    spec = new SecretKeySpec(oauthSignature.getBytes(),
                            HMAC_SHA1);
                    token.setSecretKeySpec(spec);
                }
                spec = token.getSecretKeySpec();
            }
            mac.init(spec);
            byteHMAC = mac.doFinal(data.getBytes());
        } catch (InvalidKeyException e) {
//            Log.e(TAG, e.getMessage(), e);
        } catch (NoSuchAlgorithmException ignore) {
            // ignore
//            Log.e(TAG, ignore.getMessage(), ignore);
        }
        return Base64Util.encode(byteHMAC);
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = null != consumerKey ? consumerKey : "";
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = null != consumerSecret ? consumerSecret : "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof OAuth))
            return false;

        OAuth oAuth = (OAuth) o;

        if (consumerKey != null ? !consumerKey.equals(oAuth.consumerKey)
                : oAuth.consumerKey != null)
            return false;
        return !(consumerSecret != null ? !consumerSecret
                .equals(oAuth.consumerSecret) : oAuth.consumerSecret != null);

    }

    @Override
    public int hashCode() {
        int result = consumerKey != null ? consumerKey.hashCode() : 0;
        result = 31 * result
                + (consumerSecret != null ? consumerSecret.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OAuth{" + "consumerKey='" + consumerKey + '\''
                + ", consumerSecret='" + consumerSecret + '\'' + '}';
    }
}
