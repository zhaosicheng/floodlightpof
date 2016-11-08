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

package org.onosproject.floodlightpof.protocol.statistics;


import org.codehaus.jackson.annotate.JsonIgnore;
import org.jboss.netty.buffer.ChannelBuffer;

/**
 * Represents an ofp_port_stats structure.
 */
public class OFPortStatisticsReply implements OFStatistics {
    protected short portNumber;
    protected long receivePackets;
    protected long transmitPackets;
    protected long receiveBytes;
    protected long transmitBytes;
    protected long receiveDropped;
    protected long transmitDropped;
    protected long receiveErrors;
    protected long transmitErrors;
    protected long receiveFrameErrors;
    protected long receiveOverrunErrors;
    protected long receiveCrcErrors;
    protected long collisions;

    /**
     * @return the portNumber
     */
    public short getPortNumber() {
        return portNumber;
    }

    /**
     * @param portNumber the portNumber to set
     */
    public void setPortNumber(short portNumber) {
        this.portNumber = portNumber;
    }

    /**
     * @return the receivePackets
     */
    public long getreceivePackets() {
        return receivePackets;
    }

    /**
     * @param receivePacKeTs the receivePackets to set
     */
    public void setreceivePackets(long receivePacKeTs) {
        this.receivePackets = receivePacKeTs;
    }

    /**
     * @return the transmitPackets
     */
    public long getTransmitPackets() {
        return transmitPackets;
    }

    /**
     * @param transmitPackets the transmitPackets to set
     */
    public void setTransmitPackets(long transmitPackets) {
        this.transmitPackets = transmitPackets;
    }

    /**
     * @return the receiveBytes
     */
    public long getReceiveBytes() {
        return receiveBytes;
    }

    /**
     * @param receiveBytes the receiveBytes to set
     */
    public void setReceiveBytes(long receiveBytes) {
        this.receiveBytes = receiveBytes;
    }

    /**
     * @return the transmitBytes
     */
    public long getTransmitBytes() {
        return transmitBytes;
    }

    /**
     * @param transmitBytes the transmitBytes to set
     */
    public void setTransmitBytes(long transmitBytes) {
        this.transmitBytes = transmitBytes;
    }

    /**
     * @return the receiveDropped
     */
    public long getReceiveDropped() {
        return receiveDropped;
    }

    /**
     * @param receiveDropped the receiveDropped to set
     */
    public void setReceiveDropped(long receiveDropped) {
        this.receiveDropped = receiveDropped;
    }

    /**
     * @return the transmitDropped
     */
    public long getTransmitDropped() {
        return transmitDropped;
    }

    /**
     * @param transmitDropped the transmitDropped to set
     */
    public void setTransmitDropped(long transmitDropped) {
        this.transmitDropped = transmitDropped;
    }

    /**
     * @return the receiveErrors
     */
    public long getreceiveErrors() {
        return receiveErrors;
    }

    /**
     * @param receiveErrOrs the receiveErrors to set
     */
    public void setreceiveErrors(long receiveErrOrs) {
        this.receiveErrors = receiveErrOrs;
    }

    /**
     * @return the transmitErrors
     */
    public long getTransmitErrors() {
        return transmitErrors;
    }

    /**
     * @param transmitErrors the transmitErrors to set
     */
    public void setTransmitErrors(long transmitErrors) {
        this.transmitErrors = transmitErrors;
    }

    /**
     * @return the receiveFrameErrors
     */
    public long getReceiveFrameErrors() {
        return receiveFrameErrors;
    }

    /**
     * @param receiveFrameErrors the receiveFrameErrors to set
     */
    public void setReceiveFrameErrors(long receiveFrameErrors) {
        this.receiveFrameErrors = receiveFrameErrors;
    }

    /**
     * @return the receiveOverrunErrors
     */
    public long getReceiveOverrunErrors() {
        return receiveOverrunErrors;
    }

    /**
     * @param receiveOverrunErrors the receiveOverrunErrors to set
     */
    public void setReceiveOverrunErrors(long receiveOverrunErrors) {
        this.receiveOverrunErrors = receiveOverrunErrors;
    }

    /**
     * @return the receiveCRCErrors
     */
    public long getReceiveCrcErrors() {
        return receiveCrcErrors;
    }

    /**
     * @param receiveCrcErrors the receiveCrcErrors to set
     */
    public void setReceiveCrcErrors(long receiveCrcErrors) {
        this.receiveCrcErrors = receiveCrcErrors;
    }

    /**
     * @return the collisions
     */
    public long getCollisions() {
        return collisions;
    }

    /**
     * @param collisions the collisions to set
     */
    public void setCollisions(long collisions) {
        this.collisions = collisions;
    }

    @Override
    @JsonIgnore
    public int getLength() {
        return 104;
    }

    @Override
    public void readFrom(ChannelBuffer data) {
        this.portNumber = data.readShort();
        data.readShort(); // pad
        data.readInt(); // pad
        this.receivePackets = data.readLong();
        this.transmitPackets = data.readLong();
        this.receiveBytes = data.readLong();
        this.transmitBytes = data.readLong();
        this.receiveDropped = data.readLong();
        this.transmitDropped = data.readLong();
        this.receiveErrors = data.readLong();
        this.transmitErrors = data.readLong();
        this.receiveFrameErrors = data.readLong();
        this.receiveOverrunErrors = data.readLong();
        this.receiveCrcErrors = data.readLong();
        this.collisions = data.readLong();
    }

    @Override
    public void writeTo(ChannelBuffer data) {
        data.writeShort(this.portNumber);
        data.writeShort((short) 0); // pad
        data.writeInt(0); // pad
        data.writeLong(this.receivePackets);
        data.writeLong(this.transmitPackets);
        data.writeLong(this.receiveBytes);
        data.writeLong(this.transmitBytes);
        data.writeLong(this.receiveDropped);
        data.writeLong(this.transmitDropped);
        data.writeLong(this.receiveErrors);
        data.writeLong(this.transmitErrors);
        data.writeLong(this.receiveFrameErrors);
        data.writeLong(this.receiveOverrunErrors);
        data.writeLong(this.receiveCrcErrors);
        data.writeLong(this.collisions);
    }

    @Override
    public int hashCode() {
        final int prime = 431;
        int result = 1;
        result = prime * result + (int) (collisions ^ (collisions >>> 32));
        result = prime * result + portNumber;
        result = prime * result
                + (int) (receivePackets ^ (receivePackets >>> 32));
        result = prime * result + (int) (receiveBytes ^ (receiveBytes >>> 32));
        result = prime * result
                + (int) (receiveCrcErrors ^ (receiveCrcErrors >>> 32));
        result = prime * result
                + (int) (receiveDropped ^ (receiveDropped >>> 32));
        result = prime * result
                + (int) (receiveFrameErrors ^ (receiveFrameErrors >>> 32));
        result = prime * result
                + (int) (receiveOverrunErrors ^ (receiveOverrunErrors >>> 32));
        result = prime * result
                + (int) (receiveErrors ^ (receiveErrors >>> 32));
        result = prime * result
                + (int) (transmitBytes ^ (transmitBytes >>> 32));
        result = prime * result
                + (int) (transmitDropped ^ (transmitDropped >>> 32));
        result = prime * result
                + (int) (transmitErrors ^ (transmitErrors >>> 32));
        result = prime * result
                + (int) (transmitPackets ^ (transmitPackets >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof OFPortStatisticsReply)) {
            return false;
        }
        OFPortStatisticsReply other = (OFPortStatisticsReply) obj;
        if (collisions != other.collisions) {
            return false;
        }
        if (portNumber != other.portNumber) {
            return false;
        }
        if (receivePackets != other.receivePackets) {
            return false;
        }
        if (receiveBytes != other.receiveBytes) {
            return false;
        }
        if (receiveCrcErrors != other.receiveCrcErrors) {
            return false;
        }
        if (receiveDropped != other.receiveDropped) {
            return false;
        }
        if (receiveFrameErrors != other.receiveFrameErrors) {
            return false;
        }
        if (receiveOverrunErrors != other.receiveOverrunErrors) {
            return false;
        }
        if (receiveErrors != other.receiveErrors) {
            return false;
        }
        if (transmitBytes != other.transmitBytes) {
            return false;
        }
        if (transmitDropped != other.transmitDropped) {
            return false;
        }
        if (transmitErrors != other.transmitErrors) {
            return false;
        }
        if (transmitPackets != other.transmitPackets) {
            return false;
        }
        return true;
    }
}
