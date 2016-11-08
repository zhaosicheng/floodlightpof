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
import org.onosproject.floodlightpof.protocol.OFMessage;
import org.onosproject.floodlightpof.util.HexString;

/**
 *
 */

public class OFInstructionConditionJmp extends OFInstruction {
    public static final int MINIMUM_LENGTH = OFMessage
            .MINIMUM_LENGTH + 8 + 2 * OFMatch20.MINIMUM_LENGTH + 3 * OFMatch20.MINIMUM_LENGTH;

    protected byte field2ValueType; //compare field2, 0 means to use field2_value, 1 means to use field2
    protected byte offset1Direction;    //jump direction. 0: forward; 1:backward
    protected byte offset1ValueType;
    protected byte offset2Direction;
    protected byte offset2ValueType;        //0 means to use value, 1 means to use field
    protected byte offset3Direction;
    protected byte offset3ValueType;

    protected OFMatch20 field1;          //compare field1 must be OFMatch20

    protected int field2Value;
    protected OFMatch20 field2;

    protected int offset1Value;     //if f1 < f2, jump ins number
    protected OFMatch20 offset1Field;

    protected int offset2Value;     //if f1 == f2, jump ins number
    protected OFMatch20 offset2Field;

    protected int offset3Value;     //if f2 > f2, jump ins number
    protected OFMatch20 offset3Field;

    public OFInstructionConditionJmp() {
        super.setType(OFInstructionType.CONDITIONAL_JMP);
        super.setLength((short) MINIMUM_LENGTH);
    }

    @Override
    public void readFrom(ChannelBuffer data) {
        super.readFrom(data);

        field2ValueType = data.readByte();
        offset1Direction = data.readByte();
        offset1ValueType = data.readByte();
        offset2Direction = data.readByte();
        offset2ValueType = data.readByte();
        offset3Direction = data.readByte();
        offset3ValueType = data.readByte();
        data.readBytes(1);


        field1 = new OFMatch20();
        field1.readFrom(data);

        if (field2ValueType == 0) {
            field2Value = data.readInt();
            data.readBytes(4);
            field2 = null;
        } else if (field2ValueType == 1) {
            field2Value = 0;
            field2 = new OFMatch20();
            field2.readFrom(data);
        } else {
            field2Value = 0;
            field2 = null;
            data.readBytes(OFMatch20.MINIMUM_LENGTH);
        }

        if (offset1ValueType == 0) {
            offset1Value = data.readInt();
            data.readBytes(4);
            offset1Field = null;
        } else if (offset1ValueType == 1) {
            offset1Value = 0;
            offset1Field = new OFMatch20();
            offset1Field.readFrom(data);
        } else {
            offset1Value = 0;
            offset1Field = null;
            data.readBytes(OFMatch20.MINIMUM_LENGTH);
        }

        if (offset2ValueType == 0) {
            offset2Value = data.readInt();
            data.readBytes(4);
            offset2Field = null;
        } else if (offset2ValueType == 1) {
            offset2Value = 0;
            offset2Field = new OFMatch20();
            offset2Field.readFrom(data);
        } else {
            offset2Value = 0;
            offset2Field = null;
            data.readBytes(OFMatch20.MINIMUM_LENGTH);
        }

        if (offset3ValueType == 0) {
            offset3Value = data.readInt();
            data.readBytes(4);
            offset3Field = null;
        } else if (offset3ValueType == 1) {
            offset3Value = 0;
            offset3Field = new OFMatch20();
            offset3Field.readFrom(data);
        } else {
            offset3Value = 0;
            offset3Field = null;
            data.readBytes(OFMatch20.MINIMUM_LENGTH);
        }
    }

    @Override
    public void writeTo(ChannelBuffer data) {
        super.writeTo(data);

        data.writeByte(field2ValueType);
        data.writeByte(offset1Direction);
        data.writeByte(offset1ValueType);
        data.writeByte(offset2Direction);
        data.writeByte(offset2ValueType);
        data.writeByte(offset3Direction);
        data.writeByte(offset3ValueType);
        data.writeZero(1);

        if (field1 != null) {
            field1.writeTo(data);
        } else {
            data.writeZero(OFMatch20.MINIMUM_LENGTH);
        }

        if (field2ValueType == 0) {
            data.writeInt(field2Value);
            data.writeZero(4);
        } else if (field2ValueType == 1 && field2 != null) {
            field2.writeTo(data);
        } else {
            data.writeZero(OFMatch20.MINIMUM_LENGTH);
        }

        if (offset1ValueType == 0) {
            data.writeInt(offset1Value);
            data.writeZero(4);
        } else if (offset1ValueType == 1 && offset1Field != null) {
            offset1Field.writeTo(data);
        } else {
            data.writeZero(OFMatch20.MINIMUM_LENGTH);
        }

        if (offset2ValueType == 0) {
            data.writeInt(offset2Value);
            data.writeZero(4);
        } else if (offset2ValueType == 1 && offset2Field != null) {
            offset2Field.writeTo(data);
        } else {
            data.writeZero(OFMatch20.MINIMUM_LENGTH);
        }

        if (offset3ValueType == 0) {
            data.writeInt(offset3Value);
            data.writeZero(4);
        } else if (offset3ValueType == 1 && offset3Field != null) {
            offset3Field.writeTo(data);
        } else {
            data.writeZero(OFMatch20.MINIMUM_LENGTH);
        }
    }

    @Override
    public String toBytesString() {
        String byteString =  super.toBytesString();

        byteString += HexString.toHex(field2ValueType) +
                        HexString.toHex(offset1Direction) +
                        HexString.toHex(offset1ValueType) +
                        HexString.toHex(offset2Direction) +
                        " " +
                        HexString.toHex(offset2ValueType) +
                        HexString.toHex(offset3Direction) +
                        HexString.toHex(offset3ValueType) +
                        HexString.byteZeroEnd(1);

        if (field1 != null) {
            byteString += field1.toBytesString();
        } else {
            byteString += HexString.byteZeroEnd(OFMatch20.MINIMUM_LENGTH);
        }

        if (field2ValueType == 0) {
            byteString += HexString.toHex(field2Value) + HexString.byteZeroEnd(4);
        } else if (field2ValueType == 1 && field2 != null) {
            byteString += field2.toBytesString();
        } else {
            byteString += HexString.byteZeroEnd(OFMatch20.MINIMUM_LENGTH);
        }

        if (offset1ValueType == 0) {
            byteString += HexString.toHex(offset1Value) + HexString.byteZeroEnd(4);
        } else if (offset1ValueType == 1 && offset1Field != null) {
            byteString += offset1Field.toBytesString();
        } else {
            byteString += HexString.byteZeroEnd(OFMatch20.MINIMUM_LENGTH);
        }

        if (offset2ValueType == 0) {
            byteString += HexString.toHex(offset2Value) + HexString.byteZeroEnd(4);
        } else if (offset2ValueType == 1 && offset2Field != null) {
            byteString += offset2Field.toBytesString();
        } else {
            byteString += HexString.byteZeroEnd(OFMatch20.MINIMUM_LENGTH);
        }

        if (offset3ValueType == 0) {
            byteString += HexString.toHex(offset3Value) + HexString.byteZeroEnd(4);
        } else if (offset3ValueType == 1 && offset3Field != null) {
            byteString += offset3Field.toBytesString();
        } else {
            byteString += HexString.byteZeroEnd(OFMatch20.MINIMUM_LENGTH);
        }

        return byteString;
    }

    @Override
    public String toString() {
        String string = super.toString();

        string += ";f2t=" + field2ValueType +
                    ";of1d=" + ((offset1Direction == -1) ? 0 : offset1Direction) + ";of1t="
                + ((offset1ValueType == -1) ? 0 : offset1ValueType) +
                    ";of2d=" + ((offset2Direction == -1) ? 0 : offset2Direction) + ";of2t="
                + ((offset2ValueType == -1) ? 0 : offset2ValueType) +
                    ";of3d=" + ((offset3Direction == -1) ? 0 : offset3Direction) + ";of3t="
                + ((offset3ValueType == -1) ? 0 : offset3ValueType);

        string += ";f1f=" + field1;

        if (field2ValueType == 0) {
            string += ";f2v=" + field2Value;
        } else if (field2ValueType == 1) {
            string += ";f2f=" + field2;
        } else {
            string += ";f2=0";
        }

        if (offset1ValueType == 0) {
            string += ";of1v=" + offset1Value;
        } else if (offset1ValueType == 1) {
            string += ";of1f=" + offset1Field;
        } else {
            string += ";of1=0";
        }

        if (offset2ValueType == 0) {
            string += ";of2v=" + offset2Value;
        } else if (offset2ValueType == 1) {
            string += ";of2f=" + offset2Field;
        } else {
            string += ";of2=0";
        }

        if (offset3ValueType == 0) {
            string += ";of3v=" + offset3Value;
        } else if (offset3ValueType == 1) {
            string += ";of3f=" + offset3Field;
        } else {
            string += ";of3=0";
        }

        return string;
    }

    public byte getField2ValueType() {
        return field2ValueType;
    }

    public void setField2ValueType(byte field2valueType) {
        this.field2ValueType = field2valueType;
    }

    public byte getOffset1Direction() {
        return offset1Direction;
    }

    public void setOffset1Direction(byte offset1direction) {
        this.offset1Direction = offset1direction;
    }

    public byte getOffset1ValueType() {
        return offset1ValueType;
    }

    public void setOffset1ValueType(byte offset1valueType) {
        this.offset1ValueType = offset1valueType;
    }

    public byte getOffset2Direction() {
        return offset2Direction;
    }

    public void setOffset2Direction(byte offset2direction) {
        this.offset2Direction = offset2direction;
    }

    public byte getOffset2ValueType() {
        return offset2ValueType;
    }

    public void setOffset2ValueType(byte offset2valueType) {
        this.offset2ValueType = offset2valueType;
    }

    public byte getOffset3Direction() {
        return offset3Direction;
    }

    public void setOffset3Direction(byte offset3direction) {
        this.offset3Direction = offset3direction;
    }

    public byte getOffset3ValueType() {
        return offset3ValueType;
    }

    public void setOffset3ValueType(byte offset3valueType) {
        this.offset3ValueType = offset3valueType;
    }

    public OFMatch20 getField1() {
        return field1;
    }

    public void setField1(OFMatch20 field1) {
        this.field1 = field1;
    }

    public int getField2Value() {
        return field2Value;
    }

    public void setField2Value(int field2value) {
        this.field2Value = field2value;
    }

    public OFMatch20 getField2() {
        return field2;
    }

    public void setField2(OFMatch20 field2) {
        this.field2 = field2;
    }

    public int getOffset1Value() {
        return offset1Value;
    }

    public void setOffset1Value(int offset1value) {
        this.offset1Value = offset1value;
    }

    public OFMatch20 getOffset1Field() {
        return offset1Field;
    }

    public void setOffset1Field(OFMatch20 offset1field) {
        this.offset1Field = offset1field;
    }

    public int getOffset2Value() {
        return offset2Value;
    }

    public void setOffset2Value(int offset2value) {
        this.offset2Value = offset2value;
    }

    public OFMatch20 getOffset2Field() {
        return offset2Field;
    }

    public void setOffset2Field(OFMatch20 offset2field) {
        this.offset2Field = offset2field;
    }

    public int getOffset3Value() {
        return offset3Value;
    }

    public void setOffset3Value(int offset3value) {
        this.offset3Value = offset3value;
    }

    public OFMatch20 getOffset3Field() {
        return offset3Field;
    }

    public void setOffset3Field(OFMatch20 offset3field) {
        this.offset3Field = offset3field;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((field1 == null) ? 0 : field1.hashCode());
        result = prime * result + ((field2 == null) ? 0 : field2.hashCode());
        result = prime * result + field2ValueType;
        result = prime * result + field2Value;
        result = prime * result + offset1Direction;
        result = prime * result
                + ((offset1Field == null) ? 0 : offset1Field.hashCode());
        result = prime * result + offset1ValueType;
        result = prime * result + offset1Value;
        result = prime * result + offset2Direction;
        result = prime * result
                + ((offset2Field == null) ? 0 : offset2Field.hashCode());
        result = prime * result + offset2ValueType;
        result = prime * result + offset2Value;
        result = prime * result + offset3Direction;
        result = prime * result
                + ((offset3Field == null) ? 0 : offset3Field.hashCode());
        result = prime * result + offset3ValueType;
        result = prime * result + offset3Value;
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
        if (!(obj instanceof OFInstructionConditionJmp)) {
            return false;
        }
        OFInstructionConditionJmp other = (OFInstructionConditionJmp) obj;
        if (field1 == null) {
            if (other.field1 != null) {
                return false;
            }
        } else if (!field1.equals(other.field1)) {
            return false;
        }
        if (field2 == null) {
            if (other.field2 != null) {
                return false;
            }
        } else if (!field2.equals(other.field2)) {
            return false;
        }
        if (field2ValueType != other.field2ValueType) {
            return false;
        }
        if (field2Value != other.field2Value) {
            return false;
        }
        if (offset1Direction != other.offset1Direction) {
            return false;
        }
        if (offset1Field == null) {
            if (other.offset1Field != null) {
                return false;
            }
        } else if (!offset1Field.equals(other.offset1Field)) {
            return false;
        }
        if (offset1ValueType != other.offset1ValueType) {
            return false;
        }
        if (offset1Value != other.offset1Value) {
            return false;
        }
        if (offset2Direction != other.offset2Direction) {
            return false;
        }
        if (offset2Field == null) {
            if (other.offset2Field != null) {
                return false;
            }
        } else if (!offset2Field.equals(other.offset2Field)) {
            return false;
        }
        if (offset2ValueType != other.offset2ValueType) {
            return false;
        }
        if (offset2Value != other.offset2Value) {
            return false;
        }
        if (offset3Direction != other.offset3Direction) {
            return false;
        }
        if (offset3Field == null) {
            if (other.offset3Field != null) {
                return false;
            }
        } else if (!offset3Field.equals(other.offset3Field)) {
            return false;
        }
        if (offset3ValueType != other.offset3ValueType) {
            return false;
        }
        if (offset3Value != other.offset3Value) {
            return false;
        }
        return true;
    }

    @Override
    public OFInstructionConditionJmp clone() throws CloneNotSupportedException {
        OFInstructionConditionJmp ins = (OFInstructionConditionJmp) super.clone();
        if (field1 != null) {
            ins.setField1(field1.clone());
        }

        if (field2 != null) {
            ins.setField2(field2.clone());
        }

        if (offset1Field != null) {
            ins.setOffset1Field(offset1Field.clone());
        }

        if (offset2Field != null) {
            ins.setOffset1Field(offset2Field.clone());
        }

        if (offset3Field != null) {
            ins.setOffset1Field(offset3Field.clone());
        }

        return ins;
    }

}
