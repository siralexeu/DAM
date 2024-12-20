package ro.ase.semdam_1087;

import androidx.room.TypeConverter;

import java.sql.Timestamp;
import java.util.Date;

public class DateConvertor {
@TypeConverter
    public Date fromTimestamp(Long value)
    {
        return value==null?null:new Date(value);
    }
    @TypeConverter
    public Long dateToTimeStamp(Date date)
    {
        if(date==null)
            return null;
        else
            return date.getTime();
    }
}
