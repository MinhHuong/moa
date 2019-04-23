/*
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 	        http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the
 * License.  
 */
package com.yahoo.labs.samoa.instances;

import java.text.SimpleDateFormat;

/**
 * The Class InstanceImpl.
 *
 * @author abifet
 */
public class InstanceImpl implements MultiLabelInstance {

    /**
     * The weight.
     */
    protected double weight;

    /**
     * The instance data.
     */
    protected InstanceData instanceData;

    /** The original, unmasked data */
    protected InstanceData instanceOriginal;

    /**
     * The instance information.
     */
    protected InstancesHeader instanceHeader;

    /**
     * Instantiates a new instance.
     *
     * @param inst the inst
     */
    public InstanceImpl(InstanceImpl inst) {
        this.weight = inst.weight;
        this.instanceData = inst.instanceData.copy();
        this.instanceOriginal = inst.instanceOriginal.copy(); // make a copy
        this.instanceHeader = inst.instanceHeader;
    }

    //Dense
    /**
     * Instantiates a new instance.
     *
     * @param weight the weight
     * @param res the res
     */
    public InstanceImpl(double weight, double[] res) {
        this.weight = weight;
        this.instanceData = new DenseInstanceData(res);
        this.instanceOriginal = new DenseInstanceData(res); // make a copy
    }

    //Sparse
    /**
     * Instantiates a new instance.
     *
     * @param weight the weight
     * @param attributeValues the attribute values
     * @param indexValues the index values
     * @param numberAttributes the number attributes
     */
    public InstanceImpl(double weight, double[] attributeValues, int[] indexValues, int numberAttributes) {
        this.weight = weight;
        this.instanceData = new SparseInstanceData(attributeValues, indexValues, numberAttributes);
        this.instanceOriginal = new SparseInstanceData(attributeValues, indexValues, numberAttributes);
    }

    /**
     * Instantiates a new instance.
     *
     * @param weight the weight
     * @param instanceData the instance data
     */
    public InstanceImpl(double weight, InstanceData instanceData) {
        this.weight = weight;
        this.instanceData = instanceData;
        this.instanceOriginal = instanceData.copy(); // make a copy
    }

    /**
     * Instantiates a new instance.
     *
     * @param numAttributes the num attributes
     */
    public InstanceImpl(int numAttributes) {
        this.instanceData = new DenseInstanceData(new double[numAttributes]); //JD
        this.instanceOriginal = new DenseInstanceData(new double[numAttributes]); // make a copy
        this.weight = 1;
    }

    /**
     * Weight.
     *
     * @return the double
     */
    @Override
    public double weight() {
        return weight;
    }

    /**
     * Sets the weight.
     *
     * @param weight the new weight
     */
    @Override
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Attribute.
     *
     * @param instAttIndex the inst att index
     * @return the attribute
     */
    @Override
    public Attribute attribute(int instAttIndex) {
        return this.instanceHeader.attribute(instAttIndex);
    }

    public int indexOfAttribute(Attribute attribute){
        return this.instanceHeader.indexOf(attribute);
    }
    
    /**
     * Delete attribute at.
     *
     * @param i the i
     */
    @Override
    public void deleteAttributeAt(int i) {
        this.instanceData.deleteAttributeAt(i);
        this.instanceOriginal.deleteAttributeAt(i);
    }

    /**
     * Insert attribute at.
     *
     * @param i the i
     */
    @Override
    public void insertAttributeAt(int i) {
        this.instanceData.insertAttributeAt(i);
        this.instanceOriginal.insertAttributeAt(i);
    }

    /**
     * Num attributes.
     *
     * @return the int
     */
    @Override
    public int numAttributes() {
        return this.instanceData.numAttributes();
    }

    /**
     * Value.
     *
     * @param instAttIndex the inst att index
     * @return the double
     */
    @Override
    public double value(int instAttIndex) {
        return this.instanceData.value(instAttIndex);
    }

    /**
     * Checks if is missing.
     *
     * @param instAttIndex the inst att index
     * @return true, if is missing
     */
    @Override
    public boolean isMissing(int instAttIndex) {
        return this.instanceData.isMissing(instAttIndex) && this.instanceOriginal.isMissing(instAttIndex);
    }

    /**
     * Num values.
     *
     * @return the int
     */
    @Override
    public int numValues() {
        return this.instanceData.numValues();
    }

    /**
     * Index.
     *
     * @param i the i
     * @return the int
     */
    @Override
    public int index(int i) {
        return this.instanceData.index(i);
    }

    /**
     * Value sparse.
     *
     * @param i the i
     * @return the double
     */
    @Override
    public double valueSparse(int i) {
        return this.instanceData.valueSparse(i);
    }

    /**
     * Checks if is missing sparse.
     *
     * @param p the p
     * @return true, if is missing sparse
     */
    @Override
    public boolean isMissingSparse(int p) {
        return this.instanceData.isMissingSparse(p);
    }

    /**
     * Value.
     *
     * @param attribute the attribute
     * @return the double
     */
    @Override
    public double value(Attribute attribute) {
        int index = this.instanceHeader.indexOf(attribute);
        return value(index);
    }

    /**
     * String value.
     *
     * @param i the i
     * @return the string
     */
    @Override
    public String stringValue(int i) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * To double array.
     *
     * @return the double[]
     */
    @Override
    public double[] toDoubleArray() {
        return this.instanceData.toDoubleArray();
    }

    /**
     * Sets the value.
     *
     * @param numAttribute the num attribute
     * @param d the d
     */
    @Override
    public void setValue(int numAttribute, double d) {
        this.instanceData.setValue(numAttribute, d);
        this.instanceOriginal.setValue(numAttribute, d);
    }

    @Override
    public void setMaskedValue(int instAttIndex, double value) {
        this.instanceData.setValue(instAttIndex, Double.NaN);
        this.instanceOriginal.setValue(instAttIndex, value);
    }

    /**
     * Class value.
     *
     * @return the double
     */
    @Override
    public double classValue() {
        return this.instanceData.value(classIndex());
    }

    @Override
    public double maskedClassValue() {
        return this.instanceOriginal.value(classIndex());
    }

    /**
     * Class index.
     *
     * @return the int
     */
    @Override
    public int classIndex() {
        // TODO what to do if the instance header is null? --> (temporary) returns the last index
        if (instanceHeader == null) return instanceData.numAttributes() - 1;

        int classIndex = instanceHeader.classIndex();
        // return  ? classIndex : 0;
        if (classIndex == Integer.MAX_VALUE)
            if (this.instanceHeader.instanceInformation.range != null)
                classIndex = instanceHeader.instanceInformation.range.getStart();
            else
                classIndex = 0;

        return classIndex;
    }

    /**
     * Num classes.
     *
     * @return the int
     */
    @Override
    public int numClasses() {
        return this.instanceHeader.numClasses();
    }

    /**
     * Class is missing.
     *
     * @return true, if successful
     */
    @Override
    public boolean classIsMissing() {
        return this.instanceData.isMissing(classIndex());
    }

    @Override
    public boolean classIsMasked() {
        return this.isMasked(this.classIndex());
    }

    /**
     * Class attribute.
     *
     * @return the attribute
     */
    @Override
    public Attribute classAttribute() {
        return this.instanceHeader.attribute(classIndex());
    }

    /**
     * Sets the class value.
     *
     * @param d the new class value
     */
    @Override
    public void setClassValue(double d) {
        this.setValue(classIndex(), d);
        this.instanceOriginal.setValue(classIndex(), d);
    }

    @Override
    public void setMaskedClassValue(double d) {
        int classIndex = this.classIndex();
        this.instanceOriginal.setValue(classIndex, d);
        this.instanceData.setValue(classIndex, Double.NaN);
    }

    /**
     * Copy.
     *
     * @return the instance
     */
    @Override
    public Instance copy() {
        InstanceImpl inst = new InstanceImpl(this);
        return inst;
    }

    /**
     * Dataset.
     *
     * @return the instances
     */
    @Override
    public Instances dataset() {
        return this.instanceHeader;
    }

    /**
     * Sets the dataset.
     *
     * @param dataset the new dataset
     */
    @Override
    public void setDataset(Instances dataset) {
        if(dataset instanceof InstancesHeader) {
            this.instanceHeader = (InstancesHeader) dataset;
        }else {
            this.instanceHeader = new InstancesHeader(dataset);
        }
    }

    /**
     * Adds the sparse values.
     *
     * @param indexValues the index values
     * @param attributeValues the attribute values
     * @param numberAttributes the number attributes
     */
    @Override
    public void addSparseValues(int[] indexValues, double[] attributeValues, int numberAttributes) {
        this.instanceData = this.instanceOriginal =
                new SparseInstanceData(attributeValues, indexValues, numberAttributes); //???
    }

    /**
     * Text representation of a InstanceImpl.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int attIndex = 0; attIndex < this.numAttributes(); attIndex++) {
            if (this.isMissing(attIndex)) { // missing value in both origina & current data
                str.append("?");
            } else if (this.isMasked(attIndex)) { // not missing but masked --> "?(real value)"
                str.append("?(");
                writeAttributeValue(str, attIndex, true);
                str.append(")");
            } else { // not missing nor masked
                writeAttributeValue(str, attIndex, false);
            }

            if (attIndex != this.numAttributes() - 1) str.append(",");
        }

        return str.toString();
    }

    private void writeAttributeValue(StringBuilder str, int attIndex, boolean getMasked) {
        double value = getMasked ? this.instanceOriginal.value(attIndex) : this.instanceData.value(attIndex);
        if (this.attribute(attIndex).isNominal()) {
            // int valueIndex = (int) this.value(attIndex);
            int valueIndex = (int) value;
            String stringValue = this.attribute(attIndex).value(valueIndex);
            str.append(stringValue);
        } else if (this.attribute(attIndex).isNumeric()) {
            str.append(value);
            // str.append(this.value(attIndex));
        } else if (this.attribute(attIndex).isDate()) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            str.append(dateFormatter.format(value));
            // str.append(dateFormatter.format(this.value(attIndex)));
        }
    }

    @Override
    public int numInputAttributes() {
        return this.instanceHeader.numInputAttributes();
    }

    @Override
    public int numOutputAttributes() {
        return numberOutputTargets();
    }

    @Override
    public int numberOutputTargets() {
        return this.instanceHeader.numOutputAttributes();
    }

    @Override
    public double classValue(int instAttIndex) {
        return valueOutputAttribute(instAttIndex);
    }

    @Override
    public void setClassValue(int indexClass, double valueAttribute) {
        InstanceInformation instanceInformation = this.instanceHeader.getInstanceInformation();
        this.instanceData.setValue(instanceInformation.outputAttributeIndex(indexClass), valueAttribute);

    }

    @Override
    public Attribute outputAttribute(int outputIndex) {
        InstanceInformation instanceInformation = this.instanceHeader.getInstanceInformation();
        return instanceInformation.outputAttribute(outputIndex);
    }

    @Override
    public Attribute inputAttribute(int attributeIndex) {
        InstanceInformation instanceInformation = this.instanceHeader.getInstanceInformation();
        return instanceInformation.inputAttribute(attributeIndex);
    }

    @Override
    public double valueInputAttribute(int attributeIndex) {
        InstanceInformation instanceInformation = this.instanceHeader.getInstanceInformation();
        return this.instanceData.value(instanceInformation.inputAttributeIndex(attributeIndex));
    }

    @Override
    public double valueOutputAttribute(int attributeIndex) {
        InstanceInformation instanceInformation = this.instanceHeader.getInstanceInformation();
        return this.instanceData.value(instanceInformation.outputAttributeIndex(attributeIndex));
    }

    @Override
    public double getMaskedValue(int attributeIndex) {
        return this.instanceOriginal.value(attributeIndex);
    }

    @Override
    public void setMasked(int attributeIndex) {
        // TODO is it a bit redundant?
        this.instanceData.setValue(attributeIndex, Double.NaN);
    }

    @Override
    public boolean isMasked(int attributeIndex) {
        return (Double.isNaN(this.instanceData.value(attributeIndex)) &&
                !Double.isNaN(this.instanceOriginal.value(attributeIndex)));
    }

    @Override
    public void setMissing(int instAttIndex) {
        // this.setValue(instAttIndex, Double.NaN);
        this.instanceData.setValue(instAttIndex, Double.NaN);
        this.instanceOriginal.setValue(instAttIndex, Double.NaN);
    }

    @Override
    public void setMissing(Attribute attribute) {
        int index = this.instanceHeader.indexOf(attribute);
        this.setMissing(index);
    }

    @Override
    public boolean isMissing(Attribute attribute) {
        int index = this.instanceHeader.indexOf(attribute);
        return this.isMissing(index);
    }

    @Override
    public void setValue(Attribute attribute, double value) {
        int index = this.instanceHeader.indexOf(attribute);
        this.setValue(index, value);
    }
}
