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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.netty.buffer.ChannelBuffer;
import org.onosproject.floodlightpof.protocol.OFMessage;
import org.onosproject.floodlightpof.protocol.OFType;
import org.onosproject.floodlightpof.util.HexString;
/**
 * OFFlowTableResource describes the tables resource information of switch. It is sent from switch to controller.
 * <P>
 * OFFlowTableResource<br>
 * {<br>
 *      OFMessage header;<br>
 *      <br>
 *      int8        resourceType;<br>
 *      int8        reserve[3];<br>
 *      int32       counterNum;<br>
 *      <br>
 *      int32       meterNum;<br>
 *      int32       groupNum;<br>
 *      <br>
 *      {@link OFTableResource} tableResources[{@link OFTableType#MAX_TABLE_TYPE}];<br>
 * } //size() = 8 + 16 + sizeof(OFTableResource) * {@link OFTableType#MAX_TABLE_TYPE} = 88<br>
 *
 * @see OFTableResource
 *
 */
public class OFFlowTableResource extends OFMessage {
    public static int minimumLength = OFMessage.MINIMUM_LENGTH + 16;
    public static int maximalLength = OFFlowTableResource.minimumLength + OFTableResource
            .minimumLength * OFTableType.MAX_TABLE_TYPE;

    public enum OFResourceReportType {
        OFRRT_FLOW_TABLE
    }

    protected OFResourceReportType resourceType;

    protected short slotid;

    protected int counterNum;

    protected int meterNum;
    protected int groupNum;

    protected Map<OFTableType, OFTableResource> tableResourcesMap;

    public OFFlowTableResource() {
        super();
        super.setType(OFType.RESOURCE_REPORT);
        super.setLength((short) maximalLength);
    }

    public OFResourceReportType getResourceType() {
        return resourceType;
    }
    public void setResourceType(OFResourceReportType type) {
        this.resourceType = type;
    }

    public Map<OFTableType, OFTableResource> getTableResourcesMap() {
        return tableResourcesMap;
    }
    public void setTableResourcesMap(Map<OFTableType, OFTableResource> tableResourcesMap) {
        this.tableResourcesMap = tableResourcesMap;
    }

    public short getSlotid() {
        return slotid;
    }

    public void setSlotid(short slotid) {
        this.slotid = slotid;
    }

    public int getCounterNum() {
        return counterNum;
    }
    public void setCounterNum(int counterNum) {
        this.counterNum = counterNum;
    }
    public int getMeterNum() {
        return meterNum;
    }
    public void setMeterNum(int meterNum) {
        this.meterNum = meterNum;
    }
    public int getGroupNum() {
        return groupNum;
    }
    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    @Override
    public void readFrom(ChannelBuffer data) {
        super.readFrom(data);

        this.resourceType = OFResourceReportType.values()[ data.readByte() ];
        data.readBytes(1);

        this.slotid = data.readShort();

        this.counterNum = data.readInt();

        this.meterNum = data.readInt();
        this.groupNum = data.readInt();

        if (this.tableResourcesMap == null) {
            this.tableResourcesMap = new ConcurrentHashMap<OFTableType, OFTableResource>();
        } else {
            this.tableResourcesMap.clear();
        }
        OFTableResource tableResource;
        for (int i = 0; i < OFTableType.MAX_TABLE_TYPE; i++) {
            tableResource = new OFTableResource();
            tableResource.readFrom(data);
            if (tableResource.getTableType() != OFTableType.values()[i]) {
                throw new RuntimeException("TableResource[" + i + "] Type = " + OFTableType.values()[i] + " Error!");
            }
            this.tableResourcesMap.put(OFTableType.values()[i], tableResource);
        }
    }

    @Override
    public void writeTo(ChannelBuffer data) {
        super.writeTo(data);
        data.writeByte((byte) resourceType.ordinal());
        data.writeZero(1);

        data.writeShort(slotid);

        data.writeInt(counterNum);

        data.writeInt(meterNum);
        data.writeInt(groupNum);

        if (this.tableResourcesMap != null) {
            OFTableResource tableResource;
            for (int i = 0; i < OFTableType.MAX_TABLE_TYPE; i++) {
                tableResource = this.tableResourcesMap.get(OFTableType.values()[i]);

                if (tableResource == null) {
                    data.writeZero(OFTableResource.minimumLength);
                } else {
                    if (tableResource.getTableType() != OFTableType.values()[i]) {
                        throw new RuntimeException("TableResource[" + i + "] Type = " + OFTableType
                                .values()[i] + " Error!");
                    }
                    tableResource.writeTo(data);
                }
            }
        } else {
            data.writeZero(OFTableType.MAX_TABLE_TYPE * OFTableResource.minimumLength);
        }
    }

    @Override
    public String toBytesString() {
        String bytesString = super.toBytesString();

        bytesString += HexString.toHex((byte) resourceType.ordinal());
        bytesString += HexString.byteZeroEnd(1);

        bytesString += HexString.toHex(slotid);

        bytesString += HexString.toHex(counterNum);

        bytesString += HexString.toHex(meterNum);

        bytesString += HexString.toHex(groupNum);

        if (this.tableResourcesMap != null) {
            OFTableResource tableResource;
            for (int i = 0; i < OFTableType.MAX_TABLE_TYPE; i++) {
                tableResource = this.tableResourcesMap.get(OFTableType.values()[i]);

                if (tableResource == null) {
                    bytesString += HexString.byteZeroEnd(OFTableResource.minimumLength);
                } else {
                    if (tableResource.getTableType() != OFTableType.values()[i]) {
                        throw new RuntimeException("TableResource[" + i + "] Type = " + OFTableType
                                .values()[i] + " Error!");
                    }
                    bytesString += tableResource.toBytesString();
                }
            }
        } else {
            bytesString += HexString.byteZeroEnd(OFTableType.MAX_TABLE_TYPE * OFTableResource.minimumLength);
        }

        return bytesString;
    }

    @Override
    public String toString() {
        String string =  super.toString() +
                            "; FlowTableResource:" +
                            "rt=" + this.resourceType.ordinal() +
                            ";slotid=" + this.slotid +
                            ";cn=" + this.counterNum +
                            ";mn=" + this.meterNum +
                            ";gn=" + this.groupNum;
        if (this.tableResourcesMap != null) {
            OFTableResource tableResource;
            for (int i = 0; i < OFTableType.MAX_TABLE_TYPE; i++) {
                tableResource = this.tableResourcesMap.get(OFTableType.values()[i]);
                string += ";t[" + i + "]=" + ((tableResource == null) ? "null" : tableResource.toString());
            }
        } else {
            string += ";trd=null";
        }
        return string;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + counterNum;
        result = prime * result + groupNum;
        result = prime * result + meterNum;
        result = prime * result
                + ((resourceType == null) ? 0 : resourceType.hashCode());
        result = prime * result + slotid;
        result = prime * result
                + ((tableResourcesMap == null) ? 0 : tableResourcesMap
                        .hashCode());
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
        if (!(obj instanceof OFFlowTableResource)) {
            return false;
        }
        OFFlowTableResource other = (OFFlowTableResource) obj;
        if (counterNum != other.counterNum) {
            return false;
        }
        if (groupNum != other.groupNum) {
            return false;
        }
        if (meterNum != other.meterNum) {
            return false;
        }
        if (resourceType != other.resourceType) {
            return false;
        }
        if (slotid != other.slotid) {
            return false;
        }
        if (tableResourcesMap == null) {
            if (other.tableResourcesMap != null) {
                return false;
            }
        } else if (!tableResourcesMap.equals(other.tableResourcesMap)) {
            return false;
        }
        return true;
    }
}
