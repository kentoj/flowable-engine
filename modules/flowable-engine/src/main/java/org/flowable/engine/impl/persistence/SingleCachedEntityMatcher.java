package org.flowable.engine.impl.persistence;

import org.flowable.engine.common.impl.persistence.entity.Entity;

/**
 * Interface to express a condition whether or not one specific cached entity should be used in the return result of a query.
 * 
 * @author Joram Barrez
 */
public interface SingleCachedEntityMatcher<EntityImpl extends Entity> {

  boolean isRetained(EntityImpl entity, Object param);

}