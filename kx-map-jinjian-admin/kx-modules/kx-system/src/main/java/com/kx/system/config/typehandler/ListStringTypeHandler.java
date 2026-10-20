package com.kx.system.config.typehandler;

import com.kx.common.core.utils.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes({List.class})
public class ListStringTypeHandler implements TypeHandler<List<String>> {

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, List<String> strings, JdbcType jdbcType) throws SQLException {
        String hobbys = StringUtils.join(strings, ",");
        try {
            preparedStatement.setString(i, hobbys);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<String> getResult(ResultSet resultSet, String s) throws SQLException {
        String result = resultSet.getString(s);
        if(resultSet == null||StringUtils.isEmpty(result)){
            return null;
        }
        return Arrays.asList(resultSet.getString(s).split(","));
    }

    @Override
    public List<String> getResult(ResultSet resultSet, int i) throws SQLException {
        String result = resultSet.getString(i);
        if(resultSet == null||StringUtils.isEmpty(result)){
            return null;
        }
        return Arrays.asList(resultSet.getString(i).split(","));
    }

    @Override
    public List<String> getResult(CallableStatement callableStatement, int i) throws SQLException {
        String hobbys = callableStatement.getString(i);
        if(callableStatement == null||StringUtils.isEmpty(hobbys)){
            return null;
        }
        return Arrays.asList(hobbys.split(","));
    }

}
