package com.github.hkereb.swiftcodeapi.controller;

import com.github.hkereb.swiftcodeapi.model.SwiftRecord;
import com.github.hkereb.swiftcodeapi.service.SwiftRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftRecordController {

    @Autowired
    private SwiftRecordService swiftRecordService;

    @GetMapping("/{swiftCode}")
    public SwiftRecord getSwiftByCode(@PathVariable String swiftCode) {
        return swiftRecordService.getBySwiftCode(swiftCode);
    }

    @GetMapping("/country/{countryISO2}")
    public List<SwiftRecord> getAllByCountryISO2(@PathVariable String countryISO2) {
        return swiftRecordService.getByCountryISO2(countryISO2);
    }

    @PostMapping
    public String addSwiftRecord(@RequestBody SwiftRecord swiftRecord) {
        return swiftRecordService.upsert(swiftRecord);
    }

    @DeleteMapping("/{swiftCode}")
    public Map<String, String> deleteBySwiftCode(@PathVariable String swiftCode) {
        String message = swiftRecordService.deleteBySwiftCode(swiftCode);
        return Map.of("message", message);
    }

}
