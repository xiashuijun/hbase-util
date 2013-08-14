package siemens.rcs.hbase;

import java.util.Date;

public interface LogReader {

  void get(Long l, String string, Date dateTime, Date dateTime2);

}
