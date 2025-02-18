import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

public class Person implements Writable {
    public Text firstName = new Text();
    public Text lastName = new Text();

    @Override
    public void readFields(DataInput input) throws IOException {
        firstName.readFields(input);
        lastName.readFields(input);
    }

    @Override
    public void write(DataOutput output) throws IOException  {
        firstName.write(output);
        lastName.write(output);
    }
}
