package app.repositories;

import app.entities.platform.Module;

import org.springframework.data.repository.CrudRepository;

public interface ModuleRepository extends CrudRepository<Module, Long> {
}