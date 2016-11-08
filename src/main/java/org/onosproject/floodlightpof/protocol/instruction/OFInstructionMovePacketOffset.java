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

public class OFInstructionMovePacketOffset extends OFInstruction {
    public static final int MINIMUM_LENGTH = OFInstruction.MINIMUM_LENGTH + 8 + OFMatch20.MINIMUM_LENGTH;

    protected byte direction;      //0: forward; 1: backward
    protected byte valueType;

    protected int moveValue;
    protected OFMatch20 moveField;

    public OFInstructionMovePacketOffset() {
        super.setType(OFInstructionType.MOVE_PACKET_OFFSET);
        super.setLength((short) MINIMUM_LENGTH);
    }

    @Override
    public void readFrom(ChannelBuffer data) {
        super.readFrom(data);

        direction = data.readByte();
        valueType = data.readByte();
        data.readBytes(6);

        if (valueType == 0) {
            moveValue = data.readInt();
            data.readBytes(4);
            moveField = null;
        } else if (valueType == 1) {
            moveValue = 0;
            moveField = new OFMatch20();
            moveField.readFrom(data);
        } else {
            moveValue = 0;
            moveField = null;
            data.readBytes(OFMatch20.MINIMUM_LENGTH);
        }
    }

    @Override
    public void writeTo(ChannelBuffer data) {
        super.writeTo(data);

        data.writeByte(direction);
        data.writeByte(valueType);
        data.writeZero(6);

        if (valueType == 0) {
            data.writeInt(moveValue);
            data.writeZero(4);
        } else if (valueType == 1 && moveField != null) {
            moveField.writeTo(data);
        } else {
            data.writeZero(OFMatch20.MINIMUM_LENGTH);
        }
    }

    @Override
    public String toBytesString() {
        String byteString =  super.toBytesString();
        byteString += HexString.toHex(direction) +
                        HexString.toHex(valueType) +
                        HexString.byteZeroEnd(6);

        if (valueType == 0) {
            byteString += HexString.toHex(moveValue) + HexString.byteZeroEnd(4);
        } else if (valueType == 1 && moveField != null) {
            byteString += moveField.toBytesString();
        } else {
            byteString += HexString.byteZeroEnd(OFMatch20.MINIMUM_LENGTH);
        }

        return byteString;
    }

    public String toString() {
        String string = super.toString();

        string += ";dir=" + ((direction == -1) ? 0 : direction) +
                        ";vt=" + ((valueType == -1) ? 0 : valueType);

        if (valueType == 0) {
            string += ";mv=" + moveValue;
        } else if (valueType == 1) {
            string += ";mf=" + moveField;
        } else {
            string += ";m=0";
        }

        return string;
    }

    public byte getDirection() {
        return direction;
    }

    public void setDirection(byte direction) {
        this.direction = direction;
    }

    public byte getValueType() {
        return valueType;
    }

    public void setValueType(byte valueType) {
        this.valueType = valueType;
    }

    public int getMoveValue() {
        return moveValue;
    }

    public void setMoveValue(int moveValue) {
        this.moveValue = moveValue;
    }

    public OFMatch20 getMoveField() {
        return moveField;
    }

    public void setMoveField(OFMatch20 moveField) {
        this.moveField = moveField;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + direction;
        result = prime * result
                + ((moveField == null) ? 0 : moveField.hashCode());
        result = prime * result + moveValue;
        result = prime * result + valueType;
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
        if (!(obj instanceof OFInstructionMovePacketOffset)) {
            return false;
        }
        OFInstructionMovePacketOffset other = (OFInstructionMovePacketOffset) obj;
        if (direction != other.direction) {
            return false;
        }
        if (moveField == null) {
            if (other.moveField != null) {
                return false;
            }
        } else if (!moveField.equals(other.moveField)) {
            return false;
        }
        if (moveValue != other.moveValue) {
            return false;
        }
        if (valueType != other.valueType) {
            return false;
        }
        return true;
    }

    @Override
    public OFInstructionMovePacketOffset clone() throws CloneNotSupportedException {
        OFInstructionMovePacketOffset ins = (OFInstructionMovePacketOffset) super.clone();

        if (moveField != null) {
            ins.setMoveField(moveField.clone());
        }

        return ins;
    }

}
