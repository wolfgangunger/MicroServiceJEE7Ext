package com.ntt.ms.business.base.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 
 */
public class SwaggerServlet extends HttpServlet {

    private static final String SWAGGER_JSON = "swagger.json";
    private static final long serialVersionUID = 6543218282335468456L;
    private String swaggerString = "N/A";

    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        try {
            swaggerString = loadSwagger(SWAGGER_JSON);
        } catch (IOException | URISyntaxException e) {
            throw new ServletException("swagger configuration could not be loaded", e);
        }
    }

    private String loadSwagger(final String file) throws IOException, URISyntaxException {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(file);
        if (url == null) {
            throw new IOException("The resource <" + file + "> could not be found in classpath");
        }
        return new String(Files.readAllBytes(Paths.get(url.toURI())), Charset.defaultCharset());
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        String host = request.getParameter("host");
        if (host == null) {
            host = getHostname(request);
        }

        String port = request.getParameter("port");
        if (port == null) {
            port = String.valueOf(request.getLocalPort());
        }
        response.getWriter().write(swaggerString.replace("[domain]", host).replace("[port]", port));
    }

    private String getHostname(final HttpServletRequest request) {
        final String url = request.getRequestURL().toString();
        final URI uri = URI.create(url);
        final String host = uri.getHost();
        return host;
    }
}
