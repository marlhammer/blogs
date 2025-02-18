import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

public class TextSet implements Writable {
	public final Set<Text> values = new HashSet<Text>();

	@Override
	public void readFields(DataInput input) throws IOException {
		values.clear(); // Note the reuse!
		int size = input.readInt();
		for (int i = 0; i < size; ++i) {
			Text value = new Text();
			value.readFields(input);
			values.add(value);
		}
	}

	@Override
	public void write(DataOutput output) throws IOException  {
		output.writeInt(values.size());
		for (Text value : values) {
			value.write(output);
		}
	}

	public void add(String value) {
		if (StringUtils.isNotBlank(value)) {
			values.add(new Text(value));
		}
	}
}
