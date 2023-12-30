package app.repositories;

import app.entities.platform.Step;

import org.springframework.data.repository.CrudRepository;

public interface StepRepository extends CrudRepository<Step, Long> {
}