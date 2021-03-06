// Copyright 2014 The Bazel Authors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.devtools.build.lib.rules.objc;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.devtools.build.lib.actions.Artifact;
import com.google.devtools.build.lib.cmdline.Label;
import com.google.devtools.build.lib.vfs.PathFragment;
import java.util.List;

/** An object that captures information of ObjC files generated by J2ObjC in a single target. */
public final class J2ObjcSource {

  /**
   * Indicates the type of files from which the ObjC files included in {@link J2ObjcSource} are
   * generated.
   */
  public enum SourceType {
    /**
     * Indicates the original file type is java source file.
     */
    JAVA,

    /**
     * Indicates the original file type is proto file.
     */
    PROTO;
  }

  private final Label targetLabel;
  private final List<Artifact> objcSrcs;
  private final List<Artifact> objcHdrs;
  private final PathFragment objcFilePath;
  private final SourceType sourceType;
  private final List<PathFragment> headerSearchPaths;

  /**
   * Constructs a J2ObjcSource containing target information for j2objc transpilation.
   *
   * @param targetLabel the @{code Label} of the associated target.
   * @param objcSrcs the {@code Iterable} containing objc source files generated by J2ObjC
   * @param objcHdrs the {@code Iterable} containing objc header files generated by J2ObjC
   * @param objcFilePath the {@code PathFragment} under which all the generated objc files are. It
   *     can be used as header search path for objc compilations.
   * @param sourceType the type of files from which the ObjC files are generated.
   * @param headerSearchPaths the {@code Iterable} of header search paths necessary for compiling
   *     the generated J2ObjC sources in {@code objcSrcs}
   */
  public J2ObjcSource(
      Label targetLabel,
      List<Artifact> objcSrcs,
      List<Artifact> objcHdrs,
      PathFragment objcFilePath,
      SourceType sourceType,
      List<PathFragment> headerSearchPaths) {
    this.targetLabel = targetLabel;
    this.objcSrcs = ImmutableList.copyOf(objcSrcs);
    this.objcHdrs = ImmutableList.copyOf(objcHdrs);
    this.objcFilePath = objcFilePath;
    this.sourceType = sourceType;
    this.headerSearchPaths = ImmutableList.copyOf(headerSearchPaths);
  }

  /**
   * Returns the label of the associated target.
   */
  public Label getTargetLabel() {
    return targetLabel;
  }

  /** Returns the objc source files generated by J2ObjC. */
  public List<Artifact> getObjcSrcs() {
    return objcSrcs;
  }

  /*
   * Returns the objc header files generated by J2ObjC
   */
  public List<Artifact> getObjcHdrs() {
    return objcHdrs;
  }

  /**
   * Returns the {@code PathFragment} which represents a directory where the generated ObjC files
   * reside.
   */
  public PathFragment getObjcFilePath() {
    return objcFilePath;
  }

  /** Returns a list of header search paths necessary for compiling the generated J2ObjC sources. */
  public List<PathFragment> getHeaderSearchPaths() {
    return headerSearchPaths;
  }

  @Override
  public final boolean equals(Object other) {
    if (!(other instanceof J2ObjcSource)) {
      return false;
    }

    J2ObjcSource that = (J2ObjcSource) other;
    return Objects.equal(this.targetLabel, that.targetLabel)
        && this.objcSrcs.equals(that.objcSrcs)
        && this.objcHdrs.equals(that.objcHdrs)
        && Objects.equal(this.objcFilePath, that.objcFilePath)
        && this.sourceType == that.sourceType
        && this.headerSearchPaths.equals(that.headerSearchPaths);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(
        targetLabel, objcSrcs, objcHdrs, objcFilePath, sourceType, headerSearchPaths);
  }
}

