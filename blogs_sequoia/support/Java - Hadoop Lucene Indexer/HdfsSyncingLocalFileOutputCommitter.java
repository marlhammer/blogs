import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.JobStatus.State;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;

public class HdfsSyncingLocalFileOutputCommitter extends FileOutputCommitter {
	public static final String PREFIX_LUCENE_INDEX_PART = "part-";

	private final FileSystem localFileSystem;
	private final File localScratchPath;

	private final FileSystem hdfsFileSystem;
	private final Path hdfsSyncPath;

	public HdfsSyncingLocalFileOutputCommitter(File localScratchPath, Path hdfsSyncPath, TaskAttemptContext context) throws IOException {
		super(hdfsSyncPath, context);

		Configuration conf = context.getConfiguration();

		this.localFileSystem = FileSystem.getLocal(conf);
		this.localScratchPath = localScratchPath;

		this.hdfsFileSystem = FileSystem.get(conf);
		this.hdfsSyncPath = hdfsSyncPath;
	}

	public File getLocalScratchPath() {
		return localScratchPath;
	}

	@Override
	public void abortJob(JobContext context, State state) throws IOException {
		deleteLocalScratchPath();
		super.abortJob(context, state);
	}

	@Override
	public void abortTask(TaskAttemptContext context) {
		deleteLocalScratchPath();
		super.abortTask(context);
	}

	@Override
	public void commitTask(TaskAttemptContext context) throws IOException {
		if (localScratchPath.exists()) {
			syncToHdfs(context);
		}
		super.commitTask(context);
		deleteLocalScratchPath();
	}

	@Override
	public boolean needsTaskCommit(TaskAttemptContext context) throws IOException {
		return localScratchPath.exists() || super.needsTaskCommit(context);
	}

	private void syncToHdfs(TaskAttemptContext context) throws IOException {
		if (!hdfsFileSystem.mkdirs(hdfsSyncPath)) {
			throw new IOException(String.format("Cannot create HDFS directory at [%s] to sync Lucene index!", hdfsSyncPath));
		}

		// Create subdirectory in HDFS for the Lucene index part from this particular reducer.
		Path indexPartHdfsFilePath = new Path(hdfsSyncPath, PREFIX_LUCENE_INDEX_PART + context.getTaskAttemptID().getTaskID().getId());

		if (!hdfsFileSystem.mkdirs(indexPartHdfsFilePath)) {
			throw new IOException(String.format("Cannot create HDFS directory at [%s] to sync Lucene index!", indexPartHdfsFilePath));
		}

		for (File localFile : localScratchPath.listFiles()) {
			context.progress();

			Path localFilePath = new Path("file://" + localFile.getPath());

			if (!localFileSystem.exists(localFilePath)) {
				throw new IOException(String.format("Cannot find local file [%s]!", localFilePath));
			}

			Path hdfsFilePath = new Path(indexPartHdfsFilePath, localFile.getName());
			if (hdfsFileSystem.exists(hdfsFilePath)) {
				throw new IOException(String.format("HDFS file [%s] already exists!", hdfsFilePath));
			}

			hdfsFileSystem.copyFromLocalFile(localFilePath, hdfsFilePath);
		}
	}

	private void deleteLocalScratchPath() {
		try {
			FileUtils.deleteDirectory(localScratchPath);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
