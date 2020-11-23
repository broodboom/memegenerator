package com.example.assignment2.application;

import com.example.assignment2.domain.Rekening;
import com.example.assignment2.domain.Rekeninghouder;
import com.example.assignment2.infrastructure.RekeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("rekeninghouder")
public class RekeninghoudersController {

    @Autowired
    RekeningService rekeningService;

    @GetMapping("/{id}")
    ResponseEntity<Rekeninghouder> getRekeninghouder(@PathVariable("id") Integer id) {
        var rekeninghouder = rekeningService.getRekeninghouder(id);

        if (rekeninghouder == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(rekeningService.getRekeninghouder(id), HttpStatus.OK);
    }

    @GetMapping("")
    ResponseEntity<Collection<Rekeninghouder>> getRekeninghouders() {
        return new ResponseEntity(rekeningService.getRekeninghouders(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity updateRekeninghouder(@PathVariable("id") Integer id, @Valid @RequestBody Rekeninghouder rekeninghouder, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        
        var r = rekeningService.getRekeninghouder(id);

        if (r == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        rekeningService.updateRekeninghouder(rekeninghouder);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("")
    ResponseEntity createRekeninghouder(@Valid @RequestBody Rekeninghouder rekeninghouder, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        rekeningService.createRekeninghouder(rekeninghouder);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteRekeninghouder(@PathVariable("id") Integer id) {
        var rekeninghouder = rekeningService.getRekeninghouder(id);

        if (rekeninghouder == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        rekeningService.deleteRekeninghouder(rekeninghouder);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/rekeningen")
    ResponseEntity<Set<Rekening>> getRekeningen(@PathVariable("id") Integer id) {
        var rekeninghouder = rekeningService.getRekeninghouder(id);

        if (rekeninghouder == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity(rekeningService.getRekeningenRekeninghouder(id), HttpStatus.OK);
    }

    @GetMapping("test")
    ResponseEntity<String> test() {
        return new ResponseEntity<>("hello world", HttpStatus.I_AM_A_TEAPOT);
    }
}
