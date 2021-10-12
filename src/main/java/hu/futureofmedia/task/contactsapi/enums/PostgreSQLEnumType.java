/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package hu.futureofmedia.task.contactsapi.enums;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;


public class PostgreSQLEnumType extends org.hibernate.type.EnumType {
 
    public void nullSafeSet(
            PreparedStatement st,
            Object value,
            int index,
            SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        st.setObject(
            index,
            value != null ?
                ((Enum) value).name() :
                null,
            Types.OTHER
        );
    }
}