/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.azure.eventhubs;

import java.util.Map;

import com.azure.identity.DefaultAzureCredential;
import org.apache.camel.Endpoint;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.annotations.Component;
import org.apache.camel.support.DefaultComponent;
import org.apache.camel.util.ObjectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Azure EventHubs component
 */
@Component("azure-eventhubs")
public class EventHubsComponent extends DefaultComponent {

    private static final Logger LOG = LoggerFactory.getLogger(EventHubsComponent.class);

    @Metadata
    private EventHubsConfiguration configuration = new EventHubsConfiguration();

    public EventHubsComponent() {
    }

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        final EventHubsConfiguration configuration = this.configuration.copy();

        final EventHubsEndpoint endpoint = new EventHubsEndpoint(uri, this, configuration);
        setProperties(endpoint, parameters);

        // if TokenCredential is set or we don't have client nor connectionString, we then check and use remaining
        if (isTokenCredentialSet(configuration)
                || !isProducerAsyncClientSet(configuration) && !isConnectionStringSet(configuration)) {
            checkAndSetNamespaceAndHubName(configuration, remaining);
        }

        // ensure we use default credential type if not configured
        if (endpoint.getConfiguration().getTokenCredential() == null) {
            if (endpoint.getConfiguration().getCredentialType() == null) {
                endpoint.getConfiguration().setCredentialType(CredentialType.CONNECTION_STRING);
            }
        } else {
            boolean azure = endpoint.getConfiguration().getTokenCredential() instanceof DefaultAzureCredential;
            endpoint.getConfiguration()
                    .setCredentialType(azure ? CredentialType.AZURE_IDENTITY : CredentialType.TOKEN_CREDENTIAL);
        }

        validateConfigurations(configuration);

        return endpoint;
    }

    /**
     * The component configurations
     */
    public EventHubsConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(EventHubsConfiguration configuration) {
        this.configuration = configuration;
    }

    private void validateConfigurations(final EventHubsConfiguration configuration) {
        if (!isAccessKeyAndAccessNameSet(configuration) && !isProducerAsyncClientSet(configuration)
                && !isConnectionStringSet(configuration) && !isTokenCredentialSet(configuration)
                && !isAzureIdentitySet(configuration)) {
            throw new IllegalArgumentException(
                    "Azure EventHubs SharedAccessName/SharedAccessKey, ProducerAsyncClient, ConnectionString or TokenCredential must be specified.");
        }
    }

    private boolean isAccessKeyAndAccessNameSet(final EventHubsConfiguration configuration) {
        return ObjectHelper.isNotEmpty(configuration.getSharedAccessName())
                && ObjectHelper.isNotEmpty(configuration.getSharedAccessKey());
    }

    private boolean isConnectionStringSet(final EventHubsConfiguration configuration) {
        return ObjectHelper.isNotEmpty(configuration.getConnectionString());
    }

    private boolean isTokenCredentialSet(final EventHubsConfiguration configuration) {
        return ObjectHelper.isNotEmpty(configuration.getTokenCredential());
    }

    private boolean isProducerAsyncClientSet(final EventHubsConfiguration configuration) {
        return ObjectHelper.isNotEmpty(configuration.getProducerAsyncClient());
    }

    private boolean isAzureIdentitySet(final EventHubsConfiguration configuration) {
        return ObjectHelper.isNotEmpty(configuration.getCredentialType())
                && configuration.getCredentialType().equals(CredentialType.AZURE_IDENTITY);
    }

    private void checkAndSetNamespaceAndHubName(final EventHubsConfiguration configuration, final String remaining) {
        // only set if clients are empty and remaining exists
        final String errorMessage = "ConnectionString, ProducerAsyncClient or Namespace and EventHub name must be set";
        if (ObjectHelper.isEmpty(remaining)) {
            throw new IllegalArgumentException(errorMessage);
        }

        final String[] parts = remaining.split("/");

        if (parts.length != 2) {
            throw new IllegalArgumentException(errorMessage);
        }
        configuration.setNamespace(parts[0]);
        configuration.setEventHubName(parts[1]);
    }

}
