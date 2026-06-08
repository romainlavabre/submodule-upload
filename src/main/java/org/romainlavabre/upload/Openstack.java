package org.romainlavabre.upload;

import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobRequestSigner;
import org.jclouds.io.Payload;
import org.jclouds.openstack.keystone.config.KeystoneProperties;
import org.jclouds.openstack.swift.v1.SwiftApi;
import org.jclouds.openstack.swift.v1.blobstore.RegionScopedBlobStoreContext;
import org.jclouds.openstack.swift.v1.features.AccountApi;
import org.jclouds.openstack.swift.v1.features.ObjectApi;
import org.romainlavabre.exception.HttpInternalServerErrorException;
import org.springframework.stereotype.Service;

import java.util.Properties;

import static com.google.common.io.ByteSource.wrap;
import static org.jclouds.io.Payloads.newByteSourcePayload;

/**
 * This class implement the fucking swift API. I curse the sons of a bitches who designed this shit.
 * I curse the developers of Openstack4J who fucked my holiday.
 * I curse infomaniak who claims his shit is S3 compatible.
 * I curse AWS! Because!
 * Kisses
 *
 * @author Romain Lavabre <romain.lavabre@proton.me>
 */
@Service( "documentStorageHandlerOpenstack" )
public class Openstack implements DocumentStorageHandler {
    private static final String KEYSTONE_VERSION = "3";
    private static final String OS_PROVIDER      = "openstack-swift";

    protected SwiftApi                     client;
    protected RegionScopedBlobStoreContext blobStoreContext;


    @Override
    public boolean create( String path, byte[] bytes ) {
        connect();

        ObjectApi objectApi = client.getObjectApi( UploadConfigurer.get().getRegion(), UploadConfigurer.get().getFirstBucketName() );
        Payload   payload   = newByteSourcePayload( wrap( bytes ) );

        objectApi.put( path, payload );

        return true;
    }


    @Override
    public boolean create( String bucket, String path, byte[] bytes ) {
        connect();

        ObjectApi objectApi = client.getObjectApi( UploadConfigurer.get().getRegion(), bucket );
        Payload   payload   = newByteSourcePayload( wrap( bytes ) );

        objectApi.put( path, payload );

        return true;
    }


    @Override
    public boolean remove( String path ) {
        connect();

        client.getObjectApi( UploadConfigurer.get().getRegion(), UploadConfigurer.get().getFirstBucketName() ).delete( path );

        return true;
    }


    @Override
    public boolean remove( String bucket, String path ) {
        connect();

        client.getObjectApi( UploadConfigurer.get().getRegion(), bucket ).delete( path );

        return true;
    }


    @Override
    public byte[] getContent( String path ) {
        connect();

        try {
            return client.getObjectApi( UploadConfigurer.get().getRegion(), UploadConfigurer.get().getFirstBucketName() ).get( path ).getPayload().openStream().readAllBytes();
        } catch ( Throwable e ) {
            throw new HttpInternalServerErrorException( "UNABLE_TO_READ_FILE" );
        }
    }


    @Override
    public byte[] getContent( String bucket, String path ) {
        connect();

        try {
            return client.getObjectApi( UploadConfigurer.get().getRegion(), bucket ).get( path ).getPayload().openStream().readAllBytes();
        } catch ( Throwable e ) {
            throw new HttpInternalServerErrorException( "UNABLE_TO_READ_FILE" );
        }
    }


    @Override
    public String getTemporaryLink( String path, long expirationInSeconds ) {
        return getTemporaryLink( UploadConfigurer.get().getFirstBucketName(), path, expirationInSeconds );
    }


    @Override
    public String getTemporaryLink( String bucket, String path, long expirationInSeconds ) {
        String configuredKey = UploadConfigurer.get().getTemporaryUrlKey();

        if ( configuredKey == null ) {
            return null;
        }

        connect();
        connectBlobStore();
        ensureTemporaryUrlKey();

        try {
            BlobRequestSigner signer = blobStoreContext.getSigner( UploadConfigurer.get().getRegion() );

            return signer.signGetBlob( bucket, path, expirationInSeconds ).getEndpoint().toString() + "&inline";
        } catch ( Throwable e ) {
            throw new HttpInternalServerErrorException( "UNABLE_TO_GENERATE_TEMPORARY_LINK" );
        }
    }


    /**
     * The temporary URL signature relies on a secret key stored on the account.
     * If a key is configured and the account does not have one yet, we set it,
     * so that link generation works out of the box.
     */
    protected void ensureTemporaryUrlKey() {
        String configuredKey = UploadConfigurer.get().getTemporaryUrlKey();

        if ( configuredKey == null ) {
            return;
        }

        AccountApi accountApi = client.getAccountApi( UploadConfigurer.get().getRegion() );

        if ( !accountApi.get().getTemporaryUrlKey().isPresent() ) {
            accountApi.updateTemporaryUrlKey( configuredKey );
        }
    }


    protected RegionScopedBlobStoreContext connectBlobStore() {
        if ( blobStoreContext != null ) {
            return blobStoreContext;
        }

        final Properties overrides = new Properties();
        overrides.put( KeystoneProperties.KEYSTONE_VERSION, "3" );
        overrides.put( KeystoneProperties.SCOPE, "project:" + UploadConfigurer.get().getProjectName() );

        blobStoreContext = ContextBuilder.newBuilder( OS_PROVIDER )
                .endpoint( UploadConfigurer.get().getEndpoint() )
                .credentials( "Default:" + UploadConfigurer.get().getUsername(), UploadConfigurer.get().getPassword() )
                .overrides( overrides )
                .buildView( RegionScopedBlobStoreContext.class );

        return blobStoreContext;
    }


    protected SwiftApi connect() {
        if ( client != null ) {
            return client;
        }


        final Properties overrides = new Properties();
        overrides.put( KeystoneProperties.KEYSTONE_VERSION, "3" );
        overrides.put( KeystoneProperties.SCOPE, "project:" + UploadConfigurer.get().getProjectName() );

        client = ContextBuilder.newBuilder( OS_PROVIDER )
                .endpoint( UploadConfigurer.get().getEndpoint() )
                .credentials( "Default:" + UploadConfigurer.get().getUsername(), UploadConfigurer.get().getPassword() )
                .overrides( overrides )
                .buildApi( SwiftApi.class );

        return client;
    }
}
