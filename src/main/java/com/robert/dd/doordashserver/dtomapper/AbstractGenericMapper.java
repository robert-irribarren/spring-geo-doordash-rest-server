package com.robert.dd.doordashserver.dtomapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

public abstract class AbstractGenericMapper<E, DTO extends BaseDTO> implements GenericMapper<E, DTO> {

    @Autowired
    private CrudRepository<E,Object> repository;

    @Override
    public final E map (DTO dto) {
        if (dto == null) {
            return null;
        }

        // You can also use a Java 8 Supplier and pass it down the constructor
        E entity = newInstance();
        if (dto.getId() != null) {
            user = repository.findById(dto.getId());
        }

        return map(dto, entity);
    }

    protected abstract E newInstance();
}