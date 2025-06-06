/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.component.qdrant;

import javax.annotation.processing.Generated;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.spi.ExtendedPropertyConfigurerGetter;
import org.apache.camel.spi.PropertyConfigurerGetter;
import org.apache.camel.spi.ConfigurerStrategy;
import org.apache.camel.spi.GeneratedPropertyConfigurer;
import org.apache.camel.util.CaseInsensitiveMap;
import org.apache.camel.support.component.PropertyConfigurerSupport;

/**
 * Generated by camel build tools - do NOT edit this file!
 */
@Generated("org.apache.camel.maven.packaging.EndpointSchemaGeneratorMojo")
@SuppressWarnings("unchecked")
public class QdrantEndpointConfigurer extends PropertyConfigurerSupport implements GeneratedPropertyConfigurer, PropertyConfigurerGetter {

    @Override
    public boolean configure(CamelContext camelContext, Object obj, String name, Object value, boolean ignoreCase) {
        QdrantEndpoint target = (QdrantEndpoint) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "apikey":
        case "apiKey": target.getConfiguration().setApiKey(property(camelContext, java.lang.String.class, value)); return true;
        case "filter": target.getConfiguration().setFilter(property(camelContext, io.qdrant.client.grpc.Points.Filter.class, value)); return true;
        case "host": target.getConfiguration().setHost(property(camelContext, java.lang.String.class, value)); return true;
        case "lazystartproducer":
        case "lazyStartProducer": target.setLazyStartProducer(property(camelContext, boolean.class, value)); return true;
        case "maxresults":
        case "maxResults": target.getConfiguration().setMaxResults(property(camelContext, int.class, value)); return true;
        case "port": target.getConfiguration().setPort(property(camelContext, int.class, value)); return true;
        case "timeout": target.getConfiguration().setTimeout(property(camelContext, java.time.Duration.class, value)); return true;
        case "tls": target.getConfiguration().setTls(property(camelContext, boolean.class, value)); return true;
        default: return false;
        }
    }

    @Override
    public String[] getAutowiredNames() {
        return new String[]{"filter"};
    }

    @Override
    public Class<?> getOptionType(String name, boolean ignoreCase) {
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "apikey":
        case "apiKey": return java.lang.String.class;
        case "filter": return io.qdrant.client.grpc.Points.Filter.class;
        case "host": return java.lang.String.class;
        case "lazystartproducer":
        case "lazyStartProducer": return boolean.class;
        case "maxresults":
        case "maxResults": return int.class;
        case "port": return int.class;
        case "timeout": return java.time.Duration.class;
        case "tls": return boolean.class;
        default: return null;
        }
    }

    @Override
    public Object getOptionValue(Object obj, String name, boolean ignoreCase) {
        QdrantEndpoint target = (QdrantEndpoint) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "apikey":
        case "apiKey": return target.getConfiguration().getApiKey();
        case "filter": return target.getConfiguration().getFilter();
        case "host": return target.getConfiguration().getHost();
        case "lazystartproducer":
        case "lazyStartProducer": return target.isLazyStartProducer();
        case "maxresults":
        case "maxResults": return target.getConfiguration().getMaxResults();
        case "port": return target.getConfiguration().getPort();
        case "timeout": return target.getConfiguration().getTimeout();
        case "tls": return target.getConfiguration().isTls();
        default: return null;
        }
    }
}

