/*******************************************************************************
 * Copyright (c) 2010 Sonatype, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Sonatype, Inc. - initial API and implementation
 *******************************************************************************/

package org.eclipse.m2e.core.internal.lifecyclemapping.discovery;

import java.util.Objects;

/**
 * Represents project packaging type and corresponding lifecycle mapping metadata.
 */
public class PackagingTypeMappingConfiguration implements ILifecycleMappingElement {

  public static class PackagingTypeMappingRequirement implements ILifecycleMappingRequirement {
    private final String packaging;

    public PackagingTypeMappingRequirement(String packaging) {
      this.packaging = packaging;
    }

    @Override
    public int hashCode() {
      return packaging.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      if(this == obj) {
        return true;
      }

      return obj instanceof PackagingTypeMappingRequirement other && packaging.equals(other.packaging);
    }

    public String getPackaging() {
      return packaging;
    }
  }

  public static class LifecycleStrategyMappingRequirement implements ILifecycleMappingRequirement {
    private final String packaging;

    private final String lifecycleMappingId;

    public LifecycleStrategyMappingRequirement(String packaging, String lifecycleMappingId) {
      this.packaging = packaging;
      this.lifecycleMappingId = lifecycleMappingId;
    }

    @Override
    public int hashCode() {
      return lifecycleMappingId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      if(this == obj) {
        return true;
      }

      return obj instanceof LifecycleStrategyMappingRequirement other
          && lifecycleMappingId.equals(other.lifecycleMappingId);
    }

    public String getLifecycleMappingId() {
      return lifecycleMappingId;
    }

    public String getPackaging() {
      return packaging;
    }
  }

  private final String packaging;

  private final String lifecycleMappingId;

  private final ILifecycleMappingRequirement requirement;

  public PackagingTypeMappingConfiguration(String packaging, String lifecycleMappingId) {
    this.packaging = packaging;
    this.lifecycleMappingId = lifecycleMappingId;

    if(lifecycleMappingId == null) {
      requirement = new PackagingTypeMappingRequirement(packaging);
    } else {
      requirement = new LifecycleStrategyMappingRequirement(packaging, lifecycleMappingId);
    }
  }

  public String getPackaging() {
    return packaging;
  }

  public String getLifecycleMappingId() {
    return lifecycleMappingId;
  }

  @Override
  public int hashCode() {
    int hash = packaging.hashCode();
    hash = 17 * hash + (lifecycleMappingId != null ? lifecycleMappingId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj) {
      return true;
    }

    return obj instanceof PackagingTypeMappingConfiguration other && //
        packaging.equals(other.packaging) && Objects.equals(lifecycleMappingId, other.lifecycleMappingId);
  }

  @Override
  public ILifecycleMappingRequirement getLifecycleMappingRequirement() {
    return requirement;
  }

}
