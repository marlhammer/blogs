import java.io.File;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class AwsExampleS3 {

	public static void main(String[] args) throws Exception {
		AmazonS3Client s3Client = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider());
		s3Client.setRegion(Region.getRegion(Regions.US_EAST_1));

    	PutObjectRequest putObjectRequest = new PutObjectRequest(
			"smouring-test-bucket", // Bucket Name
			"s3Java.txt",           // Object Name
			new File("s3Java.txt")  // File
		);

        s3Client.putObject(putObjectRequest);
	}
}

