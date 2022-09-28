package io.renren.modules.walk.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import io.renren.modules.walk.service.WalkDayService;
import lombok.extern.slf4j.Slf4j;

 
@Configuration       
@EnableScheduling    
@Slf4j
public class WalkGoldTask {
	
    private Logger logger= LoggerFactory.getLogger(WalkGoldTask.class);   

    @Autowired
    private WalkDayService walkDayService;
      
     
    /**
     * 每日步数兑换工银消费金
     */
    @Scheduled(cron = "${task.doGoldBudgetData.cron}")
    public void  doGoldBudgetData() {
         
        walkDayService.exchangeGoldBudget();

        logger.info("\n每日步数兑换工银消费金结束！");
    }
    
    
    
    
}
