package es.mockserver.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.mockserver.error.exceptions.GenericException;
import es.mockserver.error.exceptions.MockNotFoundException;
import es.mockserver.model.Mock;
import es.mockserver.repository.MockRepository;
import es.mockserver.service.MockService;
import es.mockserver.web.dto.MockDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Service class for mock object operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MockServiceImpl implements MockService {

    private final MockRepository mockRepository;
    private final ObjectMapper objectMapper;

    @Override
    public MockDTO findByFilename(String fileName, String fileType) {
        String fileWithExt = fileName;
        if (fileName.contains("."))
            fileWithExt = fileName.split("\\.")[0];

        fileWithExt = fileWithExt + fileType;

        String localFolder = "./mocks/";
        String dockerFolder = "/tmp/mocks/";

        if (pathExists(localFolder) && pathExists(localFolder + fileWithExt))
            return readMockFile(localFolder + fileWithExt);
        else if (pathExists(dockerFolder) && pathExists(dockerFolder + fileWithExt))
            return readMockFile(dockerFolder + fileWithExt);
        else
            throw new MockNotFoundException();
    }

    @Override
    public Mock findById(long id) {
        return mockRepository.findById(id).orElseThrow(MockNotFoundException::new);
    }

    @Override
    public List<Mock> findAll() {
        List<Mock> result = mockRepository.findAll();
        if (!result.isEmpty())
            return result;
        else throw new MockNotFoundException("There isn't any mock registered in the database.");
    }

    @Override
    public Page<Mock> findAllPaged(Pageable pageData) {
        Page<Mock> result = mockRepository.findAll(pageData);
        if (!result.isEmpty())
            return result;
        else throw new MockNotFoundException("There isn't any mock registered in the database.");
    }

    @Override
    public Mock create(Mock mock) {
        return mockRepository.save(mock);
    }

    @Override
    public Mock update(Mock mock) {
        if (mockRepository.existsById(mock.getId()))
            return mockRepository.save(mock);
        else throw new MockNotFoundException();
    }

    @Override
    public void deleteById(long id) {
        if (mockRepository.existsById(id))
            mockRepository.deleteById(id);
        else throw new MockNotFoundException();
    }

    // Reads the contents of a mock file into a MockDTO object
    private MockDTO readMockFile(String filePath) {
        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
            return objectMapper.readValue(fileContent, MockDTO.class);
        } catch (IOException ex) {
            log.error("Couldn't read the file in path " + filePath + ". The error was " + ex.getMessage());
            throw new GenericException(ex.getLocalizedMessage());
        }
    }

    // Returns true if a folder or file exist
    private boolean pathExists(String path) {
        File pathToCheck = Paths.get(path).toFile();
        return pathToCheck.exists();
    }
}
