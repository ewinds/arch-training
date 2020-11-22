import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5HashGenerator implements HashGenerator {
  private MessageDigest instance;
  private static ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);

  public MD5HashGenerator() {
    try {
      this.instance = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

  @Override
  public long hash(String key) {
    instance.reset();
    instance.update(key.getBytes());
    byte[] digest = instance.digest();
    int number = 0;

    return (((long) (digest[3 + number * 4] & 0xFF) << 24)
            | ((long) (digest[2 + number * 4] & 0xFF) << 16)
            | ((long) (digest[1 + number * 4] & 0xFF) << 8)
            | (digest[number * 4] & 0xFF))
        & 0xFFFFFFFFL;
  }
}
