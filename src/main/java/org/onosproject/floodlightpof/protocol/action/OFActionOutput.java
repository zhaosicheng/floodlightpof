/**
*    Copyright (c) 2008 The Board of Trustees of The Leland Stanford Junior
*    University
*
*    Licensed under the Apache License, Version 2.0 (the "License"); you may
*    not use this file except in compliance with the License. You may obtain
*    a copy of the License at
*
*         http://www.apache.org/licenses/LICENSE-2.0
*
*    Unless required by applicable law or agreed to in writing, software
*    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
*    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
*    License for the specific language governing permissions and limitations
*    under the License.
**/

package org.onosproject.floodlightpof.protocol.action;


import org.jboss.netty.buffer.ChannelBuffer;
import org.onosproject.floodlightpof.protocol.OFMatch20;
import org.onosproject.floodlightpof.util.HexString;

/**
 * Modified by Song Jian (jack.songjian@huawei.com), Huawei Technologies Co., Ltd.
 * Modified based on POF white paper.
 *      portID using int instead short
 *      add metadata offset and length in case adding the metadata in front of the packet
 *      add packet offset in case cut some fields what are not needed
 *      delete maxlength
 */

public class OFActionOutput extends OFAction {
    public static final int MINIMUM_LENGTH = OFAction.MINIMUM_LENGTH + 8 + OFMatch20.MINIMUM_LENGTH;

    protected byte pordIdValueType;
    protected short metadataOffset;     //bit
    protected short metadataLength;     //bit
    protected short packetOffset;       //byte

    protected int portId;
    protected OFMatch20 portIdField;

    public OFActionOutput() {
        super.setType(OFActionType.OUTPUT);
        super.setLength((short) MINIMUM_LENGTH);
    }

    /**
     * Create an Output Action sending packets out the specified
     * OpenFlow port.
     *
     * This is the most common creation pattern for OFActions.
     *
     * @param portId
     */

//    public OFActionOutput(int portId) {
//        this(portId, 0xffffffff);
//    }

    /**
     * Create an Output Action specifying both the port AND
     * the snaplen of the packet to send out that port.
     * The length field is only meaningful when port == OFPort.OFPP_CONTROLLER
     * @param port
     * @param maxLength The maximum number of bytes of the packet to send.
     * Most hardware only supports this value for OFPP_CONTROLLER
     */

//    public OFActionOutput(int port, int maxLength) {
//        super();
//        super.setType(OFActionType.OUTPUT);
//        super.setLength((short) MINIMUM_LENGTH);
//        this.portId = port;
//    }

    /**
     * Get the output port.
     * @return portId
     */
    public int getPortId() {
        return this.portId;
    }

    /**
     * Set the output port.
     * @param portid
     */
    public OFActionOutput setPortId(int portid) {
        this.portId = portid;
        return this;
    }

    public String getPortIdDis0xHex() {
        return "0x" + Integer.toHexString(this.portId);
    }

    /*
    public boolean setPortId_From0xHexString(String portId_0xHexString){
        if(portId_0xHexString == null || !portId_0xHexString.matches(GUITools.RE_0xHEX)){
            return false;
        }
        portId = Integer.parseInt(portId_0xHexString.substring(2), 16);

        return true;
    }*/

    public OFMatch20 getPortIdField() {
        return portIdField;
    }

    public void setPortIdField(OFMatch20 portIdField) {
        this.portIdField = portIdField;
    }

    public byte getPordIdValueType() {
        return pordIdValueType;
    }

    public void setPordIdValueType(byte pordIdValueType) {
        this.pordIdValueType = pordIdValueType;
    }

    public short getMetadataOffset() {
        return metadataOffset;
    }

    public void setMetadataOffset(short metadataOffset) {
        this.metadataOffset = metadataOffset;
    }

    public short getMetadataLength() {
        return metadataLength;
    }

    public void setMetadataLength(short metadataLength) {
        this.metadataLength = metadataLength;
    }

    public short getPacketOffset() {
        return packetOffset;
    }

    public void setPacketOffset(short packetOffset) {
        this.packetOffset = packetOffset;
    }

    @Override
    public void readFrom(ChannelBuffer data) {
        super.readFrom(data);

        this.pordIdValueType = data.readByte();
        data.readBytes(1);
        this.metadataOffset = data.readShort();
        this.metadataLength = data.readShort();
        this.packetOffset = data.readShort();

        if (pordIdValueType == 0) {
            portId = data.readInt();
            data.readBytes(4);
            portIdField = null;
        } else if (pordIdValueType == 1) {
            portId = 0;
            portIdField = new OFMatch20();
            portIdField.readFrom(data);
        } else {
            portId = 0;
            portIdField = null;
            data.readBytes(OFMatch20.MINIMUM_LENGTH);
        }
    }

    @Override
    public void writeTo(ChannelBuffer data) {
        super.writeTo(data);

        data.writeByte(pordIdValueType);
        data.writeZero(1);
        data.writeShort(metadataOffset);
        data.writeShort(metadataLength);
        data.writeShort(packetOffset);

        if (pordIdValueType == 0) {
            data.writeInt(portId);
            data.writeZero(4);
        } else if (pordIdValueType == 1 && portIdField != null) {
            portIdField.writeTo(data);
        } else {
            data.writeZero(OFMatch20.MINIMUM_LENGTH);
        }
    }

    public String toBytesString() {
        String byteString =  super.toBytesString();
        byteString += HexString.toHex(pordIdValueType) +
                        HexString.byteZero(1) +
                        HexString.toHex(metadataOffset) +
                        HexString.toHex(metadataLength) +
                        HexString.toHex(packetOffset) +
                        " ";

        if (pordIdValueType == 0) {
            byteString += HexString.toHex(portId) + HexString.byteZeroEnd(4);
        } else if (pordIdValueType == 1 && portIdField != null) {
            byteString += portIdField.toBytesString();
        } else {
            byteString += HexString.byteZeroEnd(OFMatch20.MINIMUM_LENGTH);
        }

        return byteString;
    }

    public String toString() {
        String string = super.toString();

        string += ";pt=" + pordIdValueType +
                ";mos=" + metadataOffset +
                ";mlen=" + metadataLength +
                ";pos=" + packetOffset;

        if (pordIdValueType == 0) {
            string += ";pidv=" + portId;
        } else if (pordIdValueType == 1) {
            string += ";pidf=" + portIdField;
        } else {
            string += ";pid=0";
        }

        return string;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + metadataLength;
        result = prime * result + metadataOffset;
        result = prime * result + packetOffset;
        result = prime * result + pordIdValueType;
        result = prime * result + portId;
        result = prime * result
                + ((portIdField == null) ? 0 : portIdField.hashCode());
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
        if (!(obj instanceof OFActionOutput)) {
            return false;
        }
        OFActionOutput other = (OFActionOutput) obj;
        if (metadataLength != other.metadataLength) {
            return false;
        }
        if (metadataOffset != other.metadataOffset) {
            return false;
        }
        if (packetOffset != other.packetOffset) {
            return false;
        }
        if (pordIdValueType != other.pordIdValueType) {
            return false;
        }
        if (portId != other.portId) {
            return false;
        }
        if (portIdField == null) {
            if (other.portIdField != null) {
                return false;
            }
        } else if (!portIdField.equals(other.portIdField)) {
            return false;
        }
        return true;
    }

    @Override
    public OFActionOutput clone() throws CloneNotSupportedException {
        OFActionOutput action = (OFActionOutput) super.clone();
        if (null != portIdField) {
            action.setPortIdField(portIdField.clone());
        }
        return action;
    }
}
