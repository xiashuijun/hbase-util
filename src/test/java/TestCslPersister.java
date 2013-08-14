import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.joda.time.DateTime;

import siemens.rcs.hbase.HBasePersisterParser;
import siemens.rcs.hbase.LogEntry;
import siemens.rcs.hbase.Util;

public class TestCslPersister {

  byte[] cf_log = Bytes.toBytes("l");
  byte[] qual_siteId = Bytes.toBytes("s");
  byte[] qual_logId = Bytes.toBytes("l");
  byte[] qual_timestamp = Bytes.toBytes("t");
  byte[] qual_value = Bytes.toBytes("d");

  public static void main(String[] args) {
    new TestCslPersister().run();
  }

  public void run() {
    LogEntry entry = new LogEntry();
    entry.setSiteId(new Long(21L));
    entry.setLogId("heyheyhey");
    entry.setTimestamp(new DateTime(1982, 9, 28, 0, 0, 0, 0).toDate());
    //x00\\x00\\x00]\\x9BH\\xD4\\x80
    entry.setValue("210");

    Put _1 = method1(entry);
    Put _2 = method2(entry);
    
    System.out.println("1:" + _1);
    System.out.println("2:" + _2);
  }

  private Put method2(LogEntry entry) {
    Put put = new Put(
        Util.createRowKey(
            entry.getSiteId(),
            entry.getLogId(),
            entry.getTimestamp()));

    put.add(cf_log, qual_siteId, Bytes.toBytes(entry.getSiteId()));
    put.add(cf_log, qual_logId, Bytes.toBytes(entry.getLogId()));
    put.add(cf_log, qual_value, Bytes.toBytes(entry.getValue().toString()));
    put.add(cf_log, qual_timestamp, Bytes.toBytes(entry.getTimestamp().getTime()));
    return put;
  }

  private Put method1(LogEntry entry) {
    return new HBasePersisterParser().parse(entry);
  }

}
