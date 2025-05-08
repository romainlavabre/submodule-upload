package org.romainlavabre.upload;

import org.jclouds.ContextBuilder;
import org.jclouds.io.Payload;
import org.jclouds.openstack.keystone.config.KeystoneProperties;
import org.jclouds.openstack.swift.v1.SwiftApi;
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

    protected SwiftApi client;


    @Override
    public boolean create( String path, byte[] bytes ) {
        connect();

        ObjectApi objectApi = client.getObjectApi( UploadConfigurer.get().getRegion(), UploadConfigurer.get().getBucketName() );
        Payload   payload   = newByteSourcePayload( wrap( bytes ) );

        objectApi.put( path, payload );

        return true;
    }


    @Override
    public boolean remove( String path ) {
        connect();

        client.getObjectApi( UploadConfigurer.get().getRegion(), UploadConfigurer.get().getBucketName() ).delete( path );

        return true;
    }


    @Override
    public byte[] getContent( String path ) {
        connect();

        try {
            return client.getObjectApi( UploadConfigurer.get().getRegion(), UploadConfigurer.get().getBucketName() ).get( path ).getPayload().openStream().readAllBytes();
        } catch ( Throwable e ) {
            throw new HttpInternalServerErrorException( "UNABLE_TO_READ_FILE" );
        }
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
