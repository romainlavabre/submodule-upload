package org.romainlavabre.upload;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface DocumentStorageHandler {


    /**
     * Create a file on remote server
     * <br/>
     * Only if you are using a single bucket, otherwise use create( String bucket, String path, byte[] bytes )
     *
     * @param path  Destination ON remote server
     * @param bytes Array of bytes of file
     * @return TRUE is file uploaded
     */
    boolean create( String path, byte[] bytes );


    /**
     * Create a file on remote server
     *
     * @param bucket Bucket name
     * @param path   Destination ON remote server
     * @param bytes  Array of bytes of file
     * @return TRUE is file uploaded
     */
    boolean create( String bucket, String path, byte[] bytes );


    /**
     * Remove file on remote server
     * <br/>
     * Only if you are using a single bucket, otherwise use remove( String bucket, String path)
     *
     * @param path Location of file
     * @return TRUE if file removed
     */
    boolean remove( String path );


    /**
     * Remove file on remote server
     *
     * @param bucket Bucket name
     * @param path   Location of file
     * @return TRUE if file removed
     */
    boolean remove( String bucket, String path );


    /**
     * Only if you are using a single bucket, otherwise use getContent( String bucket, String path )
     *
     * @param path Location on server
     * @return Array of byte in server
     */
    byte[] getContent( String path );


    /**
     *
     * @param bucket Bucket name
     * @param path   Location on server
     * @return Array of byte in server
     */
    byte[] getContent( String bucket, String path );
}
