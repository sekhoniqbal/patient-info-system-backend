package ca.ehealthsask.assignment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.ehealthsask.assignment.entities.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}