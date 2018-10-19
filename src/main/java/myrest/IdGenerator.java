package myrest;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import myrest.model.Job;
import myrest.model.Project;

public class IdGenerator implements IdentifierGenerator {


@Override
public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

    Connection connection = session.connection();
    try {
    	PreparedStatement ps;
    	if(object instanceof Project) {
    		ps = connection
                    .prepareStatement("SELECT MAX(id) as vlaue from public.project");
    	}else if(object instanceof Job) {
    		ps = connection
                    .prepareStatement("SELECT MAX(id) as vlaue from public.job");
    	}else
    		return null;
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int id = rs.getInt("vlaue") + 1;
            //String code = prefix + new Integer(id).toString();
            System.out.println("Generated Stock Code: " + id);      
            return id;
        }

    } catch (SQLException e) {       
        e.printStackTrace();
    }
    return null;
}

}
