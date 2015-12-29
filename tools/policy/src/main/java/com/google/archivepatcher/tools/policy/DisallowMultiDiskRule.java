// Copyright 2015 Google Inc. All rights reserved.
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.archivepatcher.tools.policy;

import com.google.archivepatcher.parts.EndOfCentralDirectory;

/**
 * Disallows archives that specify anything other than a single "disk", which
 * must be zero.
 */
public class DisallowMultiDiskRule extends ArchiveRule {

    @Override
    protected void checkInternal() {
        EndOfCentralDirectory eocd = archive.getCentralDirectory().getEocd();
        if (eocd.getDiskNumber_16bit() != 0) {
            notOk("archive",
                "end of central directory specifies a non-zero disk number");
        }
        if (eocd.getDiskNumberOfStartOfCentralDirectory_16bit() != 0) {
            notOk("archive",
                "end of central directory specifies a non-zero disk number " +
                "for the start of the central directory.");
        }
    }

}