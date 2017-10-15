import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest 
{
	public static void main(String[] args) 
	{
		Timestamp ts = Timestamp.valueOf("2017-10-15 13:20:25");
		Date dt = new Date();
		dt.setTime(ts.getTime());
		SimpleDateFormat x = new SimpleDateFormat("dd MMMM yyyy");
		System.out.println(x.format(dt));
		System.out.println(dt);
	}
}
