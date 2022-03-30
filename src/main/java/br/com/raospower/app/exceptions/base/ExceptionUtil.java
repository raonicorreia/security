package br.com.raospower.app.exceptions.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class ExceptionUtil {

    private ExceptionUtil() {
        // Não deve ser instanciada.
    }

    public static void responseErro(HttpServletResponse response,
                                    Exception authException) throws IOException {
        ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(),
                authException.getMessage(), new Date(), null);
        OutputStream out = response.getOutputStream();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, error);
        out.flush();
    }
}
