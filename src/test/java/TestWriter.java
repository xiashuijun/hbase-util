import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.joda.time.DateTime;
import org.junit.Test;

import siemens.rcs.hbase.LogWriterImpl;

public class TestWriter {

  @Test
  public void testWrite() throws IOException {
    Configuration conf = new Configuration();
    conf.addResource("hbase-site.xml");
    
    LogWriterImpl test = new LogWriterImpl();
    test.setConf(conf);

    test.put(21L, "_106", "100", new DateTime("2013-08-12T13:00:00").toDate());
    test.put(21L, "_106", "105", new DateTime("2013-08-12T13:05:00").toDate());
    test.put(21L, "_106", "111", new DateTime("2013-08-12T13:10:00").toDate());
    test.put(21L, "_106", "113", new DateTime("2013-08-12T13:15:00").toDate());
    test.put(21L, "_106", "121", new DateTime("2013-08-12T13:20:00").toDate());
    test.put(21L, "_106", "125", new DateTime("2013-08-12T13:25:00").toDate());
  }

}
