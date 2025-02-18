import java.io.File;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.transfer.ArchiveTransferManager;
import com.amazonaws.services.glacier.transfer.UploadResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class AwsExampleGlacier {
    public static void main(String[] args) throws Exception {

		// Create client.
        AmazonGlacierClient client = new AmazonGlacierClient(new ClasspathPropertiesFileCredentialsProvider());
		client.setRegion(Region.getRegion(Regions.US_WEST_2));

		// Simple upload.
		ArchiveTransferManager atm = new ArchiveTransferManager(client, new ClasspathPropertiesFileCredentialsProvider());

		UploadResult result = atm.upload(
			"Backup",
			"glacierJava.txt",
			new File("glacierJava.txt")
		);
	}
}

