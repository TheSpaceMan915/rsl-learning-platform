package app.repositories;

import app.domain.Module;

import org.springframework.data.repository.CrudRepository;

public interface ModuleRepository extends CrudRepository<Module, Long> {
}