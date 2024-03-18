package app.repositories;

import app.domain.Status;

import org.springframework.data.repository.CrudRepository;

public interface StatusRepository extends CrudRepository<Status, Long> {
}