package egovframework.com.mip.mva.sp.config.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import egovframework.com.mip.mva.sp.comm.enums.MipErrorEnum;
import egovframework.com.mip.mva.sp.comm.exception.SpException;
import egovframework.com.mip.mva.sp.config.ConfigBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.h2.tools.Server;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.config.db
 * @FileName SpDataSource.java
 * @Author Min Gi Ju
 * @Date 2022. 6. 3.
 * @Description DataSource 관리 Controller
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * 2025.02.13,     박성완           표준프레임워크 v4.3 - Configuration Annotation 주석처리
 * </pre>
 */
//@Configuration
public class SpDataSource {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpDataSource.class);

	private final ApplicationContext applicationContext;

	/** 설정정보 */
	private final ConfigBean configBean;

	/**
	 * 생성자
	 * 
	 * @param applicationContext ApplicationContext
	 * @param configBean 설정정보
	 */
	SpDataSource(ApplicationContext applicationContext, ConfigBean configBean) {
		this.applicationContext = applicationContext;
		this.configBean = configBean;
	}

	/**
	 * DataSource 생성 및 Bean 등록
	 * 
	 * @MethodName dataSource
	 * @return DataSource
	 * @throws SpException
	 */
	@Bean
	DataSource dataSource() throws SpException {
		HikariConfig hikariConfig = new HikariConfig();

		HikariDataSource hikariDataSource = null;

		try {
			if (ObjectUtils.isEmpty(configBean.getVerifyConfig().getDb())) {
				try {
					Server.createTcpServer("-tcp", "-tcpAllowOthers").start();
				} catch (SQLException e) {
					throw new SpException(MipErrorEnum.SP_DB_ERROR, null, "Memory Database Create Error!");
				}

				hikariConfig.setDriverClassName("org.h2.Driver");
				hikariConfig.setJdbcUrl("jdbc:h2:mem:public");
				hikariConfig.setUsername("sa");
				hikariConfig.setPassword("");

				String sql = "";

				sql += "CREATE TABLE TB_TRX_INFO (";
				sql += "    TRXCODE CHARACTER VARYING(50) NOT NULL,";
				sql += "    SVC_CODE CHARACTER VARYING(50) NOT NULL,";
				sql += "    MODE CHARACTER VARYING(50) NOT NULL,";
				sql += "    NONCE CHARACTER VARYING(100) DEFAULT NULL,";
				sql += "    ZKP_NONCE CHARACTER VARYING(100) DEFAULT NULL,";
				sql += "    VP_VERIFY_RESULT CHARACTER VARYING(1) DEFAULT 'N' NOT NULL,";
				sql += "    VP CHARACTER LARGE OBJECT DEFAULT NULL,";
				sql += "    TRX_STS_CODE CHARACTER VARYING(4) DEFAULT '0001' NOT NULL,";
				sql += "    PROFILE_SEND_DT TIMESTAMP DEFAULT NULL,";
				sql += "    IMG_SEND_DT TIMESTAMP DEFAULT NULL,";
				sql += "    VP_RECEPT_DT TIMESTAMP DEFAULT NULL,";
				sql += "    ERROR_CN CHARACTER VARYING(4000) DEFAULT NULL,";
				sql += "    REG_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,";
				sql += "    UDT_DT TIMESTAMP DEFAULT NULL,";
				sql += "    PRIMARY KEY (TRXCODE)";
				sql += ")";

				Connection connection = null;

				try (HikariDataSource hikariDataSourceTemp = new HikariDataSource(hikariConfig)) {
					connection = hikariDataSourceTemp.getConnection();

					connection.prepareStatement(sql).execute();
				} catch (SQLException e) {
					throw new SpException(MipErrorEnum.SP_DB_ERROR, null, "Memory Database CREATE TABLE Error!");
				} finally {
					try {
						connection.close();
					} catch (SQLException e) {
						LOGGER.error("Memory Database CREATE TABLE Connection Close Error!");
					}
				}
			} else {
				hikariConfig.setDriverClassName(configBean.getVerifyConfig().getDb().getDriverClassName());
				hikariConfig.setJdbcUrl(configBean.getVerifyConfig().getDb().getUrl());
				hikariConfig.setUsername(configBean.getVerifyConfig().getDb().getUsername());
				hikariConfig.setPassword(configBean.getVerifyConfig().getDb().getPassword());
			}

			hikariDataSource = new HikariDataSource(hikariConfig);
		} catch (SpException e) {
			throw e;
		} catch (Exception e) {
			throw new SpException(MipErrorEnum.SP_DB_ERROR, null, "DataSource Error!");
		}

		return hikariDataSource;
	}

	/**
	 * DataSourceTransactionManager 생성 및 Bean 등록
	 * 
	 * @MethodName transactionManager
	 * @return DataSourceTransactionManager
	 * @throws SpException
	 */
	@Bean
	DataSourceTransactionManager transactionManager() throws SpException {
		DataSourceTransactionManager dataSourceTransactionManager = null;

		try {
			dataSourceTransactionManager = new DataSourceTransactionManager(dataSource());
		} catch (Exception e) {
			throw new SpException(MipErrorEnum.SP_DB_ERROR, null, "DataSourceTransactionManager Create Error!");
		}

		return dataSourceTransactionManager;
	}

	/**
	 * SqlSessionFactory 생성 및 Bean 등록
	 * 
	 * @MethodName sqlSessionFactory
	 * @param dataSource DataSource
	 * @return SqlSessionFactory
	 * @throws SpException
	 */
	@Bean
	SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws SpException {
		SqlSessionFactory sqlSessionFactory = null;

		try {
			SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

			sqlSessionFactoryBean.setDataSource(dataSource);
			sqlSessionFactoryBean.setTypeAliasesPackage("mip.mva.sp.**.vo");

			if (ObjectUtils.isEmpty(configBean.getVerifyConfig().getDb())) {
				sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/h2/*.xml"));
			} else {
				sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/" + configBean.getVerifyConfig().getDb().getProvider() + "/*.xml"));
			}

			org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();

			configuration.setMapUnderscoreToCamelCase(true);
			configuration.setJdbcTypeForNull(JdbcType.VARCHAR);

			sqlSessionFactoryBean.setConfiguration(configuration);

			sqlSessionFactory = sqlSessionFactoryBean.getObject();
		} catch (IOException e) {
			throw new SpException(MipErrorEnum.SP_DB_ERROR, null, "MapperFile Load Error!");
		} catch (Exception e) {
			throw new SpException(MipErrorEnum.SP_DB_ERROR, null, "SqlSessionFactory Create Error!");
		}

		return sqlSessionFactory;
	}

	/**
	 * SqlSessionTemplate 생성 및 Bean 등록
	 * 
	 * @MethodName sqlSession
	 * @param sqlSessionFactory SqlSessionFactory
	 * @return SqlSessionTemplate
	 * @throws SpException
	 */
	@Bean
	SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) throws SpException {
		SqlSessionTemplate sqlSessionTemplate = null;

		try {
			sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
		} catch (Exception e) {
			throw new SpException(MipErrorEnum.SP_DB_ERROR, null, "SqlSessionTemplate Create Error!");
		}

		return sqlSessionTemplate;
	}

}
