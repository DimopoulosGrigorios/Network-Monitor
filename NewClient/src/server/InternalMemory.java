/**
 * InternalMemory.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package server;

public class InternalMemory  implements java.io.Serializable {
    private server.MaliciousPatterns malList;

    public InternalMemory() {
    }

    public InternalMemory(
           server.MaliciousPatterns malList) {
           this.malList = malList;
    }


    /**
     * Gets the malList value for this InternalMemory.
     * 
     * @return malList
     */
    public server.MaliciousPatterns getMalList() {
        return malList;
    }


    /**
     * Sets the malList value for this InternalMemory.
     * 
     * @param malList
     */
    public void setMalList(server.MaliciousPatterns malList) {
        this.malList = malList;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InternalMemory)) return false;
        InternalMemory other = (InternalMemory) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.malList==null && other.getMalList()==null) || 
             (this.malList!=null &&
              this.malList.equals(other.getMalList())));
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
        if (getMalList() != null) {
            _hashCode += getMalList().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InternalMemory.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server/", "internalMemory"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("malList");
        elemField.setXmlName(new javax.xml.namespace.QName("", "malList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server/", "maliciousPatterns"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
