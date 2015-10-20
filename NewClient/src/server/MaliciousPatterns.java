/**
 * MaliciousPatterns.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package server;

public class MaliciousPatterns  implements java.io.Serializable {
    private java.lang.String[] malIPs;

    private java.lang.String[] malPatterns;

    public MaliciousPatterns() {
    }

    public MaliciousPatterns(
           java.lang.String[] malIPs,
           java.lang.String[] malPatterns) {
           this.malIPs = malIPs;
           this.malPatterns = malPatterns;
    }


    /**
     * Gets the malIPs value for this MaliciousPatterns.
     * 
     * @return malIPs
     */
    public java.lang.String[] getMalIPs() {
        return malIPs;
    }


    /**
     * Sets the malIPs value for this MaliciousPatterns.
     * 
     * @param malIPs
     */
    public void setMalIPs(java.lang.String[] malIPs) {
        this.malIPs = malIPs;
    }

    public java.lang.String getMalIPs(int i) {
        return this.malIPs[i];
    }

    public void setMalIPs(int i, java.lang.String _value) {
        this.malIPs[i] = _value;
    }


    /**
     * Gets the malPatterns value for this MaliciousPatterns.
     * 
     * @return malPatterns
     */
    public java.lang.String[] getMalPatterns() {
        return malPatterns;
    }


    /**
     * Sets the malPatterns value for this MaliciousPatterns.
     * 
     * @param malPatterns
     */
    public void setMalPatterns(java.lang.String[] malPatterns) {
        this.malPatterns = malPatterns;
    }

    public java.lang.String getMalPatterns(int i) {
        return this.malPatterns[i];
    }

    public void setMalPatterns(int i, java.lang.String _value) {
        this.malPatterns[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MaliciousPatterns)) return false;
        MaliciousPatterns other = (MaliciousPatterns) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.malIPs==null && other.getMalIPs()==null) || 
             (this.malIPs!=null &&
              java.util.Arrays.equals(this.malIPs, other.getMalIPs()))) &&
            ((this.malPatterns==null && other.getMalPatterns()==null) || 
             (this.malPatterns!=null &&
              java.util.Arrays.equals(this.malPatterns, other.getMalPatterns())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getMalIPs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMalIPs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMalIPs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMalPatterns() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMalPatterns());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMalPatterns(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MaliciousPatterns.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server/", "maliciousPatterns"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("malIPs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "malIPs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("malPatterns");
        elemField.setXmlName(new javax.xml.namespace.QName("", "malPatterns"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
