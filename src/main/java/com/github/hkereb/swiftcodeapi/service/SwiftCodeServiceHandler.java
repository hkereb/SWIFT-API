package com.github.hkereb.swiftcodeapi.service;

import com.github.hkereb.swiftcodeapi.domain.SwiftCode;
import com.github.hkereb.swiftcodeapi.dto.request.SwiftCodeRequest;
import com.github.hkereb.swiftcodeapi.dto.response.MessageResponse;
import com.github.hkereb.swiftcodeapi.dto.response.SwiftCodeResponse;
import com.github.hkereb.swiftcodeapi.repository.SwiftCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    public String upsertAll(List<SwiftCode> records) {
        swiftCodeRepository.saveAll(records);
        return "Bulk upsert success.";
    }

    @Override
    public SwiftCode getById(Integer id) {
        Optional<SwiftCode> findById = swiftCodeRepository.findById(id);
        return findById.orElse(null);
    }

    @Override
    public SwiftCodeResponse getBySwiftCode(String swiftCode) {
        SwiftCode entity = swiftCodeRepository.getBySwiftCode(swiftCode);
        if (entity == null) return null;

        if (Boolean.TRUE.equals(entity.getIsHeadquarters())) {
            List<SwiftCode> branches = swiftCodeRepository.getBranchesBySwiftCode(entity.getSwiftCode());
            List<SwiftCodeResponse> branchResponses = SwiftCodeMapper.mapToResponseList(branches);
            return SwiftCodeMapper.mapToResponse(entity, branchResponses);
        } else {
            return SwiftCodeMapper.mapToResponse(entity);
        }
    }

    @Override
    public List<SwiftCode> getBranchesBySwiftCode(String swiftCode) {
        if (swiftCode == null || swiftCode.length() < 8) {
            return Collections.emptyList();
        }
        return swiftCodeRepository.getBranchesBySwiftCode(swiftCode.substring(0, 8));
    }


    @Override
    public List<SwiftCodeResponse> getByCountryISO2(String iso2Code) {
        List<SwiftCode> records = swiftCodeRepository.getByCountryISO2(iso2Code);
        return records.stream()
                .map(SwiftCodeMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SwiftCode> getAllSwiftRecords() {
         return swiftCodeRepository.findAll();
    }

    @Override
    public String deleteById(Integer id) {
        if (swiftCodeRepository.existsById(id)) {
            swiftCodeRepository.deleteById(id);
            return "Delete Success";
        }
        return "No Record";
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
