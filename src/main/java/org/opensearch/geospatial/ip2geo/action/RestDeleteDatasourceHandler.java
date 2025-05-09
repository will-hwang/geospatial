/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.geospatial.ip2geo.action;

import static org.opensearch.geospatial.shared.URLBuilder.URL_DELIMITER;
import static org.opensearch.geospatial.shared.URLBuilder.getPluginURLPrefix;
import static org.opensearch.rest.RestRequest.Method.DELETE;

import java.util.List;
import java.util.Locale;

import org.opensearch.rest.BaseRestHandler;
import org.opensearch.rest.RestRequest;
import org.opensearch.rest.action.RestToXContentListener;
import org.opensearch.transport.client.node.NodeClient;

/**
 * Rest handler for Ip2Geo datasource delete request
 */
public class RestDeleteDatasourceHandler extends BaseRestHandler {
    private static final String ACTION_NAME = "ip2geo_datasource_delete";
    private static final String PARAMS_NAME = "name";

    @Override
    public String getName() {
        return ACTION_NAME;
    }

    @Override
    protected RestChannelConsumer prepareRequest(final RestRequest request, final NodeClient client) {
        final String name = request.param(PARAMS_NAME);
        final DeleteDatasourceRequest deleteDatasourceRequest = new DeleteDatasourceRequest(name);

        return channel -> client.executeLocally(
            DeleteDatasourceAction.INSTANCE,
            deleteDatasourceRequest,
            new RestToXContentListener<>(channel)
        );
    }

    @Override
    public List<Route> routes() {
        String path = String.join(URL_DELIMITER, getPluginURLPrefix(), String.format(Locale.ROOT, "ip2geo/datasource/{%s}", PARAMS_NAME));
        return List.of(new Route(DELETE, path));
    }
}
