package org.databasespring.framework.database.mapper;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * For now contains only getting the registry, can be extended to include a filtering of some sorts
 */
public interface DatabaseMapperRegistry {

    @Nonnull
    List<Class<? extends DatabaseMapper>> getRegistry();

}
