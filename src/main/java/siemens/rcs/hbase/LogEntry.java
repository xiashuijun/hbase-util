package siemens.rcs.hbase;

import java.util.Date;

@HBasePersistable(order={"siteId","logId","timestamp"}, cf="l")
public class LogEntry {

  @RowKey(hash="MD5")
  @RowValue(qualifier="s")
  private Long siteId;
  
  @RowKey(hash="MD5")
  @RowValue(qualifier="l")
  private String logId;
  
  @RowKey
  @RowValue(qualifier="t")
  private Date timestamp;

  @RowValue(qualifier="d")
  private String value;
  
  public void setSiteId(Long siteId) {
    this.siteId = siteId;
  }

  public Long getSiteId() {
    return siteId;
  }

  public String getLogId() {
    return logId;
  }

  public void setLogId(String logId) {
    this.logId = logId;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
