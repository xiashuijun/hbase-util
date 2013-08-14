package siemens.rcs.hbase;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RowKey {

  String hash() default "";

}
