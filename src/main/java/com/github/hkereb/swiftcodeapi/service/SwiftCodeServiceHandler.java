package com.github.hkereb.swiftcodeapi.service;

import com.github.hkereb.swiftcodeapi.domain.SwiftCode;
import com.github.hkereb.swiftcodeapi.dto.request.SwiftCodeRequest;
import com.github.hkereb.swiftcodeapi.dto.response.MessageResponse;
import com.github.hkereb.swiftcodeapi.dto.response.SwiftCodeByCountryResponse;
import com.github.hkereb.swiftcodeapi.dto.response.SwiftCodeDetailResponse;
import com.github.hkereb.swiftcodeapi.dto.response.SwiftCodePartialResponse;
import com.github.hkereb.swiftcodeapi.exceptions.RecordNotFoundException;
import com.github.hkereb.swiftcodeapi.repository.SwiftCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SwiftCodeServiceHandler implements SwiftCodeService {

    @Autowired
    private SwiftCodeRepository swiftCodeRepository;

    @Override
    public MessageResponse upsert(SwiftCodeRequest request) {
        SwiftCode entity = SwiftCodeMapper.mapToEntity(request);
        swiftCodeRepository.save(entity);
        return new MessageResponse("Swift code added successfully.");
    }
    @Override
    public SwiftCodeDetailResponse getBySwiftCode(String swiftCode) {
        SwiftCode entity = swiftCodeRepository.getBySwiftCode(swiftCode);
        if (entity == null) {
            throw new RecordNotFoundException(swiftCode);
        }

        if (Boolean.TRUE.equals(entity.getIsHeadquarter())) {
            List<SwiftCode> branches = swiftCodeRepository.getBySwiftCodeStartingWithAndIsHeadquarterFalse(entity.getSwiftCode().substring(0, 8));
            return SwiftCodeMapper.mapToResponse(entity, branches);
        } else {
            return SwiftCodeMapper.mapToResponse(entity);
        }
    }
    @Override
    public SwiftCodeByCountryResponse getByCountryISO2(String iso2Code) {
        List<SwiftCode> entities = swiftCodeRepository.getByCountryISO2(iso2Code);
        String countryISO2 = entities.getFirst().getCountryISO2();
        String countryName = entities.getFirst().getCountryName();

        return SwiftCodeMapper.mapToByCountryResponse(countryISO2, countryName, entities);
    }
    @Override
    public MessageResponse deleteBySwiftCode(String swiftCode) {
        SwiftCode entity = swiftCodeRepository.getBySwiftCode(swiftCode);
        if (entity == null) {
            throw new RecordNotFoundException(swiftCode);
        }

        swiftCodeRepository.delete(entity);
        return new MessageResponse("Swift code deleted successfully.");
    }

}
