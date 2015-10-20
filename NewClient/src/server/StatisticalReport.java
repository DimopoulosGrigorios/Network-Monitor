/**
 * StatisticalReport.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package server;

public class StatisticalReport  implements java.io.Serializable {
    private java.lang.String[][] MIPreport;

    private java.lang.String[][] MPreport;

    private java.lang.String pcUUID;

    public StatisticalReport() {
    }

    public StatisticalReport(
           java.lang.String[][] MIPreport,
           java.lang.String[][] MPreport,
           java.lang.String pcUUID) {
           this.MIPreport = MIPreport;
           this.MPreport = MPreport;
           this.pcUUID = pcUUID;
    }


    /**
     * Gets the MIPreport value for this StatisticalReport.
     * 
     * @return MIPreport
     */
    public java.lang.String[][] getMIPreport() {
        return MIPreport;
    }


    /**
     * Sets the MIPreport value for this StatisticalReport.
     * 
     * @param MIPreport
     */
    public void setMIPreport(java.lang.String[][] MIPreport) {
        this.MIPreport = MIPreport;
    }

    public java.lang.String[] getMIPreport(int i) {
        return this.MIPreport[i];
    }

    public void setMIPreport(int i, java.lang.String[] _value) {
        this.MIPreport[i] = _value;
    }


    /**
     * Gets the MPreport value for this StatisticalReport.
     * 
     * @return MPreport
     */
    public java.lang.String[][] getMPreport() {
        return MPreport;
    }


    /**
     * Sets the MPreport value for this StatisticalReport.
     * 
     * @param MPreport
     */
    public void setMPreport(java.lang.String[][] MPreport) {
        this.MPreport = MPreport;
    }

    public java.lang.String[] getMPreport(int i) {
        return this.MPreport[i];
    }

    public void setMPreport(int i, java.lang.String[] _value) {
        this.MPreport[i] = _value;
    }


    /**
     * Gets the pcUUID value for this StatisticalReport.
     * 
     * @return pcUUID
     */
    public java.lang.String getPcUUID() {
        return pcUUID;
    }


    /**
     * Sets the pcUUID value for this StatisticalReport.
     * 
     * @param pcUUID
     */
    public void setPcUUID(java.lang.String pcUUID) {
        this.pcUUID = pcUUID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StatisticalReport)) return false;
        StatisticalReport other = (StatisticalReport) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.MIPreport==null && other.getMIPreport()==null) || 
             (this.MIPreport!=null &&
              java.util.Arrays.equals(this.MIPreport, other.getMIPreport()))) &&
            ((this.MPreport==null && other.getMPreport()==null) || 
             (this.MPreport!=null &&
              java.util.Arrays.equals(this.MPreport, other.getMPreport()))) &&
            ((this.pcUUID==null && other.getPcUUID()==null) || 
             (this.pcUUID!=null &&
              this.pcUUID.equals(other.getPcUUID())));
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
        if (getMIPreport() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMIPreport());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMIPreport(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMPreport() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMPreport());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMPreport(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPcUUID() != null) {
            _hashCode += getPcUUID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StatisticalReport.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server/", "statisticalReport"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MIPreport");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MIPreport"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://jaxb.dev.java.net/array", "stringArray"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MPreport");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MPreport"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://jaxb.dev.java.net/array", "stringArray"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pcUUID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pcUUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
