package org.romainlavabre.upload;

import org.romainlavabre.upload.exception.NotInitializedException;

import java.util.ArrayList;
import java.util.List;

public class UploadConfigurer {
    private static UploadConfigurer INSTANCE;
    protected      UploadProvider   uploadProvider = UploadProvider.AWS_S3;
    protected      List< String >   bucketName     = new ArrayList();
    protected      String           username;
    protected      String           password;
    protected      String           endpoint;
    protected      String           projectName;
    protected      String           region;
    protected      String           temporaryUrlKey;


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


    protected List< String > getBucketNames() {
        return bucketName;
    }


    public String getFirstBucketName() {
        return bucketName.getFirst();
    }


    /**
     * Sets the AWS S3 bucket name for the UploadConfigurer.
     * Find it on <a href="https://s3.console.aws.amazon.com/s3/home">AWS</a>
     *
     * @param bucketName the name of the AWS S3 bucket to be set
     * @return the modified UploadConfigurer instance
     */
    public UploadConfigurer setBucketName( String bucketName ) {
        this.bucketName.add( bucketName );

        return this;
    }


    public String getUsername() {
        return username;
    }


    /**
     * Sets the AWS S3 public key for the UploadConfigurer.
     *
     * @param username the public key to be set for AWS S3
     * @return the modified UploadConfigurer instance with the AWS S3 public key set
     */
    public UploadConfigurer setUsername( String username ) {
        this.username = username;
        return this;
    }


    protected String getPassword() {
        return password;
    }


    /**
     * Sets the AWS S3 private key for the UploadConfigurer.
     *
     * @param password the private key to be set for AWS S3
     * @return the modified UploadConfigurer instance with the AWS S3 private key set
     */
    public UploadConfigurer setPassword( String password ) {
        this.password = password;

        return this;
    }


    public String getEndpoint() {
        return endpoint;
    }


    /**
     * Optional : Only if you use a provider other than AWS
     *
     * @param endpointUrl
     * @return
     */
    public UploadConfigurer setEndpoint( String endpointUrl ) {
        this.endpoint = endpointUrl;
        return this;
    }


    public String getProjectName() {
        return projectName;
    }


    public UploadConfigurer setProjectName( String projectName ) {
        this.projectName = projectName;
        return this;
    }


    public String getRegion() {
        return region;
    }


    public UploadConfigurer setRegion( String region ) {
        this.region = region;
        return this;
    }


    protected String getTemporaryUrlKey() {
        return temporaryUrlKey;
    }


    /**
     * Optional : secret key used to sign temporary (pre-signed) links.
     * <br/>
     * If provided and the storage account does not have a temporary URL key yet,
     * it will be set automatically on the first link generation.
     *
     * @param temporaryUrlKey the secret key used to sign temporary links
     * @return the modified UploadConfigurer instance
     */
    public UploadConfigurer setTemporaryUrlKey( String temporaryUrlKey ) {
        this.temporaryUrlKey = temporaryUrlKey;
        return this;
    }


    public void build() {
    }


    public enum UploadProvider {
        AWS_S3
    }
}
