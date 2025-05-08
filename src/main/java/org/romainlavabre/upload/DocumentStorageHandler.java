package org.romainlavabre.upload;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface DocumentStorageHandler {


    /**
     * Create a file on remote server
     *
     * @param path  Destination ON remote server
     * @param bytes Array of bytes of file
     * @return TRUE is file uploaded
     */
    boolean create( String path, byte[] bytes );


    /**
     * Remove file on remote server
     *
     * @param path Location of file
     * @return TRUE if file removed
     */
    boolean remove( String path );


    /**
     * @param path Location on server
     * @return Array of byte in server
     */
    byte[] getContent( String path );
}
