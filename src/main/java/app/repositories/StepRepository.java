package app.repositories;

import app.domain.Step;

import org.springframework.data.repository.CrudRepository;

public interface StepRepository extends CrudRepository<Step, Long> {
}