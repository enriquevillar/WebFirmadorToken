package cl.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Log4j
@Component
public class ClienteS3 {

    @Value("${S3_ACCESS_KEY}")
    private String S3_ACCESS_KEY;

    @Value("${S3_SECRET_KEY}")
    private String S3_SECRET_KEY;

    public void uploadObject(InputStream objeto, String id) throws Exception {
        AWSCredentials credentials = new BasicAWSCredentials(
                S3_ACCESS_KEY,
                S3_SECRET_KEY
        );

        final AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_WEST_2)
                .build();

        try {

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("title", "someTitle");
            PutObjectRequest request = new PutObjectRequest("mrz", id, objeto, metadata);
            //request.setMetadata(metadata);
            s3client.putObject(request);

        } catch (SdkClientException e) {
           e.printStackTrace();

            throw e;
        }

    }
    public void uploadArchivoAFirma(String buketName,InputStream objeto, String id) throws Exception {
        AWSCredentials credentials = new BasicAWSCredentials(
                S3_ACCESS_KEY,
                S3_SECRET_KEY
        );




        final AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_WEST_2)
                .build();

        try {

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("title", "someTitle");
            PutObjectRequest request = new PutObjectRequest(buketName, id, objeto, metadata);
            //request.setMetadata(metadata);
            s3client.putObject(request);

        } catch (SdkClientException e) {


            throw e;
        }

    }




    public InputStream downloadObject(String id,String buketName) throws Exception {
        AWSCredentials credentials = new BasicAWSCredentials(
                S3_ACCESS_KEY,
                S3_SECRET_KEY
        );


        final AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_WEST_2)
                .build();

        try {
            S3Object object = s3client.getObject(new GetObjectRequest(buketName, id));
            return object.getObjectContent();



        } catch (SdkClientException e) {
            e.printStackTrace();

            throw e;
        }

    }
    public InputStream downloadObjectVerificacion(String id) throws Exception {
        AWSCredentials credentials = new BasicAWSCredentials(
                S3_ACCESS_KEY,
                S3_ACCESS_KEY
        );

        final AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_WEST_2)
                .build();

        try {
            S3Object object = s3client.getObject(new GetObjectRequest("verificacion", id));
            return object.getObjectContent();



        } catch (SdkClientException e) {
            e.printStackTrace();

            throw e;
        }

    }




}
