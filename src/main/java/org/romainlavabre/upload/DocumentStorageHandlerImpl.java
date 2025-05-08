package org.romainlavabre.upload;

import org.springframework.stereotype.Service;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service( "documentStorageHandler" )
public class DocumentStorageHandlerImpl implements DocumentStorageHandler {

    protected final DocumentStorageHandler documentStorageHandlerOpenstack;


    public DocumentStorageHandlerImpl( DocumentStorageHandler documentStorageHandlerOpenstack ) {
        this.documentStorageHandlerOpenstack = documentStorageHandlerOpenstack;
    }


    @Override
    public boolean create( String path, byte[] bytes ) {
        return documentStorageHandlerOpenstack.create( path, bytes );
    }


    @Override
    public boolean remove( String path ) {
        return documentStorageHandlerOpenstack.remove( path );
    }


    @Override
    public byte[] getContent( String path ) {
        return documentStorageHandlerOpenstack.getContent( path );
    }
}
