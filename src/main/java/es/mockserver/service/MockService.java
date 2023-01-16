package es.mockserver.service;

import es.mockserver.model.Mock;
import es.mockserver.web.dto.MockDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MockService {
    MockDTO findByFilename(String fileName, String fileType);
    Mock findById(long id);
    List<Mock> findAll();
    Page<Mock> findAllPaged(Pageable pageData);
    Mock create(Mock mock);
    Mock update(Mock mock);
    void deleteById(long id);
}
