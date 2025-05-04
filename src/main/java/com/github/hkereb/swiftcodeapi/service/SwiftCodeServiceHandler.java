package com.github.hkereb.swiftcodeapi.service;

import com.github.hkereb.swiftcodeapi.domain.SwiftCode;
import com.github.hkereb.swiftcodeapi.dto.request.SwiftCodeRequest;
import com.github.hkereb.swiftcodeapi.dto.response.MessageResponse;
import com.github.hkereb.swiftcodeapi.dto.response.SwiftCodeResponse;
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
        return new MessageResponse("New SWIFT code has been added.");
    }
    @Override
    public SwiftCodeResponse getBySwiftCode(String swiftCode) {
        SwiftCode entity = swiftCodeRepository.getBySwiftCode(swiftCode);
        if (entity == null) return null;

        if (Boolean.TRUE.equals(entity.getIsHeadquarter())) {
            List<SwiftCode> branches = swiftCodeRepository.getBySwiftCodeStartingWith(entity.getSwiftCode().substring(0, 8));
            List<SwiftCodeResponse> branchResponses = SwiftCodeMapper.mapToResponseList(branches);
            return SwiftCodeMapper.mapToResponse(entity, branchResponses);
        } else {
            return SwiftCodeMapper.mapToResponse(entity);
        }
    }
    @Override
    public List<SwiftCodeResponse> getByCountryISO2(String iso2Code) {
        List<SwiftCode> records = swiftCodeRepository.getByCountryISO2(iso2Code);
        return records.stream()
                .map(SwiftCodeMapper::mapToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public MessageResponse deleteBySwiftCode(String swiftCode) {
        SwiftCode entity = swiftCodeRepository.getBySwiftCode(swiftCode);
        if (entity != null) {
            swiftCodeRepository.delete(entity);
            return new MessageResponse("Swift code deleted successfully.");
        }
        return new MessageResponse("No record found for swift code: " + swiftCode);
    }
    @Override
    public boolean isDatabaseInitialized() {
        return swiftCodeRepository.count() > 0;
    }

}
