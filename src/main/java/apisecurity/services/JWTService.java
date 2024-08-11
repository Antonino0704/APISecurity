package apisecurity.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import test.models.Privilege;
import test.models.User;

public class JWTService implements RSAKeyProvider {
  private KeyPairGenerator kpg;
  private KeyPair kp;
  private Algorithm algorithm;

  private final String PATH = System.getProperty("user.dir") + "/src/main/resources";

  public JWTService() {
    super();
    if (!new File(PATH + "/private.key").isFile() || !new File(PATH + "/public.key").isFile()) {
      try {
        kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        kp = kpg.generateKeyPair();
        this.SaveKeyPair();
        System.out.println("keys created");
      } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
      }
    } else System.out.println("keys already exists");
  }

  public String generateToken(User user) {
    algorithm = Algorithm.RSA256(this.getPublicKey(), this.getPrivateKey());
    return JWT.create()
        .withIssuer("auth0")
        .withClaim("email", user.getEmail())
        .withClaim("privilege", user.getPrivileges().get(user.getPrivileges().size() - 1).getCode())
        .withExpiresAt(new Date(System.currentTimeMillis() + (60 * 24 * 60 * 60 * 1000)))
        .sign(algorithm);
  }

  public void verifyJWT(String token, Privilege privilege) {
    DecodedJWT decodedJWT = null;
    algorithm = Algorithm.RSA256(this.getPublicKey(), this.getPrivateKey());
    token = token.replace("Bearer ", "");
    JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();

    decodedJWT = verifier.verify(token);
    if (!(decodedJWT.getClaim("privilege").asInt() >= privilege.getCode())) {
      throw new JWTVerificationException(privilege.getPrivilege());
    }
  }

  public RSAPublicKey getPublicKey() {
    return (RSAPublicKey) LoadKeyPair().getPublic();
  }

  @Override
  public RSAPublicKey getPublicKeyById(String keyId) {
    return null;
  }

  @Override
  public RSAPrivateKey getPrivateKey() {
    return (RSAPrivateKey) LoadKeyPair().getPrivate();
  }

  @Override
  public String getPrivateKeyId() {
    return null;
  }

  public void SaveKeyPair() {
    try {

      // Store Public Key.
      X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(kp.getPublic().getEncoded());
      FileOutputStream fos = new FileOutputStream(PATH + "/public.key");
      fos.write(x509EncodedKeySpec.getEncoded());
      fos.close();

      // Store Private Key.
      PKCS8EncodedKeySpec pkcs8EncodedKeySpec =
          new PKCS8EncodedKeySpec(kp.getPrivate().getEncoded());
      fos = new FileOutputStream(PATH + "/private.key");
      fos.write(pkcs8EncodedKeySpec.getEncoded());
      fos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public KeyPair LoadKeyPair() {
    PublicKey publicKey = null;
    PrivateKey privateKey = null;

    try {
      // Read Public Key.
      File filePublicKey = new File(PATH + "/public.key");
      FileInputStream fis = new FileInputStream(PATH + "/public.key");
      byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
      fis.read(encodedPublicKey);
      fis.close();

      // Read Private Key.
      File filePrivateKey = new File(PATH + "/private.key");
      fis = new FileInputStream(PATH + "/private.key");
      byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
      fis.read(encodedPrivateKey);
      fis.close();

      // Generate KeyPair.
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
      publicKey = keyFactory.generatePublic(publicKeySpec);

      PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);

      privateKey = keyFactory.generatePrivate(privateKeySpec);
    } catch (InvalidKeySpecException | IOException | NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    return new KeyPair(publicKey, privateKey);
  }
}
