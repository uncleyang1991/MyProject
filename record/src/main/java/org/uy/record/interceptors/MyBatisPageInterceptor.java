package org.uy.record.interceptors;

import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.uy.record.page.PageParameter;
import org.uy.record.page.PageResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class ,Integer.class}),
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class }) })
public class MyBatisPageInterceptor implements Interceptor {

    //线程变量
    private final ThreadLocal<PageParameter> threadLocal = new ThreadLocal<PageParameter>();

    //定义数据库类型常量
    private static String MYSQL = "mysql";
    private static String POSTGRE = "postgre";
    private static String ORACLE = "oracle";

    //数据库方言,默认为mysql
    private String dialect = MYSQL;

    public Object intercept(Invocation invocation) throws Throwable {
        if(invocation.getTarget() instanceof StatementHandler){
            StatementHandler statement = (StatementHandler)invocation.getTarget();
            MetaObject metaStatementHandler = SystemMetaObject.forObject(statement);
            MappedStatement mappedStatement = (MappedStatement)metaStatementHandler.getValue("delegate.mappedStatement");
            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
            PageParameter parameter;
            try{
                parameter = (PageParameter)metaStatementHandler.getValue("delegate.boundSql.parameterObject.pageParameter");
            }catch(BindingException be){
                parameter = null;
            }
            if(parameter==null){
                return invocation.proceed();
            }

            Connection connection;
            PreparedStatement pstm = null;
            ResultSet result = null;
            int totalRow = 0;
            try {
                connection = (Connection)invocation.getArgs()[0];
                pstm = connection.prepareStatement(buildCountSql(boundSql.getSql()));
                new DefaultParameterHandler(mappedStatement,boundSql.getParameterObject(),boundSql).setParameters(pstm);
                result = pstm.executeQuery();
                if(result.next()){
                    totalRow = result.getInt(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(pstm!=null){
                    pstm.close();
                }
                if(result!=null){
                    result.close();
                }
            }

            parameter.setTotalRow(totalRow);
            parameter.setTotalPage(totalRow%parameter.getPageSize()==0?totalRow/parameter.getPageSize():totalRow/parameter.getPageSize()+1);
            parameter.setPage(parameter.getPage()>parameter.getTotalPage()?parameter.getTotalPage():parameter.getPage());

            metaStatementHandler.setValue("delegate.boundSql.sql",bulidPageSql(boundSql.getSql(),parameter));

            threadLocal.set(parameter);
        }else if(invocation.getTarget() instanceof ResultSetHandler){
            PageParameter parameter = threadLocal.get();
            List list = (List)invocation.proceed();
            PageResult result;
            if(parameter!=null){
                result = new PageResult(list,parameter.getTotalPage(),parameter.getPage(),
                        parameter.getTotalRow(),parameter.getPageSize());
            }else{
                result = new PageResult(list);
            }

            return result;
        }
        return invocation.proceed();
    }

    private String buildCountSql(String sql){
        StringBuffer sb = new StringBuffer();
        if(dialect.equals(MYSQL)||dialect.equals(ORACLE)||dialect.equals(POSTGRE)){
            sb.append("select count(1) from (");
            sb.append(sql);
            sb.append(") uy");
        }
        return sb.toString();
    }

    private String bulidPageSql(String sql,PageParameter parameter){
        int pageSize = parameter.getPageSize();
        int page = parameter.getPage();
        StringBuffer sb = new StringBuffer();
        if(dialect.equals(MYSQL)){
            sb.append(sql);
            sb.append(" limit ").append((page-1)*pageSize).append(",").append(pageSize);
        }else if(dialect.equals(POSTGRE)){
            sb.append(sql);
            sb.append(" limit ").append(pageSize).append(" offset ").append((page-1)*pageSize);
        }else if(dialect.equals(ORACLE)){

        }
        return sb.toString();
    }

    public void setProperties(Properties properties) {
        this.dialect = properties.get("mybatis.dialect").toString();
    }

    public Object plugin(Object target) {
        if(target instanceof StatementHandler || target instanceof ResultSetHandler){
            return Plugin.wrap(target,this);
        }
        return target;
    }
}
