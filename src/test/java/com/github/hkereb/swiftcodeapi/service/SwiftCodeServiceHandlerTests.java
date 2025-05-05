package com.github.hkereb.swiftcodeapi.service;

import com.github.hkereb.swiftcodeapi.domain.SwiftCode;
import com.github.hkereb.swiftcodeapi.dto.request.SwiftCodeRequest;
import com.github.hkereb.swiftcodeapi.dto.response.MessageResponse;
import com.github.hkereb.swiftcodeapi.dto.response.SwiftCodeByCountryResponse;
import com.github.hkereb.swiftcodeapi.dto.response.SwiftCodeDetailResponse;
import com.github.hkereb.swiftcodeapi.exceptions.EntityNotFoundException;
import com.github.hkereb.swiftcodeapi.exceptions.MissingPathVariableException;
import com.github.hkereb.swiftcodeapi.repository.SwiftCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SwiftCodeServiceHandlerTests {
    private SwiftCodeRepository repository;
    private SwiftCodeServiceHandler service;

    @BeforeEach
    void setUp() {
        repository = mock(SwiftCodeRepository.class);
        SwiftCodeMapper mapper = new SwiftCodeMapper();
        service = new SwiftCodeServiceHandler(repository, mapper);
    }

    @Test
    void upsert_shouldSaveMappedEntity() throws MissingPathVariableException {
        SwiftCodeRequest request = new SwiftCodeRequest(
                "TEST ADDRESS",
                "TEST BANK",
                "TE",
                "TEST COUNTRY",
                true,
                "TESTCODEXXX"
        );

        MessageResponse response = service.upsert(request);

        ArgumentCaptor<SwiftCode> captor = ArgumentCaptor.forClass(SwiftCode.class);
        verify(repository).save(captor.capture());
        assertEquals("TESTCODEXXX", captor.getValue().getSwiftCode());
        assertEquals("Swift code added successfully.", response.getMessage());
    }

    @Test
    void getBySwiftCode_shouldReturnHeadquarterWithBranches() {
        SwiftCode hq = createSwiftCode("BANKPLPWXXX", true);
        SwiftCode branch = createSwiftCode("BANKPLPW001", false);

        when(repository.getBySwiftCode("BANKPLPWXXX")).thenReturn(hq);
        when(repository.getBySwiftCodeStartingWithAndIsHeadquarterFalse("BANKPLPW"))
                .thenReturn(List.of(branch));

        SwiftCodeDetailResponse response = service.getBySwiftCode("BANKPLPWXXX");

        assertEquals("BANKPLPWXXX", response.getSwiftCode());
        assertEquals(1, response.getBranches().size());
        assertEquals("BANKPLPW001", response.getBranches().getFirst().getSwiftCode());
    }

    @Test
    void getBySwiftCode_shouldReturnBranchWithoutBranches() {
        SwiftCode branch = createSwiftCode("BANKPLPW001", false);
        when(repository.getBySwiftCode("BANKPLPW001")).thenReturn(branch);

        SwiftCodeDetailResponse response = service.getBySwiftCode("BANKPLPW001");

        assertEquals("BANKPLPW001", response.getSwiftCode());
        assertTrue(response.getBranches().isEmpty());
    }

    @Test
    void getBySwiftCode_shouldThrowWhenNotFound() {
        when(repository.getBySwiftCode("UNKNOWN")).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> service.getBySwiftCode("UNKNOWN"));
    }

    @Test
    void getByCountryISO2_shouldReturnCountryResponse() {
        SwiftCode swift = createSwiftCode("BANKPLPWXXX", true);
        when(repository.getByCountryISO2("PL")).thenReturn(List.of(swift));

        SwiftCodeByCountryResponse response = service.getByCountryISO2("PL");

        assertEquals("PL", response.getCountryISO2());
        assertEquals("Poland", response.getCountryName());
        assertEquals(1, response.getSwiftCodes().size());
    }

    @Test
    void getByCountryISO2_shouldFailOnEmptyList() {
        when(repository.getByCountryISO2("PL")).thenReturn(Collections.emptyList());

        // brak zabezpieczenia w kodzie → może rzucić NoSuchElementException
        assertThrows(EntityNotFoundException.class, () -> service.getByCountryISO2("PL"));
    }

    @Test
    void deleteBySwiftCode_shouldDeleteIfExists() {
        SwiftCode swift = createSwiftCode("BANKPLPWXXX", true);
        when(repository.getBySwiftCode("BANKPLPWXXX")).thenReturn(swift);

        MessageResponse response = service.deleteBySwiftCode("BANKPLPWXXX");

        verify(repository).delete(swift);
        assertEquals("Swift code deleted successfully.", response.getMessage());
    }

    @Test
    void deleteBySwiftCode_shouldThrowIfNotFound() {
        when(repository.getBySwiftCode("UNKNOWN")).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> service.deleteBySwiftCode("UNKNOWN"));
    }

    private SwiftCode createSwiftCode(String code, boolean isHQ) {
        SwiftCode swift = new SwiftCode();
        swift.setSwiftCode(code);
        swift.setBankName("Test Bank");
        swift.setBankAddress("Warsaw");
        swift.setCountryISO2("PL");
        swift.setCountryName("Poland");
        swift.setIsHeadquarter(isHQ);
        return swift;
    }
}
