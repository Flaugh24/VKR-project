package org.barmaley.vkr.generator;

/**
 * Created by Flugh on 16.04.2017.
 */
import java.io.Serializable;
import java.sql.*;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class TicketIdGenerator implements IdentifierGenerator{
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Connection connection = session.connection();

        try {
            java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
            calendar.setTime(new java.util.Date());
            Integer year = calendar.get(java.util.Calendar.YEAR);

            Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery("select count(*) from Ticket");


            if(rs.next())
            {
                int id=rs.getInt(1)+1;
                String generatedId = "V" + year%100 + "-" + new Integer(id).toString();
                System.out.println("Generated Id: " + generatedId);
                return generatedId;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return null;
    }
 }
