import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.glacier.model.CompleteMultipartUploadResult;
import com.amazonaws.services.glacier.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.glacier.model.InitiateMultipartUploadResult;
import com.amazonaws.services.glacier.model.UploadMultipartPartRequest;
import com.amazonaws.services.glacier.model.UploadMultipartPartResult;
import com.amazonaws.services.glacier.TreeHashGenerator;
import com.amazonaws.services.glacier.transfer.ArchiveTransferManager;
import com.amazonaws.services.glacier.transfer.UploadResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.util.BinaryUtils;

public class AwsExampleGlacierMultipart {

    private static int partSize = 1024 * 1024;

    public static void main(String[] args) throws Exception {

		// Create client.
        AmazonGlacierClient client = new AmazonGlacierClient(new ClasspathPropertiesFileCredentialsProvider());
		client.setRegion(Region.getRegion(Regions.US_WEST_2));

		String fileName = "glacierMultiPartJava.txt";

		File file = new File(fileName);

		// Request a multipart upload.
        InitiateMultipartUploadRequest initiateRequest = new InitiateMultipartUploadRequest()
            .withVaultName("Backup")
            .withArchiveDescription(fileName)
            .withPartSize("" + partSize);

        InitiateMultipartUploadResult initiateResult = client.initiateMultipartUpload(initiateRequest);

		// Get an upload id that is used to tie each upload part together
        String uploadId = initiateResult.getUploadId();

		// Upload each part and collect final checksum.
		String checksum = uploadParts(client, file, uploadId);

		// Conclude the multipart upload.
        CompleteMultipartUploadRequest completeRequest = new CompleteMultipartUploadRequest()
            .withVaultName("Backup")
            .withUploadId(uploadId)
            .withChecksum(checksum)
            .withArchiveSize(String.valueOf(file.length()));

        CompleteMultipartUploadResult completeResult = client.completeMultipartUpload(completeRequest);
	}

	private static String uploadParts(AmazonGlacierClient client, File upload, String uploadId) throws Exception {
		int pos = 0;

		int bytesRead = 0;

        FileInputStream uploadStream = new FileInputStream(upload);

		byte[] uploadBuffer = new byte[partSize];

		List<byte[]> partChecksums = new ArrayList<byte[]>();

		while(pos < upload.length()) {
			bytesRead = uploadStream.read(uploadBuffer, 0, uploadBuffer.length);
            if (bytesRead == -1) { break; }

			byte[] part = Arrays.copyOf(uploadBuffer, bytesRead);
			String partChecksum = TreeHashGenerator.calculateTreeHash(new ByteArrayInputStream(part));

			partChecksums.add(BinaryUtils.fromHex(partChecksum));

            UploadMultipartPartRequest partRequest = new UploadMultipartPartRequest()
				.withVaultName("Backup")
				.withBody(new ByteArrayInputStream(part))
				.withChecksum(partChecksum)
				.withRange(String.format("bytes %s-%s/*", pos, pos + bytesRead - 1)) // This is a standard format defined by a Java RFC.
				.withUploadId(uploadId);

			System.out.print("Upload: ");

            client.uploadMultipartPart(partRequest);

			System.out.println("SUCCEEDED! (pos = " + pos + ")");

            pos = pos + bytesRead;
		}

		return TreeHashGenerator.calculateTreeHash(partChecksums);
	}
}

