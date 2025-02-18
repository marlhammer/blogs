import java.io.File;
import java.io.IOException;

import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class HdfsSyncingLocalFileOutputFormat<K, V> extends FileOutputFormat<K, V> {
	public static final String PARAMETER_LOCAL_SCRATCH_PATH = "param.localScratchPath";

	private HdfsSyncingLocalFileOutputCommitter committer;

	@Override
	public synchronized OutputCommitter getOutputCommitter(TaskAttemptContext context) throws IOException {

		if (committer == null) {
			// Create temporary local directory on the local file system as pass it to the committer.
			File localScratchPath = new File (context.getConfiguration().get(PARAMETER_LOCAL_SCRATCH_PATH) + File.separator + "scratch" + File.separator + context.getTaskAttemptID().toString() + File.separator);

			committer = new HdfsSyncingLocalFileOutputCommitter(localScratchPath, super.getOutputPath(context), context);
		}

		return committer;
	}

	@Override
	public RecordWriter<K, V> getRecordWriter(TaskAttemptContext context) throws IOException, InterruptedException {
		return new RecordWriter<K, V>() {
			@Override
			public void close(TaskAttemptContext context) throws IOException, InterruptedException { }

			@Override
			public void write(K key, V val) throws IOException, InterruptedException { }
		};
	}
}
