package com.api.ws.impala;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by DLRR
 */
@Service
public class ImpalaServiceImpl implements ImpalaService {
	private static Logger logger = Logger.getLogger(ImpalaServiceImpl.class);
	
    private static String jdbcDriverName = "com.cloudera.impala.jdbc41.Driver";
    private static String connectionUrl = "jdbc:impala://quickstart.cloudera:21050/";
    private static String sqlStatement = "SELECT * FROM my_database.my_numbers";
    
	@Override
	public void executeImpalaQuery() {

        logger.info("\n=============================================");
        logger.info("Cloudera Impala JDBC Example");
        logger.info("Using Connection URL: " + connectionUrl);
        logger.info("Running Query: " + sqlStatement);

        Connection con = null;
        try {
            Class.forName(jdbcDriverName);
            con = DriverManager.getConnection(connectionUrl);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStatement);
            logger.info("\n== Begin Query Results ======================");
            while (rs.next()) {
                logger.info(rs.getString(1) + " " + rs.getString(2));
            }
            logger.info("== End Query Results =======================\n\n");
        }catch (Exception e) {
        	logger.info("[Webservice Call] Error calling Impala: " + e.getLocalizedMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e) {
            	logger.info("[Webservice Call] Error calling Impala (closing bd): " + e.getLocalizedMessage());
            }
        }
	}
}




