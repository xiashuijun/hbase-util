import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.apache.hadoop.hbase.util.Bytes;
import org.joda.time.DateTime;


public class Blah {

  public static void main(String[] args) throws UnsupportedEncodingException {
    System.out.println(Bytes.toLong(Bytes.toBytes(402037200000L)));
  }

}
