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

import java.util.ArrayList;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;
import org.onosproject.floodlightpof.protocol.factory.OFInstructionFactory;
import org.onosproject.floodlightpof.protocol.factory.OFInstructionFactoryAware;
import org.onosproject.floodlightpof.protocol.instruction.OFInstruction;
import org.onosproject.floodlightpof.protocol.table.OFTableType;
import org.onosproject.floodlightpof.util.HexString;
import org.onosproject.floodlightpof.util.U16;

/**
 * Modified by Song Jian (jack.songjian@huawei.com), Huawei Technologies Co., Ltd.
 *      Modify the OFPFC_ command to enum
 *      Delete class member:
 *          actionFactory, match, bufferId, outPort, flags, actions
 *      Add class member:
 *          matchFieldNum, instructionNum, counterId, cookieMask, tableId,
 *          tableType, index(flowId), matchList, instructionList, instructionFactory
 *      Modify class member:
 *          short command is changed to byte command
 *
 *      Modify the set/get/writeTo/readFrom methods based on updated class member
 */

/**
 * Represents an ofp_flow_mod message.
 *
 */
public class OFFlowMod extends OFMessage implements OFInstructionFactoryAware, Cloneable {
    public static final int MINIMUM_LENGTH = OFMessage.MINIMUM_LENGTH + 40;  //48
    public static final int MAXIMAL_LENGTH = OFMessage.MINIMUM_LENGTH
                                                + 40
                                                + OFMatchX.MINIMUM_LENGTH * OFGlobal.OFP_MAX_MATCH_FIELD_NUM
                                                + OFInstruction.MAXIMAL_LENGTH * OFGlobal.OFP_MAX_INSTRUCTION_NUM;

    public enum OFFlowEntryCmd {
        OFPFC_ADD,                  /* New flow. */
        OFPFC_MODIFY,               /* Modify all matching flows. */
        OFPFC_MODIFY_STRICT,        /* Modify entry strictly matching wildcards */
        OFPFC_DELETE,               /* Delete all matching flows. */
        OFPFC_DELETE_STRICT         /* Strictly match wildcards and priority. */
    }

    // Open Flow Flow Mod Flags. Use "or" operation to set multiple flags
    public static final short OFPFF_SEND_FLOW_REM = 0x1; // 1 << 0
    public static final short OFPFF_CHECK_OVERLAP = 0x2; // 1 << 1
    public static final short OFPFF_EMERG         = 0x4; // 1 << 2

    protected byte command;
    protected byte matchFieldNum;
    protected byte instructionNum;
    protected int counterId;

    protected long cookie;
    protected long cookieMask;

    protected byte tableId;
    protected OFTableType tableType;
    protected short idleTimeout;
    protected short hardTimeout;
    protected short priority;

    protected int index;

    protected List<OFMatchX> matchList;
    protected List<OFInstruction> instructionList;

    protected OFInstructionFactory instructionFactory;

    public OFFlowMod() {
        super();
        this.type = OFType.FLOW_MOD;
        this.length = U16.t(MINIMUM_LENGTH);
    }

    public int getLengthU() {
        return MAXIMAL_LENGTH;
    }

    /**
     * Get cookie.
     * @return cookie
     */
    public long getCookie() {
        return this.cookie;
    }

    /**
     * Set cookie.
     * @param cooKie
     */
    public OFFlowMod setCookie(long cooKie) {
        this.cookie = cooKie;
        return this;
    }

    /**
     * Get command.
     * @return command
     */
    public byte getCommand() {
        return this.command;
    }

    /**
     * Set command.
     * @param commAnd
     */
    public OFFlowMod setCommand(byte commAnd) {
        this.command = commAnd;
        return this;
    }

    /**
     * Get hard_timeout.
     * @return hardTimeout
     */
    public short getHardTimeout() {
        return this.hardTimeout;
    }

    /**
     * Set hard_timeout.
     * @param hardTimeOut
     */
    public OFFlowMod setHardTimeout(short hardTimeOut) {
        this.hardTimeout = hardTimeOut;
        return this;
    }

    /**
     * Get idle_timeout.
     * @return idleTimeout
     */
    public short getIdleTimeout() {
        return this.idleTimeout;
    }

    /**
     * Set idle_timeout.
     * @param idleTimeOut
     */
    public OFFlowMod setIdleTimeout(short idleTimeOut) {
        this.idleTimeout = idleTimeOut;
        return this;
    }

    /**
     * Get priority.
     * @return priority
     */
    public short getPriority() {
        return this.priority;
    }

    /**
     * Set priority.
     * @param pRioRiTy
     */
    public OFFlowMod setPriority(short pRioRiTy) {
        this.priority = pRioRiTy;
        return this;
    }



    public byte getMatchFieldNum() {
        return matchFieldNum;
    }

    public void setMatchFieldNum(byte matchFieldNum) {
        this.matchFieldNum = matchFieldNum;
    }

    public byte getInstructionNum() {
        return instructionNum;
    }

    public void setInstructionNum(byte instructionNum) {
        this.instructionNum = instructionNum;
    }

    public int getCounterId() {
        return counterId;
    }

    public void setCounterId(int counterId) {
        this.counterId = counterId;
    }

    public long getCookieMask() {
        return cookieMask;
    }

    public void setCookieMask(long cookieMask) {
        this.cookieMask = cookieMask;
    }

    public byte getTableId() {
        return tableId;
    }

    public void setTableId(byte tableId) {
        this.tableId = tableId;
    }

    public List<OFMatchX> getMatchList() {
        return matchList;
    }

    public List<OFInstruction> getInstructionList() {
        return instructionList;
    }

    public void setInstructionList(List<OFInstruction> instructionList) {
        this.instructionList = instructionList;
    }

    public OFTableType getTableType() {
        return tableType;
    }

    public void setTableType(OFTableType tableType) {
        this.tableType = tableType;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public OFInstructionFactory getInstructionFactory() {
        return instructionFactory;
    }

    public void setMatchList(List<OFMatchX> matchList) {
        this.matchList = matchList;
    }

    @Override
    public void readFrom(ChannelBuffer data) {
        super.readFrom(data);

        this.command = data.readByte();
        this.matchFieldNum = data.readByte();
        this.instructionNum = data.readByte();
        data.readByte();
        this.counterId = data.readInt();

        this.cookie = data.readLong();
        this.cookieMask = data.readLong();

        this.tableId = data.readByte();
        this.tableType = OFTableType.values()[ data.readByte() ];
        this.idleTimeout = data.readShort();
        this.hardTimeout = data.readShort();
        this.priority = data.readShort();

        this.index = data.readInt();
        data.readBytes(4);

        if (this.matchList == null) {
            this.matchList = new ArrayList<OFMatchX>();
        } else {
            this.matchList.clear();
        }
        OFMatchX matchX;
        for (int i = 0; i < OFGlobal.OFP_MAX_MATCH_FIELD_NUM; i++) {
            matchX = new OFMatchX();
            matchX.readFrom(data);
            this.matchList.add(matchX);
        }

        if (this.instructionFactory == null) {
            throw new RuntimeException("OFInstructionFactory not set");
        }
        this.instructionList = this.instructionFactory.parseInstructions(data, OFGlobal
                .OFP_MAX_INSTRUCTION_NUM * OFInstruction.MAXIMAL_LENGTH);
    }

    @Override
    public void writeTo(ChannelBuffer data) {
        super.writeTo(data);

        data.writeByte(this.command);
        data.writeByte(this.matchFieldNum);
        data.writeByte(this.instructionNum);
        data.writeZero(1);
        data.writeInt(this.counterId);

        data.writeLong(this.cookie);
        data.writeLong(this.cookieMask);

        data.writeByte(this.tableId);
        data.writeByte(this.tableType.getValue());
        data.writeShort(idleTimeout);
        data.writeShort(hardTimeout);
        data.writeShort(priority);

        data.writeInt(index);
        data.writeZero(4);

        if (this.matchList == null) {
            data.writeZero(OFGlobal.OFP_MAX_MATCH_FIELD_NUM * OFMatchX.MINIMUM_LENGTH);
        } else {
            OFMatchX matchX;

            if (matchFieldNum > matchList.size()) {
                throw new RuntimeException("matchFieldNum " + matchFieldNum + " > matchList.size()" + matchList.size());
            }

            int i = 0;
            for (; i < matchFieldNum && i < OFGlobal.OFP_MAX_MATCH_FIELD_NUM; i++) {
                matchX = matchList.get(i);
                if (matchX == null) {
                    data.writeZero(OFMatchX.MINIMUM_LENGTH);
                } else {
                    matchX.writeTo(data);
                }
            }
            if (i < OFGlobal.OFP_MAX_MATCH_FIELD_NUM) {
                data.writeZero((OFGlobal.OFP_MAX_MATCH_FIELD_NUM - i) * OFMatchX.MINIMUM_LENGTH);
            }
        }

        if (this.instructionList == null) {
            data.writeZero(OFGlobal.OFP_MAX_INSTRUCTION_NUM * OFInstruction.MAXIMAL_LENGTH);
        } else {
            OFInstruction instruction;

            if (instructionNum > instructionList.size()) {
                throw new RuntimeException("instructionNum "
                        + instructionNum + " > instructionList.size()" + instructionList.size());
            }

            int i = 0;
            for (; (i < instructionNum) && (i < OFGlobal.OFP_MAX_INSTRUCTION_NUM); i++) {
                instruction = instructionList.get(i);
                if (instruction == null) {
                    data.writeZero(OFInstruction.MAXIMAL_LENGTH);
                } else {
                    instruction.writeTo(data);
                    if (instruction.getLength() < OFInstruction.MAXIMAL_LENGTH) {
                        data.writeZero(OFInstruction.MAXIMAL_LENGTH - instruction.getLength());
                    }
                }
            }
            if (i < OFGlobal.OFP_MAX_INSTRUCTION_NUM) {
                data.writeZero((OFGlobal.OFP_MAX_INSTRUCTION_NUM - i) * OFInstruction.MAXIMAL_LENGTH);
            }
        }
    }

    @Override
    public String toBytesString() {
        String string = super.toBytesString();

        string += HexString.toHex(command);
        string += HexString.toHex(matchFieldNum);
        string += HexString.toHex(instructionNum);
        string += HexString.byteZeroEnd(1);

        string += HexString.toHex(counterId);

        string += HexString.toHex(cookie);

        string += HexString.toHex(cookieMask);

        string += HexString.toHex(tableId);
        string += HexString.toHex(tableType.getValue());
        string += HexString.toHex(idleTimeout);
        string += " ";

        string += HexString.toHex(hardTimeout);
        string += HexString.toHex(priority);
        string += " ";

        string += HexString.toHex(index);

        string += HexString.byteZeroEnd(4);

        if (this.matchList == null) {
            string += HexString.byteZeroEnd(OFGlobal.OFP_MAX_MATCH_FIELD_NUM * OFMatchX.MINIMUM_LENGTH);
        } else {
            OFMatchX matchX;

            if (matchFieldNum > matchList.size()) {
                throw new RuntimeException("matchFieldNum " + matchFieldNum + " > matchList.size()" + matchList.size());
            }

            int i = 0;
            for (; i < matchFieldNum && i < OFGlobal.OFP_MAX_MATCH_FIELD_NUM; i++) {
                matchX = matchList.get(i);
                if (matchX == null) {
                    string += HexString.byteZeroEnd(OFMatchX.MINIMUM_LENGTH);
                } else {
                    string += matchX.toBytesString();
                }
            }
            if (i < OFGlobal.OFP_MAX_MATCH_FIELD_NUM) {
                string += HexString.byteZeroEnd((OFGlobal.OFP_MAX_MATCH_FIELD_NUM - i) * OFMatchX.MINIMUM_LENGTH);
            }
        }

        if (this.instructionList == null) {
            string += HexString.byteZeroEnd(OFGlobal.OFP_MAX_INSTRUCTION_NUM * OFInstruction.MAXIMAL_LENGTH);
        } else {
            OFInstruction instruction;

            if (instructionNum > instructionList.size()) {
                throw new RuntimeException("instructionNum "
                        + instructionNum + " > instructionList.size()" + instructionList.size());
            }

            int i = 0;
            for (; (i < instructionNum) && (i < OFGlobal.OFP_MAX_INSTRUCTION_NUM); i++) {
                instruction = instructionList.get(i);
                if (instruction == null) {
                    string += HexString.byteZeroEnd(OFInstruction.MAXIMAL_LENGTH);
                } else {
                    string += instruction.toBytesString();
                    if (instruction.getLength() < OFInstruction.MAXIMAL_LENGTH) {
                        string += HexString.byteZeroEnd(OFInstruction.MAXIMAL_LENGTH - instruction.getLength());
                    }
                }
            }
            if (i < OFGlobal.OFP_MAX_INSTRUCTION_NUM) {
                string += HexString.byteZeroEnd((OFGlobal.OFP_MAX_INSTRUCTION_NUM - i) * OFInstruction.MAXIMAL_LENGTH);
            }
        }

        return string;

    }

    @Override
    public String toString() {
        String string = super.toString();
        string += "; FlowEntry:" +
                    "cmd=" + command +
                    ";mfn=" + matchFieldNum +
                    ";isn=" + instructionNum +
                    ";cid=" + counterId +
                    ";ck=" + cookie +
                    ";ckm=" + cookieMask +
                    ";tid=" + tableId +
                    ";tt=" + tableType +
                    ";it=" + idleTimeout +
                    ";ht=" + hardTimeout +
                    ";p=" + priority +
                    ";i=" + index;
        if (this.matchList != null) {
            string += ";match(" + matchList.size() + ")=";
            for (OFMatchX match : matchList) {
                string += match.toString() + ",";
            }
        } else {
            string += ";match=null";
        }

        if (this.instructionList != null) {
            string += ";ins(" + instructionList.size() + ")=";
            for (OFInstruction ins : instructionList) {
                string += ins.toString() + ",";
            }
        } else {
            string += ";ins=null";
        }

        return string;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + command;
        result = prime * result + (int) (cookie ^ (cookie >>> 32));
        result = prime * result + (int) (cookieMask ^ (cookieMask >>> 32));
        result = prime * result + counterId;
        result = prime * result + hardTimeout;
        result = prime * result + idleTimeout;
        result = prime * result + index;
        result = prime * result + ((instructionFactory == null) ? 0 : instructionFactory.hashCode());
        result = prime * result + ((instructionList == null) ? 0 : instructionList.hashCode());
        result = prime * result + instructionNum;
        result = prime * result + matchFieldNum;
        result = prime * result + ((matchList == null) ? 0 : matchList.hashCode());
        result = prime * result + priority;
        result = prime * result + tableId;
        result = prime * result + ((tableType == null) ? 0 : tableType.hashCode());
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        OFFlowMod other = (OFFlowMod) obj;
        if (command != other.command) {
            return false;
        }
        if (cookie != other.cookie) {
            return false;
        }
        if (cookieMask != other.cookieMask) {
            return false;
        }
        if (counterId != other.counterId) {
            return false;
        }
        if (hardTimeout != other.hardTimeout) {
            return false;
        }
        if (idleTimeout != other.idleTimeout) {
            return false;
        }
        if (index != other.index) {
            return false;
        }
        if (instructionFactory == null) {
            if (other.instructionFactory != null) {
                return false;
            }
        } else if (!instructionFactory.equals(other.instructionFactory)) {
            return false;
        }
        if (instructionList == null) {
            if (other.instructionList != null) {
                return false;
            }
        } else if (!instructionList.equals(other.instructionList)) {
            return false;
        }
        if (instructionNum != other.instructionNum) {
            return false;
        }
        if (matchFieldNum != other.matchFieldNum) {
            return false;
        }
        if (matchList == null) {
            if (other.matchList != null) {
                return false;
            }
        } else if (!matchList.equals(other.matchList)) {
            return false;
        }
        if (priority != other.priority) {
            return false;
        }
        if (tableId != other.tableId) {
            return false;
        }
        if (tableType != other.tableType) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public OFFlowMod clone() throws CloneNotSupportedException {
        OFFlowMod flowMod = (OFFlowMod) super.clone();

        if (null != matchList
                && 0 != matchList.size()
                && 0 != matchFieldNum) {
            List<OFMatchX> neoMatchXList = new ArrayList<OFMatchX>();
            for (OFMatchX matchX: this.matchList) {
                neoMatchXList.add((OFMatchX) matchX.clone());
            }
            flowMod.setMatchList(neoMatchXList);
        }

        if (null != instructionList
                && 0 != instructionList.size()
                && 0 != instructionNum) {
            List<OFInstruction> neoInstructionList = new ArrayList<OFInstruction>();
            for (OFInstruction ins: this.instructionList) {
                neoInstructionList.add((OFInstruction) ins.clone());
            }
            flowMod.setInstructionList(neoInstructionList);
        }

        return flowMod;
    }

    @Override
    public void setInstructionFactory(OFInstructionFactory instructionFactory) {
        this.instructionFactory = instructionFactory;
    }
}
