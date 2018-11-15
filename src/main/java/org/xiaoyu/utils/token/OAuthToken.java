package org.xiaoyu.utils.token;

import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;

public class OAuthToken implements Serializable {
    private static final String TAG = OAuthToken.class.getSimpleName();

    private String oauthToken;

    private String oauthTokenSecret;

    private transient SecretKeySpec secretKeySpec;

    public OAuthToken(String oauthToken, String oauthTokenSecret) {
        this.oauthToken = oauthToken;
        this.oauthTokenSecret = oauthTokenSecret;
    }

    public OAuthToken() {
    }

    public String getOauthtoken() {
        return oauthToken;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    public String getOauthTokenSecret() {
        return oauthTokenSecret;
    }

    public void setOauthTokenSecret(String oauthTokenSecret) {
        this.oauthTokenSecret = oauthTokenSecret;
    }

    public SecretKeySpec getSecretKeySpec() {
        return secretKeySpec;
    }

    public void setSecretKeySpec(SecretKeySpec secretKeySpec) {
        this.secretKeySpec = secretKeySpec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof OAuthToken))
            return false;
        OAuthToken that = (OAuthToken) o;
        if (secretKeySpec != null ? !secretKeySpec.equals(that.secretKeySpec) : that.secretKeySpec != null)
            return false;
        if (!oauthToken.equals(that.oauthToken))
            return false;
        return oauthTokenSecret.equals(that.oauthTokenSecret);

    }

    @Override
    public int hashCode() {
        if (oauthToken != null) {
            int result = oauthToken.hashCode();
            result = 31 * result + oauthTokenSecret.hashCode();
            result = 31 * result + (secretKeySpec != null ? secretKeySpec.hashCode() : 0);
            return result;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "OAuthToken{" + "oauth_token='" + oauthToken + '\'' + ", oauth_token_secret='" + oauthTokenSecret + '\'' + ", secretKeySpec="
                + secretKeySpec + '}';
    }
}
