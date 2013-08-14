package siemens.rcs.hbase;

import java.io.IOException;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class LogWriterImpl implements LogWriter {

  private Configuration conf;
  byte[] cf_log = Bytes.toBytes("l");
  byte[] qual_siteId = Bytes.toBytes("s");
  byte[] qual_logId = Bytes.toBytes("l");
  byte[] qual_timestamp = Bytes.toBytes("t");
  byte[] qual_value = Bytes.toBytes("d");
  
  @Override
  public void put(Long siteId, String logName, String value, Date timestamp) throws IOException {
    HBaseAdmin admin = new HBaseAdmin(conf);
    HTable table = new HTable(conf, "csl_test");
    try {
      Put put = new Put(Util.createRowKey(siteId, logName, timestamp));
      put.add(cf_log, qual_siteId, Bytes.toBytes(siteId));
      put.add(cf_log, qual_logId, Bytes.toBytes(logName));
      put.add(cf_log, qual_value, Bytes.toBytes(value));
      put.add(cf_log, qual_timestamp, Bytes.toBytes(timestamp.getTime()));
      table.put(put);
    } finally {
      admin.close();
      table.close();
    }
  }

  public void setConf(Configuration conf) {
    this.conf = conf;
  }

}
