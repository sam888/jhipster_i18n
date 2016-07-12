package org.jhipster.i18n.web.rest.util;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author Samuel Huang
 * Created on 21-Jun-16.
 */
public class UrlUtil {

    public static URI getContextURI( HttpServletRequest request ) throws URISyntaxException {
        URI requestUri = new URI( request.getRequestURL().toString() );
        URI contextUri = new URI( requestUri.getScheme(),
                requestUri.getAuthority(), request.getContextPath(), null, null);

        /*
        System.out.println("request.getRequestURL(): " + request.getRequestURL());
        System.out.println("requestUri.getScheme(): " + requestUri.getScheme());
        System.out.println("requestUri.getAuthority(): " + requestUri.getAuthority());
        System.out.println("request.getContextPath(): " + request.getContextPath());
        */
        return contextUri;
    }

    public static String getBaseURL(HttpServletRequest request) throws MalformedURLException {
        URL requestURL = new URL(request.getRequestURL().toString());
        String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
        return requestURL.getProtocol() + "://" + requestURL.getHost() + port;
    }
}
