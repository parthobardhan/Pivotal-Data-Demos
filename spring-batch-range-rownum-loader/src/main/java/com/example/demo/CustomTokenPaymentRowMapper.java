package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

public class CustomTokenPaymentRowMapper implements RowMapper<TokenPayment> {

	private static final Logger log = LoggerFactory.getLogger(CustomTokenPaymentRowMapper.class);

	@Override
	public TokenPayment mapRow(ResultSet rs, int rowNum) throws SQLException {

		TokenPayment tokenPayment = new TokenPayment();
		tokenPayment.setTokenRefId(rs.getLong("TOK_REF_ID"));
		tokenPayment.setTokenNumber(rs.getLong("TOK_NBR"));
		tokenPayment.setTokenExpMonthDate(rs.getString("TOK_EXP_MY_DT"));
		tokenPayment.setShardSvcId(rs.getLong("SHARD_SVC_ID"));
		tokenPayment.setTokenSeqNumber(rs.getString("TOK_SEQ_NBR"));
		tokenPayment.setTokenPanId(rs.getString("TOK_PAN_ID"));
		tokenPayment.setTokenId(rs.getString("TOK_ID"));
		tokenPayment.setTokenReuseCounter(rs.getShort("TOK_REUSE_CTR"));

		// log.info("Exiting row mapper");

		return tokenPayment;
	}

}
