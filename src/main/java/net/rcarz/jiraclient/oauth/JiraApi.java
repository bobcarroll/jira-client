package net.rcarz.jiraclient.oauth;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;
import org.scribe.services.RSASha1SignatureService;
import org.scribe.services.SignatureService;

import javax.xml.bind.DatatypeConverter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class JiraApi extends DefaultApi10a {
  private static final String SERVLET_BASE_URL = "/plugins/servlet";

  private static final String AUTHORIZE_URL = "/oauth/authorize?oauth_token=%s";

  private static final String REQUEST_TOKEN_RESOURCE = "/oauth/request-token";

  private static final String ACCESS_TOKEN_RESOURCE = "/oauth/access-token";

  private String serverBaseUrl = null;

  private String privateKey = null;

  public JiraApi(String serverBaseUrl, String privateKey) {
    this.serverBaseUrl = serverBaseUrl;
    this.privateKey = privateKey;
  }

  @Override
  public String getAccessTokenEndpoint() {
    if (null == serverBaseUrl || 0 == serverBaseUrl.length()) {
      throw new RuntimeException("serverBaseUrl is not properly initialized");
    }

    return serverBaseUrl + SERVLET_BASE_URL + ACCESS_TOKEN_RESOURCE;
  }

  @Override
  public String getRequestTokenEndpoint() {
    if (null == serverBaseUrl || 0 == serverBaseUrl.length()) {
      throw new RuntimeException("serverBaseUrl is not properly initialized");
    }

    return serverBaseUrl + SERVLET_BASE_URL + REQUEST_TOKEN_RESOURCE;
  }

  @Override
  public String getAuthorizationUrl(Token requestToken) {
    if (null == serverBaseUrl || 0 == serverBaseUrl.length()) {
      throw new RuntimeException("serverBaseUrl is not properly initialized");
    }

    return String.format(serverBaseUrl + SERVLET_BASE_URL + AUTHORIZE_URL, requestToken.getToken());
  }

  @Override
  public SignatureService getSignatureService() {
    if (null == privateKey || 0 == privateKey.length()) {
      throw new RuntimeException("privateKey is not properly initialized");
    }

    try {
      KeyFactory fac = KeyFactory.getInstance("RSA");
      PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(DatatypeConverter.parseBase64Binary(privateKey));
      PrivateKey privateKey = fac.generatePrivate(privKeySpec);
      return new RSASha1SignatureService(privateKey);
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
