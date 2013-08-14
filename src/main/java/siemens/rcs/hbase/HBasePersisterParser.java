package siemens.rcs.hbase;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.joda.time.DateTime;

public class HBasePersisterParser {

  public Put parse(Object obj) {
    Class<?> clazz = obj.getClass();

    List<byte[]> bytes = new LinkedList<byte[]>();

    HBasePersistable persistable = clazz.getAnnotation(HBasePersistable.class);

    for (String name : persistable.order()) {
      try {
        Field f = clazz.getDeclaredField(name);
        f.setAccessible(true);
        for (Annotation a : f.getAnnotations()) {
          if (a.annotationType().equals(RowKey.class)) {
            try {

              Object o = f.get(obj);
              RowKey anno = f.getAnnotation(RowKey.class);

              //TODO - genericize
              if ("".equals(anno.hash()) == false) {
                MessageDigest md = MessageDigest.getInstance(anno.hash());
                if (o.getClass().equals(Long.class)) {
                  Long myLong = (Long) o;
                  byte[] digest = md.digest(Bytes.toBytes(myLong));
                  bytes.add(digest);
                } else {
                  byte[] digest = md.digest(Bytes.toBytes(o.toString()));
                  bytes.add(digest);
                }
              } else {
                if (o.getClass().equals(Date.class)) {
                  Date d = (Date) o;
                  bytes.add(Bytes.toBytes(d.getTime()));
                }
              }

            } catch (IllegalAccessException e) {
              throw new IllegalArgumentException(e);
            } catch (NoSuchAlgorithmException e) {
              throw new IllegalArgumentException("We're using MD5 for encryption, so shut up about SHA being better.", e);
            }
          }
        }
      } catch (SecurityException e) {
        e.printStackTrace();
      } catch (NoSuchFieldException e) {
        e.printStackTrace();
      }
    }

    int totalLength = 0;
    for (byte[] bar : bytes) {
      totalLength += bar.length;
    }

    int lastSpot = 0;
    byte[] rowKey = new byte[totalLength];
    for (byte[] bar : bytes) {
      System.arraycopy(bar, 0, rowKey, lastSpot, bar.length);
      lastSpot += bar.length;
    }

    Put put = new Put(rowKey);

    for (Field field : clazz.getDeclaredFields()) {
      if (field.isAccessible() == false) {
        field.setAccessible(true);
      }

      for (Annotation a : field.getAnnotations()) {
        if (a.annotationType().equals(RowValue.class)) {

          try {
            byte[] cf = Bytes.toBytes(persistable.cf());
            RowValue rowValue = (RowValue) a;
            byte[] qualifier = Bytes.toBytes(rowValue.qualifier());

            //TODO - genericize
            if (field.get(obj).getClass().equals(Date.class)) {
              Date d = (Date) field.get(obj);
              byte[] value = Bytes.toBytes(new DateTime(d).getMillis());
              put.add(cf, qualifier, value);
              System.out.println("date:" + d.getTime());
            } else if (field.get(obj).getClass().equals(Long.class)
                || field.get(obj).getClass().equals(long.class)) {
              Long d = (Long) field.get(obj);
              byte[] value = Bytes.toBytes(d);
              put.add(cf, qualifier, value);
              System.out.println("long:" + d.longValue());
            } else {
              byte[] value = Bytes.toBytes(field.get(obj).toString());
              put.add(cf, qualifier, value);
              System.out.println("other:" + field.get(obj).toString());
            }

          } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
          }

        }
      }
    }

    return put;
  }

}
