package es.mockserver.web.controller;

import es.mockserver.error.exceptions.GenericException;
import es.mockserver.model.Mock;
import es.mockserver.model.mapper.MockMapper;
import es.mockserver.service.MockService;
import es.mockserver.web.dto.MockDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class that exposes the API endpoints.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mock")
public class MockController {

    private final MockService mockService;
    private final MockMapper mockMapper;

    @Operation(summary = "Find mocks in the database", description = "Find mocks in the temporary database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "404", description = "Entries not found")
    })
    @GetMapping(value="/db", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MockDTO>> getAllMocks() {
        List<Mock> result = mockService.findAll();
        List<MockDTO> mappedResult = result.stream().map(mockMapper::map).collect(Collectors.toList());
        return ResponseEntity.ok(mappedResult);
    }

    @Operation(summary = "Find mocks in the database",
            description = "Find mocks in the temporary database with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Entries not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value="/db/paging", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<MockDTO>> getPagedMocks(Pageable pageData) {
        Page<Mock> result = mockService.findAllPaged(pageData);
        Page<MockDTO> mappedResult = new PageImpl<>(result.stream().map(mockMapper::map).collect(Collectors.toList()));
        return ResponseEntity.ok(mappedResult);
    }

    @Operation(summary = "Creates a new mock",
            description = "Creates a new mock in the temporary database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value="/db", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MockDTO> createNewMock(@Valid @RequestBody MockDTO input) {
        Mock mappedInput = mockMapper.map(input);
        return ResponseEntity.ok(mockMapper.map(mockService.create(mappedInput)));
    }

    @Operation(summary = "Updates a mock",
            description = "Updates a mock from the temporary database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(value="/db/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MockDTO> updateMock(@PathVariable String id, @Valid @RequestBody MockDTO input) {
        Mock mockToUpdate = mockMapper.map(input);

        try {
            mockToUpdate.setId(Long.parseLong(id));
        } catch(NumberFormatException ex) {
            throw new GenericException("The id to update must be a number. Error = " + ex.getLocalizedMessage());
        }

        return ResponseEntity.ok(mockMapper.map(mockService.update(mockToUpdate)));
    }

    @Operation(summary = "Deletes a mock",
            description = "Deletes a mock from the temporary database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping(value="/db/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteMock(@PathVariable String id) {
        try {
            mockService.deleteById(Long.parseLong(id));
        } catch(NumberFormatException ex) {
            throw new GenericException("The id to delete must be a number. Error = " + ex.getLocalizedMessage());
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Returns a mocked response",
            description = "Returns a mocked response from the given id or filename")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "***", description = "Mocked response, can be any value"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> mockGetById(@PathVariable String id) {
        return getMockedResponse(id);
    }

    @Operation(summary = "Returns a mocked response",
            description = "Returns a mocked response from the given id or filename")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "***", description = "Mocked response, can be any value"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> mockPostById(@PathVariable String id) {
        return getMockedResponse(id);
    }

    @Operation(summary = "Returns a mocked response",
            description = "Returns a mocked response from the given id or filename")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "***", description = "Mocked response, can be any value"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> mockPatchById(@PathVariable String id) {
        return getMockedResponse(id);
    }

    @Operation(summary = "Returns a mocked response",
            description = "Returns a mocked response from the given id or filename")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "***", description = "Mocked response, can be any value"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> mockPutById(@PathVariable String id) {
        return getMockedResponse(id);
    }

    @Operation(summary = "Returns a mocked response",
            description = "Returns a mocked response from the given id or filename")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "***", description = "Mocked response, can be any value"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> mockDeleteById(@PathVariable String id) {
        return getMockedResponse(id);
    }

    private ResponseEntity<String> getMockedResponse(String id) {
        MockDTO result = null;

        try {
            result = mockMapper.map(mockService.findById(Long.parseLong(id)));
        } catch (NumberFormatException ex) {
            log.info("Id passed wasn't a number, looking for mock files instead...");
        }

        if (result == null)
            result = mockService.findByFilename(id, ".json");

        HttpHeaders responseHeaders = new HttpHeaders();

        if (result.getHeaders() != null && !result.getHeaders().keySet().isEmpty())
            result.getHeaders().forEach(responseHeaders::add);

        return ResponseEntity.status(result.getHttpStatus()).headers(responseHeaders).body(result.getMockContent());
    }
}
