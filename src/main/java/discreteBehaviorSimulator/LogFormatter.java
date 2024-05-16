package discreteBehaviorSimulator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * @author Flavien Vernier
 *
 */
public class LogFormatter  extends Formatter {

	/**
	 * Formats the given LogRecord into a string representation.
	 *
	 * @param rec the LogRecord to be formatted
	 * @return a string representation of the given LogRecord. If rec is null, an empty string is returned.
	 * The string representation includes the date, level, and message of the LogRecord, each on a new line.
	 */
	public String format(LogRecord rec) {
		if (rec == null) {
			return "";
		}

		StringBuffer buf = new StringBuffer();

		buf.append(calcDate(rec.getMillis()));
		buf.append(": ");
		if (rec.getLevel() == null) {
			buf.append("null");
		} else {
			buf.append(rec.getLevel());
		}

		buf.append(System.getProperty("line.separator"));
		buf.append(formatMessage(rec));
		buf.append(System.getProperty("line.separator"));
		
		return buf.toString();
	}

	/**
	 * Converts the given milliseconds into a formatted date string.
	 *
	 * @param millisecs the milliseconds to be converted
	 * @return a string representation of the date in the format "yyyy.MM.dd HH:mm:ss.SS".
	 * If millisecs is negative, the result is the string representation of a negative date.
	 */
	public String calcDate(long millisecs) {
	    SimpleDateFormat date_format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SS");
	    Date resultdate = new Date(millisecs);
	    return date_format.format(resultdate);
	  }

	/**
	 * Returns an empty string as the header of the log.
	 *
	 * @param h the Handler for which the header string is being generated
	 * @return an empty string. This method is intended to be overridden in subclasses.
	 */
	  public String getHead(Handler h) {
		  return "";
	  }

	 /**
	 * Returns an empty string as the tail of the log.
	 *
	 * @param h the Handler for which the tail string is being generated
	 * @return an empty string. This method is intended to be overridden in subclasses.
	 */
	  public String getTail(Handler h) {
	    return "";
	  }


}
