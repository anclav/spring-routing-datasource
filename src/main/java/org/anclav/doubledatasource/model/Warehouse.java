package org.anclav.doubledatasource.model;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    private String name;

    private String address;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<WarehouseSchedule> warehouseSchedules = new HashSet<>();

    public Warehouse addSchedule(WarehouseSchedule schedule) {
        schedule.setWarehouse(this);
        warehouseSchedules.add(schedule);
        return this;
    }

    public Warehouse addSchedules(Set<WarehouseSchedule> schedules) {
        schedules.forEach(this::addSchedule);
        return this;
    }
}
