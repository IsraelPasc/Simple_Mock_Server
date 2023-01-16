package es.mockserver.model.mapper;

import es.mockserver.model.MockHeader;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mapper class for the http headers field on the mock classes.
 * Since we need to mock an object to a map, this is required.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MockHeaderMapper {
    @Named("mockHeaderList")
    default List<MockHeader> mapToMockHeaders(Map<String, String> headersMap) {
        List<MockHeader> mappedResult = new ArrayList<>();

        if (headersMap == null)
            return mappedResult;

        headersMap.forEach((key, value) -> mappedResult.add(new MockHeader(null, key, value)));
        return mappedResult;
    }

    @Named("mockHeaderMap")
    default Map<String, String> mockHeadersToMap(List<MockHeader> headersList) {
        Map<String, String> mappedResult = new HashMap<>();

        if (headersList == null)
            return mappedResult;

        headersList.forEach(header -> mappedResult.put(header.getHeaderKey(), header.getHeaderValue()));
        return mappedResult;
    }
}
