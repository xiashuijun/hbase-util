package siemens.rcs.hbase;

import java.io.IOException;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class LogReaderImpl implements LogReader {

  private Configuration conf;

  @Override
  public void get(Long siteId, String logName, Date start, Date end) {
    try {
      HTable table = new HTable(conf, "csl_test");

      Scan scan = new Scan();

      RowFilter lowerRowFilter = new RowFilter(CompareOp.GREATER_OR_EQUAL,
          new BinaryComparator(Util.createRowKey(siteId, logName, start)));

      RowFilter upperRowFilter = new RowFilter(CompareOp.LESS_OR_EQUAL,
          new BinaryComparator(Util.createRowKey(siteId, logName, end)));

      FilterList filterList = new FilterList();
      filterList.addFilter(lowerRowFilter);
      filterList.addFilter(upperRowFilter);

      scan.setFilter(filterList);
      ResultScanner scanner = table.getScanner(scan);

      for (Result r = scanner.next(); r != null; r = scanner.next()) {
        for (KeyValue kv : r.raw()) {
          String q = Bytes.toString(kv.getQualifier());
          if ("s".equals(q)) {
            System.out.println("site: "+Bytes.toLong(kv.getValue()));
          } else if ("l".equals(q)) {
            System.out.println("log: "+Bytes.toString(kv.getValue()));
          } else if ("t".equals(q)) {
            System.out.println("ts: "+new Date(Bytes.toLong(kv.getValue())));
          } else if ("d".equals(q)) {
            System.out.println("data: "+Bytes.toString(kv.getValue()));
          }
        }
        System.out.println("---");
      }
      scanner.close();
      table.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void setConf(Configuration conf) {
    this.conf = conf;
  }

}
