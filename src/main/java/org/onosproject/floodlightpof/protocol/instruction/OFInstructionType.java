/**
 * modified by hdy
 */

package org.onosproject.floodlightpof.protocol.instruction;

import org.onosproject.floodlightpof.protocol.Instantiable;

import java.lang.reflect.Constructor;

/**
 * List of OpenFlow with POF Instruction types and mappings to wire protocol value and
 * derived classes.
 *
 */
public enum OFInstructionType {
    GOTO_TABLE(1, OFInstructionGotoTable.class, new OFInstructionGotoTableInstantiable()),
    WRITE_METADATA(2, OFInstructionWriteMetadata.class, new OFInstructionWriteMetadataInstantiable()),
    WRITE_ACTIONS(3, OFInstructionWriteActions.class, new OFInstructionWriteActionsInstantiable()),
    APPLY_ACTIONS(4, OFInstructionApplyActions.class, new OFInstructionApplyActionsInstantiable()),
    CLEAR_ACTIONS(5, OFInstructionClearActions.class, new OFInstructionClearActionsInstantiable()),
    METER(6, OFInstructionMeter.class, new OFInstructionMeterInstantiable()),
    WRITE_METADATA_FROM_PACKET(7, OFInstructionWriteMetadataFromPacket
            .class, new OFInstructionWriteMetadataFromPacketInstantiable()),
    GOTO_DIRECT_TABLE(8, OFInstructionGotoDirectTable.class, new OFInstructionGotoDirectTableInstantiable()),
    CONDITIONAL_JMP(9, OFInstructionConditionJmp.class, new OFInstructionConditionJmpInstantiable()),
    CALCULATE_FIELD(10, OFInstructionCalculateField.class, new OFInstructionCalculateFieldInstantiable()),
    MOVE_PACKET_OFFSET(11, OFInstructionMovePacketOffset.class, new OFInstructionMovePacketOffsetInstantiable()),
    EXPERIMENTER(0xffff, OFInstructionExperimenter.class, new OFInstructionExperimenterInstantiable());


    protected static OFInstructionType[] mapping;

    protected Class<? extends OFInstruction> clazz;
    protected Constructor<? extends OFInstruction> constructor;
    protected Instantiable<OFInstruction> instantiable;
    protected short type;

    /**
     * Store some information about the OpenFlow Instruction type, including wire
     * protocol type number, length, and class.
     *
     * @param type Wire protocol number associated with this OFInstruction
     * @param clazz The Java class corresponding to this type of OpenFlow Instruction
     * @param instantiable the instantiable for the OFInstruction this type represents
     */
    OFInstructionType(int type, Class<? extends OFInstruction> clazz, Instantiable<OFInstruction> instantiable) {
        this.type = (short) type;
        this.clazz = clazz;
        this.instantiable = instantiable;
        try {
            this.constructor = clazz.getConstructor(new Class[]{});
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failure getting constructor for class: " + clazz, e);
        }
        OFInstructionType.addMapping(this.type, this);
    }

    /**
     * Adds a mapping from type value to OFInstructionType enum.
     *
     * @param i OpenFlow wire protocol Instruction type value
     * @param t type
     */
    public static void addMapping(short i, OFInstructionType t) {
        if (mapping == null) {
            mapping = new OFInstructionType[16];
        }
        // bring higher mappings down to the edge of our array
        if (i < 0) {
            i = (short) (16 + i);
        }
        OFInstructionType.mapping[i] = t;
    }

    /**
     * Given a wire protocol OpenFlow type number, return the OFInstructionType associated
     * with it.
     *
     * @param i wire protocol number
     * @return OFInstructionType enum type
     */

    public static OFInstructionType valueOf(short i) {
        if (i < 0) {
            i = (short) (16 + i);
        }
        return OFInstructionType.mapping[i];
    }

    /**
     * @return Returns the wire protocol value corresponding to this
     *         OFInstructionType.
     */
    public short getTypeValue() {
        return this.type;
    }

    /**
     * @return return the OFInstruction subclass corresponding to this OFInstructionType
     */
    public Class<? extends OFInstruction> toClass() {
        return clazz;
    }

    /**
     * Returns the no-argument Constructor of the implementation class for
     * this OFInstructionType.
     * @return the constructor
     */
    public Constructor<? extends OFInstruction> getConstructor() {
        return constructor;
    }

    /**
     * Returns a new instance of the OFInstruction represented by this OFInstructionType.
     * @return the new object
     */
    public OFInstruction newInstance() {
        return instantiable.instantiate();
    }

    /**
     * @return the instantiable
     */
    public Instantiable<OFInstruction> getInstantiable() {
        return instantiable;
    }

    /**
     * @param instantiable the instantiable to set
     */
    public void setInstantiable(Instantiable<OFInstruction> instantiable) {
        this.instantiable = instantiable;
    }

    static class OFInstructionGotoTableInstantiable implements Instantiable<OFInstruction> {
        @Override
        public OFInstruction instantiate() {
            return new OFInstructionGotoTable();
        } }



    static class OFInstructionWriteMetadataInstantiable implements Instantiable<OFInstruction> {
        @Override
        public OFInstruction instantiate() {
            return new OFInstructionWriteMetadata();
        } }

    static class OFInstructionWriteActionsInstantiable implements Instantiable<OFInstruction> {
        @Override
        public OFInstruction instantiate() {
            return new OFInstructionWriteActions();
        } }
    static class OFInstructionApplyActionsInstantiable implements Instantiable<OFInstruction> {
        @Override
        public OFInstruction instantiate() {
            return new OFInstructionApplyActions();
        } }

    static class OFInstructionClearActionsInstantiable implements Instantiable<OFInstruction> {
        @Override
        public OFInstruction instantiate() {
            return new OFInstructionClearActions();
        } }

    static class OFInstructionMeterInstantiable implements Instantiable<OFInstruction> {
        @Override
        public OFInstruction instantiate() {
            return new OFInstructionMeter();
        } }

    static class OFInstructionWriteMetadataFromPacketInstantiable implements Instantiable<OFInstruction> {
        @Override
        public OFInstruction instantiate() {
            return new OFInstructionWriteMetadataFromPacket();
        } }

    static class OFInstructionGotoDirectTableInstantiable implements Instantiable<OFInstruction> {
        @Override
        public OFInstruction instantiate() {
            return new OFInstructionGotoDirectTable();
        } }
    static class OFInstructionConditionJmpInstantiable implements Instantiable<OFInstruction> {
        @Override
        public OFInstruction instantiate() {
            return new OFInstructionConditionJmp();
        } }
    static class OFInstructionCalculateFieldInstantiable implements Instantiable<OFInstruction> {
        @Override
        public OFInstruction instantiate() {
            return new OFInstructionCalculateField();
        } }
    static class OFInstructionMovePacketOffsetInstantiable implements Instantiable<OFInstruction> {
        @Override
        public OFInstruction instantiate() {
            return new OFInstructionMovePacketOffset();
        } }

    static class OFInstructionExperimenterInstantiable implements Instantiable<OFInstruction> {
        @Override
        public OFInstruction instantiate() {
            return new OFInstructionExperimenter();
        } }
}
