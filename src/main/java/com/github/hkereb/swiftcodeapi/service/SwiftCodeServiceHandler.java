package com.github.hkereb.swiftcodeapi.service;

import com.github.hkereb.swiftcodeapi.domain.SwiftCode;
import com.github.hkereb.swiftcodeapi.dto.request.SwiftCodeRequest;
import com.github.hkereb.swiftcodeapi.dto.response.MessageResponse;
import com.github.hkereb.swiftcodeapi.dto.response.SwiftCodeByCountryResponse;
import com.github.hkereb.swiftcodeapi.dto.response.SwiftCodeDetailResponse;
import com.github.hkereb.swiftcodeapi.exceptions.InvalidInputException;
import com.github.hkereb.swiftcodeapi.exceptions.EntityNotFoundException;
import com.github.hkereb.swiftcodeapi.exceptions.MissingPathVariableException;
import com.github.hkereb.swiftcodeapi.repository.SwiftCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SwiftCodeServiceHandler implements SwiftCodeService {

    private final SwiftCodeRepository swiftCodeRepository;
    private final SwiftCodeMapper swiftCodeMapper;

    @Autowired
    public SwiftCodeServiceHandler(SwiftCodeRepository swiftCodeRepository, SwiftCodeMapper swiftCodeMapper) {
        this.swiftCodeRepository = swiftCodeRepository;
        this.swiftCodeMapper = swiftCodeMapper;
    }

    @Override
    public MessageResponse upsert(SwiftCodeRequest request) {
        SwiftCode entity = swiftCodeMapper.mapToEntity(request);
        swiftCodeRepository.save(entity);
        return new MessageResponse("Swift code added successfully.");
    }
    @Override
    public SwiftCodeDetailResponse getBySwiftCode(String swiftCode) {
        SwiftCode entity = swiftCodeRepository.getBySwiftCode(swiftCode);

        if (entity == null) {
            throw new EntityNotFoundException("No swift code found: ", swiftCode);
        }

        if (Boolean.TRUE.equals(entity.getIsHeadquarter())) {
            List<SwiftCode> branches = swiftCodeRepository.getBySwiftCodeStartingWithAndIsHeadquarterFalse(entity.getSwiftCode().substring(0, 8));
            return swiftCodeMapper.mapToResponse(entity, branches);
        } else {
            return swiftCodeMapper.mapToResponse(entity);
        }
    }
    @Override
    public SwiftCodeByCountryResponse getByCountryISO2(String iso2Code) {
        if (iso2Code.length() != 2) {
            throw new InvalidInputException("Country ISO2 code must be exactly 2 characters.");
        }

        List<SwiftCode> entities = swiftCodeRepository.getByCountryISO2(iso2Code);

        if (entities.isEmpty()) {
            throw new EntityNotFoundException("No SWIFT codes found for country", iso2Code);
        }

        String countryISO2 = entities.getFirst().getCountryISO2();
        String countryName = entities.getFirst().getCountryName();

        return swiftCodeMapper.mapToByCountryResponse(countryISO2, countryName, entities);
    }
    @Override
    public MessageResponse deleteBySwiftCode(String swiftCode) {
        SwiftCode entity = swiftCodeRepository.getBySwiftCode(swiftCode);
        if (entity == null) {
            throw new EntityNotFoundException("No SWIFT code found", swiftCode);
        }

        swiftCodeRepository.delete(entity);
        return new MessageResponse("Swift code deleted successfully.");
    }

    @Override
    public boolean isDatabaseInitialized() {
        return swiftCodeRepository.count() > 0;
    }

    private boolean isNullOrBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
