package siemens.rcs.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;

public class HBasePersisterImpl implements HBasePersister {

  private Configuration configuration;

  public void persist(Object object) {
    HBasePersisterParser parser = new HBasePersisterParser();
    Put put = parser.parse(object);

    /*
    HBaseAdmin admin = null;
    HTable table = null;
    try {
      admin = new HBaseAdmin(configuration);
      table = new HTable(configuration, "csl_test");
      table.put(put);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (admin != null) admin.close();
        if (table != null) table.close();
      } catch (IOException e1) {
        e1.printStackTrace();
      }

    }*/
  }

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }

}
