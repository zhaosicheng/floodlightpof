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

package org.onosproject.floodlightpof.protocol.instruction;

import java.util.Arrays;

import org.jboss.netty.buffer.ChannelBuffer;
import org.onosproject.floodlightpof.protocol.OFGlobal;
import org.onosproject.floodlightpof.util.HexString;

/**
 * Write metadata at {@link #metadataOffset} : {@link #writeLength} using {@link #value}.
 */
public class OFInstructionWriteMetadata extends OFInstruction {
    public static final int MINIMUM_LENGTH = OFInstruction
            .MINIMUM_LENGTH + 4 + OFGlobal.OFP_MAX_FIELD_LENGTH_IN_BYTE + 4;

    protected short metadataOffset;     //bit
    protected short writeLength;        //bit

    protected byte[] value;

    public OFInstructionWriteMetadata() {
        super.setType(OFInstructionType.WRITE_METADATA);
        super.setLength((short) MINIMUM_LENGTH);
    }

    @Override
    public void readFrom(ChannelBuffer data) {
        super.readFrom(data);
        metadataOffset = data.readShort();
        writeLength = data.readShort();

        value = new byte[OFGlobal.OFP_MAX_FIELD_LENGTH_IN_BYTE];
        data.readBytes(value);

        data.readBytes(4);
    }

    @Override
    public void writeTo(ChannelBuffer data) {
        super.writeTo(data);
        data.writeShort(metadataOffset);
        data.writeShort(writeLength);

        if (value == null) {
            data.writeZero(OFGlobal.OFP_MAX_FIELD_LENGTH_IN_BYTE);
        } else {
            if (value.length > OFGlobal.OFP_MAX_FIELD_LENGTH_IN_BYTE) {
                data.writeBytes(value, OFGlobal.OFP_MAX_FIELD_LENGTH_IN_BYTE - value
                        .length, OFGlobal.OFP_MAX_FIELD_LENGTH_IN_BYTE);
            } else {
                data.writeBytes(value);
                data.writeZero(OFGlobal.OFP_MAX_FIELD_LENGTH_IN_BYTE - value.length);
            }
        }

        data.writeZero(4);
    }

    @Override
    public String toBytesString() {
        String string =  super.toBytesString() +
                            HexString.toHex(metadataOffset) +
                            HexString.toHex(writeLength) +
                            " " +
                            HexString.toHex(value);

        if (value == null) {
            string += HexString.byteZeroEnd(OFGlobal.OFP_MAX_FIELD_LENGTH_IN_BYTE);
        } else {
            if (value.length > OFGlobal.OFP_MAX_FIELD_LENGTH_IN_BYTE) {
                string += HexString.toHex(value, 0, OFGlobal.OFP_MAX_FIELD_LENGTH_IN_BYTE);
                string += HexString.zeroEnd(0);
            } else {
                //string += HexString.ZeroEnd(0);
                string += HexString.toHex(value);
                //string += HexString.ZeroEnd(0);
                string += HexString.byteZeroEnd(OFGlobal.OFP_MAX_FIELD_LENGTH_IN_BYTE - value.length);
            }
        }

        return string;
    }

    @Override
    public String toString() {
        return super.toString() +
                ";mos=" + metadataOffset +
                ";wl=" + writeLength +
                ";val=" + HexString.toHex(value);
    }

    public short getMetadataOffset() {
        return metadataOffset;
    }

    public void setMetadataOffset(short metadataOffset) {
        this.metadataOffset = metadataOffset;
    }

    public short getWriteLength() {
        return writeLength;
    }

    public void setWriteLength(short writeLength) {
        this.writeLength = writeLength;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + metadataOffset;
        result = prime * result + Arrays.hashCode(value);
        result = prime * result + writeLength;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof OFInstructionWriteMetadata)) {
            return false;
        }
        OFInstructionWriteMetadata other = (OFInstructionWriteMetadata) obj;
        if (metadataOffset != other.metadataOffset) {
            return false;
        }
        if (!Arrays.equals(value, other.value)) {
            return false;
        }
        if (writeLength != other.writeLength) {
            return false;
        }
        return true;
    }
}
