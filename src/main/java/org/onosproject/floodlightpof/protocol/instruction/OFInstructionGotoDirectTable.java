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

import org.jboss.netty.buffer.ChannelBuffer;
import org.onosproject.floodlightpof.protocol.OFMatch20;
import org.onosproject.floodlightpof.util.HexString;

/**
 * Goto next <B>direct</B> table.<br>
 * If next table is not direct table, use {@link OFInstructionGotoTable} instead.
 */
public class OFInstructionGotoDirectTable extends OFInstruction {
    public static final int MINIMUM_LENGTH = OFInstruction.MINIMUM_LENGTH + 8 + OFMatch20.MINIMUM_LENGTH;

    protected byte nextTableId;
    protected byte indexType;   //0: value; 1: field
    protected short packetOffset;       //byte

    protected int indexValue;
    protected OFMatch20 indexField;

    public OFInstructionGotoDirectTable() {
        super.setType(OFInstructionType.GOTO_DIRECT_TABLE);
        super.setLength((short) MINIMUM_LENGTH);
    }

    @Override
    public void readFrom(ChannelBuffer data) {
        super.readFrom(data);

        nextTableId = data.readByte();
        indexType = data.readByte();
        packetOffset = data.readShort();
        data.readBytes(4);

        if (indexType == 0) {
            indexValue = data.readInt();
            data.readBytes(4);
            indexField = null;
        } else if (indexType == 1) {
            indexField = new OFMatch20();
            indexField.readFrom(data);
            this.indexValue = 0;
        } else {
            indexValue = 0;
            indexField = null;
            data.readBytes(OFMatch20.MINIMUM_LENGTH);
        }
    }

    @Override
    public void writeTo(ChannelBuffer data) {
        super.writeTo(data);

        data.writeByte(nextTableId);
        data.writeByte(indexType);
        data.writeShort(packetOffset);
        data.writeZero(4);

        if (indexType == 0) {
            data.writeInt(indexValue);
            data.writeZero(4);
        } else if (indexType == 1 && indexField != null) {
            indexField.writeTo(data);
        } else {
            data.writeZero(OFMatch20.MINIMUM_LENGTH);
        }
    }

    @Override
    public String toBytesString() {
        String byteString = super.toBytesString();

        byteString += HexString.toHex(nextTableId) +
                        HexString.toHex(indexType) +
                        HexString.toHex(packetOffset) +
                        HexString.byteZeroEnd(4);

        if (indexType == 0) {
            byteString += HexString.toHex(indexValue) + HexString.byteZeroEnd(4);
        } else if (indexType == 1 && indexField != null) {
            byteString += indexField.toBytesString();
        } else {
            byteString += HexString.byteZeroEnd(OFMatch20.MINIMUM_LENGTH);
        }

        return byteString;
    }

    @Override
    public String toString() {
        String string  = super.toString();
        string += ";ntid=" + nextTableId +
                    ";it=" + indexType +
                    ";poff=" + packetOffset;

        if (indexType == 0) {
            string += ";iv=" + indexValue;
        } else if (indexType == 1) {
            string += ";if=" + indexField;
        } else {
            string += ";i=0";
        }

        return string;
    }

    public byte getIndexType() {
        return indexType;
    }

    public void setIndexType(byte indexType) {
        this.indexType = indexType;
    }

    public int getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(int indexValue) {
        this.indexValue = indexValue;
    }

    public OFMatch20 getIndexField() {
        return indexField;
    }

    public void setIndexField(OFMatch20 indexField) {
        this.indexField = indexField;
    }

    public byte getNextTableId() {
        return nextTableId;
    }

    public void setNextTableId(byte nextTableId) {
        this.nextTableId = nextTableId;
    }

    public short getPacketOffset() {
        return packetOffset;
    }

    public void setPacketOffset(short packetOffset) {
        this.packetOffset = packetOffset;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((indexField == null) ? 0 : indexField.hashCode());
        result = prime * result + indexType;
        result = prime * result + indexValue;
        result = prime * result + nextTableId;
        result = prime * result + packetOffset;
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
        if (!(obj instanceof OFInstructionGotoDirectTable)) {
            return false;
        }
        OFInstructionGotoDirectTable other = (OFInstructionGotoDirectTable) obj;
        if (indexField == null) {
            if (other.indexField != null) {
                return false;
            }
        } else if (!indexField.equals(other.indexField)) {
            return false;
        }
        if (indexType != other.indexType) {
            return false;
        }
        if (indexValue != other.indexValue) {
            return false;
        }
        if (nextTableId != other.nextTableId) {
            return false;
        }
        if (packetOffset != other.packetOffset) {
            return false;
        }
        return true;
    }

    @Override
    public OFInstructionGotoDirectTable clone() throws CloneNotSupportedException {
        OFInstructionGotoDirectTable ins = (OFInstructionGotoDirectTable) super.clone();

        if (indexField != null) {
            ins.setIndexField(indexField.clone());
        }

        return ins;
    }
}
