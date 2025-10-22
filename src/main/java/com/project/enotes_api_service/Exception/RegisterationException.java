package com.project.enotes_api_service.Exception;


    public class RegisterationException extends RuntimeException {


        public RegisterationException(String message,Throwable cause) {
            super(message,cause);
        }

        public RegisterationException(String message) {
            super(message);
        }
    }
