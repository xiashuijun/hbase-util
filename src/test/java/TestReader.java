import org.apache.hadoop.conf.Configuration;
import org.joda.time.DateTime;
import org.junit.Test;

import siemens.rcs.hbase.LogReaderImpl;

public class TestReader {

  @Test
  public void test() {
    Configuration conf = new Configuration();
    conf.addResource("hbase-site.xml");
    
    LogReaderImpl test = new LogReaderImpl();
    test.setConf(conf);

    test.get(
        21L,
        "_106",
        new DateTime(2013, 8, 12, 12, 15, 0, 0).toDate(),
        new DateTime(2013, 8, 12, 14, 25, 0, 0).toDate());
  }

}
