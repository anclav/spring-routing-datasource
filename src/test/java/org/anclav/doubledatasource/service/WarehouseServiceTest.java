package org.anclav.doubledatasource.service;

import org.anclav.doubledatasource.AbstractTest;
import org.anclav.doubledatasource.model.Warehouse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class WarehouseServiceTest extends AbstractTest {

    @Autowired
    private WarehouseService warehouseService;

    @Test
    void differentDatasourcesAreUsed() {
        softly.assertThat(warehouseService.findAll()).extracting(Warehouse::getName)
            .containsExactly("warehouse1", "warehouse2");

        softly.assertThat(warehouseService.findAllInReplica()).extracting(Warehouse::getName)
            .containsExactly("warehouse12", "warehouse22");
    }
}