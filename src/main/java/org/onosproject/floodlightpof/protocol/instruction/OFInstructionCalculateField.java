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

public class OFInstructionCalculateField  extends OFInstruction {
    public static final int MINIMUM_LENGTH = OFInstruction
            .MINIMUM_LENGTH + 8 + OFMatch20.MINIMUM_LENGTH + OFMatch20.MINIMUM_LENGTH;

    public enum OFCalcType {
        OFPCT_ADD,              // +
        OFPCT_SUBTRACT,         // -
        OFPCT_LEFT_SHIFT,       // <<
        OFPCT_RIGHT_SHIFT,      // >>
        OFPCT_BITWISE_ADD,      // &
        OFPCT_BITWISE_OR,       // |
        OFPCT_BITWISE_XOR,      // ^
        OFPCT_BITWISE_NOR,
    }

    protected OFCalcType calcType;
    protected byte srcValueType;                //0: use srcField_Value; 1: use srcField;

    protected OFMatch20 desField;

    protected int srcValue;
    protected OFMatch20 srcField;

    public OFInstructionCalculateField() {
        super.setType(OFInstructionType.CALCULATE_FIELD);
        super.setLength((short) MINIMUM_LENGTH);
    }

    @Override
    public void readFrom(ChannelBuffer data) {
        super.readFrom(data);

        short ct = data.readShort();
        if (ct >= 0 && ct < OFCalcType.values().length) {
            calcType = OFCalcType.values()[ct];
        } else {
            calcType = null;
        }

        srcValueType = data.readByte();
        data.readBytes(5);

        desField = new OFMatch20();
        desField.readFrom(data);

        if (srcValueType == 0) {
            srcValue = data.readInt();
            data.readBytes(4);
            srcField = null;
        } else if (srcValueType == 1) {
            srcValue = 0;
            srcField = new OFMatch20();
            srcField.readFrom(data);
        } else {
            srcValue = 0;
            srcField = null;
            data.readBytes(OFMatch20.MINIMUM_LENGTH);
        }
    }

    @Override
    public void writeTo(ChannelBuffer data) {
        super.writeTo(data);

        if (calcType != null) {
            data.writeShort((short) calcType.ordinal());
        } else {
            data.writeShort(0);
        }

        data.writeByte(srcValueType);
        data.writeZero(5);

        if (desField != null) {
            desField.writeTo(data);
        } else {
            data.writeZero(OFMatch20.MINIMUM_LENGTH);
        }

        if (srcValueType == 0) {
            data.writeInt(srcValue);
            data.writeZero(4);
        } else if (srcValueType == 1 && srcField != null) {
            srcField.writeTo(data);
        } else {
            data.writeZero(OFMatch20.MINIMUM_LENGTH);
        }
    }

    @Override
    public String toBytesString() {
        String byteString =  super.toBytesString();

        short ct = (calcType == null) ? (short) -1 : (short) calcType.ordinal();
        byteString += HexString.toHex(ct) +
                        HexString.toHex(srcValueType) +
                        HexString.byteZeroEnd(5);

        if (srcField != null) {
            byteString += srcField.toBytesString();
        } else {
            byteString += HexString.byteZeroEnd(OFMatch20.MINIMUM_LENGTH);
        }

        if (srcValueType == 0) {
            byteString += HexString.toHex(srcValue) + HexString.byteZeroEnd(4);
        } else if (srcValueType == 1 && srcField != null) {
            byteString += srcField.toBytesString();
        } else {
            byteString += HexString.byteZeroEnd(OFMatch20.MINIMUM_LENGTH);
        }

        return byteString;
    }

    @Override
    public String toString() {
        String string = super.toString();
        string += ";ct=" + calcType +
                    ";st=" + ((srcValueType == -1) ? 0 : srcValueType);

        string += ";df=" + desField;

        if (srcValueType == 0) {
            string += ";sv=" + srcValue;
        } else if (srcValueType == 1) {
            string += ";sf=" + srcField;
        } else {
            string += ";s=0";
        }

        return string;
    }


    public OFCalcType getCalcType() {
        return calcType;
    }

    public void setCalcType(OFCalcType calcType) {
        this.calcType = calcType;
    }

    public byte getSrcValueType() {
        return srcValueType;
    }

    public void setSrcValueType(byte srcvaluetype) {
        this.srcValueType = srcvaluetype;
    }

    public OFMatch20 getDesField() {
        return desField;
    }

    public void setDesField(OFMatch20 desfield) {
        this.desField = desfield;
    }

    public int getSrcValue() {
        return srcValue;
    }

    public void setSrcValue(int srcvalue) {
        this.srcValue = srcvalue;
    }

    public OFMatch20 getSrcField() {
        return srcField;
    }

    public void setSrcField(OFMatch20 srcfield) {
        this.srcField = srcfield;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((calcType == null) ? 0 : calcType.hashCode());
        result = prime * result
                + ((desField == null) ? 0 : desField.hashCode());
        result = prime * result
                + ((srcField == null) ? 0 : srcField.hashCode());
        result = prime * result + srcValueType;
        result = prime * result + srcValue;
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
        if (!(obj instanceof OFInstructionCalculateField)) {
            return false;
        }
        OFInstructionCalculateField other = (OFInstructionCalculateField) obj;
        if (calcType != other.calcType) {
            return false;
        }
        if (desField == null) {
            if (other.desField != null) {
                return false;
            }
        } else if (!desField.equals(other.desField)) {
            return false;
        }
        if (srcField == null) {
            if (other.srcField != null) {
                return false;
            }
        } else if (!srcField.equals(other.srcField)) {
            return false;
        }
        if (srcValueType != other.srcValueType) {
            return false;
        }
        if (srcValue != other.srcValue) {
            return false;
        }
        return true;
    }

    @Override
    public OFInstructionCalculateField clone() throws CloneNotSupportedException {
        OFInstructionCalculateField ins = (OFInstructionCalculateField) super.clone();

        if (desField != null) {
            ins.setDesField(desField.clone());
        }

        if (srcField != null) {
            ins.setSrcField(srcField.clone());
        }

        return ins;
    }
}
