package org.anclav.doubledatasource.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.anclav.doubledatasource.config.ReplicaQuery;
import org.anclav.doubledatasource.model.Warehouse;
import org.anclav.doubledatasource.repository.WarehouseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }

    @ReplicaQuery
    public List<Warehouse> findAllInReplica() {
        return warehouseRepository.findAll();
    }

    @Transactional
    void updateMain(Long id, String name) {
        Warehouse warehouse = warehouseRepository.findByIdOrThrow(id);
        warehouse.setName(name);
    }

    void updateReplica(Long id, String name) {
        Warehouse warehouse = warehouseRepository.findByIdOrThrow(id);
        warehouse.setName(name);
    }
}
