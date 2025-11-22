package com.clinicaODontologica.UP.Exception;

public class UsuarioExistenteException extends ResourceExistingException {
    public UsuarioExistenteException(String message) {
        super(message);
    }
}
