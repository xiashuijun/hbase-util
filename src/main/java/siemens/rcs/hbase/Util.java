package siemens.rcs.hbase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.hadoop.hbase.util.Bytes;

public class Util {
  
  public static byte[] createRowKey(Long siteId, String logName, Date timestamp) {
    try {
      MessageDigest md5 = MessageDigest.getInstance("MD5");

      byte[] hSite = md5.digest(Bytes.toBytes(siteId));
      byte[] hLog = md5.digest(Bytes.toBytes(logName));
      byte[] timestampBytes = Bytes.toBytes(timestamp.getTime());

      byte[] retVal = new byte[hSite.length + hLog.length + timestampBytes.length];

      //System.arraycopy(src, srcPos, dest, destPos, length);
      System.arraycopy(hSite, 0, retVal, 0, hSite.length);
      System.arraycopy(hLog, 0, retVal, hSite.length, hLog.length);
      System.arraycopy(timestampBytes, 0, retVal, hSite.length + hLog.length, timestampBytes.length);

      return retVal;
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalArgumentException("NoSuchAlgorithmException means you chose the wrong algorithm"
          + " or this version of Java doesn't support MD5.");
    }
  }
  
}
