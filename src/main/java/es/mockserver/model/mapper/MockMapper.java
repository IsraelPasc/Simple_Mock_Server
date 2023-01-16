package es.mockserver.model.mapper;

import es.mockserver.model.Mock;
import es.mockserver.web.dto.MockDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper class for mock objects.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = MockHeaderMapper.class)
public interface MockMapper {
    @Mapping(target="response", source="mockContent")
    @Mapping(target="responseStatus", source="httpStatus")
    @Mapping(target="responseHeaders", source="headers", qualifiedByName = "mockHeaderList")
    Mock map(MockDTO input);

    @Mapping(target="mockContent", source="response")
    @Mapping(target="httpStatus", source="responseStatus")
    @Mapping(target="headers", source="responseHeaders", qualifiedByName = "mockHeaderMap")
    MockDTO map(Mock input);
}
