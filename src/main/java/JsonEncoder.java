import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hujian.star.model.CommentEntry;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hujian on 2017/5/9.
 */
public class JsonEncoder implements Serializable {

    public static void main(String... args) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        CommentEntry commentEntry = new CommentEntry();
        commentEntry.setComment_content("yes,good");
        commentEntry.setFrom_user_id(7);
        commentEntry.setTo_user_id(3);
        commentEntry.setWork_id(BigInteger.valueOf(5));

        String json = mapper.writeValueAsString(commentEntry);

        System.out.println(json);


    }

}
