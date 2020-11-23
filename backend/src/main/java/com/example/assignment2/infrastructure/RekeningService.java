package com.example.assignment2.infrastructure;

import com.example.assignment2.domain.Rekening;
import com.example.assignment2.domain.Rekeninghouder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class RekeningService {
    @Autowired
    RekeningRepository rekeningRepositoryImpl;

    @Autowired
    RekeninghouderRepository rekeninghouderRepositoryImpl;

    public Rekening getRekening(Integer rekeningid) {
        Optional<Rekening> rekening = rekeningRepositoryImpl.findById(rekeningid);

        if (rekening.isPresent()) {
            return rekening.get();
        }

        return null;
    }

    public Collection<Rekening> getRekeningen() {
        return rekeningRepositoryImpl.findAll();
    }

    public void updateRekening(Rekening rekening) {
        rekeningRepositoryImpl.save(rekening);
    }

    public void createRekening(Rekening rekening) {
        rekeningRepositoryImpl.save(rekening);
    }

    public void deleteRekening(Rekening rekening) {
        rekeningRepositoryImpl.delete(rekening);
    }

    public void blokkeerRekening(Rekening rekening) {
        rekening.setGeblokkeerd(true);
        rekeningRepositoryImpl.save(rekening);
    }

    public void deblokkeerRekening(Rekening rekening) {
        rekening.setGeblokkeerd(false);
        rekeningRepositoryImpl.save(rekening);
    }

    public void koppelRekeninghouder(Rekening rekening, Rekeninghouder rekeninghouder) {
        rekening.rekeninghouders.add(rekeninghouder);

        rekeningRepositoryImpl.save(rekening);
    }

    public void ontkoppelRekeninghouder(Rekening rekening, Rekeninghouder rekeninghouder) {
        rekening.rekeninghouders.remove(rekeninghouder);

        rekeningRepositoryImpl.save(rekening);
    }

    public Rekeninghouder getRekeninghouder(Integer rekeninghouderId) {
        Optional<Rekeninghouder> rekeninghouder = rekeninghouderRepositoryImpl.findById(rekeninghouderId);

        if (rekeninghouder.isPresent()) {
            return rekeninghouder.get();
        }

        return null;
    }

    public Collection<Rekeninghouder> getRekeninghouders() {
        return rekeninghouderRepositoryImpl.findAll();
    }

    public void updateRekeninghouder(Rekeninghouder rekeninghouder) {
        rekeninghouderRepositoryImpl.save(rekeninghouder);
    }

    public void createRekeninghouder(Rekeninghouder rekeninghouder) {
        rekeninghouderRepositoryImpl.save(rekeninghouder);
    }

    public void deleteRekeninghouder(Rekeninghouder rekeninghouder) {
        rekeninghouderRepositoryImpl.delete(rekeninghouder);
    }

    public Set<Rekening> getRekeningenRekeninghouder(Integer rekeninghouderId) {
        Optional<Rekeninghouder> rekeninghouder = rekeninghouderRepositoryImpl.findById(rekeninghouderId);

        if (rekeninghouder.isPresent()) {
            return (Set<Rekening>) rekeninghouder.get().rekeningen;
        }

        return null;
    }
}
