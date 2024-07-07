package io.sy.data.controller;

import io.sy.data.service.DataService;
import io.sy.metadata.api.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data")
public class DataServerController {

    private final DataService dataService;

    @Autowired
    public DataServerController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/health")
    public ResponseEntity<Void> checkHealth() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/object/{name}")
    public List<Record> getAllRecords(@PathVariable String name) {
        return dataService.getAllRecords(name);
    }

    @PostMapping("/object/{name}")
    public ResponseEntity<Void> saveRecord(@PathVariable String name, @RequestBody Map<String, String> record) {
        dataService.saveRecord(name, record);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
