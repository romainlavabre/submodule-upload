package org.romainlavabre.upload;

import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.ByteBuffer;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service( "documentStorageHandler" )
public class DocumentStorageHandlerImpl implements DocumentStorageHandler {

    protected final DocumentStorageHandler documentStorageHandlerAwsS3;


    public DocumentStorageHandlerImpl( DocumentStorageHandler documentStorageHandlerAwsS3 ) {
        this.documentStorageHandlerAwsS3 = documentStorageHandlerAwsS3;
    }


    @Override
    public boolean create( String path, File file ) {
        return documentStorageHandlerAwsS3.create( path, file );
    }


    @Override
    public boolean create( String path, ByteBuffer byteBuffer ) {
        return documentStorageHandlerAwsS3.create( path, byteBuffer );
    }


    @Override
    public boolean create( String path, byte[] bytes ) {
        return documentStorageHandlerAwsS3.create( path, bytes );
    }


    @Override
    public boolean remove( String path ) {
        return documentStorageHandlerAwsS3.remove( path );
    }


    @Override
    public byte[] getContent( String path ) {
        return documentStorageHandlerAwsS3.getContent( path );
    }


    @Override
    public String getUrl( String path, Integer duration ) {
        return documentStorageHandlerAwsS3.getUrl( path, duration );
    }


    @Override
    public String getUrl( String path ) {
        return documentStorageHandlerAwsS3.getUrl( path );
    }
}
