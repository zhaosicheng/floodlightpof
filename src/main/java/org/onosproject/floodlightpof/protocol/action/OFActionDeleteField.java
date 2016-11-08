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

package org.onosproject.floodlightpof.protocol.action;

import org.jboss.netty.buffer.ChannelBuffer;
import org.onosproject.floodlightpof.protocol.OFMatch20;
import org.onosproject.floodlightpof.util.HexString;

/**
 * Delete a field with field position and length.
 *
 *
 */
public class OFActionDeleteField extends OFAction {
    public static final int MINIMUM_LENGTH = OFAction.MINIMUM_LENGTH + 8 + OFMatch20.MINIMUM_LENGTH;

    protected short tagPosition;    //bit
    protected byte tagLengthValueType;

    protected int tagLengthValue;   //bit
    protected OFMatch20 tagLengthField;

    public OFActionDeleteField() {
        super.setType(OFActionType.DELETE_FIELD);
        super.setLength((short) MINIMUM_LENGTH);
    }

    public void readFrom(ChannelBuffer data) {
        super.readFrom(data);

        this.tagPosition = data.readShort();
        this.tagLengthValueType = data.readByte();
        data.readBytes(5);

        if (tagLengthValueType == 0) {
            tagLengthValue = data.readInt();
            data.readBytes(4);
            tagLengthField = null;
        } else if (tagLengthValueType == 1) {
            tagLengthValue = 0;
            tagLengthField = new OFMatch20();
            tagLengthField.readFrom(data);
        } else {
            tagLengthValue = 0;
            tagLengthField = null;
            data.readBytes(OFMatch20.MINIMUM_LENGTH);
        }
    }

    public void writeTo(ChannelBuffer data) {
        super.writeTo(data);

        data.writeShort(tagPosition);
        data.writeByte(tagLengthValueType);
        data.writeZero(5);

        if (tagLengthValueType == 0) {
            data.writeInt(tagLengthValue);
            data.writeZero(4);
        } else if (tagLengthValueType == 1 && tagLengthField != null) {
            tagLengthField.writeTo(data);
        } else {
            data.writeZero(OFMatch20.MINIMUM_LENGTH);
        }
    }

    public String toBytesString() {
        String byteString =  super.toBytesString();
        byteString += HexString.toHex(tagPosition) +
                            HexString.toHex(tagLengthValueType) +
                            HexString.byteZeroEnd(5);

        if (tagLengthValueType == 0) {
            byteString += HexString.toHex(tagLengthValue) + HexString.byteZeroEnd(4);
        } else if (tagLengthValueType == 1 && tagLengthField != null) {
            byteString += tagLengthField.toBytesString();
        } else {
            byteString += HexString.byteZeroEnd(OFMatch20.MINIMUM_LENGTH);
        }

        return byteString;
    }

    public String toString() {
        String string =  super.toString();
        string += ";fpos=" + tagPosition +
                    ";flent=" + tagLengthValueType;

        if (tagLengthValueType == 0) {
            string += ";flenv=" + tagLengthValue;
        } else if (tagLengthValueType == 1) {
            string += ";flenf=" + tagLengthField;
        } else {
            string += ";flen=0";
        }

        return string;
    }

    public short getTagPosition() {
        return tagPosition;
    }

    public void setTagPosition(short tagPosition) {
        this.tagPosition = tagPosition;
    }

    public byte getTagLengthValueType() {
        return tagLengthValueType;
    }

    public void setTagLengthValueType(byte tagLengthValueType) {
        this.tagLengthValueType = tagLengthValueType;
    }

    public int getTagLengthValue() {
        return tagLengthValue;
    }

    public void setTagLengthValue(int tagLengthValue) {
        this.tagLengthValue = tagLengthValue;
    }

    public OFMatch20 getTagLengthField() {
        return tagLengthField;
    }

    public void setTagLengthField(OFMatch20 tagLengthField) {
        this.tagLengthField = tagLengthField;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((tagLengthField == null) ? 0 : tagLengthField.hashCode());
        result = prime * result + tagLengthValue;
        result = prime * result + tagLengthValueType;
        result = prime * result + tagPosition;
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
        if (!(obj instanceof OFActionDeleteField)) {
            return false;
        }
        OFActionDeleteField other = (OFActionDeleteField) obj;
        if (tagLengthField == null) {
            if (other.tagLengthField != null) {
                return false;
            }
        } else if (!tagLengthField.equals(other.tagLengthField)) {
            return false;
        }
        if (tagLengthValue != other.tagLengthValue) {
            return false;
        }
        if (tagLengthValueType != other.tagLengthValueType) {
            return false;
        }
        if (tagPosition != other.tagPosition) {
            return false;
        }
        return true;
    }

    @Override
    public OFActionDeleteField clone() throws CloneNotSupportedException {
        OFActionDeleteField action = (OFActionDeleteField) super.clone();
        if (null != tagLengthField) {
            action.setTagLengthField(tagLengthField.clone());
        }
        return action;
    }
}
