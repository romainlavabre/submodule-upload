package org.romainlavabre.upload.exception;

public class NotInitializedException extends RuntimeException {
    public NotInitializedException(){
        super("Upload not initialized, use UploadConfigurer for fix it");
    }
}
