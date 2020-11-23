package com.example.assignment2.application;

import com.example.assignment2.domain.Rekening;
import com.example.assignment2.infrastructure.RekeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("rekening")
public class RekeningController {

    @Autowired
    RekeningService rekeningService;

    @GetMapping("/{id}")
    ResponseEntity<String> getRekening(@PathVariable("id") Integer id) {
        var rekening = rekeningService.getRekening(id);

        if (rekening == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(rekening, HttpStatus.OK);
    }

    @GetMapping("")
    ResponseEntity<Collection<Rekening>> getRekeningen() {
        return new ResponseEntity(rekeningService.getRekeningen(), HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity createRekening(@Valid @RequestBody Rekening rekening, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        rekeningService.createRekening(rekening);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}/blokkeer")
    ResponseEntity blokkeerRekening(@PathVariable("id") Integer id) {
        Rekening rekening = rekeningService.getRekening(id);
        if (rekening == null) {
            return new ResponseEntity("Rekening bestaat niet", HttpStatus.NOT_FOUND);
        }

        rekeningService.blokkeerRekening(rekening);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/deblokkeer")
    ResponseEntity deblokkeerRekening(@PathVariable("id") Integer id) {
        Rekening rekening = rekeningService.getRekening(id);
        if (rekening == null) {
            return new ResponseEntity("Rekening bestaat niet", HttpStatus.NOT_FOUND);
        }

        rekeningService.deblokkeerRekening(rekening);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    ResponseEntity updateRekening(@PathVariable("id") Integer id, @Valid @RequestBody Rekening rekening, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        var r = rekeningService.getRekening(id);

        if (r == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        rekeningService.updateRekening(rekening);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteRekening(@PathVariable("id") Integer id) {
        var rekening = rekeningService.getRekening(id);

        if (rekening == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        rekeningService.deleteRekening(rekening);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{rekeningid}/rekeninghouders/{rekeninghouderid}")
    ResponseEntity koppelRekeninghouder(@PathVariable("rekeningid") Integer rekeningid, @PathVariable("rekeninghouderid") Integer rekeninghouderid) {
        var rekening = rekeningService.getRekening(rekeningid);
        var rekeninghouder = rekeningService.getRekeninghouder(rekeninghouderid);

        rekeningService.koppelRekeninghouder(rekening, rekeninghouder);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/{rekeningid}/rekeninghouders/{rekeninghouderid}")
    ResponseEntity ontkoppelRekeninghouder(@PathVariable("rekeningid") Integer rekeningid, @PathVariable("rekeninghouderid") Integer rekeninghouderid) {
        var rekening = rekeningService.getRekening(rekeningid);
        var rekeninghouder = rekeningService.getRekeninghouder(rekeninghouderid);

        rekeningService.ontkoppelRekeninghouder(rekening, rekeninghouder);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("test")
    ResponseEntity<String> test() {
        return new ResponseEntity<>("hello world", HttpStatus.I_AM_A_TEAPOT);
    }
}
