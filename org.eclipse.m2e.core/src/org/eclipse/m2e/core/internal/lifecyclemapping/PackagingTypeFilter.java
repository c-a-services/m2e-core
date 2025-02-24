/*******************************************************************************
 * Copyright (c) 2022 Christoph Läubrich and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Christoph Läubrich - initial API and implementation
 *******************************************************************************/
package org.eclipse.m2e.core.internal.lifecyclemapping;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

import org.eclipse.m2e.core.internal.lifecyclemapping.model.LifecycleMappingFilter;
import org.eclipse.m2e.core.internal.lifecyclemapping.model.LifecycleMappingMetadata;
import org.eclipse.m2e.core.internal.lifecyclemapping.model.LifecycleMappingMetadataSource;


/**
 * A {@link Predicate} that returns true if the given {@link LifecycleMappingMetadata} is filtered for the packaging
 * type by any of the filters.
 */
public class PackagingTypeFilter implements Predicate<LifecycleMappingMetadata> {
  private List<LifecycleMappingFilter> filters;

  /**
   * 
   */
  public PackagingTypeFilter(List<MappingMetadataSource> metadataSources, String packagingType) {
    filters = metadataSources.stream().flatMap(s -> s.getFilters().stream())
        .filter(filter -> filter.getPackagingTypes().contains(packagingType)).collect(Collectors.toList());
  }

  /* (non-Javadoc)
   * @see java.util.function.Predicate#test(java.lang.Object)
   */
  @Override
  public boolean test(LifecycleMappingMetadata metadata) {

    return Optional.ofNullable(metadata.getSource())//
        .map(LifecycleMappingMetadataSource::getSource)//
        .filter(Bundle.class::isInstance)//
        .map(Bundle.class::cast)//
        .filter(bundle -> {
          return filters.stream().anyMatch(filter -> {
            if(filter.getSymbolicName().equals(bundle.getSymbolicName())) {
              Version version = bundle.getVersion();
              return filter.matches(version.getMajor() + "." + version.getMinor() + "." + version.getMicro());
            }
            return false;
          });
        }).isPresent();
  }
}
