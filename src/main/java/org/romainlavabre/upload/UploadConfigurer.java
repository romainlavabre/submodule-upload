package org.romainlavabre.upload;

import org.romainlavabre.upload.exception.NotInitializedException;

public class UploadConfigurer {
    private static UploadConfigurer INSTANCE;
    protected      UploadProvider   uploadProvider = UploadProvider.AWS_S3;
    protected      String           awsS3BucketName;
    protected      String           awsS3PublicKey;
    protected      String           awsS3PrivateKey;


    public UploadConfigurer() {
        INSTANCE = this;
    }


    protected static UploadConfigurer get() {
        if ( INSTANCE == null ) {
            throw new NotInitializedException();
        }

        return INSTANCE;
    }


    /**
     * Initializes a new UploadConfigurer instance.
     *
     * @return a new UploadConfigurer instance
     */
    public static UploadConfigurer init() {
        return new UploadConfigurer();
    }


    protected UploadProvider getUploadProvider() {
        return uploadProvider;
    }


    /**
     * Sets the file database provider for the UploadConfigurer.
     *
     * @param uploadProvider the upload provider to be set
     * @return the modified UploadConfigurer instance
     */
    public UploadConfigurer setUploadProvider( UploadProvider uploadProvider ) {
        this.uploadProvider = uploadProvider;

        return this;
    }


    protected String getAwsS3BucketName() {
        return awsS3BucketName;
    }


    /**
     * Sets the AWS S3 bucket name for the UploadConfigurer.
     * Find it on <a href="https://s3.console.aws.amazon.com/s3/home">AWS</a>
     *
     * @param awsS3BucketName the name of the AWS S3 bucket to be set
     * @return the modified UploadConfigurer instance
     */
    public UploadConfigurer setAwsS3BucketName( String awsS3BucketName ) {
        this.awsS3BucketName = awsS3BucketName;

        return this;
    }


    protected String getAwsS3PublicKey() {
        return awsS3PublicKey;
    }


    /**
     * Sets the AWS S3 public key for the UploadConfigurer.
     *
     * @param awsS3PublicKey the public key to be set for AWS S3
     * @return the modified UploadConfigurer instance with the AWS S3 public key set
     */
    public UploadConfigurer setAwsS3PublicKey( String awsS3PublicKey ) {
        this.awsS3PublicKey = awsS3PublicKey;

        return this;
    }


    protected String getAwsS3PrivateKey() {
        return awsS3PrivateKey;
    }


    /**
     * Sets the AWS S3 private key for the UploadConfigurer.
     *
     * @param awsS3PrivateKey the private key to be set for AWS S3
     * @return the modified UploadConfigurer instance with the AWS S3 private key set
     */
    public UploadConfigurer setAwsS3PrivateKey( String awsS3PrivateKey ) {
        this.awsS3PrivateKey = awsS3PrivateKey;

        return this;
    }


    public void build() {
    }


    public enum UploadProvider {
        AWS_S3
    }
}
