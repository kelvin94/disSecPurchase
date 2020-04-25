package com.jyl.portfolio.commons.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GeneralUtil {
    private static Logger logger = LogManager.getLogger(GeneralUtil.class.getSimpleName());
    public static final String jianKuExchangename = "jianku_exchange";
    public static final String jiankuRoutingKey = "jianku";
    public static final String jianKuQueuename = "jiankuQueue";

    public static String convertToJson(Object obj) throws JsonProcessingException {

        if (obj != null) {
            ObjectMapper mapper = new ObjectMapper();
            logger.debug("## obj " + mapper.writeValueAsString(obj));
            return mapper.writeValueAsString(obj);
        }
        return "";
    }

    public static String getUrlRedisKey(Long seckillSwagId) {
        return "swagUrl:" + seckillSwagId;
    }

    public static String getSeckillOrderRedisKey(Long userPhone, Long seckillSwagId) {
        return userPhone + ":" + seckillSwagId;
    }
}
