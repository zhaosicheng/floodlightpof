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

package org.onosproject.floodlightpof.protocol;

import java.util.Arrays;

import org.jboss.netty.buffer.ChannelBuffer;
import org.onosproject.floodlightpof.util.HexString;
import org.onosproject.floodlightpof.util.U16;
import org.onosproject.floodlightpof.util.U32;
import org.onosproject.floodlightpof.util.U8;

/**
 * Modified by Song Jian (jack.songjian@huawei.com), Huawei Technologies Co., Ltd.
 *      Add INVALID_TTL in OFPacketInReason
 *      Add class member:
 *          tableId, cookie, deviceId
 *      Delete class member:
 *          inPort
 *
 *      Modify the set/get/readFrom/writeTo methods based on updated class members.
 */

/**
 * Represents an ofp_packet_in.
 *
 */
public class OFPacketIn extends OFMessage {
    public static short minimumLength = (short) (OFMessage.MINIMUM_LENGTH + 24); //32
    public static short maximalLength = (short) (OFPacketIn.minimumLength + OFGlobal
            .OFP_PACKET_IN_MAX_LENGTH); //2080;

    public enum OFPacketInReason {
        OFPR_NO_MATCH,
        OFPR_ACTION,
        OFPR_INVALID_TTL
    }

    protected int bufferId;
    protected short totalLength;
    protected OFPacketInReason reason;
    protected byte tableId;

    protected long cookie;

    protected int deviceId;
    protected int slotPortId;

    protected byte[] packetData;

    public OFPacketIn() {
        super();
        this.type = OFType.PACKET_IN;
        this.length = U16.t(minimumLength);
    }

    /**
     * Get buffer_id.
     * @return bufferId
     */
    public int getBufferId() {
        return this.bufferId;
    }

    /**
     * Set buffer_id.
     * @param buffErId
     */
    public OFPacketIn setBufferId(int buffErId) {
        this.bufferId = buffErId;
        return this;
    }

    /**
     * Returns the packet data.
     * @return packetData
     */
    public byte[] getPacketData() {
        return this.packetData;
    }

    /**
     * Sets the packet data, and updates the length of this message.
     * @param pacKetData
     */
    public OFPacketIn setPacketData(byte[] pacKetData) {
        this.packetData = pacKetData;
        this.length = U16.t(OFPacketIn.minimumLength + packetData.length);
        return this;
    }

//    /**
//     * Get in_port
//     * @return
//     */
//    public short getInPort() {
//        return 0;//this.inPort;
//    }
//
//    /**
//     * Set in_port
//     * @param inPort
//     */
//    public OFPacketIn setInPort(short inPort) {
//        this.inPort = inPort;
//        return this;
//    }

    /**
     * Get reason.
     * @return reason
     */
    public OFPacketInReason getReason() {
        return this.reason;
    }

    /**
     * Set reason.
     * @param reaSon
     */
    public OFPacketIn setReason(OFPacketInReason reaSon) {
        this.reason = reaSon;
        return this;
    }

    /**
     * Get total_len.
     * @return totalLength
     */
    public short getTotalLength() {
        return this.totalLength;
    }

    /**
     * Set total_len.
     * @param totalLenGth
     */
    public OFPacketIn setTotalLength(short totalLenGth) {
        this.totalLength = totalLenGth;
        return this;
    }

    public byte getTableId() {
        return tableId;
    }

    public void setTableId(byte tableId) {
        this.tableId = tableId;
    }

    public long getCookie() {
        return cookie;
    }

    public void setCookie(long cookie) {
        this.cookie = cookie;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getSlotPortId() {
        return slotPortId;
    }

    public void setSlotPortId(int slotPortId) {
        this.slotPortId = slotPortId;
    }

    public short getSlotId() {
        return (short) ((slotPortId & 0xffff0000) >> 16);
    }

    public void setSlotId(short slotId) {
        this.slotPortId = (slotPortId & 0x0000ffff) & (((int) slotId) << 16);
    }

    public short getPortId() {
        return (short) ((slotPortId & 0x0000ffff) >> 16);
    }

    public void setPortId(short portId) {
        this.slotPortId = (slotPortId & 0xffff0000) & portId;
    }

    @Override
    public void readFrom(ChannelBuffer data) {
        super.readFrom(data);
        this.bufferId = data.readInt();
        this.totalLength = data.readShort();
        this.reason = OFPacketInReason.values()[U8.f(data.readByte())];
        this.tableId = data.readByte();

        this.cookie = data.readLong();

        this.deviceId = data.readInt();
        this.slotPortId = data.readInt();

        this.packetData = new byte[totalLength];
        //this.packetData = new byte[OFGlobal.OFP_PACKET_IN_MAX_LENGTH];
        data.readBytes(this.packetData);
    }

    @Override
    public void writeTo(ChannelBuffer data) {
        super.writeTo(data);

        data.writeInt(bufferId);
        data.writeShort(totalLength);
        data.writeByte((byte) reason.ordinal());
        data.writeByte(tableId);

        data.writeLong(cookie);

        data.writeInt(deviceId);
        data.writeInt(slotPortId);

        if (null != packetData) {
            if (packetData.length < OFGlobal.OFP_PACKET_IN_MAX_LENGTH) {
                data.writeBytes(this.packetData);
                data.writeZero(OFGlobal.OFP_PACKET_IN_MAX_LENGTH - packetData.length);
            } else {
                data.writeBytes(this.packetData, 0, OFGlobal.OFP_PACKET_IN_MAX_LENGTH);
            }
        } else {
            data.writeZero(OFGlobal.OFP_PACKET_IN_MAX_LENGTH);
        }

    }


    public String toBytesString() {
        String string = super.toBytesString();

        string += HexString.toHex(bufferId);

        string += HexString.toHex(totalLength);
        string += HexString.toHex((byte) reason.ordinal());
        string += HexString.toHex(tableId);
        string += " ";

        string += HexString.toHex(cookie);

        string += HexString.toHex(deviceId);
        string += HexString.toHex(slotPortId);

        string += HexString.byteZeroEnd(4);

        string += HexString.toHex(packetData);

        if (null != packetData) {
            if (packetData.length < OFGlobal.OFP_PACKET_IN_MAX_LENGTH) {
                string += HexString.toHex(packetData);
                string += HexString.byteZero(OFGlobal.OFP_PACKET_IN_MAX_LENGTH - packetData.length);
            } else {
                string += HexString.toHex(packetData, 0, OFGlobal.OFP_PACKET_IN_MAX_LENGTH);
            }
        } else {
            string += HexString.byteZeroEnd(OFGlobal.OFP_PACKET_IN_MAX_LENGTH);
        }

        return string;
    }

    public String toString() {
        String myStr = super.toString();
        return myStr +
                "; packetIn:" +
                "bufferId=" + U32.f(this.bufferId) +
                ";tl=" + totalLength +
                ";rz=" + reason +
                ";tid=" + tableId +
                ";ck=" + cookie +
                ";did=" + deviceId +
                ";pid=" + HexString.toHex(slotPortId) +
                ";data=" + HexString.toHex(packetData);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + bufferId;
        result = prime * result + (int) (cookie ^ (cookie >>> 32));
        result = prime * result + deviceId;
        result = prime * result + Arrays.hashCode(packetData);
        result = prime * result + ((reason == null) ? 0 : reason.hashCode());
        result = prime * result + slotPortId;
        result = prime * result + tableId;
        result = prime * result + totalLength;
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
        if (!(obj instanceof OFPacketIn)) {
            return false;
        }
        OFPacketIn other = (OFPacketIn) obj;
        if (bufferId != other.bufferId) {
            return false;
        }
        if (cookie != other.cookie) {
            return false;
        }
        if (deviceId != other.deviceId) {
            return false;
        }
        if (!Arrays.equals(packetData, other.packetData)) {
            return false;
        }
        if (reason != other.reason) {
            return false;
        }
        if (slotPortId != other.slotPortId) {
            return false;
        }
        if (tableId != other.tableId) {
            return false;
        }
        if (totalLength != other.totalLength) {
            return false;
        }
        return true;
    }

}
