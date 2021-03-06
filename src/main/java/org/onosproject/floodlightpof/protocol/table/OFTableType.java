/**
 * Copyright (c) 2012, 2013, Huawei Technologies Co., Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.onosproject.floodlightpof.protocol.table;

/**
 * open flow table type enum:
 * <br>  OF_MM_TABLE  (MaskedMatch         = 0),
 * <br>  OF_LPM_TABLE (LongestPrefixMatch  = 1),
 * <br>  OF_EM_TABLE  (ExactMatch          = 2),
 * <br>  OF_LINEAR_TABLE (Linear           = 3).
 *
 */
public enum OFTableType {
    /** MaskedMatch Table.*/
    OF_MM_TABLE(0),

    /** LongestPrefixMatch Table.*/
    OF_LPM_TABLE(1),

    /** ExactMatch Table.*/
    OF_EM_TABLE(2),

    /** Linear Table.*/
    OF_LINEAR_TABLE(3),

    OF_MAX_TABLE_TYPE(4);

    protected byte value;

    /** max table type number.*/
    public static final int MAX_TABLE_TYPE = OF_MAX_TABLE_TYPE.getValue();

    public byte getValue() {
        return value;
    }

    private OFTableType(int value) {
        this.value = (byte) value;
    }
}
