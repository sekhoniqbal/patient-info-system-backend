package ca.ehealthsask.assignment.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ca.ehealthsask.assignment.entities.Patient;

import ca.ehealthsask.assignment.repositories.PatientRepository;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("")
    public List<Patient> getPatient() {
        return patientRepository.findAll();
    }

    @GetMapping("/{id}")
    public Patient getPatient(@PathVariable("id") Long id) {
        return findPatientByIdOrElseThrowError(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Patient createPatient(@Validated @RequestBody Patient newPatient) {
        return patientRepository.save(newPatient);
    }

    @PutMapping("/{id}")
    public Patient updatePatient(@Validated @RequestBody Patient updatedPatient, @PathVariable Long id) {
        Patient patient = findPatientByIdOrElseThrowError(id);
        patient.setFirstName(updatedPatient.getFirstName());
        patient.setLastName(updatedPatient.getLastName());
        patient.setDateOfBirth(updatedPatient.getDateOfBirth());
        return patientRepository.save(patient);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable("id") Long id) {
        Patient patient = findPatientByIdOrElseThrowError(id);
        patientRepository.delete(patient);
    }

    // helper
    private Patient findPatientByIdOrElseThrowError(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient Not Found"));
    }

}