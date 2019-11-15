package org.anclav.doubledatasource.repository;

import org.anclav.doubledatasource.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    default Warehouse findByIdOrThrow(Long id) {
        return findById(id)
            .orElseThrow(() -> new RuntimeException("Warehouse not found: " + id));
    }
}
