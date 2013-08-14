package siemens.rcs.hbase;

import java.io.IOException;
import java.util.Date;

public interface LogWriter {

  void put(Long siteId, String logName, String value, Date timestamp) throws IOException;

}
